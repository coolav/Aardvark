/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uib.scratch;

import java.io.Reader;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.util.Version;

/**
 *
 * @author Olav
 */
public class MyAnalyser extends Analyzer{
    
    @Override
    public TokenStream tokenStream(String fieldName, Reader reader) {
        StandardTokenizer tokenizer = new StandardTokenizer(
                Version.LUCENE_30, reader);
        TokenStream tokenStream = new StandardFilter(tokenizer);
        tokenStream = new MyTokenFilter(tokenStream);
        tokenStream = new StopFilter(true, tokenStream,
                StopAnalyzer.ENGLISH_STOP_WORDS_SET);
        return tokenStream;
    }
    
}
