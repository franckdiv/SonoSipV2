package sonosip.record;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

import sonosip.Activator;
import sonosip.preferences.CongregationPreferencePage;
import sonosip.preferences.RecorderPreferencePage;
import sonosip.utils.EventLogger;
import sonosip.views.RecordView;

public class RecordManager {

    private static RecordManager instance;
    private RecordView recordView;
	private int recordState;
	private Vector<String> congregationList;
	private TargetDataLine targetLine;
	
	
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
    	if(recordState == RecordState.STOP) {
			try {
				AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
				int sampleRate = 44100;
				byte sampleSizeInBits = 16;
				int channels = 2;
				int frameSize = 4;
				int frameRate = 44100;
				boolean bigEndian = false;
				AudioFormat format = new AudioFormat(encoding, sampleRate, sampleSizeInBits, channels, frameSize, frameRate, bigEndian);
				targetLine = AudioSystem.getTargetDataLine(format);
				targetLine.open(format);
				targetLine.start();
				
				final String filePath = Activator.getDefault().getPreferenceStore().getString(RecorderPreferencePage.RECORD_PATH);
				if(filePath != null && !"".equals(filePath)) {
					final String fileNameToEncode = fileName;
					final AudioInputStream stream = new AudioInputStream(targetLine);
					final AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
		            final File audioFile = new File(filePath + File.separator + fileName + ".wav");	
		            if(!audioFile.exists()) {
		            	audioFile.createNewFile();
		            }
					new Thread() {
						public void run() {
							try {
								AudioSystem.write(stream, fileType, audioFile);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}.start();
					
					EventLogger.addInfo("Enregistrement du programme en cours : " + fileName);
					recordState = RecordState.RECORD;
					notifyRecordStateChange();
				}
			} catch (IllegalArgumentException e) {
				EventLogger.addError("Le périphérique d'enregistrement audio n'est pas disponible. " + e.getMessage());
			} catch (LineUnavailableException e) {
				EventLogger.addError("Le périphérique d'enregistrement audio n'est pas disponible. " + e.getMessage());
			} catch (IOException e) {
				EventLogger.addError("Impossible d'écrire dans le fichier de destination. " + e.getMessage());
			}
    	}
    }
    
    public void stop() {
		if (recordState == RecordState.RECORD) {
			new Thread() {
				public void run() {
					targetLine.stop();
					targetLine.close();
				}
			}.start();

			EventLogger.addInfo("Arrêt de l'enregistrement du programme");
			recordState = RecordState.STOP;
			notifyRecordStateChange();
		}
    }
    

	public void notifyRecordStateChange() {
		if(this.recordView != null) {
			this.recordView.handleRecordState(recordState);
		} 
	}
}
