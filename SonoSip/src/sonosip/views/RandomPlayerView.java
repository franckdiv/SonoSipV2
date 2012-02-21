package sonosip.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISizeProvider;
import org.eclipse.ui.part.ViewPart;

import sonosip.player.PlayerManager;
import sonosip.ressources.RessourcePathPointer;
import sonosip.utils.EventLogger;

public class RandomPlayerView extends ViewPart implements ISizeProvider {

	public static final String ID = "sonosip.views.RandomPlayerView";
	
	private Composite parent;
	private Button stopButton;
	private Button playButton;
	

	public RandomPlayerView() {
	}
	
	@Override
	public void createPartControl(Composite parent) {
		
		this.parent = parent;
		Composite composite = new Composite(this.parent, SWT.FILL);
	    GridLayout gridLayout = new GridLayout();
	    gridLayout.numColumns = 1;
	    composite.setLayout(gridLayout);
	    
		Composite buttonComposite = new Composite(composite,SWT.NONE);		
		gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.horizontalSpacing = 0;
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		buttonComposite.setLayout(gridLayout);

		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		buttonComposite.setLayoutData(gridData);
		
		playButton = new Button(buttonComposite, SWT.PUSH | SWT.LEFT);
		playButton.setText("Lancer");
		playButton.setEnabled(true); 
		playButton.setImage(new Image(this.parent.getDisplay(), RessourcePathPointer.class.getResourceAsStream("play.png")));
		playButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				PlayerManager.getInstance().playRandom();
				playButton.setEnabled(false); 
				stopButton.setEnabled(true);
			}
		});

		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		playButton.setLayoutData(gridData);
		
		stopButton = new Button(buttonComposite, SWT.PUSH | SWT.LEFT);
		stopButton.setImage(new Image(this.parent.getDisplay(), RessourcePathPointer.class.getResourceAsStream("stop.png")));
		stopButton.setText("Arrêter");
		stopButton.setEnabled(false);
		stopButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				PlayerManager.getInstance().stop();
				playButton.setEnabled(true); 
				stopButton.setEnabled(false); 
			}
		});
		
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		stopButton.setLayoutData(gridData);
		
		PlayerManager.getInstance().setRandomPlayerView(this);
		
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
			return 53;
		}
	}

	@Override
	public int getSizeFlags(boolean width) {
		return SWT.MIN;
	}


}
