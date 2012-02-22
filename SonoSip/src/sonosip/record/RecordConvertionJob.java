package sonosip.record;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderProgressListener;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.MultimediaInfo;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import sonosip.utils.EventLogger;

public class RecordConvertionJob extends Job {

	File wavFile;
	public RecordConvertionJob(File wavFile) {
		super("Conversion de l'enregistrement en MP3");
		this.wavFile = wavFile;
	}

	@Override
	protected IStatus run(final IProgressMonitor monitor) {
		monitor.beginTask("Conversion de l'enregistrement en MP3", 1000);
		try {
			String fileName = this.wavFile.getAbsolutePath();
			fileName = fileName.substring(0, fileName.lastIndexOf('.')) + ".mp3";
			
			EventLogger.addInfo("Conversion de l'enregistrement en MP3");
			
			File target = new File(fileName);
			AudioAttributes audio = new AudioAttributes();
			audio.setCodec("libmp3lame");
			audio.setBitRate(new Integer(128000));
			audio.setChannels(new Integer(2));
			audio.setSamplingRate(new Integer(44100));
			EncodingAttributes attrs = new EncodingAttributes();
			attrs.setFormat("mp3");
			attrs.setAudioAttributes(audio);
			Encoder encoder = new Encoder();
			encoder.encode(this.wavFile, target, attrs, new EncoderProgressListener() {
				
				@Override
				public void sourceInfo(MultimediaInfo arg0) {
				}
				
				@Override
				public void progress(int arg0) {
					monitor.worked(arg0);
				}
				
				@Override
				public void message(String arg0) {
				}
			});
			this.wavFile.delete();

			EventLogger.addInfo("Conversion terminée - " + fileName);
			

		} catch (Exception e) {
			EventLogger.addError(e.getMessage());
			return Status.CANCEL_STATUS;
		}
		
		monitor.done();
		return Status.OK_STATUS;
	}

}
