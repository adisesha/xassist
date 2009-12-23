/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.ws.eclipse.xassist.editors.java;


import java.text.MessageFormat;
import java.util.ArrayList;

import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ContextInformation;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.ui.PlatformUI;


/**
 * Example Java completion processor.
 */
public class JavaCompletionProcessor extends TypePackageCompletionProcessor {
	
	

	/* (non-Javadoc)
	 * Method declared on IContentAssistProcessor
	 */
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int documentOffset) {
		
		IDocument doc = viewer.getDocument();
	
		
		String content=guessContentRequest(new int[]{documentOffset,documentOffset,documentOffset}, doc);
		
		if(content==null || content.length()==0){
			return null;
		}
		
		int docLen = doc.getLength();
		ArrayList list = new ArrayList();
		ICompletionProposal[] proposals = null;
		
		IResource resource=(IResource) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor().getEditorInput().getAdapter(IResource.class); 

		generateTypePackageProposals(content, resource.getProject(), list, documentOffset - content.length(), IJavaSearchConstants.CLASS_AND_INTERFACE);

		if ((list != null) && (list.size() != 0)) {
			// Convert the results array list into an array of completion
			// proposals
			proposals = (ICompletionProposal[]) list.toArray(new ICompletionProposal[list.size()]);
			
			return proposals;
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * Method declared on IContentAssistProcessor
	 */
	public IContextInformation[] computeContextInformation(ITextViewer viewer, int documentOffset) {
		IContextInformation[] result= new IContextInformation[5];
		for (int i= 0; i < result.length; i++)
			result[i]= new ContextInformation(
				MessageFormat.format("To Replace 3", new Object[] { new Integer(i), new Integer(documentOffset) }),  //$NON-NLS-1$
				MessageFormat.format("To Replace 4", new Object[] { new Integer(i), new Integer(documentOffset - 5), new Integer(documentOffset + 5)})); //$NON-NLS-1$
		return result;
	}
	
	/* (non-Javadoc)
	 * Method declared on IContentAssistProcessor
	 */
	public char[] getCompletionProposalAutoActivationCharacters() {
		return null;
	}
	
	/* (non-Javadoc)
	 * Method declared on IContentAssistProcessor
	 */
	public char[] getContextInformationAutoActivationCharacters() {
		return null;
	}
	
	/* (non-Javadoc)
	 * Method declared on IContentAssistProcessor
	 */
	public IContextInformationValidator getContextInformationValidator() {
		return null;
	}
	
	/* (non-Javadoc)
	 * Method declared on IContentAssistProcessor
	 */
	public String getErrorMessage() {
		return null;
	}
	
	private String guessContentRequest(int[] offset, IDocument doc) {
		StringBuffer attrValBuffer = new StringBuffer();
	
		String attVal = null;
		int quoteCount = 0;
		try {
			while (--offset[0] >= 0) {
				char c = doc.getChar(offset[0]);
				if (c == '"') {
					quoteCount += 1;
					if (attVal != null) // ran into 2nd quotation mark, we are out of range
						continue;
					offset[2] = offset[0];
					attVal = attrValBuffer.toString();
				}  else {
				   attrValBuffer.insert(0, c);
				}
			}
		} catch (BadLocationException e) {
		}
		
		return attVal;
	}
	
	
}
