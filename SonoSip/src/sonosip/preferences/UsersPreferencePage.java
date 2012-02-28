package sonosip.preferences;

import java.util.StringTokenizer;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import sonosip.Activator;
import sonosip.ressources.RessourcePathPointer;
import sonosip.wizard.AddUserWizardPage;


public class UsersPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {


	public static final String USER_LIST = "preference.softphone.userlist";
	
	private List userList;
	
	
	public class AddUserWizard extends Wizard {
		private AddUserWizardPage addUserWizardPage ;

		public AddUserWizard() {
			super();
			setNeedsProgressMonitor(true);
		}

		@Override
		public void addPages() {
			addUserWizardPage = new AddUserWizardPage();
			addPage(addUserWizardPage);
		}

		@Override
		public boolean performFinish() {
			userList.add(addUserWizardPage.getUserName());
			return true;
		}

	};
	
	protected Control createContents(Composite parent) {
		final Composite parentComposite = parent;
		Composite entryTable = new Composite(parent, SWT.NULL);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.grabExcessHorizontalSpace = true;
		entryTable.setLayoutData(data);
		GridLayout layout = new GridLayout();
		entryTable.setLayout(layout);			
		userList = new List(entryTable, SWT.BORDER);
		data = new GridData(GridData.FILL_BOTH);
		userList.setLayoutData(data);

		String 	userListString = Activator.getDefault().getPreferenceStore().getString(UsersPreferencePage.USER_LIST);
	
		StringTokenizer tokenizer = new StringTokenizer(userListString, "¤");
		int tokenCount = tokenizer.countTokens();
		String[] elements = new String[tokenCount];
	
		for (int i = 0; i < tokenCount; i++) {
			elements[i] = tokenizer.nextToken();
		}
		userList.setItems(elements);

		Composite buttonComposite = new Composite(entryTable,SWT.NULL);
		
		GridLayout buttonLayout = new GridLayout();
		buttonLayout.numColumns = 2;
		buttonComposite.setLayout(buttonLayout);
		data = new GridData(GridData.FILL_BOTH);
		buttonComposite.setLayoutData(data);
		
		Button addButton = new Button(buttonComposite, SWT.PUSH | SWT.CENTER);
		addButton.setText("Ajouter un utilisateur"); 
		addButton.setImage(new Image(parent.getDisplay(), RessourcePathPointer.class.getResourceAsStream("user--plus.png")));
		addButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {		
				AddUserWizard wizard = new AddUserWizard();
				WizardDialog dialog = new WizardDialog(parentComposite.getShell(), wizard);
				dialog.open();
			}
		});
		
		Button removeButton = new Button(buttonComposite, SWT.PUSH | SWT.CENTER);
		removeButton.setImage(new Image(parent.getDisplay(), RessourcePathPointer.class.getResourceAsStream("user--minus.png")));
		removeButton.setText("Supprimer l'utilisateur séléctionné");
		removeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if(userList.getSelectionIndex() != -1) {
					if(MessageDialog.openConfirm(parentComposite.getShell(), "Confirmation", "Souhaitez-vous vraiment supprimer cet utilisateur de la liste ?")) {
						userList.remove(userList.getSelectionIndex());
					}
				}
			}
		});

		
		return entryTable;
	}



	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Paramètrage des utilisateurs");
	}

	protected void performDefaults() {		
		String 	userListString = Activator.getDefault().getPreferenceStore().getString(UsersPreferencePage.USER_LIST);
	
		StringTokenizer tokenizer = new StringTokenizer(userListString, "¤");
		int tokenCount = tokenizer.countTokens();
		String[] elements = new String[tokenCount];
	
		for (int i = 0; i < tokenCount; i++) {
			elements[i] = tokenizer.nextToken();
		}
		userList.setItems(elements);
	}

	public boolean performOk() {
		String[] elements = userList.getItems();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < elements.length; i++) {
			buffer.append(elements[i]);
			buffer.append("¤");
		}
		Activator.getDefault().getPreferenceStore().setValue(UsersPreferencePage.USER_LIST, buffer.toString());
		return super.performOk();
	}
}