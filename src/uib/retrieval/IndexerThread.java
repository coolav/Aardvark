/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uib.retrieval;

/**
 *
 * @author Olav
 */
import uib.gui.AardvarkGui;
import uib.retrieval.engine.LucenePathIndexRetrievalEngine;
import uib.retrieval.engine.LuceneRetrievalEngine;
//import at.lux.fotoretrieval.retrievalengines.DatabaseRetrievalEngine;

/**
 * Date: 14.10.2004
 * Time: 10:06:49
 * @author Mathias Lux, mathias@juggle.at
 */
public class IndexerThread implements Runnable {
    private AardvarkGui parent;
    private String indexBasePath;

    public IndexerThread(AardvarkGui parent, String indexBasePath) {
        this.parent = parent;
        this.indexBasePath = indexBasePath;
    }

    public void run() {
        parent.setEnabled(true);
        parent.status.setText("Please wait while indexing");
        // use derby only if it is defined in the configuration.
        //if (EmirConfiguration.getInstance().getBoolean("Retrieval.Cbir.useDerby")) {
        //    DatabaseRetrievalEngine dbEngine = new DatabaseRetrievalEngine();
        //    dbEngine.indexAllImages(indexBasePath, parent);
        //}
        LuceneRetrievalEngine engine = new LuceneRetrievalEngine(40);
        engine.indexFiles(indexBasePath);
//        engine.indexFilesSemantically(indexBasePath, parent);
        LucenePathIndexRetrievalEngine pathIndexEngine = new LucenePathIndexRetrievalEngine(30);
        pathIndexEngine.indexFilesSemantically(indexBasePath, parent.status);
        parent.status.setText("Indexing finished");
        parent.setEnabled(true);
    }
}
