/*******************************************************************************
 * Copyright (c) 2016 itemis AG (http://www.itemis.de) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.ide.server.formatting

import com.google.inject.Inject
import io.typefox.lsapi.Range
import io.typefox.lsapi.RangeImpl
import io.typefox.lsapi.TextEdit
import io.typefox.lsapi.TextEditImpl
import java.util.Collections
import java.util.List
import javax.inject.Provider
import javax.inject.Singleton
import org.eclipse.xtext.formatting.INodeModelFormatter
import org.eclipse.xtext.formatting.INodeModelFormatter.IFormattedRegion
import org.eclipse.xtext.formatting2.FormatterRequest
import org.eclipse.xtext.formatting2.IFormatter2
import org.eclipse.xtext.formatting2.regionaccess.ITextReplacement
import org.eclipse.xtext.formatting2.regionaccess.TextRegionAccessBuilder
import org.eclipse.xtext.ide.server.Document
import org.eclipse.xtext.ide.server.DocumentExtensions
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.util.TextRegion

/**
 * Service for the Formatter
 * 
 * @author Christian DIetrich - Initial contribution and API
 * @since 2.11
 */
@Singleton
class FormattingService {

	@Inject(optional=true) INodeModelFormatter formatter1

	@Inject(optional=true) Provider<IFormatter2> formatter2Provider

	@Inject Provider<FormatterRequest> formatterRequestProvider

	@Inject TextRegionAccessBuilder regionBuilder

	@Inject extension DocumentExtensions

	/**
	 * Format the given document. This operation modifies the document content and returns the 
	 */
	public def List<TextEdit> format(Document document, XtextResource resource, Range range) {
		if (formatter2Provider !== null) {
			return format2(document, resource, range)
		} else if (formatter1 !== null) {
			return format1(document, resource, range)
		} else {
			return Collections.emptyList
		}

	}
	
	protected def List<TextEdit> format1(Document document, XtextResource resource, Range range) {
		val offset = document.offsetFor(range)
		val length = document.lengthFor(range)
		val parseResult = resource.parseResult
		if (parseResult === null)
			return #[]
		val rootNode = parseResult.rootNode
		return #[resource.toTextEdit(formatter1.format(rootNode, offset, length))]
	}
	

	protected def List<TextEdit> format2(Document document, XtextResource resource, Range range) {
		val request = formatterRequestProvider.get()
		request.allowIdentityEdits = false
		request.formatUndefinedHiddenRegionsOnly = false
		if (range !== null) {
			val offset = document.offsetFor(range)
			val length = document.lengthFor(range)
			request.regions = #[new TextRegion(offset, length)]
		}
		val regionAccess = regionBuilder.forNodeModel(resource).create()
		request.textRegionAccess = regionAccess
		val formatter2 = formatter2Provider.get();
		val replacements = formatter2.format(request)
		return replacements.map [resource.toTextEdit(it)]
	}
	
	private def TextEdit toTextEdit(XtextResource resource, ITextReplacement textReplacement) {
		new TextEditImpl => [ it |
			newText = textReplacement.replacementText
			range = new RangeImpl() => [
				start = resource.newPosition(textReplacement.offset)
				end = resource.newPosition(textReplacement.offset + textReplacement.length)
			]
		]

	}
	private def TextEdit toTextEdit(XtextResource resource, IFormattedRegion textReplacement) {
		new TextEditImpl => [ it |
			newText = textReplacement.formattedText
			range = new RangeImpl() => [
				start = resource.newPosition(textReplacement.offset)
				end = resource.newPosition(textReplacement.offset + textReplacement.length)
			]
		]

	}

	def private offsetFor(Document document, Range range) {
		if (range == null) return 0
		document.getOffSet(range.start)
	}
	
	def private lengthFor(Document document, Range range) {
		if (range == null) return document.contents.length
		document.getOffSet(range.end)-document.getOffSet(range.start)
	}
	

}
