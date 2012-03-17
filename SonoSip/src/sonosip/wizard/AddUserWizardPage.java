package sonosip.wizard;

import java.util.Random;
import java.util.Vector;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import sonosip.Activator;
import sonosip.preferences.UsersPreferencePage;
import sonosip.ressources.RessourcePathPointer;

public class AddUserWizardPage extends WizardPage {
	private Text userName;
	private Text accessCode;
	private Composite container;
	private Vector<String> accesCodeList;

	public AddUserWizardPage() {
		super("Ajouter un utilisateur");
		setTitle("Ajouter un utilisateur");
		setImageDescriptor(ImageDescriptor.createFromFile(RessourcePathPointer.class, "icon-user-add.png"));
		setDescription("Cet assistant vous permet d'ajouter un nouvel utilisateur\nafin qu'il puisse s'identifier par téléphone et participer aux réunions.");
		
		accesCodeList = new Vector<String>();
		String userList = Activator.getDefault().getPreferenceStore().getString(UsersPreferencePage.USER_LIST);
		String[] userArray = userList.split(UsersPreferencePage.USER_SEPARATOR);
		for (int i = 0 ; i < userArray.length ; i++) {
			String[] userPassword = userArray[i].split(UsersPreferencePage.USER_PASSWORD_SEPARATOR);
			accesCodeList.add(userPassword[0]);
		}
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
				if (!accesCodeList.contains(accessCode.getText()) && accessCode.getText().length() == 4 && !userName.getText().isEmpty()) {
					setPageComplete(true);
				} else {
					setPageComplete(false);
				}
			}

		});
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		userName.setLayoutData(gd);

		Label label2 = new Label(container, SWT.NULL);
		label2.setText("Code d'accés (4 chiffres) : ");


		Random random = new Random();
		String _accessCode = "0000" + random.nextInt(10000);
		_accessCode = _accessCode.substring(_accessCode.length() - 4);
		
		accessCode = new Text(container, SWT.BORDER | SWT.SINGLE);
		accessCode.setText(_accessCode);
		accessCode.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (!accesCodeList.contains(accessCode.getText()) && accessCode.getText().length() == 4 && !userName.getText().isEmpty()) {
					setPageComplete(true);
				} else {
					setPageComplete(false);
				}
			}

		});
		gd = new GridData(GridData.FILL_HORIZONTAL);
		accessCode.setLayoutData(gd);
		accessCode.setTextLimit(4);
		accessCode.addListener(SWT.Verify, new Listener() {
	        public void handleEvent(Event e) {
	          String string = e.text;
	          char[] chars = new char[string.length()];
	          string.getChars(0, chars.length, chars, 0);
	          for (int i = 0; i < chars.length; i++) {
	            if (!('0' <= chars[i] && chars[i] <= '9')) {
	              e.doit = false;
	              return;
	            }
	          }
	        }
	      });
		
		// Required to avoid an error in the system
		setControl(container);
		setPageComplete(false);

	}

	public String getUserName() {
		return accessCode.getText() + UsersPreferencePage.USER_PASSWORD_SEPARATOR + userName.getText();
	}

}
