package sonosip.softphone;

public class ConnectionStatus {
	public static final int NO_INTERNET 			= 1;
	public static final int UNABLE_TO_CONNECT 		= 2;
	public static final int DISCONNECTED 			= 3; 
	public static final int CONNECTED 				= 5; 
	public static final int CONNECTED_TRANSMITING 	= 6; 
	
	
	public static String getStatusLabel(int status) {
		String label = "";
		
		switch (status) {
		case NO_INTERNET:
			label = "Pas de connection à Internet";
			break;
		case UNABLE_TO_CONNECT:
			label = "Connection au serveur de téléphonie impossible";
			break;
		case DISCONNECTED:
			label = "Module de téléphonie inactif";
			break;
		case CONNECTED:
			label = "Connecté au serveur de téléphonie";
			break;
		case CONNECTED_TRANSMITING:
			label = "Connecté au serveur de téléphonie - Retransmition en cours";
			break;

		default:
			break;
		}
		
		return label;
	}
}
