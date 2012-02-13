package sonosip;

import java.awt.Dimension;

import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
        return new ApplicationActionBarAdvisor(configurer);
    }
    
    public void preWindowOpen() {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        
        Dimension screenDimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        configurer.setInitialSize(new Point((int)screenDimension.getWidth() - 100, (int)screenDimension.getHeight() - 100));
        configurer.setShowCoolBar(false);	
        configurer.setShowStatusLine(true);
        configurer.setShowProgressIndicator(true); 
        configurer.setTitle("SonoSip"); //$NON-NLS-1$
    }
    
    public void postWindowCreate() {
    	getWindowConfigurer().getWindow().getShell().setMaximized(true);
    }
    
}
