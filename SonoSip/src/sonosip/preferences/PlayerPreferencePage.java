package sonosip.preferences;

import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import sonosip.Activator;


public class PlayerPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public static final String SONG_PATH = "preference.song.path";
	
	public PlayerPreferencePage() {
		super(GRID);
	}

	public void createFieldEditors() {
		addField(new DirectoryFieldEditor(SONG_PATH, "Dossier contenant les cantiques :", getFieldEditorParent()));
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Paramètrage du module de lecture des cantiques");
	}
}

