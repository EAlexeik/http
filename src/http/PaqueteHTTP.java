package http;

public class PaqueteHTTP {
	String cadena;
	String cabeceraE="HTTP/1.1 200 OK\r\nContent-Type: text/html\nContent-Length:";
	public PaqueteHTTP(String dato){
		cadena=dato;
	}
	public boolean estaPalabra(){
		CharSequence buscar="POST";
		return cadena.contains(buscar);
	}
	public String obtenerDireccion(){
		cadena=cadena.replaceAll("POST", "");
		cadena=cadena.replaceAll("HTTP/1.1", "");
		cadena=cadena.trim();
		return cadena;
	}
	public String obtenerCabecera(long tamanio){
		cabeceraE+=tamanio;
		return cabeceraE;
	}
	
}
