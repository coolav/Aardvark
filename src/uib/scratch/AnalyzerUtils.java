/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uib.scratch;

/**
 *
 * @author Olav
 */
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import junit.framework.Assert;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.AttributeSource;
import org.apache.lucene.util.Version;
import uib.retrieval.analyze.GraphTokenizer2;

// From chapter 4
public class AnalyzerUtils {

    public static void displayTokens(Analyzer analyzer,
            String text) throws IOException {
        displayTokens(analyzer.tokenStream("contents", new StringReader(text)));  //A
    }

    public static void displayTokens(TokenStream stream)
            throws IOException {

        TermAttribute term = stream.addAttribute(TermAttribute.class);
        while (stream.incrementToken()) {
            System.out.print("[" + term.term() + "] ");    //B
        }
    }
    /*
     #A Invoke analysis process
     #B Print token text surrounded by brackets
     */

    public static int getPositionIncrement(AttributeSource source) {
        PositionIncrementAttribute attr = source.addAttribute(PositionIncrementAttribute.class);
        return attr.getPositionIncrement();
    }

    public static String getTerm(AttributeSource source) {
        TermAttribute attr = source.addAttribute(TermAttribute.class);
        return attr.term();
    }

    public static String getType(AttributeSource source) {
        TypeAttribute attr = source.addAttribute(TypeAttribute.class);
        return attr.type();
    }

    public static void setPositionIncrement(AttributeSource source, int posIncr) {
        PositionIncrementAttribute attr = source.addAttribute(PositionIncrementAttribute.class);
        attr.setPositionIncrement(posIncr);
    }

    public static void setTerm(AttributeSource source, String term) {
        TermAttribute attr = source.addAttribute(TermAttribute.class);
        attr.setTermBuffer(term);
    }

    public static void setType(AttributeSource source, String type) {
        TypeAttribute attr = source.addAttribute(TypeAttribute.class);
        attr.setType(type);
    }

    public static List<String> parseKeywords(Analyzer analyzer, String field, String keywords) {

        List<String> result = new ArrayList<String>();
        TokenStream stream  = analyzer.tokenStream(field, new StringReader(keywords));

        try {
            while(stream.incrementToken()) {
                result.add(stream.getAttribute(TermAttribute.class).term());
            }
        }
        catch(IOException e) {
            // not thrown b/c we're using a string reader...
        }

        return result;
    }  
    public static Token insertB (Analyzer analyzer, String text)
        throws IOException{
        TokenStream stream = analyzer.tokenStream("contents", new StringReader(text));
        TermAttribute term = stream.addAttribute(TermAttribute.class);
        PositionIncrementAttribute posIncr = stream.addAttribute(PositionIncrementAttribute.class);
        OffsetAttribute offset = stream.addAttribute(OffsetAttribute.class);
        
         StringBuilder currenttoken = new StringBuilder(64);
        // currenttoken.append('[');
        char[] character = new char[1];
        int i = posIncr.getPositionIncrement();
        // reset our states :)
        //posIncr
        
        boolean tokenstart = false;
        boolean tokenend = false;
        stream.reset();
        while(stream.incrementToken()){
            
             /* end of stream reached ...    
            if (i == 0) return null;

            if (character[0] == '[') { // token starts here ...
                tokenstart = true;
            } else if (character[0] == ']') { // token ends here ...
                tokenend = true;
            } else if (tokenstart && !tokenend) { // between end and start ...
                currenttoken.append(character[0]);
            }
            // we found our token and return it ...
            if (tokenstart && tokenend) {
                // currenttoken.append(']');
                // prepend a token because lucene does not allow leading wildcards. 
                //currenttoken.insert(0, '_');*/
                //String tokenString = currenttoken.toString().toLowerCase().replace(' ', '_').trim();
                String tokenString = term.toString(); 
                Token t = new Token(tokenString, 0, tokenString.length()-1);
                System.out.println(t);
                //return t;
            
        }
        return null;
    }
    public static void insertBracket(Analyzer analyzer, String text) throws IOException {
        TokenStream stream = analyzer.tokenStream("contents", new StringReader(text));
        TermAttribute term = stream.addAttribute(TermAttribute.class);
        PositionIncrementAttribute posIncr = stream.addAttribute(PositionIncrementAttribute.class);
        OffsetAttribute offset = stream.addAttribute(OffsetAttribute.class);
        StringBuilder currentToken = new StringBuilder(64);
        
        int position = 0;
        while(stream.incrementToken()){
            //final String token = new StringTokenizer();
            int increment = posIncr.getPositionIncrement();
            if(increment > 0){
                position += increment;
                offset.endOffset();
                currentToken.append(term);  
                currentToken.insert(0, "_"); 
                String tokenString = currentToken.toString().toLowerCase().replace(' ', '_').trim();
                Token t = new Token(tokenString, 0, tokenString.length()-1);
                t.setTermBuffer(tokenString);
                
                System.out.println("test " + " " + t);
            }
        }
    }

    
    public static void displayTokensWithPositions(Analyzer analyzer, String text) throws IOException {

        TokenStream stream = analyzer.tokenStream("contents",
                new StringReader(text));
        TermAttribute term = stream.addAttribute(TermAttribute.class);
        PositionIncrementAttribute posIncr = stream.addAttribute(PositionIncrementAttribute.class);

        int position = 0;
        while (stream.incrementToken()) {
            int increment = posIncr.getPositionIncrement();
            if (increment > 0) {
                position = position + increment;
                System.out.println();
                System.out.print(position + ": ");
            }

            System.out.print("[" + term.term() + "] ");
        }
        System.out.println();
    }

    public static void displayTokensWithFullDetails(Analyzer analyzer,
            String text) throws IOException {

        TokenStream stream = analyzer.tokenStream("contents", // #A
                new StringReader(text));

        TermAttribute term = stream.addAttribute(TermAttribute.class);        // #B
        PositionIncrementAttribute posIncr = // #B 
                stream.addAttribute(PositionIncrementAttribute.class);              // #B
        OffsetAttribute offset = stream.addAttribute(OffsetAttribute.class);  // #B
        TypeAttribute type = stream.addAttribute(TypeAttribute.class);        // #B

        int position = 0;
        stream.reset();
        while (stream.incrementToken()) {                                  // #C

            int increment = posIncr.getPositionIncrement();                 // #D
            if (increment > 0) {                                            // #D
                position = position + increment;                              // #D
                System.out.println();                                         // #D
                System.out.print(position + ": ");                            // #D
            }

            System.out.print("[" + // #E
                    term.term() + ":" + // #E
                    offset.startOffset() + "->" + // #E
                    offset.endOffset() + ":" + // #E
                    type.type() + "] ");                  // #E
        }
        System.out.println();
    }
    /*
     #A Perform analysis
     #B Obtain attributes of interest
     #C Iterate through all tokens
     #D Compute position and print
     #E Print all token details
     */

    public static void assertAnalyzesTo(Analyzer analyzer, String input,
            String[] output) throws Exception {
        TokenStream stream = analyzer.tokenStream("field", new StringReader(input));

        TermAttribute termAttr = stream.addAttribute(TermAttribute.class);
        for (String expected : output) {
            Assert.assertTrue(stream.incrementToken());
            Assert.assertEquals(expected, termAttr.term());
        }
        Assert.assertFalse(stream.incrementToken());
        stream.close();
    }

    public static void displayPositionIncrements(Analyzer analyzer, String text)
            throws IOException {
        TokenStream stream = analyzer.tokenStream("contents", new StringReader(text));
        PositionIncrementAttribute posIncr = stream.addAttribute(PositionIncrementAttribute.class);
        while (stream.incrementToken()) {
            System.out.println("posIncr=" + posIncr.getPositionIncrement());
        }
    }
    public static void diplayTokens(Analyzer analyzer, String text){
    
    }

    public static void main(String[] args) throws IOException {
        /**/
        System.out.println("insertBracket");
        System.out.println("\n-------");
        insertB(new SimpleAnalyzer(),
                "Relation type=\"urn:mpeg:mpeg7:cs:SemanticRelationCS:2001:time\" source=\"#id_1\" target=\"#id_2\" />");
        
        System.out.println("SimpleAnalyzer");
        displayTokensWithFullDetails(new SimpleAnalyzer(),
                "The quick brown fox....");

        System.out.println("\n----");
        System.out.println("StandardAnalyzer");
        displayTokensWithFullDetails(new StandardAnalyzer(Version.LUCENE_30),
                "I'll email you at xyz@example.com");

        System.out.println("\n------");
        System.out.println("Display tokens with full details");
        displayTokensWithFullDetails(new SimpleAnalyzer(),
                "Relation type=\"urn:mpeg:mpeg7:cs:SemanticRelationCS:2001:time\" source=\"#id_1\" target=\"#id_2\" />");

        System.out.println("\n-----");
        System.out.println("display postition increments");
        displayTokensWithPositions(new StandardAnalyzer(Version.LUCENE_30),
                "Relation type=\"urn:mpeg:mpeg7:cs:SemanticRelationCS:2001:time\" source=\"#id_1\" target=\"#id_2\" />");
        
        System.out.println("\n-----");
        System.out.println("display tokens");
        displayTokens(new MyAnalyser(),
                "test_one1_test_two2_test_three3_test_four4");
        
       System.out.println("\n-----");
       System.out.println("tokenize a string");
        List<String> parseKeywords = parseKeywords(new StandardAnalyzer(Version.LUCENE_30), 
                "content", "Relation type=\"urn:mpeg:mpeg7:cs:SemanticRelationCS:2001:time\" source=\"#id_1\" target=\"#id_2\" />");
        for(String outPut : parseKeywords){
            String s = " "+ outPut;
            String tokenString = s.toLowerCase().replace(' ', '_').trim();
            Token t = new Token(tokenString, 0 , tokenString.length() -1);
            System.out.println(t.termBuffer());
        }
        
        /*depreciated 
        StringTokenizer st = new StringTokenizer("Relation type=\"urn:mpeg:mpeg7:cs:SemanticRelationCS:2001:time\" source=\"#id_1\" target=\"#id_2\" />");
        while (st.hasMoreTokens()) {
         System.out.println(st.nextToken());
     } */
        System.out.println("\n----");
        System.out.println("StandardAnalyzer");
        displayTokens(new StandardAnalyzer(Version.LUCENE_30),
                "Relation type=\\\"urn:mpeg:mpeg7:cs:SemanticRelationCS:2001:time\\\" source=\\\"#id_1\\\" target=\\\"#id_2\\\" />");
    }

}

/*
 #1 Invoke analysis process
 #2 Output token text surrounded by brackets
 */
