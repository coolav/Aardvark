/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uib.scratch;

import java.io.IOException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;

/**
 *
 * @author Olav
 */
public class CustomTokenizer extends TokenStream {
    
    private final Analyzer reader; 
    private char last = ' ';
    
    public CustomTokenizer (Analyzer input){
        reader = input;
    }

    @Override
    public boolean incrementToken() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
