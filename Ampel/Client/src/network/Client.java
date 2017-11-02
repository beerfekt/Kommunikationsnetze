package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

import anwendung.Gui;


public class Client {
	
	//clientsocket
	private DatagramSocket socket ;	
	//destination
	private InetAddress    server ;
	private int            port;	
	//Gui
	private Gui            gui;
	
	
//CONSTRUCTOR
	
	public Client (String serverAddress, int port, Gui gui) throws UnknownHostException, IOException {
			InetAddress address = InetAddress.getByName(serverAddress);
			//prüfung ob addresse funktioniert
			if ( address.isReachable( 2000 ) ){
				System.out.println("Host:" + address + "    ist erreichbar");
				createClient(address, port, gui);
			}//if
	}//Client construktor1
	
	
	//Client Objekt + Socket erstellen
	private void createClient(InetAddress serverAddress, int port, Gui gui){
		this.port = port;  //laborserver		
		this.server = serverAddress;
		this.gui    = gui;		
		try {
			socket = new DatagramSocket();
			socket.setReceiveBufferSize(32);
		} catch (SocketException e) {
			gui.feedback("Kein Verbindungsaufbau...", "red");
			System.out.println("Clientkonstruktor: Keine Verbindung möglich  ");
			e.printStackTrace();
		}//try	
	}//createClient constructor continue2
	
	
	
	
	
	
	
//Methods
		
	
//SENDEN
	
	//String
	public boolean send ( String message ) {
		//vorbereiten - packet
		byte[] letters = stb(message) ;
		DatagramPacket packet = new DatagramPacket(letters ,letters.length, server, port);	
		
		//senden
		try {
			
			socket.send(packet);
			System.out.println("SEND_METHOD: sende daten: " + message);
			socket.setSoTimeout(2000);																		//TIMEOUT
		} catch (IOException e) {
			System.out.println("SEND_METHOD: Packet konnte nicht versand werden!   ");
			e.printStackTrace();
			return false;
		}//try
		
		//ACK
		if (receive().equals("FE,0000")){
			gui.feedback("Verbunden -" + "\n" + " String in Ordnung", "green");
			return true;
		} else {
			gui.feedback(handleError(message), "red");
			return false;
		}//if

	}//send	
	
	
	
	
//EMPFANGEN
	
	public String receive () {
		//vorbereiten - packet
		byte[] letters = new byte[32];
		DatagramPacket packet = new DatagramPacket(letters, letters.length);
		//erhalten
		try {
			socket.receive(packet);
			String answer = new String(packet.getData(), 0, packet.getLength());
			System.out.println("Receive_Method: Daten bekommen: " + answer);
			return answer.trim();
		} catch (IOException e) {
			gui.feedback("Packet konnte nicht versand werden!", "red");
			System.out.println("Receive_Method: Packet konnte nicht versand werden!   ");
			e.printStackTrace();
			return "";
		}//try
	}//receive
	
	
	
	
	//Fehlererkennung
	
	private String handleError(String message) {		
		switch(message){
			case "FE0001":
				return " FE0001  - \n Fehlerhafter String  \n an den Server";
			case "FE0002":
				return " FE0002  - \n Es sind noch Zeichen \n hinter dem Fragezeichen gesendet worden";
			case "FE0003":
				return " FE0003  - \n Fehlerhafter String  \n an den Server";
			case "FE9999":
				return " FE9999  - \n Server angehalten";
			default:
				return " Timeout - \n Try to connect!";
		}//switch		
	}//handleError
	
	
	
		
//Verbunden?
	
	public boolean isConnected(){
		if (this.socket == null) return false;
		return true;
	}//isConnected
	
	
	
//Datatype Conversion
	
	//int -> String
	public  String ts(int n){
		return String.valueOf(n);
	}//ToString
	
	//String-> ByteArray
	public byte[] stb (String string){
		return string.getBytes(Charset.forName("US-ASCII"));         //Charset.forName("US-ASCII")
	}//StringtoByte
	
	//Byte -> String
	public String bts(byte[] bytearray){
		return new String(bytearray, Charset.forName("US-ASCII"));    //Charset.forName("US-ASCII") 
	}//byteToSTring
	
	
	
//Commands - Coding
	
	//codieren (Ausgang)
	//int -> String 
	public String encode (int state) {
		switch (state){
			case 1: 
				return "DA,00001001";
			case 2: 
				return "DA,00001010";
			case 3: 
				return "DA,00001100";
			case 4: 
				return "DA,00001010";
			case 5: 
				return "DA,00001001";
			case 6: 
				return "DA,00010001";
			default:
				return "DA,00000000";
		}//switch		
	}//encode
	
	
	
	//decodieren (Eingang)
	//String -> int
	public int decode (String received, int state) {
		//Vorbereiten: Leerzeichen raus
		received = received.trim();		
		System.out.println("              im RECEIVED DECODE angekommen");
		if (received.substring(0,3).equals("DE,")) {
			String value = received.substring(6);
			value = value.trim();
			System.out.println(               "state ist: -" + state + "-");
			System.out.println("              value ist: -" + value + "-");
			if (state == 2 && value.equals("01010")) return 2;
			if (state == 3 && value.equals("01100")) return 3; 
			if (state == 4 && value.equals("01010")) return 4; 			
			if (state == 5 && value.equals("01001")) return 5; 
			if (state == 6 && value.equals("10001")) return 6; 			
			if (state == 1 && value.equals("01001")) return 1;
		}//if
		System.out.println("              Keine Bedingung erfüllt - Ausgabe : " + 1);
		return 1;
	}//decode	
	
	
	

	
}//Client
