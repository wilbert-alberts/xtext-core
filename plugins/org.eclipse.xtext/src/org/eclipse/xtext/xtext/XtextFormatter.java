/*******************************************************************************
 * Copyright (c) 2008 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.xtext;

import org.eclipse.xtext.formatting.impl.AbstractDeclarativeFormatter;
import org.eclipse.xtext.formatting.impl.FormattingConfig;
import org.eclipse.xtext.services.XtextGrammarAccess;
import org.eclipse.xtext.services.XtextGrammarAccess.AbstractTokenWithCardinalityElements;
import org.eclipse.xtext.services.XtextGrammarAccess.ActionElements;
import org.eclipse.xtext.services.XtextGrammarAccess.AlternativesElements;
import org.eclipse.xtext.services.XtextGrammarAccess.AssignmentElements;
import org.eclipse.xtext.services.XtextGrammarAccess.CharacterRangeElements;
import org.eclipse.xtext.services.XtextGrammarAccess.CrossReferenceElements;
import org.eclipse.xtext.services.XtextGrammarAccess.EnumLiteralDeclarationElements;
import org.eclipse.xtext.services.XtextGrammarAccess.EnumRuleElements;
import org.eclipse.xtext.services.XtextGrammarAccess.GeneratedMetamodelElements;
import org.eclipse.xtext.services.XtextGrammarAccess.GrammarElements;
import org.eclipse.xtext.services.XtextGrammarAccess.GrammarIDElements;
import org.eclipse.xtext.services.XtextGrammarAccess.NegatedTokenElements;
import org.eclipse.xtext.services.XtextGrammarAccess.ParenthesizedElementElements;
import org.eclipse.xtext.services.XtextGrammarAccess.ParenthesizedTerminalElementElements;
import org.eclipse.xtext.services.XtextGrammarAccess.ParserRuleElements;
import org.eclipse.xtext.services.XtextGrammarAccess.ReferencedMetamodelElements;
import org.eclipse.xtext.services.XtextGrammarAccess.TerminalRuleElements;
import org.eclipse.xtext.services.XtextGrammarAccess.TerminalTokenElements;
import org.eclipse.xtext.services.XtextGrammarAccess.TypeRefElements;
import org.eclipse.xtext.services.XtextGrammarAccess.UntilTokenElements;

/**
 * @author Moritz Eysholdt - Initial contribution and API
 */
public class XtextFormatter extends
		AbstractDeclarativeFormatter {

	@Override
	protected void configureFormatting(FormattingConfig cfg) {
		XtextGrammarAccess g = (XtextGrammarAccess) getGrammarAccess();

		// Grammar
		GrammarElements gr = g.getGrammarAccess();
		cfg.setLinewrap().between(g.getML_COMMENTRule(), gr.getGrammarKeyword_0());
		cfg.setNoSpace().after(gr.getLeftParenthesisKeyword_3_1());
		cfg.setNoSpace().before(gr.getRightParenthesisKeyword_3_3());
		cfg.setNoSpace().before(gr.getCommaKeyword_2_2_0());
		cfg.setNoSpace().before(gr.getCommaKeyword_3_2_1_0());
		// cfg.setLinewrap(2).after(gr.getGroup_2());
		// cfg.setLinewrap(2).after(gr.getMetamodelDeclarationsAssignment_4());
		// cfg.setLinewrap(2).after(gr.getRulesAssignment_5());
		
		// GrammarID
		GrammarIDElements gi = g.getGrammarIDAccess();
		cfg.setNoSpace().around(gi.getFullStopKeyword_1_0());

		// Generate
		GeneratedMetamodelElements gm = g.getGeneratedMetamodelAccess();
		cfg.setLinewrap(2).before(gm.getGenerateKeyword_0());

		// Import
		ReferencedMetamodelElements rm = g.getReferencedMetamodelAccess();
		cfg.setLinewrap(2).before(rm.getImportKeyword_0());

		// ParserRule
		ParserRuleElements pr = g.getParserRuleAccess();
		cfg.setLinewrap(2).before(pr.getNameIDTerminalRuleCall_0_0());
		cfg.setLinewrap().after(pr.getColonKeyword_3());
		cfg.setIndentation(pr.getColonKeyword_3(), pr.getSemicolonKeyword_5());
		cfg.setNoSpace().before(pr.getColonKeyword_3());
		cfg.setNoSpace().before(pr.getSemicolonKeyword_5());

		// TerminalRule
		TerminalRuleElements tr = g.getTerminalRuleAccess();
		cfg.setLinewrap(2).before(tr.getTerminalKeyword_0());
		cfg.setLinewrap().after(tr.getColonKeyword_3());
		cfg.setIndentation(tr.getColonKeyword_3(), tr.getSemicolonKeyword_5());
		cfg.setNoSpace().before(tr.getColonKeyword_3());
		cfg.setNoSpace().before(tr.getSemicolonKeyword_5());

		// Assignment
		AssignmentElements as = g.getAssignmentAccess();
		cfg.setNoSpace().around(as.getOperatorEqualsSignKeyword_1_0_1());
		cfg.setNoLinewrap().around(as.getOperatorEqualsSignKeyword_1_0_1());
		cfg.setNoSpace().around(as.getOperatorPlusSignEqualsSignKeyword_1_0_0());
		cfg.setNoLinewrap().around(as.getOperatorPlusSignEqualsSignKeyword_1_0_0());
		cfg.setNoSpace().around(as.getOperatorQuestionMarkEqualsSignKeyword_1_0_2());
		cfg.setNoLinewrap().around(as.getOperatorQuestionMarkEqualsSignKeyword_1_0_2());

		// AbstractToken
		AbstractTokenWithCardinalityElements at = g
				.getAbstractTokenWithCardinalityAccess();
		cfg.setNoSpace().before(at.getCardinalityAsteriskKeyword_1_0_1());
		cfg.setNoSpace().before(at.getCardinalityPlusSignKeyword_1_0_2());
		cfg.setNoSpace().before(at.getCardinalityQuestionMarkKeyword_1_0_0());

		// ParenthesizedElement
		ParenthesizedElementElements pe = g.getParenthesizedElementAccess();
		cfg.setNoSpace().after(pe.getLeftParenthesisKeyword_0());
		cfg.setNoSpace().before(pe.getRightParenthesisKeyword_2());

		// CrossReference
		CrossReferenceElements cr = g.getCrossReferenceAccess();
		cfg.setNoSpace().after(cr.getLeftSquareBracketKeyword_0());
		cfg.setNoSpace().before(cr.getRightSquareBracketKeyword_3());
		cfg.setNoSpace().around(cr.getVerticalLineKeyword_2_0());

		// Alternatives
		AlternativesElements al = g.getAlternativesAccess();
		cfg.setNoSpace().around(al.getVerticalLineKeyword_1_1_0());

		// Action
		ActionElements ac = g.getActionAccess();
		cfg.setNoSpace().around(ac.getOperatorAssignment_2_2());
		cfg.setNoSpace().around(ac.getFullStopKeyword_2_0());
		cfg.setNoSpace().after(ac.getLeftCurlyBracketKeyword_0());
		cfg.setNoSpace().before(ac.getRightCurlyBracketKeyword_3());
		cfg.setNoSpace().around(ac.getOperatorEqualsSignKeyword_2_2_0_0());
		cfg.setNoSpace().around(
				ac.getOperatorPlusSignEqualsSignKeyword_2_2_0_1());

		// TypeRef
		TypeRefElements typeRef = g.getTypeRefAccess();
		cfg.setNoSpace().around(typeRef.getColonColonKeyword_0_1());
		
		// EnumRule
		EnumRuleElements er = g.getEnumRuleAccess();
		cfg.setLinewrap(2).before(er.getEnumKeyword_0());
		cfg.setNoSpace().before(er.getColonKeyword_3());
		cfg.setLinewrap().after(er.getColonKeyword_3());
		cfg.setIndentation(er.getColonKeyword_3(), er.getSemicolonKeyword_5());
		cfg.setNoSpace().before(er.getSemicolonKeyword_5());
		
		// EnumLiteralDeclaration
		EnumLiteralDeclarationElements eld = g.getEnumLiteralDeclarationAccess();
		cfg.setNoSpace().around(eld.getEqualsSignKeyword_1_0());
		
		// TerminalToken
		TerminalTokenElements tt = g.getTerminalTokenAccess();
		cfg.setNoSpace().before(tt.getCardinalityAssignment_1());
		
		// ParenthesizedTerminalElement
		ParenthesizedTerminalElementElements pte = g.getParenthesizedTerminalElementAccess();
		cfg.setNoSpace().after(pte.getLeftParenthesisKeyword_0());
		cfg.setNoSpace().before(pte.getRightParenthesisKeyword_2());
		
		// CharacterRange
		CharacterRangeElements cre = g.getCharacterRangeAccess();
		cfg.setNoSpace().around(cre.getFullStopFullStopKeyword_1_1());
		
		// NegatedToken
		NegatedTokenElements ne = g.getNegatedTokenAccess();
		cfg.setNoSpace().after(ne.getExclamationMarkKeyword_0());
		
		// UntilToken
		UntilTokenElements ut = g.getUntilTokenAccess();
		cfg.setNoSpace().around(ut.getHyphenMinusGreaterThanSignKeyword_0());
	}

}