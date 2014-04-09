/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uib.retrieval.analyze;

import java.io.IOException;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;

/**
 *
 * @author Olav
 */
public class GraphTokenizer2 extends TokenStream {

    public GraphTokenizer2(String[] data) {
        tokens = data;
        reset();
    }
    private String[] tokens;
    private int index;
    private int offset;
    private final TermAttribute termAtt = addAttribute(TermAttribute.class);
    private final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);
    private final PositionIncrementAttribute posIncrAtt = addAttribute(PositionIncrementAttribute.class);

    @Override
    public void reset() {
        index = 0;
        offset = 0;
    }

    @Override
    public boolean incrementToken() throws IOException {
        clearAttributes();
        if (index == tokens.length) {
            return false;
        } else {
            String tkn = tokens[index];
            int len = tkn.length();
            index++;
            offsetAtt.setOffset(offset, offset + len - 1);
            posIncrAtt.setPositionIncrement(1);
            //termAtt.setEmpty(); 4.0??
            termAtt.setTermLength(len);
            //termAtt.copyBuffer(len); 4.0 ??
            termAtt.setTermBuffer(tkn);
            offset += len;
            return true;
        }
    }

    
}
