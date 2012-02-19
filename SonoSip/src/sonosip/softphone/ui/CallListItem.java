package sonosip.softphone.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import sonosip.ressources.RessourcePathPointer;
import sonosip.softphone.CallStatus;
import sonosip.softphone.ui.interfaces.CallListItemEventListener;

public class CallListItem extends Composite {

	protected Composite parent;
	protected Label statusImageHolder;
	protected Label statusTextLabel;
	protected Button microphoneStartButton;
	protected Button microphoneStopButton;
	protected Button cancelRequestButton;

	protected int callStatus = 0;
	protected int callId = 0;
	
	protected CallListItemEventListener eventListener; 
	
	
	Listener cancelRequestListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			if(eventListener != null) {
				eventListener.onClickCancelCallerSpeakRequestButton();
			}
		}
	};	
	Listener connectMicroListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			if(eventListener != null) {
				eventListener.onClickConnectCallerMicrophoneButton();
			}
		}
	};	
	Listener disconnectMicroListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			if(eventListener != null) {
				eventListener.onClickDisconnectCallerMicrophoneButton();
			}
		}
	};
	
	public void setEventListener(CallListItemEventListener eventListener) {
		this.eventListener = eventListener;
	}

	public CallListItem(Composite parent, String callerName, int callId, int callStatus) {
		super(parent, SWT.BORDER);
		this.parent = parent;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 5;
		gridLayout.horizontalSpacing = 0;
		this.setLayout(gridLayout);
		this.setBackgroundMode(SWT.INHERIT_DEFAULT);

		/**
		 * Create status image widget
		 */
		Image statusImage = parent.getDisplay().getSystemImage(SWT.ICON_QUESTION);
		statusImageHolder = new Label(this, SWT.NONE);
		statusImageHolder.setImage(statusImage);
		
		
		/**
		 * Create name and status label widget
		 */
		Composite labelsHolder = new Composite(this, SWT.NONE);		
		GridLayout labelsHolderGridLayout = new GridLayout();
		labelsHolderGridLayout.numColumns = 1;
		labelsHolderGridLayout.marginLeft = 5;
		labelsHolder.setLayout(labelsHolderGridLayout);
		    
		Label callerNameLabel = new Label(labelsHolder, SWT.NONE);
		callerNameLabel.setText(callerName);
		Font font = new Font(parent.getDisplay(), "Arial", 12, SWT.BOLD);
		callerNameLabel.setFont(font);

		statusTextLabel = new Label(labelsHolder, SWT.NONE);
		statusTextLabel.setText(CallStatus.getStatusLabel(callStatus));

		GridData labelsHolderGridData = new GridData();
		labelsHolderGridData.horizontalAlignment = SWT.FILL;
		labelsHolderGridData.grabExcessHorizontalSpace = true;
		labelsHolder.setLayoutData(labelsHolderGridData);

		
		/**
		 * Create action button widgets
		 */
		Image connectCallerMicrophoneImage = new Image(parent.getDisplay(), RessourcePathPointer.class.getResourceAsStream("mic-ok.png"));
		microphoneStartButton = new Button(this, SWT.PUSH);
		microphoneStartButton.setImage(connectCallerMicrophoneImage);
		microphoneStartButton.setText("Activer le micro");
		microphoneStartButton.addListener (SWT.Selection, connectMicroListener);
		
		Image disconnectCallerMicrophoneImage = new Image(parent.getDisplay(), RessourcePathPointer.class.getResourceAsStream("mic-cancel.png"));
		microphoneStopButton = new Button(this, SWT.PUSH);
		microphoneStopButton.setImage(disconnectCallerMicrophoneImage);
		microphoneStopButton.setText("Désactiver le micro");
		microphoneStopButton.addListener (SWT.Selection, disconnectMicroListener);

		Image cancelCallerSpeakRequest = new Image(parent.getDisplay(), RessourcePathPointer.class.getResourceAsStream("mic-cancel.png"));
		cancelRequestButton = new Button(this, SWT.PUSH);
		cancelRequestButton.setImage(cancelCallerSpeakRequest);
		cancelRequestButton.setText("Annuler la demande");
		cancelRequestButton.addListener (SWT.Selection, cancelRequestListener);


		handleCallStatus(callStatus);
	}
	
	public void handleCallStatus(int status) {
		Image statusImage;
		Image backgroundImage;
		if(status != this.callStatus) {

			this.setBackgroundImage(null);
			this.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
			switch (status) {
				case CallStatus.LISTENING_HOLD_SOUND:
					statusImage = new Image(parent.getDisplay(), RessourcePathPointer.class.getResourceAsStream("icon-user-listen-hold-message.png"));
					statusImageHolder.setImage(statusImage);
					microphoneStartButton.setEnabled(false);
					microphoneStopButton.setEnabled(false);
					cancelRequestButton.setEnabled(false);
					statusTextLabel.setText(CallStatus.getStatusLabel(status));
					statusTextLabel.pack();
					backgroundImage = new Image(parent.getDisplay(), RessourcePathPointer.class.getResourceAsStream("bg-holding.png"));
					this.setBackgroundImage(backgroundImage);
					break;
				case CallStatus.LISTENING_MEETING:
					statusImage = new Image(parent.getDisplay(), RessourcePathPointer.class.getResourceAsStream("icon-user-listen-meeting.png"));
					statusImageHolder.setImage(statusImage);
					microphoneStartButton.setEnabled(false);
					microphoneStopButton.setEnabled(false);
					cancelRequestButton.setEnabled(false);
					statusTextLabel.setText(CallStatus.getStatusLabel(status));
					statusTextLabel.pack();
					break;
				case CallStatus.REQUEST_TO_SPEAK:
					statusImage = new Image(parent.getDisplay(), RessourcePathPointer.class.getResourceAsStream("icon-user-request-talking.png"));
					statusImageHolder.setImage(statusImage);
					microphoneStartButton.setEnabled(true);
					microphoneStopButton.setEnabled(false);
					cancelRequestButton.setEnabled(true);
					statusTextLabel.setText(CallStatus.getStatusLabel(status));
					statusTextLabel.pack();
					backgroundImage = new Image(parent.getDisplay(), RessourcePathPointer.class.getResourceAsStream("bg-request-talking.png"));
					this.setBackgroundImage(backgroundImage);
					break;
				case CallStatus.SPEAKING:
					statusImage = new Image(parent.getDisplay(), RessourcePathPointer.class.getResourceAsStream("icon-user-talking.png"));
					statusImageHolder.setImage(statusImage);
					microphoneStartButton.setEnabled(false);
					microphoneStopButton.setEnabled(true);
					cancelRequestButton.setEnabled(false);
					statusTextLabel.setText(CallStatus.getStatusLabel(status));
					statusTextLabel.pack();
					backgroundImage = new Image(parent.getDisplay(), RessourcePathPointer.class.getResourceAsStream("bg-talking.png"));
					this.setBackgroundImage(backgroundImage);
					break;
				case CallStatus.LISTENING_GOODBYE_SOUND:
					statusImage = new Image(parent.getDisplay(), RessourcePathPointer.class.getResourceAsStream("icon-user-listen-bye-message.png"));
					statusImageHolder.setImage(statusImage);
					microphoneStartButton.setEnabled(false);
					microphoneStopButton.setEnabled(false);
					cancelRequestButton.setEnabled(false);
					statusTextLabel.setText(CallStatus.getStatusLabel(status));
					statusTextLabel.pack();
					break;
		
				default:
					break;
			}
		}
		this.callStatus = status;
		this.layout();
	}

}
