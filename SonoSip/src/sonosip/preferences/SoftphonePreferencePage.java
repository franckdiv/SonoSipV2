package sonosip.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import sonosip.Activator;


public class SoftphonePreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public static final String ACTIVATE_SOFTPHONE = "preference.softphone.activate";
	
	public SoftphonePreferencePage() {
		super(GRID);
	}

	public void createFieldEditors() {		
		addField(new BooleanFieldEditor(ACTIVATE_SOFTPHONE, "Activer le module de t�l�phonie", getFieldEditorParent()));

	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Param�trage du compte SIP et des utilisateurs");
	}
}
