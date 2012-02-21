package sonosip.record;

import java.io.File;
import java.io.StringBufferInputStream;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.LogManager;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.id3.ID3v24Tag;

import sonosip.Activator;
import sonosip.preferences.CongregationPreferencePage;
import sonosip.preferences.PlayerPreferencePage;
import sonosip.preferences.RecorderPreferencePage;
import sonosip.utils.EventLogger;
import sonosip.views.RecordView;

public class RecordManager {


    private static RecordManager instance;
    
    private RecordView recordView;
    
	private Vector<String> congregationList;
     
    public static synchronized  RecordManager getInstance() {
        if (null == instance) {
        	instance = new RecordManager();
        }
        return instance;
    }
    
    private RecordManager() {

		IPropertyChangeListener preferenceListener = new IPropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent event) {
				if (event.getProperty().equals(CongregationPreferencePage.CONGREGATION_LIST)) {
					updateCongregationList();
				}
			}
		};
		
		Activator.getDefault().getPreferenceStore().addPropertyChangeListener(preferenceListener); 
		
		preferenceListener = new IPropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent event) {
				if (event.getProperty().equals(RecorderPreferencePage.RECORD_PATH)) {
					updateRecordPath();
				}
			}
		};
		
		Activator.getDefault().getPreferenceStore().addPropertyChangeListener(preferenceListener);  
		
		updateCongregationList();
		updateRecordPath();

    }

    public void setRecordView(RecordView recordView) {
		this.recordView = recordView;
		this.recordView.updateCongregationList(congregationList);
	}	
    
    private void updateRecordPath() {
		String recordPath = Activator.getDefault().getPreferenceStore().getString(RecorderPreferencePage.RECORD_PATH);
		if(recordPath.isEmpty()) {
			EventLogger.addError("Le dossier des enregistrements n'est pas paramétré, veuillez vérifiez la configuration !");
		}
    }
    
    private void updateCongregationList() {
		String 	congregationListString = Activator.getDefault().getPreferenceStore().getString(CongregationPreferencePage.CONGREGATION_LIST);
		congregationList = new Vector<String>();
		
		StringTokenizer tokenizer =	new StringTokenizer(congregationListString, "¤");
		int tokenCount = tokenizer.countTokens();
		for (int i = 0; i < tokenCount; i++) {
			congregationList.add(tokenizer.nextToken());
		}

		
		if(congregationList.isEmpty()) {
			EventLogger.addError("Aucune congrégation n'a été ajoutée, veuillez vérifiez la configuration !");
		}
		
		if(this.recordView != null) {
			this.recordView.updateCongregationList(congregationList);
		}
		
	}
    
    public void record(String fileName) {
    	
    }
}
