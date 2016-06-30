/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.xtext.testing;

import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.testing.TextDocumentConfiguration;
import org.eclipse.xtext.xbase.lib.Pure;

@Accessors
@SuppressWarnings("all")
public class RangeFormattingTestConfiguration extends TextDocumentConfiguration {
  private String expectedDocument = "";
  
  private int startLine = 0;
  
  private int startColumn = 0;
  
  private int endLine = 0;
  
  private int endColumn = 0;
  
  @Pure
  public String getExpectedDocument() {
    return this.expectedDocument;
  }
  
  public void setExpectedDocument(final String expectedDocument) {
    this.expectedDocument = expectedDocument;
  }
  
  @Pure
  public int getStartLine() {
    return this.startLine;
  }
  
  public void setStartLine(final int startLine) {
    this.startLine = startLine;
  }
  
  @Pure
  public int getStartColumn() {
    return this.startColumn;
  }
  
  public void setStartColumn(final int startColumn) {
    this.startColumn = startColumn;
  }
  
  @Pure
  public int getEndLine() {
    return this.endLine;
  }
  
  public void setEndLine(final int endLine) {
    this.endLine = endLine;
  }
  
  @Pure
  public int getEndColumn() {
    return this.endColumn;
  }
  
  public void setEndColumn(final int endColumn) {
    this.endColumn = endColumn;
  }
}
