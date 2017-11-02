package gui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;

//Container zum mitschleifen an run Methode

public class Gui {
	

	//Ausgabe	
	//Statusfeld

	//gui elements
	@FXML private TextField state;
	
	@FXML private Circle running;

	//Ampel
	//Auto
	@FXML private Circle    autorot;
	@FXML private Circle    autogelb;	
	@FXML private Circle    autogruen;
	
	//Fussgänger
	@FXML private Circle    fussrot;	
	@FXML private Circle    fussgruen;
	
	
	//constructor
	public Gui (TextField state, Circle running, Circle autorot, Circle autogelb, Circle autogruen, Circle fussrot, Circle fussgruen) {
		this.state 	   = state;
		this.running   = running;
		this.autorot   = autorot;
		this.autogelb  = autogelb;
		this.autogruen = autogruen;
		this.fussrot   = fussrot;
		this.fussgruen = fussgruen;
	}//Gui		

	
	//gui manipulation
	public void setState(String state){
		this.state.setText(state);
	}

	public void setRun(){
		this.running.setStyle("-fx-fill:red");
	}//setRun
	
	
	
	public void setLights (int state) {
		
		switch (state) {
		case 1:
			//Fussgänger -> rot
			this.fussrot.setStyle("-fx-fill:red;");
			this.fussgruen.setStyle("-fx-fill:white;");
			
			//Auto
			this.autorot.setStyle("-fx-fill:red;");
			this.autogelb.setStyle("-fx-fill:white;");
			this.autogruen.setStyle("-fx-fill:white;");
			break;

		
		case 2:
			this.autorot.setStyle("-fx-fill: white;");
			this.autogelb.setStyle("-fx-fill:yellow;");
			this.autogruen.setStyle("-fx-fill:white;");
			break;

		
		case 3:			
			this.autorot.setStyle("-fx-fill:  white;");
			this.autogelb.setStyle("-fx-fill: white;");
			this.autogruen.setStyle("-fx-fill:green;");
			break;	

		case 6:
			//Fussgänger -> grün
			this.fussrot.setStyle("-fx-fill:   white;");
			this.fussgruen.setStyle("-fx-fill: green;");	
			
			this.autorot.setStyle("-fx-fill:  red;");
			this.autogelb.setStyle("-fx-fill: white;");
			this.autogruen.setStyle("-fx-fill:white;");
			break;					
			
		default: 
			System.out.println("DEFAULT BEI GUI -> why? state?");			
			
		}//Switch
		
	}//setLights

	
}//Gui
