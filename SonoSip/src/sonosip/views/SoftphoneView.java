package sonosip.views;

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
	
	public SoftphoneView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		
		
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

		
		CallListItem c = new CallListItem(listItemHolder,"Hidalgo Luc", 1, 5);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		c.setLayoutData(gridData);


		c = new CallListItem(listItemHolder,"Hidalgo Luc", 1, 6);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		c.setLayoutData(gridData);
		
		c = new CallListItem(listItemHolder,"Hidalgo Luc", 1, 7);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		c.setLayoutData(gridData);
		
		c = new CallListItem(listItemHolder,"Hidalgo Luc", 1, 8);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		c.setLayoutData(gridData);
		
		c = new CallListItem(listItemHolder,"Hidalgo Luc", 1, 9);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		c.setLayoutData(gridData);
		

		computeScrolledCompositeSize();
		

		SoftphoneManager.getInstance().setSoftphoneView(this);
	}
	

	private void computeScrolledCompositeSize() {
		scrolledComposite.setMinHeight(listItemHolder.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
