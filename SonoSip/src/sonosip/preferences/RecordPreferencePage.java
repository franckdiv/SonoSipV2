package sonosip.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import sonosip.Activator;


public class RecordPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {


	public RecordPreferencePage() {
	}

	public void createFieldEditors() {
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Paramètrage du dossier de destination des enregistrements et des congrégations");
	}
}