/*******************************************************************************
 * Copyright (c) 2015 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.xtext;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.CompoundElement;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.serializer.XtextSyntacticSequencer;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynNavigable;

import com.google.common.base.Strings;

/**
 * @author Moritz Eysholdt - Initial contribution and API
 */
public class XtextXtextSyntacticSequencer extends XtextSyntacticSequencer {

	@Override
	protected void emit_ParenthesizedElement_LeftParenthesisKeyword_0_a(EObject semanticObject, ISynNavigable transition,
			List<INode> nodes) {
		if (semanticObject instanceof CompoundElement) {
			AbstractElement ele = (CompoundElement) semanticObject;
			if (!Strings.isNullOrEmpty(ele.getCardinality())) {
				Keyword kw = grammarAccess.getParenthesizedElementAccess().getLeftParenthesisKeyword_0();
				acceptUnassignedKeyword(kw, kw.getValue(), nodes == null || nodes.isEmpty() ? null : (ILeafNode) nodes.get(0));
			}
		}
		super.emit_ParenthesizedCondition_LeftParenthesisKeyword_0_a(semanticObject, transition, nodes);
	}
}
