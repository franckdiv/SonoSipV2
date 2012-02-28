package sonosip.wizard;

import java.util.Random;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import sonosip.ressources.RessourcePathPointer;

public class AddUserWizardPage extends WizardPage {
	private Text userName;
	private String accessCode;
	private Composite container;

	public AddUserWizardPage() {
		super("Ajouter un utilisateur");
		setTitle("Ajouter un utilisateur");
		setImageDescriptor(ImageDescriptor.createFromFile(RessourcePathPointer.class, "icon-user-add.png"));
		setDescription("Cet assistant vous permet d'ajouter un nouvel utilisateur\nafin qu'il puisse s'identifier par téléphone et participer aux réunions.");
		
		Random random = new Random();
		accessCode = "0000" + random.nextInt(10000);
		accessCode = accessCode.substring(accessCode.length() - 4);
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 1;
		Label label1 = new Label(container, SWT.NULL);
		label1.setText("Nom de l'utilisateur à ajouter : ");

		userName = new Text(container, SWT.BORDER | SWT.SINGLE);
		userName.setText("");
		userName.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (!userName.getText().isEmpty()) {
					setPageComplete(true);
				} else {
					setPageComplete(false);
				}
			}

		});
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		userName.setLayoutData(gd);

		Label label2 = new Label(container, SWT.NULL);
		label2.setText("Code d'accés : ");

		Label label3 = new Label(container, SWT.NULL);
		label3.setFont(new Font(parent.getDisplay(), "Arial", 16, SWT.BOLD));
		label3.setText(accessCode);
		
		// Required to avoid an error in the system
		setControl(container);
		setPageComplete(false);

	}

	public String getUserName() {
		return accessCode + " - " + userName.getText();
	}

}
