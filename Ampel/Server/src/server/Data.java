package server;

public class Data {
	
	//Commands - Coding
	
	//codieren (Ausgang)
	public static String encodeOut (int state) {
		switch (state){
			case 1: 
				return "DE,00001001";
			case 2: 
				return "DE,00001010";
			case 3: 
				return "DE,00001100";
			case 6: 
				return "DE,00010001";
			default:
				return "DE,00000000";
		}//switch		
	}//encode	
	
	
	//codieren (Eingang)
	public static String encodeIn (int state) {
		switch (state){
			case 1: 
				return "DA,00001001";
			case 2: 
				return "DA,00001010";
			case 3: 
				return "DA,00001100";
			case 6: 
				return "DA,00010001";
			default:
				return "DA,00000000";
		}//switch		
	}//encode
	
	
	
	//decodieren (Eingang)
	public static int decode (String received, int currentState) {
		//Vorbereiten: Leerzeichen raus
		received = received.trim();
		if (received.startsWith("DA,")){
			String value = received.substring(6);			
			switch (value){
			//2 fälle von 01001
			 case "01001":
				 return 1;
			//2 fälle von 01010
			 case "01010":
				 return 2;
			 case "01100":
				 return 3;
			 case "10001":
				 return 6;	
			}//switch
		}//if		
		return 1;		
	}//decode	

}//Data
