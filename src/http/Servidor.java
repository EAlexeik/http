package http;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	public static final int PUERTO=5000;
	ServerSocket servers;
	Socket cliente;
	InputStream is;
	InetAddress direccion;
	
	public Servidor(String host) throws IOException{
		
		servers=new ServerSocket(PUERTO,10,host);
		
	}
}
