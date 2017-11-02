package gui;

import java.io.IOException;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import server.AServerThread;


public class AServerController {

	@FXML private Button start;
	@FXML private Button quit;	
	@FXML private TextField port;	
	@FXML private TextField state;
	
	
	//Ampel
	//Auto
	@FXML private Circle    autorot;
	@FXML private Circle    autogelb;	
	@FXML private Circle    autogruen;
	
	//Fussgänger
	@FXML private Circle    fussrot;	
	@FXML private Circle    fussgruen;
	
	
	//Running
	@FXML private Circle running;
	

	

	//START
	@FXML 
	public void setStart(ActionEvent event) throws IOException{
		Gui gui = new Gui(state, running, autorot, autogelb,  autogruen, fussrot, fussgruen);
		new AServerThread(gui, Integer.parseInt(port.getText())).start();
	}//setStart	
	
	
	
	//BEENDEN
	@FXML 
	public void setQuit(ActionEvent event){
		AServerThread.interrupted();
		System.exit(0);	
	}
	
}//AServerController
