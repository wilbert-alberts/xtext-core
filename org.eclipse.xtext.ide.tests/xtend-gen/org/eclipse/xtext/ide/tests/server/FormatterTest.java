/**
 * Copyright (c) 2016 itemis AG (http://www.itemis.de) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.xtext.ide.tests.server;

import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.ide.tests.server.AbstractTestLangLanguageServerTest;
import org.eclipse.xtext.testing.FormattingTestConfiguration;
import org.eclipse.xtext.testing.RangeFormattingTestConfiguration;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.junit.Test;

/**
 * @author Christian Dietrich - Initial contribution and API
 */
@SuppressWarnings("all")
public class FormatterTest extends AbstractTestLangLanguageServerTest {
  @Test
  public void testFormatting_01() {
    final Procedure1<FormattingTestConfiguration> _function = (FormattingTestConfiguration it) -> {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("type Foo {");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      it.setModel(_builder.toString());
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("type Foo { }");
      _builder_1.newLine();
      it.setExpectedDocument(_builder_1.toString());
    };
    this.testFormatting(_function);
  }
  
  @Test
  public void testRangeFormatting_01() {
    final Procedure1<RangeFormattingTestConfiguration> _function = (RangeFormattingTestConfiguration it) -> {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("type Foo {");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      _builder.append("type Bar {");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      it.setModel(_builder.toString());
      it.setStartLine(2);
      it.setStartColumn(0);
      it.setEndLine(3);
      it.setEndColumn(1);
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("type Foo {");
      _builder_1.newLine();
      _builder_1.append("}");
      _builder_1.newLine();
      _builder_1.append("type Bar { }");
      _builder_1.newLine();
      it.setExpectedDocument(_builder_1.toString());
    };
    this.testRangeFormatting(_function);
  }
}
