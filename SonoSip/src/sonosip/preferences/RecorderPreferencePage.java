package sonosip.preferences;

import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import sonosip.Activator;


public class RecorderPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	public static final String RECORD_PATH = "preference.record.path";

	public RecorderPreferencePage() {
		super(GRID);
	}

	public void createFieldEditors() {
		addField(new DirectoryFieldEditor(RECORD_PATH, "Dossier de destination :", getFieldEditorParent()));
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Paramètrage du dossier de destination des enregistrements");
	}
}