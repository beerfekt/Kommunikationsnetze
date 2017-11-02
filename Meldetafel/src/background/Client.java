package background;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	private Socket clientsocket;
	
	
//Konstruktor öffnet socket
	
	public Client (String serverip, int port) {		
		try {
			clientsocket = new Socket( serverip, port );
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Server nicht erreichbartim");
			e.printStackTrace();
			clientsocket = null;
		}//Try,catch socket
		
	}//Konstruktor
	
	
	
//Daten senden
	
	public void send (String message) {		
		
			OutputStream send;
			try {
				send = clientsocket.getOutputStream();
				send.write(message.getBytes());
				send.flush();	
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}//sendMessage
	
	
//Socket schließen
	
		public void closeSocket(){
			try {
				this.clientsocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}//closeSocket


}//Client
