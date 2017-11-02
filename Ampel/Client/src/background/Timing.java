package background;

import java.util.Timer;
import anwendung.Gui;
//import javafx.scene.control.TextField;

import network.Client;

public class Timing {

	
	private static  Timer timer;
	private static Command2 task2;
		
	
//Durchgehender Timer
	
	//Timer erstellen	
	public static void clock (int nextstate, Gui gui, Client client) {
		//canceln
		cancelClock();
		//timer erstellen
		timer = new Timer();
		timer.scheduleAtFixedRate( task2 = new Command2(gui,client),0 , 1000);
	}//clock

	
	//Timer canceln
	public static void cancelClock(){
		if (task2 != null) task2.cancel(); 				//Task beenden falls vorhanden
		if (timer != null) timer.cancel();		
		task2 = null;									//variablen auf null setzen
		timer = null;
	}//cancelClock
	
	
}//Timing
