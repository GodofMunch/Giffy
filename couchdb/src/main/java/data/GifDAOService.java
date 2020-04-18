package data;
import javax.swing.*;
import java.util.List;

public interface GifDAOService {

     List<Gif> getGifs();

     ImageIcon getAttachment(Gif gif);

     boolean uploadGif(String path, String tags);

     void deleteGif(Gif gif);

     void editGif(Gif gif, String[] editedTags);
}
