package anwendung;

import network.Client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Optional;

//import background.Command;
import background.Timing;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;

public class AmpelController {
	
	//Eingabe
	@FXML Button    exit;
	@FXML Button    walk;
	
	//Ausgabe	
	//Statusfeld
	@FXML TextField state;	
	//Feedbackfeld
	@FXML TextArea feedback;
	//IP Eingabefeld
	@FXML TextField ip   ;
	@FXML TextField port ;
	
	//Ampel
	//Auto
	@FXML Circle    autorot;
	@FXML Circle    autogelb;	
	@FXML Circle    autogruen;
	//Fussgänger
	@FXML Circle    fussrot;	
	@FXML Circle    fussgruen;	
		 
	//container für alle Gui elemente oben
	Gui gui;
	
	
	
//LAUFEN
	
	@FXML
	public void setWalk( ActionEvent e ) throws UnknownHostException, IOException {
		int status = 1;
		


		
		
		System.out.println("StartStatus-Server: " + status); //REAKTION AUSGABE DES STATUS
		//Gui Container erstellen
		gui = new Gui(state, ip, feedback, autorot, autogelb, autogruen, fussrot, fussgruen);
		
		//Eingaben auslesen
		int portin       = Integer.parseInt(port.getText());
		String addressin = ip.getText();
		
		//Prüfung der Eingaben für Entwickler
		System.out.println("Eingabe:  ip: " + addressin + " port: " + portin);
		
		//Verbindung aufbauen
		try {
			//Client erstellen
			Client client = new Client(addressin, portin, gui);			
			if (client.isConnected()){
				//Timer/Programmlogik aktivieren/starten
				Timing.clock(status, gui, client);
				gui.feedback("Verbunden", "green");
			} else {
				gui.feedback("Keine Verbindung", "red");
				System.out.println("Controller-Actionevent setWalk(): Verbindung nicht möglich");
			}//if	
		} catch (Exception e2) {
			gui.feedback("Keine Verbindung","red");
		}//Try Catch


	}//setWalk
	
	

	
	
			
	
//BEENDEN
	
	@FXML
	public void setExit (ActionEvent e) {
		System.exit(0);
	}//setExit
	
	
}//AmpelController
