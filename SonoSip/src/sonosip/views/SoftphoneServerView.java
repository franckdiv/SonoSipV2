package sonosip.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ISizeProvider;
import org.eclipse.ui.part.ViewPart;

import sonosip.ressources.RessourcePathPointer;
import sonosip.softphone.ConnectionStatus;
import sonosip.softphone.SoftphoneManager;

public class SoftphoneServerView extends ViewPart implements ISizeProvider {

	public static final String ID = "sonosip.views.SoftphoneServerView";
	

	private Composite parent;
	private Button reconnectButton;
	private Button startButton;
	private Button stopButton;
	private Label statusImageHolder;
	private Label statusTextLabel;
	
	
	public SoftphoneServerView() {
	}


	@Override
	public void setFocus() {
	}


	@Override
	public int computePreferredSize(boolean width, int availableParallel,
			int availablePerpendicular, int preferredResult) {
		if(width) {
			return 360;
		} else {
			return 200;
		}
	}

	@Override
	public int getSizeFlags(boolean width) {
		return SWT.MIN;
	}
	
	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
		Composite composite = new Composite(this.parent, SWT.FILL);
	    GridLayout gridLayout = new GridLayout();
	    gridLayout.numColumns = 1;
	    composite.setLayout(gridLayout);
	    
		
		Label infoLabel = new Label(composite, SWT.NONE);
		infoLabel.setText("Retransmition du programme :");
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		infoLabel.setLayoutData(gridData);
		
		Composite buttonComposite = new Composite(composite,SWT.NONE);		
		gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.horizontalSpacing = 0;
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		buttonComposite.setLayout(gridLayout);

		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		buttonComposite.setLayoutData(gridData);
		
		startButton = new Button(buttonComposite, SWT.PUSH | SWT.LEFT);
		startButton.setText("Commencer");
		startButton.setEnabled(false); 
		startButton.setImage(new Image(this.parent.getDisplay(), RessourcePathPointer.class.getResourceAsStream("play.png")));
		startButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {	
				SoftphoneManager.getInstance().startRetransmition();
			}
		});

		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		startButton.setLayoutData(gridData);
		  
		stopButton = new Button(buttonComposite, SWT.PUSH | SWT.LEFT);
		stopButton.setImage(new Image(this.parent.getDisplay(), RessourcePathPointer.class.getResourceAsStream("stop.png")));
		stopButton.setText("Arrêter");
		stopButton.setEnabled(false); 
		stopButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				SoftphoneManager.getInstance().stopRetransmition();
			}
		});
		
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		stopButton.setLayoutData(gridData);


		Label infoConnLabel = new Label(composite, SWT.NONE);
		infoConnLabel.setText("Etat de la connexion :");
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		infoConnLabel.setLayoutData(gridData);

		statusTextLabel = new Label(composite, SWT.NONE);
		statusTextLabel.setText("Module de téléphonie inactif");
		statusTextLabel.setForeground(new Color(parent.getDisplay(), 150, 150, 150));
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		statusTextLabel.setLayoutData(gridData);
		

		this.reconnectButton = new Button(composite, SWT.PUSH | SWT.LEFT);
		this.reconnectButton.setImage(new Image(this.parent.getDisplay(), RessourcePathPointer.class.getResourceAsStream("plug-disconnect.png")));
		this.reconnectButton.setText("Reconnecter au serveur de téléphonie");
		this.reconnectButton.setEnabled(false);
		this.reconnectButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				SoftphoneManager.getInstance().reconnectServer();
			}
		});
		
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.reconnectButton.setLayoutData(gridData);

		
		
		statusImageHolder = new Label(composite, SWT.NONE);
		statusImageHolder.setImage(new Image(this.parent.getDisplay(), RessourcePathPointer.class.getResourceAsStream("connection-3.png")));
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.CENTER;
		gridData.grabExcessHorizontalSpace = true;
		statusImageHolder.setLayoutData(gridData);
		

		SoftphoneManager.getInstance().setSoftphoneServerView(this);
	}
	
	public void updateConnectionStatus(final int connectionStatus) {
		this.parent.getDisplay().asyncExec (new Runnable () {
			public void run () {	

				switch (connectionStatus) {
				case ConnectionStatus.NO_INTERNET:
					
					break;
				case ConnectionStatus.UNABLE_TO_CONNECT:

					break;
				case ConnectionStatus.DISCONNECTED:
					
					break;
				case ConnectionStatus.CONNECTED:
					
					break;
				case ConnectionStatus.CONNECTED_TRANSMITING:
					
					break;

				default:
					break;
				}

				statusTextLabel.setText(ConnectionStatus.getStatusLabel(connectionStatus));
			}
		});
	}
}
