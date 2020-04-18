package data;

import org.apache.commons.io.IOUtils;
import org.ektorp.Attachment;
import org.ektorp.AttachmentInputStream;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;

import javax.swing.*;
import java.io.*;
import java.util.List;
import java.util.Map;

public class GifDAOImpl extends CouchDbRepositorySupport<Gif> implements GifDAOService {


    public GifDAOImpl(CouchDbConnector db) {
        super(Gif.class, db);
        initStandardDesignDocument();
    }

    public List<Gif> getGifs() {
        return queryView("allGifs");
    }

    public ImageIcon getAttachment(Gif gif){
        if(null != gif.getGif()) {
            Map<String, Attachment> gifMap = gif.getGif();
            Map.Entry<String, Attachment> entry = gifMap.entrySet().iterator().next();
            AttachmentInputStream gifData = db.getAttachment(gif.get_id(), entry.getKey());
            try {
                byte[] data = IOUtils.toByteArray(gifData);
                ImageIcon icon = new ImageIcon(data);
                return icon;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       return null;
    }

    public boolean uploadGif(String path, String tags){
        for(int i = 0; i < path.length();i++){
            if(path.charAt(i) == '\\'){
                path = path.replace('\\', '/');
            }
        }
        String[] tagsArray = tags.split(",");
        String[] directory = path.split("/");
        String filename = directory[directory.length-1];
        final String CONTENT_TYPE = "image/gif";
        try {
            Gif uploadGif = new Gif(tagsArray);

            db.create(uploadGif);
            System.out.println(uploadGif.get_id());
            InputStream is = new BufferedInputStream(new FileInputStream( new File(path)));
            AttachmentInputStream inputStream = new AttachmentInputStream(filename,is, CONTENT_TYPE);
            db.createAttachment(uploadGif.get_id(), uploadGif.get_rev(), inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void deleteGif(Gif gif) {
        db.delete(gif);
    }

    public void editGif(Gif gif, String[] editedTags){

        String[] newTags = new String[editedTags.length + gif.getTags().length];
        for(int i = 0; i < newTags.length; i ++){
            if(i < gif.getTags().length)
                newTags[i] = gif.getTags()[i];
            else
                newTags[i] = editedTags[i - gif.getTags().length];
        }
        gif.setTags(newTags);
        db.update(gif);
    }
}
