package db;

import main.ConfigParams;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;

import java.net.MalformedURLException;

    public class CouchConnector {
        private CouchDbConnector db;

        public CouchDbConnector getConnector() {
            if(db==null) {
                HttpClient client = null;
                try {
                    client = new StdHttpClient.Builder()
                            .url("http://" + ConfigParams.dbip + ":" + ConfigParams.dbPort)
                            .build();
                    CouchDbInstance dbInstance = new StdCouchDbInstance(client);
                    CouchDbConnector db = dbInstance.createConnector(ConfigParams.dbName, true);
                    this.db = db;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
            return db;
        }
    }
