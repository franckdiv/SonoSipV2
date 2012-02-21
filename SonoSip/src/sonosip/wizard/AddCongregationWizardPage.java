package sonosip.wizard;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import sonosip.ressources.RessourcePathPointer;

public class AddCongregationWizardPage extends WizardPage {
	private Text congregationName;
	private Composite container;

	public AddCongregationWizardPage() {
		super("Ajouter une congrégation");
		setTitle("Ajouter une congrégation");
		setImageDescriptor(ImageDescriptor.createFromFile(RessourcePathPointer.class, "home.png"));
		setDescription("Cet assistant vous permet d'ajouter le nom de votre congrégation\nafin d'enregistrer le programme des réunions sous son nom.");
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 1;
		Label label1 = new Label(container, SWT.NULL);
		label1.setText("Nom de la congrégation à ajouter : ");

		congregationName = new Text(container, SWT.BORDER | SWT.SINGLE);
		congregationName.setText("");
		congregationName.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (!congregationName.getText().isEmpty()) {
					setPageComplete(true);
				} else {
					setPageComplete(false);
				}
			}

		});
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		congregationName.setLayoutData(gd);
		// Required to avoid an error in the system
		setControl(container);
		setPageComplete(false);

	}

	public String getCongregationName() {
		return congregationName.getText();
	}

}
