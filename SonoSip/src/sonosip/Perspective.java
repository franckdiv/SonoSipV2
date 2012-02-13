package sonosip;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import sonosip.views.EventView;
import sonosip.views.PlayerView;
import sonosip.views.RandomPlayerView;
import sonosip.views.RecordView;
import sonosip.views.SoftphoneView;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		layout.setFixed(true);
		layout.setEditorAreaVisible(false);	
		layout.addStandaloneView(SoftphoneView.ID, true, IPageLayout.TOP, IPageLayout.RATIO_MAX, IPageLayout.ID_EDITOR_AREA);
		layout.getViewLayout(SoftphoneView.ID).setCloseable(false);  
		layout.getViewLayout(SoftphoneView.ID).setMoveable(false);
		
		layout.addStandaloneView(EventView.ID, true, IPageLayout.BOTTOM, 1f, SoftphoneView.ID);
		layout.getViewLayout(EventView.ID).setCloseable(false);  
		layout.getViewLayout(EventView.ID).setMoveable(false);

		layout.addStandaloneView(PlayerView.ID, true, IPageLayout.LEFT, 0f , SoftphoneView.ID);
		layout.getViewLayout(PlayerView.ID).setCloseable(false);  
		layout.getViewLayout(PlayerView.ID).setMoveable(false);
		
		layout.addStandaloneView(RandomPlayerView.ID, true, IPageLayout.BOTTOM, 1f , PlayerView.ID);
		layout.getViewLayout(RandomPlayerView.ID).setCloseable(false);  
		layout.getViewLayout(RandomPlayerView.ID).setMoveable(false);
		
		layout.addStandaloneView(RecordView.ID, true, IPageLayout.BOTTOM, 1f, RandomPlayerView.ID);
		layout.getViewLayout(RecordView.ID).setCloseable(false);  
		layout.getViewLayout(RecordView.ID).setMoveable(false);
		 
	}
}