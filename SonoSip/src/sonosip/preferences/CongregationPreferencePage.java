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
import sonosip.wizard.AddCongregationWizardPage;


public class CongregationPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {


	public static final String CONGREGATION_LIST = "preference.recorder.congregationlist";
	
	private List congregationList;
	
	
	public class AddCongregationWizard extends Wizard {
		private AddCongregationWizardPage addCongregationWizardPage ;

		public AddCongregationWizard() {
			super();
			setNeedsProgressMonitor(true);
		}

		@Override
		public void addPages() {
			addCongregationWizardPage = new AddCongregationWizardPage();
			addPage(addCongregationWizardPage);
		}

		@Override
		public boolean performFinish() {
			congregationList.add(addCongregationWizardPage.getCongregationName());
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
		congregationList = new List(entryTable, SWT.BORDER);
		data = new GridData(GridData.FILL_BOTH);
		congregationList.setLayoutData(data);

		String 	congregationListString = Activator.getDefault().getPreferenceStore().getString(CongregationPreferencePage.CONGREGATION_LIST);
	
		StringTokenizer tokenizer = new StringTokenizer(congregationListString, "¤");
		int tokenCount = tokenizer.countTokens();
		String[] elements = new String[tokenCount];
	
		for (int i = 0; i < tokenCount; i++) {
			elements[i] = tokenizer.nextToken();
		}
		congregationList.setItems(elements);

		Composite buttonComposite = new Composite(entryTable,SWT.NULL);
		
		GridLayout buttonLayout = new GridLayout();
		buttonLayout.numColumns = 2;
		buttonComposite.setLayout(buttonLayout);
		data = new GridData(GridData.FILL_BOTH);
		buttonComposite.setLayoutData(data);
		
		Button addButton = new Button(buttonComposite, SWT.PUSH | SWT.CENTER);
		addButton.setText("Ajouter une congrégation"); 
		addButton.setImage(new Image(parent.getDisplay(), RessourcePathPointer.class.getResourceAsStream("building--plus.png")));
		addButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {		
				AddCongregationWizard wizard = new AddCongregationWizard();
				WizardDialog dialog = new WizardDialog(parentComposite.getShell(), wizard);
				dialog.open();
			}
		});
		
		Button removeButton = new Button(buttonComposite, SWT.PUSH | SWT.CENTER);
		removeButton.setImage(new Image(parent.getDisplay(), RessourcePathPointer.class.getResourceAsStream("building--minus.png")));
		removeButton.setText("Supprimer la congrégation séléctionnée");
		removeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if(congregationList.getSelectionIndex() != -1) {
					if(MessageDialog.openConfirm(parentComposite.getShell(), "Confirmation", "Souhaitez-vous vraiment supprimer cette congrégation de la liste ?")) {
						congregationList.remove(congregationList.getSelectionIndex());
					}
				}
			}
		});

		
		return entryTable;
	}



	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Paramètrage des enregistrements");
	}

	protected void performDefaults() {		
		String 	congregationListString = Activator.getDefault().getPreferenceStore().getString(CongregationPreferencePage.CONGREGATION_LIST);
	
		StringTokenizer tokenizer = new StringTokenizer(congregationListString, "¤");
		int tokenCount = tokenizer.countTokens();
		String[] elements = new String[tokenCount];
	
		for (int i = 0; i < tokenCount; i++) {
			elements[i] = tokenizer.nextToken();
		}
		congregationList.setItems(elements);
	}

	public boolean performOk() {
		String[] elements = congregationList.getItems();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < elements.length; i++) {
			buffer.append(elements[i]);
			buffer.append("¤");
		}
		Activator.getDefault().getPreferenceStore().setValue(CONGREGATION_LIST, buffer.toString());
		return super.performOk();
	}
}