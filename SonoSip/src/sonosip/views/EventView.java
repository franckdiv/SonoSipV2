package sonosip.views;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.ISizeProvider;
import org.eclipse.ui.part.ViewPart;

import sonosip.ressources.RessourcePathPointer;

public class EventView extends ViewPart implements ISizeProvider {

	public static final String ID = "sonosip.views.EventView";

	public EventView() {
		// TODO Auto-generated constructor stub
	}

	private Composite parent;
	

	@Override
	public void createPartControl(Composite parent) {
		
		this.parent = parent;
		
	    

	    Table eventTable = new Table(this.parent, SWT.FULL_SELECTION);

	    eventTable.setLinesVisible (false);
	    eventTable.setHeaderVisible (false);
	    
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		eventTable.setLayoutData(gridData);

		TableColumn messageColumn = new TableColumn (eventTable, SWT.NONE);
		TableColumn dateColumn = new TableColumn (eventTable, SWT.NONE);
			
		TableColumnLayout tableLayout = new TableColumnLayout();
		this.parent.setLayout(tableLayout);
		
		tableLayout.setColumnData( messageColumn, new ColumnWeightData( 90 ) );
		tableLayout.setColumnData( dateColumn, new ColumnWeightData( 15 ) );
			
		TableItem item = new TableItem (eventTable, SWT.NONE);
		item.setImage(0, new Image(this.parent.getDisplay(), RessourcePathPointer.class.getResourceAsStream("information10x10.png"))) ;
		item.setText (0, "Connecté au serveur de téléphonie");
		item.setText (1, "12/05/2012 - 14:20"); 
		
		item = new TableItem (eventTable, SWT.NONE);
		item.setImage(0, new Image(this.parent.getDisplay(), RessourcePathPointer.class.getResourceAsStream("information10x10.png"))) ;
		item.setText (0, "Debut de la retransmition");
		item.setText (1, "12/05/2012 - 14:25");
		
		item = new TableItem (eventTable, SWT.NONE);
		item.setImage(0, new Image(this.parent.getDisplay(), RessourcePathPointer.class.getResourceAsStream("alert10x10.png"))) ;
		item.setText (0, "Déconnecté du serveur de téléphonie");
		item.setText (1, "12/05/2012 - 14:26");
		
		item = new TableItem (eventTable, SWT.NONE);
		item.setImage(0, new Image(this.parent.getDisplay(), RessourcePathPointer.class.getResourceAsStream("error10x10.png"))) ;
		item.setText (0, "Impossible de se connecter au serveur de téléphonie. Identifiants incorrects");
		item.setText (1, "12/05/2012 - 14:29");
		
		

		
		
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
			return 150;
		}
	}

	@Override
	public int getSizeFlags(boolean width) {
		return SWT.MIN;
	}
}
