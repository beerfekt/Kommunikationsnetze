package background;


import java.util.TimerTask;

import anwendung.Gui;
import network.Client;


public class Command2 extends TimerTask {
	
	
	//objekt mit grafischen elementen aus controller
	private Gui gui;	
	
	//client
	private Client client;
	
	//clock
	private int counter;	
	private boolean enabled = false;
	private boolean running = false;
	
	//zustände versand/empfangen
	private int state;
	
	private int receivedstate;
	
	//konstruktor
	
	public Command2 (Gui gui, Client client) {
		this.client = client;
		this.gui = gui;
		this.state = 1;
		this.counter = 1;
	}//Command

	
	
//run Methode
	
	@Override
	public void run() {
		//count clock
		count();	
		
		//erster Aufruf
		if ( running == false) {
			//wenn status erfolgreich versand wurde....				
			if (client.send(client.encode(1)) )
			{
				receivedstate = 1;
				running = true;				
		     }//if										
			setGui();
		    enabled = true;
		}//if
		
		// ( VOR ÄNDERUNG WAR count() hier )


		//Zustands- & Timerprüfung für Reaktion...
		if
		(
			receivedstate == 1 && this.counter == 11 ||
			receivedstate == 2 && this.counter ==  3 ||
			receivedstate == 3 && this.counter == 16 ||
			receivedstate == 4 && this.counter ==  3 ||
			receivedstate == 5 && this.counter ==  3 ||
			receivedstate == 6 && this.counter == 11 
		) 	react();
		System.out.println("      --clock--" + counter);		
	}//run
		
	
	
		
	
	
	
	//Reaktion	
	
	
	public void react () {		
		//Server abfragen
		getServerState();		
		//Ausgabe der GUI
		setGui();
		//Zurücksetzen des Zählers
		counter = 1;
		System.out.println(receivedstate + "received");
	}//react
	
	

	

	
	
	
	//NETZWERKKOMMUNIKATION  - Ablauf
	private boolean getServerState(){

						if ( client.isConnected() ){
							
							boolean done = false;
							
							//status erhöhen
							done = setNewState();		
					
							//diesen an Server schicken
							done = tryToSendState();
									
							//Serverstatuts anfordern/erhalten
							done = tryToGetServerState();

							if (done) {
								enabled = true;
								return true;									
							}//if

						}//if conected?
						
						//Falls nicht verbunden:
						return false;	
									
	}//getServerState	

	
	
	
	//Try to send state
	private boolean tryToSendState(){
		//ANFRAGE - wurde ACK erhalten?
		boolean ack = false;							
		//STATUS VERSENDEN :							
		//Solange Status senden versuchen bis ACK erhalten wurde
		while ( ack == false){
			ack = client.send( client.encode(state) );	//Status senden	- mit FE0000 bestätigen	
			if (ack == false) System.out.println("      !!-> KEIN STATUS KONNTE ÜBERMITTELT WERDEN - Versuche es erneut");
		}//while 									
		//..... ok while überlebt, string korrekt...
		System.out.println("String korrekt:" + ack);	
		return true;
	}//tryToSendState
	
	
	
	
	//Try to get Server State	
	private boolean tryToGetServerState (){
		//ANFRAGE stellen
		//Solange versuchen, solange ack false...
		boolean ack = false;
		while (ack == false){
			ack = client.send("DE,?");
			if (ack == false) System.out.println("FEHLERHAFTER STRING - bei - Statusabfrage" );
		}//while statusanfrage				
		//wenn Anfrage geglückt: Status abfangen:	
		System.out.println("RECEIVEDSTATE IST VOR ERHALT:  " +  receivedstate);
		receivedstate = client.decode(client.receive(), state);		
		System.out.println("RECEIVEDSTATE IST:  "  + receivedstate + " STATE IST: " + state);
		return true;
	}//tryToGetServerState
	
	
	
	
	//Status erhöhen
	private boolean setNewState () {
		//reaktion -> status erhöhen
		++state;
		//Dauerschleife
				if (state == 7) state = 1;					
				
//Optionales Ende nach einem Durchgang
		//zurücksetzen des Zustands auf 1 falls letzter (6) erreicht
//		if (state == 7) {
//			state = 1;				//zurücksetzen
//			Timing.cancelClock();	//timer canceln
//		}
		return true;
	}//setNewState	
	
	
	
	
	//Counter -> clock
	private void count(){
		if ( enabled) { 
			counter++;
		} else {
			counter = 1;
		}//if
	}//count		
	
	
	//GUI ansteuern
	private void setGui () {
		String feedback = Integer.toString(receivedstate);
		gui.setState(feedback);
		gui.setLights(receivedstate);
	}//setFeedback
	

}//Command



