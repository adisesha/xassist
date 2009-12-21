package org.adi.xassist.editors;

import org.adi.xassist.Activator;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.eclipse.ui.texteditor.TextOperationAction;

public class XAssistEditor extends TextEditor {

	private ColorManager colorManager;

	public XAssistEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new XMLConfiguration(colorManager));
		setDocumentProvider(new XMLDocumentProvider());
		
	}
	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}
	
	@Override
	protected void createActions() {
		super.createActions();
		
		IAction a= new TextOperationAction(Activator.getResourceBundle(), "ContentAssistProposal.", this, ISourceViewer.CONTENTASSIST_PROPOSALS); //$NON-NLS-1$
		a.setActionDefinitionId(ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS);
		setAction("ContentAssistProposal", a);
	}

}
