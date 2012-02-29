package sonosip.views;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import sonosip.softphone.SoftphoneManager;
import sonosip.softphone.ui.CallListItem;

public class SoftphoneView extends ViewPart {

	public static final String ID = "sonosip.views.SoftphoneView";
	
	private ScrolledComposite 				scrolledComposite;
	private Composite 						listItemHolder;
	private Composite 						parent;
	private HashMap<Integer, CallListItem> 	callListItemList;
	
	public SoftphoneView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		
		this.parent = parent;
		scrolledComposite = new ScrolledComposite(parent, SWT.V_SCROLL | SWT.H_SCROLL);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setMinWidth(600);
		scrolledComposite.getVerticalBar().setIncrement(30);
		
		listItemHolder = new Composite(scrolledComposite, SWT.NONE);
	    GridLayout gridLayout = new GridLayout();
	    gridLayout.numColumns = 1;
	    listItemHolder.setLayout(gridLayout);
		listItemHolder.setBackground(new Color(parent.getDisplay(), 255, 255, 255));
	    
	    scrolledComposite.setContent(listItemHolder);
		
		computeScrolledCompositeSize();
		SoftphoneManager.getInstance().setSoftphoneView(this);
	}
	

	private void computeScrolledCompositeSize() {
		scrolledComposite.setMinHeight(listItemHolder.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
	}

	@Override
	public void setFocus() {

	}
	
	public void addCall(final int callId, final String callerName, final int callStatus) {
		this.parent.getDisplay().asyncExec (new Runnable () {
			public void run () {	
				CallListItem c = new CallListItem(listItemHolder, callerName, callId, callStatus);
				GridData gridData = new GridData();
				gridData.horizontalAlignment = SWT.FILL;
				gridData.grabExcessHorizontalSpace = true;
				c.setLayoutData(gridData);
				
				callListItemList.put(new Integer(callId), c);
				
				computeScrolledCompositeSize();
			}
		});
	}
	
	public void updateCallStatus(final int callId, final int callStatus) {
		this.parent.getDisplay().asyncExec (new Runnable () {
			public void run () {	
				CallListItem c = callListItemList.get(new Integer(callId));
				c.handleCallStatus(callStatus);
			}
		});
	}
	
	public void removeCall(final int callId) {
		this.parent.getDisplay().asyncExec (new Runnable () {
			public void run () {	
				CallListItem c = callListItemList.get(new Integer(callId));
				c.dispose();
				computeScrolledCompositeSize();
				callListItemList.remove(new Integer(callId));
			}
		});
	}
	
	
}
