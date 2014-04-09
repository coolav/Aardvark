/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uib.gui;

import java.io.File;
import java.io.IOException;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import uib.retrieval.engine.LuceneRetrievalEngine;

/**
 *
 * @author Olav
 */
public class RetrievalTools{ 
    private AardvarkGui parent;
    private String BASE_DIRECTORY = "./artimages/";

    public RetrievalTools(AardvarkGui parent){
        this.parent = parent;
    }
    
     public void searchForImage(String xPath, String directory, boolean descendIntoSubdirs) {
        //debug("Starting search for keywords ... ");
        

        //Thread t = new SearchThroughXPathThread(xPath, directory, descendIntoSubdirs, this, parent.progressBarSearchProgress);
        //t.start();
    }

    public void searchForImageInIndex(String xPath, String directory, boolean descendIntoSubdirs) throws IOException {
        
        File fullTextIndex = new File(LuceneRetrievalEngine.parseFulltextIndexDirectory(BASE_DIRECTORY));
        Directory fullTextDir = FSDirectory.open(fullTextIndex);
        File semanticIndex = new File(LuceneRetrievalEngine.parseSemanticIndexDirectory(BASE_DIRECTORY));
        Directory semanticDir = FSDirectory.open(semanticIndex);
        if (IndexReader.indexExists(fullTextDir) &&
                IndexReader.indexExists(semanticDir)) {

            //debug("Starting search for keywords ... ");
            //JProgressBar pbar = new JProgressBar(JProgressBar.HORIZONTAL);
            //pbar.setStringPainted(true);

            Thread t = new SearchLucene(xPath, directory, descendIntoSubdirs, parent);
            t.start();
        } else {
            setStatus("Using index from " + BASE_DIRECTORY);
            JOptionPane.showMessageDialog(parent, "Given directory (" + BASE_DIRECTORY + ") contains no index, please \ncreate one first (see menu).");
        }

    }

    public void searchForImage(String xPath, Vector objects, String directory, boolean descendIntoSubdirs) {
        //debug("Starting search for semantic description ... ");
        JProgressBar pbar = new JProgressBar(JProgressBar.HORIZONTAL);
        pbar.setStringPainted(true);

        //Thread t = new SearchSemanticDescriptionThread(xPath, objects, directory, descendIntoSubdirs, this, pbar);
        //t.start();
    }

    public void setStatus(String message) {
        parent.status.setText(message);
    }

    //public void addResult(ResultsPanel rp) {
    //    tabs.add("Results", rp);
    //    tabs.setSelectedIndex(tabs.getTabCount() - 1);
    //}

    
}
