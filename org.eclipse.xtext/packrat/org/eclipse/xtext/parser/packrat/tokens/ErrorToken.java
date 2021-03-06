/*******************************************************************************
 * Copyright (c) 2008 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.parser.packrat.tokens;

import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.parser.packrat.IParsedTokenVisitor;

/**
 * @author Sebastian Zarnekow - Initial contribution and API
 */
public class ErrorToken extends ParsedToken {

	private final String errorMessage;

	public ErrorToken(int offset, int length, AbstractElement grammarElement, String errorMessage) {
		super(offset, length, grammarElement, null, false);
		this.errorMessage = errorMessage;
	}

	@Override
	public void accept(IParsedTokenVisitor visitor) {
		visitor.visitErrorToken(this);
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public AbstractElement getGrammarElement() {
		return (AbstractElement) super.getGrammarElement();
	}

	@Override
	public String toString() {
		return super.toString() + " message: '" + errorMessage + "'";
	}

}
