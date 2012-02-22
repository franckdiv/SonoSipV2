package sonosip.views;

import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ISizeProvider;
import org.eclipse.ui.part.ViewPart;

import sonosip.player.PlayerManager;
import sonosip.player.PlayerState;
import sonosip.ressources.RessourcePathPointer;

public class PlayerView extends ViewPart implements ISizeProvider {

	public static final String ID = "sonosip.views.PlayerView";
	
	public PlayerView() {
	}

	private Combo songListCombo;
	private Composite parent;
	private Button stopButton;
	private Button playButton;
	

	@Override
	public void createPartControl(Composite parent) {
		
		this.parent = parent;
		Composite composite = new Composite(this.parent, SWT.FILL);
	    GridLayout gridLayout = new GridLayout();
	    gridLayout.numColumns = 1;
	    composite.setLayout(gridLayout);
	    

		Label infoLabel = new Label(composite, SWT.NONE);
		infoLabel.setText("Sélectionnez un cantique :");
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		infoLabel.setLayoutData(gridData);
		
		songListCombo = new Combo(composite, SWT.READ_ONLY | SWT.DROP_DOWN);
		songListCombo.setVisibleItemCount(30);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		songListCombo.setLayoutData(gridData);
		songListCombo.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(songListCombo.getSelectionIndex() != -1) {
					playButton.setEnabled(true);
				} else {
					playButton.setEnabled(false);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {	
			}
		});
		
		

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
		
		playButton = new Button(buttonComposite, SWT.PUSH | SWT.LEFT);
		playButton.setText("Lecture");
		playButton.setEnabled(false); 
		playButton.setImage(new Image(this.parent.getDisplay(), RessourcePathPointer.class.getResourceAsStream("play.png")));
		playButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if(songListCombo.getSelectionIndex() != -1) {
					PlayerManager.getInstance().play(songListCombo.getSelectionIndex());
				}
			}
		});

		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		playButton.setLayoutData(gridData);
		
		stopButton = new Button(buttonComposite, SWT.PUSH | SWT.LEFT);
		stopButton.setImage(new Image(this.parent.getDisplay(), RessourcePathPointer.class.getResourceAsStream("stop.png")));
		stopButton.setText("Stop");
		stopButton.setEnabled(false);
		stopButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				PlayerManager.getInstance().stop();
			}
		});
		
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		stopButton.setLayoutData(gridData);
		

		PlayerManager.getInstance().setPlayerView(this);
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
			return 100;
		}
	}

	@Override
	public int getSizeFlags(boolean width) {
		return SWT.MIN;
	}

	public void updatePlaylist(Vector<String> playlist) {
		final Vector<String> _playlist = playlist;
		this.parent.getDisplay().asyncExec (new Runnable () {
			public void run () {	
				songListCombo.removeAll();
				String[] songNameValues = new String[_playlist.size()];
				for (int i = 0 ; i < _playlist.size() ; i++) {
					songNameValues[i] = _playlist.get(i);
				}
				songListCombo.setItems(songNameValues);
			}
		});
	}
	
	public void handlePlayerState(int playerState) {
		final int _playerState = playerState;
		this.parent.getDisplay().asyncExec (new Runnable () {
			public void run () {	
				if(_playerState == PlayerState.PLAY || _playerState == PlayerState.PLAY_RANDOM) {
					stopButton.setEnabled(true); 
				}
				if(_playerState == PlayerState.STOP) { 
					stopButton.setEnabled(false); 
				}
			}
		});
	}
}
