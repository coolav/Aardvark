/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uib.scratch;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;

/**
 *
 * @author Olav
 */
public class TokenCombiner extends TokenFilter {
  private final StringBuilder sb = new StringBuilder();
  private final TermAttribute termAtt = addAttribute(TermAttribute.class);
  private final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);
  private final char separator;
  private boolean consumed; // true if we already consumed

  protected TokenCombiner(TokenStream input, char separator) {
    super(input);
    this.separator = separator;
  }

  @Override
  public final boolean incrementToken() throws IOException {
    if (consumed) {
      return false; // don't call input.incrementToken() after it returns false
    }
    consumed = true;

    int startOffset = 0;
    int endOffset = 0;

    boolean found = false; // true if we actually consumed any tokens
    while (input.incrementToken()) {
      if (!found) {
        startOffset = offsetAtt.startOffset();
        found = true;
      }
      sb.append(termAtt);
      sb.append(separator);
      endOffset = offsetAtt.endOffset();
    }

    if (found) {
      assert sb.length() > 0; // always: because we append separator
      sb.setLength(sb.length() - 1);
      clearAttributes();
      termAtt.setTermBuffer(sb.toString());
      offsetAtt.setOffset(startOffset, endOffset);
      return true;
    } else {
      return false;
    }
  }

  @Override
  public void reset() throws IOException {
    super.reset();
    sb.setLength(0);
    consumed = false;
  }
}
