package application;

import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import background.Client;
import background.HostData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;

public class Meldetafel_Controller {
	
	@FXML Label  name;
	@FXML TextField ip;
	@FXML Circle lamp;
	@FXML Button start;
	@FXML Button ok;
	@FXML Button fehler;
	@FXML Button aus;
	@FXML Button beenden;
	
	//zeig an ob programm läuft	
	private boolean running = false;
	
	//Client-Socket geöffnet wenn nicht null
    private Client client;
    
    //2 Threads -> Timer müssen gleichzeitig 
    	//-falls nicht gesetzt- gesetzt
    	//-gesetzt- gecancelt+neugesetzt werden
    private Timer     timer1, timer2;    
    private TimerTask task1,  task2;        
    
    
    
    
    
    
//START    
	
	@FXML
	public void setStart(ActionEvent e){	
		//bei laufendem Programm - weiteren Start verhindern
		if (running == true) return;		
		this.running = true ;	
		
//		//einfache Version		
//		name.setText( HostData.getName() + " - " + HostData.getIP() );	

		//aufwendige Version
		name.setText(HostData.getHost());
		
//Client verbinden          (zielip           , zielport)
		client = new Client( ip.getText() , 1111);
// Timer Threads erstellen:		
		setTimer("Awhite");				
		
	}//setStart	
	
	
	
	
	
	
	
	
	
	
//BUTTON-EVENTS
	
	@FXML
	public void setOk(ActionEvent e) throws UnknownHostException{		
		setTimer("Ggreen");	
	}//setStart	
	
	
	@FXML
	public void setFehler(ActionEvent e){
		setTimer("Rred");	
	}//setStart	
	
	@FXML
	public void setAus(ActionEvent e){
		setTimer("Awhite");
	}//setStart	
	
	
		
	
	
	
//TIMER SETZEN
	
	private void setTimer (String message){
		
		//Test ob Programm läuft, sonst abbruch
		if (running == false) return;
		
		//Canceln (falls geöffnet)
		if (this.timer1 != null && this.timer2 != null){
			this.task2.cancel();
			this.task1.cancel();
			this.timer1.cancel() ;
			this.timer2.cancel();		
		}

		
		//Task Grau
		 this.task1 = new TimerTask() {
			@Override
			public void run () {
				client.send("A");
				lamp.setStyle("-fx-fill: white;");
			}//run
		};//task1
		
		//Task Farbig Zustände
		 this.task2 = new TimerTask() {
			@Override
			public void run () {
				client.send(message.substring(0,1));
				lamp.setStyle("-fx-fill: " + message.substring(1) +";");
			}//run 
		}; //task2
		
		
		//Timer1 setzen - mit Tasks versehen
		this.timer1 = new Timer();
		this.timer1.scheduleAtFixedRate(task1, 0,1000);
		
		//Timer2
		this.timer2 = new Timer();
		this.timer2.scheduleAtFixedRate(task2, 500,1000);

	}//setState
	
	
	
	
	
//BEENDEN
	
	@FXML
	public void setBeenden(ActionEvent e){
		this.running = false;
		if (this.timer1 != null) this.timer1.cancel();
		if (this.timer2 != null) this.timer2.cancel();  //bedingung falls laufend...sonst nullpointer
		if (this.client != null) this.client.closeSocket();
		System.exit(0);
	}//setBeenden
	
	
	
	
}//Meldetafel_Controller
