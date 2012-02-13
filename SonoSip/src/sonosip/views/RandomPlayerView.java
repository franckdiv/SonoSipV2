package sonosip.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class RandomPlayerView extends ViewPart {

	public static final String ID = "sonosip.views.RandomPlayerView";
	
	public RandomPlayerView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		Text text = new Text(parent, SWT.BORDER);
		text.setText("Imagine a fantastic user interface here");

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
