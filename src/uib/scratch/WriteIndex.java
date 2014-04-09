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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/**
 * sample application that writes to a file store run ./ramdisk.sh to create a
 * ram disk on OSX
 * 
* @author rick crawford
 * 
*/
public class WriteIndex {

    public static final String INDEX_DIRECTORY = "C:\\Users\\Olav\\Documents\\Dokument\\NetBeans\\Aardvark\\artimages\\";

    /**
     * @param args
     * @throws java.io.IOException
     * @throws org.xml.sax.SAXException
     */
    public static void main(String[] args) throws IOException, SAXException {

        File docs = new File("documents");
        File indexDir = new File(INDEX_DIRECTORY);

        //Directory directory = FSDirectory.open(indexDir);

        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
        //IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_30, analyzer);
        //IndexWriter writer = new IndexWriter(directory, analyzer, true, IndexWriter.MaxFieldLength.UNLIMITED);
        IndexWriter writer = new IndexWriter(
                FSDirectory.open(indexDir),
                analyzer,
                true,
                IndexWriter.MaxFieldLength.LIMITED);
        System.out.println(indexDir
                );
        writer.deleteAll();

        for (File file : docs.listFiles()) {
            Metadata metadata = new Metadata();
            ContentHandler handler = new BodyContentHandler();
            ParseContext context = new ParseContext();
            Parser parser = new AutoDetectParser();
            InputStream stream = new FileInputStream(file);
            try {
                parser.parse(stream, handler, metadata, context);
            } catch (TikaException e) {
            }catch (IOException e){
                e.printStackTrace();
            }
            finally {
                stream.close();
            }

            String text = handler.toString();
            String fileName = file.getName();

            Document doc = new Document();
            doc.add(new Field("file", fileName, Store.YES, Index.NO));

            for (String key : metadata.names()) {
                String name = key.toLowerCase();
                String value = metadata.get(key);

                if (StringUtils.isEmpty(value)) {
                    continue;
                }

                if ("keywords".equalsIgnoreCase(key)) {
                    for (String keyword : value.split(",?(\\s+)")) {
                        doc.add(new Field(name, keyword, Store.YES, Index.NOT_ANALYZED));
                    }
                } else if ("title".equalsIgnoreCase(key)) {
                    doc.add(new Field(name, value, Store.YES, Index.ANALYZED));
                } else {
                    doc.add(new Field(name, fileName, Store.YES, Index.NOT_ANALYZED));
                }
            }
            doc.add(new Field("text", text, Store.NO, Index.ANALYZED));
            writer.addDocument(doc);

        }

        writer.commit();
        //.deleteUnusedFiles();

        System.out.println(writer.maxDoc() + " documents written");
    }

}
