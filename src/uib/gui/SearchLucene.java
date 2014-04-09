/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uib.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import javax.swing.JProgressBar;
import uib.retrieval.ResultListEntry;
import uib.retrieval.engine.RetrievalEngine;
import uib.retrieval.engine.RetrievalEngineFactory;
/**
 *
 * @author Olav
 */
public class SearchLucene extends Thread{
    
    String dir, xPath;
    boolean recursive;
    private AardvarkGui parent;    
    DecimalFormat df;
    
    public SearchLucene(String xPath, String directory, boolean descend,
                                     AardvarkGui parent) {
        this.xPath = xPath;
        this.dir = directory;
        this.recursive = descend;
        this.parent = parent;
        //this.progress = parent.progressBarSearchProgress;
        df = (DecimalFormat) NumberFormat.getInstance();
        df.setMaximumFractionDigits(2);
    }
    
    @Override
    public void run() {
        parent.setEnabled(false);
        //ProgressWindow pw = new ProgressWindow(parent, progress);
        //pw.pack();
        //Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        //pw.setLocation((d.width - pw.getWidth()) / 2, (d.height - pw.getHeight()) / 2);
        //pw.setVisible(true);
        long stime, ftime;
        stime = System.currentTimeMillis();
        parent.setStatus("searching for similar images ...");
        debug("XPath statement: " + xPath);
        RetrievalEngine engine;
        engine = RetrievalEngineFactory.getLuceneRetrievalEngine();
        stime = System.currentTimeMillis();
        List<uib.retrieval.model.ResultListEntry> results = engine.getImagesByXPathSearch(xPath, dir, recursive, parent.progressBarSearchProgress);
        stime = System.currentTimeMillis() - stime;
//        System.out.println(stime);
//        System.out.println(results.size());
        ftime = System.currentTimeMillis();
        parent.setStatus("Formatting results ...");
        //ResultsPanel rp = new ResultsPanel(results, progress, parent.getQualityConstraints());
        ftime = System.currentTimeMillis() - ftime;
        //parent.addResult(rp);
        parent.setStatus("Searched for " + df.format(stime / 1000.0) + " s, formatting lasted " + df.format(ftime / 1000.0) + " s");
        //pw.setVisible(false);
        parent.setEnabled(true);
    }

    private void debug(String message) {
        if (parent.DEBUG) {
            System.out.println("[at.lux.fotoretrieval.SearchThroughXPathThread] " + message);
        }
    }
}
