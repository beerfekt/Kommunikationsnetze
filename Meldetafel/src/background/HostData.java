package background;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;

public class HostData {

	
//EINFACH
	//(Gefahr dass Loopback IP genommen wird)
	
	
	//Hostname 	
	public static String getName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}//trycatch	
		return "unknown host";
	}//getName
	
	
	//IP	
	public static String getIP() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}//trycatch	
		return "unknown ip";
	}//getName
	
	
	

//Aufwändige Version 	
	
	//Starter Methode - try-catch block 
	public static String getHost(){		
		String feedback = "";		
		try {
			feedback = (HostData.getHostAndIP());
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return feedback;
	}//
	
	
	
	
    //liest Netzwerkinterfaces - Router aus - throws (wird von starter gefangen)
	private static String getHostAndIP () throws SocketException {
		
		String hostip = "";
		
		Enumeration<NetworkInterface> netfaces = NetworkInterface.getNetworkInterfaces();
		
		while (netfaces.hasMoreElements()) {
			NetworkInterface netface =  netfaces.nextElement();				
			
////FÜR DIESEN RECHNER MIT UBUNTU		(geht nach netzname)
//			if (netface.getDisplayName().equals("wlo1")){		
//				// beer-beerbel.fritz.box , 192.168.178.24
//				for (InetAddress address : Collections.list(netface.getInetAddresses())) {
//						hostip = ( address.getHostName() + "\n" + address.getHostAddress() );
//				}//foreach
//			}//if "wlo1"
		
			
//ALLGEMEIN -> ROUTERADRESSE  (geht nach routerdisplay)
			// beer-beerbel.fritz.box , 192.168.178.30 bei lan / .24 bei wlan
			for (InetAddress address : Collections.list(netface.getInetAddresses())) {
				if (address.isLoopbackAddress() != true && address.isSiteLocalAddress() == true){
					hostip = ( address.getHostName() + "\n" + address.getHostAddress() );
				}//if
			}//foreach
			
			
		}//while
		

		// *BSP FÜR ALLE VERBINDUNGEN HIER []
		
		
		return hostip;
	}//getHostAndIP
	
	
	
	
}//Network




















//      //BSP UM ALLE INFOS RAUSZULASSEN
//
//		Enumeration<NetworkInterface> netInter = NetworkInterface.getNetworkInterfaces();
//		int n = 0;
//
//		while ( netInter.hasMoreElements() )
//		{
//		  NetworkInterface ni = netInter.nextElement();
//
//		  System.out.println( "NetworkInterface " + n++ + ": " + ni.getDisplayName() );
//
//		  for ( InetAddress iaddress : Collections.list(ni.getInetAddresses()) )
//		  {
//		    System.out.println( "CanonicalHostName: " +
//		                         iaddress.getCanonicalHostName() );
//
//		    System.out.println( "IP: " + iaddress.getHostAddress() );
//
//		    System.out.println( "Loopback? " + iaddress.isLoopbackAddress() );
//		    System.out.println( "SiteLocal? " + iaddress.isSiteLocalAddress() );
//		    System.out.println();
//		  }
//		}

		


