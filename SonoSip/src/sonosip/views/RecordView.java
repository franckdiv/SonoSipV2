package sonosip.views;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import sonosip.record.RecordManager;
import sonosip.record.RecordState;
import sonosip.ressources.RessourcePathPointer;

public class RecordView extends ViewPart implements ISizeProvider {

	public static final String ID = "sonosip.views.RecordView";
	

	private Combo congregationListCombo;
	private Combo meetingTypeListCombo;
	private Composite parent;
	private Button stopButton;
	private Button recButton;
	private Button openButton;
	
	public RecordView() {
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
			return 187;
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
		infoLabel.setText("Sélectionnez la congrégation :");
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		infoLabel.setLayoutData(gridData);
		
		this.congregationListCombo = new Combo(composite, SWT.READ_ONLY | SWT.DROP_DOWN);
		this.congregationListCombo.setVisibleItemCount(30);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.congregationListCombo.setLayoutData(gridData);
		this.congregationListCombo.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(congregationListCombo.getSelectionIndex() != -1 && meetingTypeListCombo.getSelectionIndex() != -1) {
					recButton.setEnabled(true);
				} else {
					recButton.setEnabled(false);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {	
			}
		});
		

		Label infoLabel2 = new Label(composite, SWT.NONE);
		infoLabel2.setText("Sélectionnez le type de réunion :");
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		infoLabel2.setLayoutData(gridData);
		
		this.meetingTypeListCombo = new Combo(composite, SWT.READ_ONLY | SWT.DROP_DOWN);
		this.meetingTypeListCombo.setVisibleItemCount(30);
		this.meetingTypeListCombo.setItems(new String[] {"EMT & Réunion de service","Discours public & TG","Réunion spéciale"});
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.meetingTypeListCombo.setLayoutData(gridData);
		this.meetingTypeListCombo.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(congregationListCombo.getSelectionIndex() != -1 && meetingTypeListCombo.getSelectionIndex() != -1) {
					recButton.setEnabled(true);
				} else {
					recButton.setEnabled(false);
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
		
		this.recButton = new Button(buttonComposite, SWT.PUSH | SWT.LEFT);
		this.recButton.setText("Enregistrer");
		this.recButton.setEnabled(false); 
		this.recButton.setImage(new Image(this.parent.getDisplay(), RessourcePathPointer.class.getResourceAsStream("record.png")));
		this.recButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {	
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
				String recordName = congregationListCombo.getText() + "_" + meetingTypeListCombo.getText() + "_" + simpleDateFormat.format(new Date(System.currentTimeMillis()));
				RecordManager.getInstance().record(recordName);
			}
		});

		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.recButton.setLayoutData(gridData);
		
		this.stopButton = new Button(buttonComposite, SWT.PUSH | SWT.LEFT);
		this.stopButton.setImage(new Image(this.parent.getDisplay(), RessourcePathPointer.class.getResourceAsStream("stop.png")));
		this.stopButton.setText("Stop");
		this.stopButton.setEnabled(false);
		this.stopButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				RecordManager.getInstance().stop();
			}
		});
		
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.stopButton.setLayoutData(gridData);
		
		
		this.openButton = new Button(composite, SWT.PUSH | SWT.LEFT);
		this.openButton.setImage(new Image(this.parent.getDisplay(), RessourcePathPointer.class.getResourceAsStream("folder-horizontal-open.png")));
		this.openButton.setText("Ouvrir le dossier des enregistrements");
		this.openButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				RecordManager.getInstance().openRecordFolder();
			}
		});
		
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.openButton.setLayoutData(gridData);
		

		RecordManager.getInstance().setRecordView(this);
		
	}
	
	public void updateCongregationList(Vector<String> congregationList) {
		final Vector<String> _congregationList = congregationList;
		this.parent.getDisplay().asyncExec (new Runnable () {
			public void run () {	
				congregationListCombo.removeAll();
				String[] congregationNameValues = new String[_congregationList.size()];
				for (int i = 0 ; i < _congregationList.size() ; i++) {
					congregationNameValues[i] = _congregationList.get(i);
				}
				congregationListCombo.setItems(congregationNameValues);
			}
		});
	}
	

	
	public void handleRecordState(int recordState) {
		final int _recordState = recordState;
		this.parent.getDisplay().asyncExec (new Runnable () {
			public void run () {	
				if(_recordState == RecordState.RECORD) {
					stopButton.setEnabled(true); 
					recButton.setEnabled(false); 
				} else if(_recordState == RecordState.STOP) { 
					stopButton.setEnabled(false); 
					recButton.setEnabled(true); 
				}
			}
		});
	}
}
