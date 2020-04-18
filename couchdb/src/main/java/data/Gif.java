package data;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.ektorp.Attachment;
import java.util.Map;

public class Gif {
    @JsonProperty("_id") private String _id;
    @JsonProperty("_rev") private String _rev;
    @JsonProperty("tags") private String[] tags;
    @JsonProperty("_attachments") private Map<String, Attachment> gif;


    public Gif(){}

    public Gif(String[] tags){
        this.tags = tags;
    }
    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public Map<String, Attachment> getGif() {
        return gif;
    }

    public void setGif(Map<String,Attachment> gif) {
        this.gif = gif;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_rev() {
        return _rev;
    }

    public void set_rev(String _rev) {
        this._rev = _rev;
    }

    public String toString(){
        return "{\n\t\"_id\":" + this._id;
    }
}
