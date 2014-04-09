/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uib.scratch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;

/**
 *
 * @author Olav
 */
public class MyTokenFilter extends TokenFilter {

    private final TermAttribute termAttr;
    private final List<String> terms2 = new ArrayList<String>();
    private String[] terms;
    private int pos;

    public MyTokenFilter(TokenStream tokenStream) {
        super(tokenStream);
        this.termAttr = input.addAttribute(TermAttribute.class);
    }

    public boolean incrementToken() throws IOException {
        if (terms == null) {
            if (!input.incrementToken()) {
                return false;
            }
            terms = termAttr.term().split("_");
        }

        termAttr.setTermBuffer(terms[pos++]);
        if (pos == terms.length) {
            terms = null;
            pos = 0;
        }
        return true;
    }
}


//String replace = (termAttr.class).term().toString().toLowerCase().replace('_', '-');
            //terms2.add(termAttr.term().toString().toLowerCase().replace("_", "!!"));
            //terms = termAttr.term().toString().toLowerCase().replace(' ', ' ');
            //terms = replace.split("-"); 
/*for(String tokens : terms2){
         termAttr.setTermBuffer(tokens);
         if (pos == terms2.size()) {
         terms = null;
         pos = 0;
         }
         }*/