import com.mongodb.*;

public class Database {

	MongoClient mongoClient;
    DB db;
    DBCollection MUSTERILER;
    DBCollection URUNLER;
    DBCollection BAKANLAR;
    
    public Database() {//Database Construction
        mongoClient = new MongoClient("127.0.0.1", 27017);
        db = mongoClient.getDB("database");
        BILGISAYARLAR = db.getCollection("BILGISAYARLAR");
        TELEFONLAR = db.getCollection("TELEFONLAR");
        TVLER = db.getCollection("TVLER");
        CAMASIR = db.getCollection("CAMASIR");
        BUZDOLABI = db.getCollection("BUZDOLABI");

    }
    
}
