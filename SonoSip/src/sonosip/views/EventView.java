package sonosip.views;

import java.text.SimpleDateFormat;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.ISizeProvider;
import org.eclipse.ui.part.ViewPart;

import sonosip.ressources.RessourcePathPointer;
import sonosip.utils.Event;
import sonosip.utils.EventLogger;

public class EventView extends ViewPart implements ISizeProvider {

	public static final String ID = "sonosip.views.EventView";

	public EventView() {
		this.dateFormatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
	}

	private SimpleDateFormat dateFormatter;
	
	private Composite parent;
	private Table eventTable;

	@Override
	public void createPartControl(Composite parent) {
		
		this.parent = parent;

	    this.eventTable = new Table(this.parent, SWT.FULL_SELECTION);

	    this.eventTable.setLinesVisible (false);
	    this.eventTable.setHeaderVisible (false);
	    
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		this.eventTable.setLayoutData(gridData);

		TableColumn messageColumn = new TableColumn (this.eventTable, SWT.NONE);
		TableColumn dateColumn = new TableColumn (this.eventTable, SWT.NONE);
			
		TableColumnLayout tableLayout = new TableColumnLayout();
		this.parent.setLayout(tableLayout);
		
		tableLayout.setColumnData( messageColumn, new ColumnWeightData( 90 ) );
		tableLayout.setColumnData( dateColumn, new ColumnWeightData( 15 ) );

		EventLogger.setEventView(this);
	}
	
	public void addEvent(Event event) {
		final Event _event = event;
		this.parent.getDisplay().asyncExec (new Runnable () {
			public void run () {		
				TableItem item = new TableItem (eventTable, SWT.NONE);
				switch (_event.getEventType()) {
				case Event.EVENT_TYPE_ALERT:
					item.setImage(0, new Image(parent.getDisplay(), RessourcePathPointer.class.getResourceAsStream("alert10x10.png"))) ;
					break;
				case Event.EVENT_TYPE_ERROR:
					item.setImage(0, new Image(parent.getDisplay(), RessourcePathPointer.class.getResourceAsStream("error10x10.png"))) ;
					break;
				default:
					item.setImage(0, new Image(parent.getDisplay(), RessourcePathPointer.class.getResourceAsStream("information10x10.png"))) ;
					break;
				}
				item.setText (0, " " + _event.getMessage());
				item.setText (1, dateFormatter.format(_event.getDate()));
			}
		});
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
