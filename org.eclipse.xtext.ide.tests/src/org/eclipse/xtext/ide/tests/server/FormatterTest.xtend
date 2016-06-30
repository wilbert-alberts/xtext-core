/*******************************************************************************
 * Copyright (c) 2016 itemis AG (http://www.itemis.de) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.ide.tests.server

import org.junit.Test

/**
 * @author Christian Dietrich - Initial contribution and API
 */
class FormatterTest extends AbstractTestLangLanguageServerTest {
	
	@Test
	def void testFormatting_01() {
		testFormatting[
			model = '''
				type Foo {
				}
			'''
			expectedDocument = '''
				type Foo { }
			'''
		]
	}
	
	@Test
	def void testRangeFormatting_01() {
		testRangeFormatting[
			model = '''
				type Foo {
				}
				type Bar {
				}
			'''
			startLine = 2
			startColumn = 0
			endLine = 3
			endColumn = 1
			expectedDocument = '''
				type Foo {
				}
				type Bar { }
			'''
		]
	}
	
}