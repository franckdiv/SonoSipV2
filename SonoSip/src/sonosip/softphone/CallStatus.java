package sonosip.softphone;

public class CallStatus {
	public static final int LISTENING_INSTRUCTION_SOUND = 1;
	public static final int TYPING_ACCESS_CODE 			= 2;
	public static final int LISTENING_CODE_ERROR_SOUND 	= 3; 
	public static final int LISTENING_WELCOME_SOUND 	= 4; 
	public static final int LISTENING_HOLD_SOUND 		= 5; 
	public static final int LISTENING_MEETING 			= 6; 
	public static final int REQUEST_TO_SPEAK 			= 7; 
	public static final int SPEAKING 					= 8; 
	public static final int LISTENING_GOODBYE_SOUND 	= 9; 
	
	public static String getStatusLabel(int status) {
		String label = "";
		
		switch (status) {
		case LISTENING_INSTRUCTION_SOUND:
			label = "Ecoute les instructions de connexion";
			break;
		case TYPING_ACCESS_CODE:
			label = "Saisi son code d'accés";
			break;
		case LISTENING_CODE_ERROR_SOUND:
			label = "Ecoute le message d'erreur";
			break;
		case LISTENING_WELCOME_SOUND:
			label = "Ecoute le message de bienvenue";
			break;
		case LISTENING_HOLD_SOUND:
			label = "En attente";
			break;
		case LISTENING_MEETING:
			label = "En écoute";
			break;
		case REQUEST_TO_SPEAK:
			label = "Demande à participer";
			break;
		case SPEAKING:
			label = "Prend la parole";
			break;
		case LISTENING_GOODBYE_SOUND:
			label = "Ecoute le message de déconnection";
			break;

		default:
			break;
		}
		
		return label;
	}
}
