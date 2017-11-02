package server;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import gui.Gui;


public class AServerThread extends Thread {

//	private String name;
//	private DatagramSocket socket;
	
	DatagramSocket socket = null;
	int state;
	Gui gui;

//Constructors	
	//default - ruft 2. konstruktor auf
	public AServerThread(Gui gui, int port) throws IOException{
		this("AServerThread", port);	//gibt an parameterkonstruktor weiter (eigentlich nur für parameterlose konstr. gedacht)
		this.gui = gui;
	}//default constructor
	
	//2.Konstruktor (parameter)  öffnet ein datagramsocket
	public AServerThread (String name, int port) throws IOException  {
		super(name); 										  //gibt dem Thread namen? kommt aus dem standartkonstruktor
		this.socket = new DatagramSocket(port); 	  //öffnet socket
		this.state = 0;
	}//constructor 2
	
	
	
	
	//THREAD RUN METHODE
	public void run(){
		System.out.println("Hello from the AServerThread");
		gui.setRun();
		try {
			waitForPacket(socket);
		} catch (IOException e) {
			System.out.println("run(): cant wait for packet");
			e.printStackTrace();
		}								  //wartet auf packet
	}//run
	
	
	
	//AUF PAKET WARTEN
	private void waitForPacket(DatagramSocket socket) throws IOException{
		 while (true) {
			 
			 //Anfrage warten
			 System.out.println("	run()->waitForPacket(): waiting for packet");
			 
			 //Paket schnüren
			 DatagramPacket receivedPacket = new DatagramPacket( new byte[32], 32 );
			 //Inhalt des erhaltenen Packetes in geschnürtes Packet stecken
			 socket.receive(receivedPacket);
			 
			  //Paket auslesen:
		      InetAddress address = receivedPacket.getAddress();
		      int         port    = receivedPacket.getPort();
//		      int         len     = receivedPacket.getLength();
		      byte[]      data    = receivedPacket.getData();
		      
		      //Nutzdaten/Nachricht auslesen  bytes -> string
		      String message = new String(data);
		      message = message.trim();          //Leerzeichen raus - Fehlervermeidung
		      
		      //feedback
		      System.out.println("	run()->waitForPacket(): ->packet erhalten von: " + address + " address, " + port + " port, " );
		      System.out.println("	run()->waitForPacket(): 				   -" + message + "-data");	      
			 
//ANTWORTEN
		      	//bei statussendung
		      for (int i = 1; i <= 6; i++){		    	
		    	String string = Data.encodeIn(i);		    	  
		      	if (message.equals(string)) {
		        	 send("FE,0000",address,port);
		        	 this.state = Data.decode(message, state);
		        	 break;
		      	} //if  (Hier könnte in Else ERROR FE,0001 stehen
		      }//for
		      
		      
		       //bei statusabfrage
		      if (message.length() > 3 && message.substring(0,4).equals("DE,?")) {
		    	  System.out.println("status angefragt");
		    	  send("FE,0000", address,port);
		    	  send(Data.encodeOut(state),address,port);
		      }//if		      
		      
		     //STATUS 
		     System.out.println("STATUS: " + state); 
			 gui.setState(Integer.toString(state));
			 gui.setLights(state);
		 }//while - warten auf Packet
	}//waitForPacket
	
	
	
	
	
	//SEND
	private boolean send(String message, InetAddress address, int port){		
		//packet schnüren
		byte[] sendMessage = message.getBytes();
		DatagramPacket packetToSend = new DatagramPacket(sendMessage, sendMessage.length, address, port);
		//versuchen zu senden
		try {
			socket.send(packetToSend);
			System.out.println("Status versendet" + message);			
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}//try		
	}//send
	
	
	
}//AServerThread
