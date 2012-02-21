package sonosip.player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringBufferInputStream;
import java.util.Vector;
import java.util.logging.LogManager;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.id3.ID3v24Tag;

import sonosip.Activator;
import sonosip.preferences.PlayerPreferencePage;
import sonosip.utils.EventLogger;
import sonosip.views.PlayerView;
import sonosip.views.RandomPlayerView;

public class PlayerManager {


    private static PlayerManager instance;

    private PlayerView playerView;
    private RandomPlayerView randomPlayerView;
    
    private AdvancedPlayer player;
	private Thread playerThread;
    
	private Vector<String> songPathList;
	private Vector<String> songNameList;
     
    public static synchronized  PlayerManager getInstance() {
        if (null == instance) {
        	instance = new PlayerManager();
        }
        return instance;
    }
    
    private PlayerManager() {		
    	songPathList = new Vector<String>();		
    	songNameList = new Vector<String>();

		IPropertyChangeListener preferenceListener = new IPropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent event) {
				if (event.getProperty().equals(PlayerPreferencePage.SONG_PATH)) {
					updatePlaylist();
				}
			}
		};
		
		Activator.getDefault().getPreferenceStore().addPropertyChangeListener(preferenceListener);    	
		updatePlaylist();
	}
    
    public void setPlayerView(PlayerView playerView) {
		this.playerView = playerView;
		this.playerView.updatePlaylist(this.songNameList);
	}
	
    public void setRandomPlayerView(RandomPlayerView randomPlayerView) {
		this.randomPlayerView = randomPlayerView;
	}
    
    public void stop() {
    	if (this.player != null) {
			player.close();
		}
		playerThread = null;
    	EventLogger.addInfo("Arrêt de la lecture des cantique");
    }
    
    public void play(int track) {
    	final int _track = track;
		Runnable runnable = new Runnable () {
			public void run () {	
		    	try {
		    		if(player != null) {
		    			player.close();
		    		}
					FileInputStream fis = new FileInputStream(songPathList.get(_track));
					player = new AdvancedPlayer(fis);
			    	EventLogger.addInfo("Lecture du cantique n°" + songNameList.get(_track));
					player.play();
				} catch (FileNotFoundException | JavaLayerException e) {
					EventLogger.addError(e.getMessage());
				}
			}
		};
		playerThread = new Thread(runnable);
		playerThread.start();
    }
    
    public void playRandom() {
    	EventLogger.addInfo("Lecture des cantiques en fond musical");
    }
    
    public Vector<String> getPlayList() {
    	return this.songNameList;
    }
    
	private void updatePlaylist() {
		String 	songPath = Activator.getDefault().getPreferenceStore().getString(PlayerPreferencePage.SONG_PATH);
		songPathList = new Vector<String>();		
		songNameList = new Vector<String>();		
		
		if(songPath != null && !"".equals(songPath)) {
		    File folder = new File(songPath);
		    File[] listOfFiles = folder.listFiles();
		    if(listOfFiles != null) {
			    for (int i = 0; i < listOfFiles.length; i++) {
			    	if (listOfFiles[i].isFile() && listOfFiles[i].getName().toLowerCase().endsWith(".mp3")) {
			    		String songName = listOfFiles[i].getName();
			    		try {
			    			LogManager.getLogManager().readConfiguration(new StringBufferInputStream("org.jaudiotagger.level = OFF"));
			    			MP3File f = (MP3File)AudioFileIO.read(listOfFiles[i]);
			    			ID3v24Tag v24tag = f.getID3v2TagAsv24();
			    			String title = v24tag.getFirst(FieldKey.TITLE);
			    			if(title != null && !"".equals(title)) {
			    				songName = title;
			    			}
			    		} catch (Exception e) {
			    		}
			    		songNameList.add(songName);
			    		songPathList.add(listOfFiles[i].getAbsolutePath());
			    	}
			    }
		    }
		}
		
		if(songNameList.isEmpty()) {
			EventLogger.addError("Aucun cantique n'a été trouvé, veuillez vérifiez la configuration !");
		}
		
		if(this.playerView != null) {
			this.playerView.updatePlaylist(this.songNameList);
		}
		
	}
    
}
