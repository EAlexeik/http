package http;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	public static final int PUERTO=8350;
	private ServerSocket servidor;
	private Socket cliente;
	private DataInputStream entrada;
	private static DataInputStream fichero;
	private DataOutputStream salida;
	private static FileInputStream f;
	
	public Servidor() throws IOException{
		servidor=new ServerSocket(PUERTO);
	}
	
	public boolean Conectar(){
		try {
			cliente=servidor.accept();
			entrada=new DataInputStream(cliente.getInputStream());
			salida=new DataOutputStream(cliente.getOutputStream());
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public String RecibirDatos(){
		String aux;
		try {
			aux=entrada.readLine();
			return aux;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void EnviarDatos(String datos){
		try {
			salida.writeUTF(datos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void Desconectar(){
		try {
			salida.close();
			entrada.close();
			cliente.close();
			servidor.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) throws IOException{
		Servidor nuevo=new Servidor();
		PaqueteHTTP ph;
		String direccion="";
		String cadena;
		String html="";
		System.out.println("Creacion");
		nuevo.Conectar();
		System.out.println("Conexion");
		String aux=nuevo.RecibirDatos();
		ph=new PaqueteHTTP(aux);
		if(ph.estaPalabra()){
			cadena=ph.obtenerDireccion();
			direccion=cadena.replaceAll("/", "");
			if(direccion.isEmpty())
				direccion="index.html";
			f=new FileInputStream(direccion);
			fichero=new DataInputStream(f);
			 while((aux=fichero.readLine())!=null) {
		          html+=aux+"\n";
		    }
			System.out.println(html);
			cadena=ph.obtenerCabecera(html.length());
			System.out.println(cadena+html);
			nuevo.EnviarDatos(cadena+"\r\n\n"+html);
		}
		else
			nuevo.EnviarDatos("Not Found\n");
		System.out.println(direccion);
		System.out.println("Paso");
		nuevo.Desconectar();
	}
}