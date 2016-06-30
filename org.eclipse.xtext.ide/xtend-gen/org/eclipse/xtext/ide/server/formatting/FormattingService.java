/**
 * Copyright (c) 2016 itemis AG (http://www.itemis.de) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.xtext.ide.server.formatting;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import io.typefox.lsapi.Position;
import io.typefox.lsapi.PositionImpl;
import io.typefox.lsapi.Range;
import io.typefox.lsapi.RangeImpl;
import io.typefox.lsapi.TextEdit;
import io.typefox.lsapi.TextEditImpl;
import java.util.Collections;
import java.util.List;
import javax.inject.Provider;
import javax.inject.Singleton;
import org.eclipse.xtext.formatting.INodeModelFormatter;
import org.eclipse.xtext.formatting2.FormatterRequest;
import org.eclipse.xtext.formatting2.IFormatter2;
import org.eclipse.xtext.formatting2.regionaccess.ITextRegionAccess;
import org.eclipse.xtext.formatting2.regionaccess.ITextReplacement;
import org.eclipse.xtext.formatting2.regionaccess.TextRegionAccessBuilder;
import org.eclipse.xtext.ide.server.Document;
import org.eclipse.xtext.ide.server.DocumentExtensions;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.TextRegion;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

/**
 * Service for the Formatter
 * 
 * @author Christian DIetrich - Initial contribution and API
 * @since 2.11
 */
@Singleton
@SuppressWarnings("all")
public class FormattingService {
  @Inject(optional = true)
  private INodeModelFormatter formatter1;
  
  @Inject(optional = true)
  private Provider<IFormatter2> formatter2Provider;
  
  @Inject
  private Provider<FormatterRequest> formatterRequestProvider;
  
  @Inject
  private TextRegionAccessBuilder regionBuilder;
  
  @Inject
  @Extension
  private DocumentExtensions _documentExtensions;
  
  /**
   * Format the given document. This operation modifies the document content and returns the
   */
  public List<TextEdit> format(final Document document, final XtextResource resource, final Range range) {
    if ((this.formatter2Provider != null)) {
      return this.format2(document, resource, range);
    } else {
      if ((this.formatter1 != null)) {
        return this.format1(document, resource, range);
      } else {
        return Collections.<TextEdit>emptyList();
      }
    }
  }
  
  protected List<TextEdit> format1(final Document document, final XtextResource resource, final Range range) {
    final int offset = this.offsetFor(document, range);
    final int length = this.lengthFor(document, range);
    final IParseResult parseResult = resource.getParseResult();
    if ((parseResult == null)) {
      return Collections.<TextEdit>unmodifiableList(CollectionLiterals.<TextEdit>newArrayList());
    }
    final ICompositeNode rootNode = parseResult.getRootNode();
    INodeModelFormatter.IFormattedRegion _format = this.formatter1.format(rootNode, offset, length);
    TextEdit _textEdit = this.toTextEdit(resource, _format);
    return Collections.<TextEdit>unmodifiableList(CollectionLiterals.<TextEdit>newArrayList(_textEdit));
  }
  
  protected List<TextEdit> format2(final Document document, final XtextResource resource, final Range range) {
    final FormatterRequest request = this.formatterRequestProvider.get();
    request.setAllowIdentityEdits(false);
    request.setFormatUndefinedHiddenRegionsOnly(false);
    if ((range != null)) {
      final int offset = this.offsetFor(document, range);
      final int length = this.lengthFor(document, range);
      TextRegion _textRegion = new TextRegion(offset, length);
      request.setRegions(Collections.<ITextRegion>unmodifiableList(CollectionLiterals.<ITextRegion>newArrayList(_textRegion)));
    }
    TextRegionAccessBuilder _forNodeModel = this.regionBuilder.forNodeModel(resource);
    final ITextRegionAccess regionAccess = _forNodeModel.create();
    request.setTextRegionAccess(regionAccess);
    final IFormatter2 formatter2 = this.formatter2Provider.get();
    final List<ITextReplacement> replacements = formatter2.format(request);
    final Function1<ITextReplacement, TextEdit> _function = (ITextReplacement it) -> {
      return this.toTextEdit(resource, it);
    };
    return ListExtensions.<ITextReplacement, TextEdit>map(replacements, _function);
  }
  
  private TextEdit toTextEdit(final XtextResource resource, final ITextReplacement textReplacement) {
    TextEditImpl _textEditImpl = new TextEditImpl();
    final Procedure1<TextEditImpl> _function = (TextEditImpl it) -> {
      String _replacementText = textReplacement.getReplacementText();
      it.setNewText(_replacementText);
      RangeImpl _rangeImpl = new RangeImpl();
      final Procedure1<RangeImpl> _function_1 = (RangeImpl it_1) -> {
        int _offset = textReplacement.getOffset();
        PositionImpl _newPosition = this._documentExtensions.newPosition(resource, _offset);
        it_1.setStart(_newPosition);
        int _offset_1 = textReplacement.getOffset();
        int _length = textReplacement.getLength();
        int _plus = (_offset_1 + _length);
        PositionImpl _newPosition_1 = this._documentExtensions.newPosition(resource, _plus);
        it_1.setEnd(_newPosition_1);
      };
      RangeImpl _doubleArrow = ObjectExtensions.<RangeImpl>operator_doubleArrow(_rangeImpl, _function_1);
      it.setRange(_doubleArrow);
    };
    return ObjectExtensions.<TextEditImpl>operator_doubleArrow(_textEditImpl, _function);
  }
  
  private TextEdit toTextEdit(final XtextResource resource, final INodeModelFormatter.IFormattedRegion textReplacement) {
    TextEditImpl _textEditImpl = new TextEditImpl();
    final Procedure1<TextEditImpl> _function = (TextEditImpl it) -> {
      String _formattedText = textReplacement.getFormattedText();
      it.setNewText(_formattedText);
      RangeImpl _rangeImpl = new RangeImpl();
      final Procedure1<RangeImpl> _function_1 = (RangeImpl it_1) -> {
        int _offset = textReplacement.getOffset();
        PositionImpl _newPosition = this._documentExtensions.newPosition(resource, _offset);
        it_1.setStart(_newPosition);
        int _offset_1 = textReplacement.getOffset();
        int _length = textReplacement.getLength();
        int _plus = (_offset_1 + _length);
        PositionImpl _newPosition_1 = this._documentExtensions.newPosition(resource, _plus);
        it_1.setEnd(_newPosition_1);
      };
      RangeImpl _doubleArrow = ObjectExtensions.<RangeImpl>operator_doubleArrow(_rangeImpl, _function_1);
      it.setRange(_doubleArrow);
    };
    return ObjectExtensions.<TextEditImpl>operator_doubleArrow(_textEditImpl, _function);
  }
  
  private int offsetFor(final Document document, final Range range) {
    int _xblockexpression = (int) 0;
    {
      boolean _equals = Objects.equal(range, null);
      if (_equals) {
        return 0;
      }
      Position _start = range.getStart();
      _xblockexpression = document.getOffSet(_start);
    }
    return _xblockexpression;
  }
  
  private int lengthFor(final Document document, final Range range) {
    int _xblockexpression = (int) 0;
    {
      boolean _equals = Objects.equal(range, null);
      if (_equals) {
        String _contents = document.getContents();
        return _contents.length();
      }
      Position _end = range.getEnd();
      int _offSet = document.getOffSet(_end);
      Position _start = range.getStart();
      int _offSet_1 = document.getOffSet(_start);
      _xblockexpression = (_offSet - _offSet_1);
    }
    return _xblockexpression;
  }
}
