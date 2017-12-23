import com.mongodb.*;

public class Database {

	MongoClient mongoClient;
    DB db;
    DBCollection MUSTERILER;
    DBCollection URUNLER;
    DBCollection BAKANLAR;
    
    public Database() {//Database Construction
        mongoClient = new MongoClient("127.0.0.1", 27017);
        db = mongoClient.getDB("DATABASE");
        MUSTERILER = db.getCollection("MUSTERILER");
        URUNLER = db.getCollection("URUNLER");
        BAKANLAR = db.getCollection("BAKANLAR");


   
     }
    
}
