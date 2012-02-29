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
			label = "Pas de connection � Internet";
			break;
		case UNABLE_TO_CONNECT:
			label = "Connection au serveur de t�l�phonie impossible";
			break;
		case DISCONNECTED:
			label = "Module de t�l�phonie inactif";
			break;
		case CONNECTED:
			label = "Connect� au serveur de t�l�phonie";
			break;
		case CONNECTED_TRANSMITING:
			label = "Connect� au serveur de t�l�phonie - Retransmition en cours";
			break;

		default:
			break;
		}
		
		return label;
	}
}
