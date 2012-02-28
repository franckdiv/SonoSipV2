package sonosip.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import sonosip.Activator;


public class SipPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {


	public static final String USER 			= "preference.sip.user";
	public static final String PASSWORD 		= "preference.sip.password";
	public static final String REALM 			= "preference.sip.realm";
	
	public SipPreferencePage() {
		super(GRID);
	}

	public void createFieldEditors() {		
		addField(new StringFieldEditor(USER, "Utilisateur :", getFieldEditorParent()));
		addField(new StringFieldEditor(PASSWORD, "Mot de passe :", getFieldEditorParent()));
		addField(new StringFieldEditor(REALM, "Domaine :", getFieldEditorParent()));
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Paramètrage du module de téléphonie SIP");
	}
}