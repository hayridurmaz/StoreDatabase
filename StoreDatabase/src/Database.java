import java.util.*;

import org.bson.BSONObject;

import com.mongodb.*;

public class Database {

	MongoClient mongoClient;
	DB db;
	DBCollection KULLANICI;
	DBCollection URUN;
	DBCollection SATINALMA;

	public Database() {// Database Construction
		mongoClient = new MongoClient("127.0.0.1", 27017);
		db = mongoClient.getDB("db");
		KULLANICI = db.getCollection("KULLANICI");
		URUN = db.getCollection("URUN");
		SATINALMA = db.getCollection("SATINALMA");
	}

	public void kullaniciEkle(Kullanici kullanici) {
		BasicDBObject document = new BasicDBObject();
		document.put("kullaniciId", kullanici.kullaniciId);
		document.put("isim", kullanici.isim);
		document.put("soyisim", kullanici.soyisim);
		document.put("yas", kullanici.yas);
		KULLANICI.insert(document);
	}

	public void urunEkle(Urun urun) {
		BasicDBObject document = new BasicDBObject();
		document.put("urunId", urun.urunId);
		document.put("marka", urun.marka);
		document.put("model", urun.model);
		URUN.insert(document);
	}

	public void satinalmaEkle(SatinAlma satinAlma) {
		BasicDBObject document = new BasicDBObject();
		document.put("kullaniciId", satinAlma.kullanici.kullaniciId);
		document.put("urunId", satinAlma.urun.urunId);
		SATINALMA.insert(document);
	}

	public ArrayList<Kullanici> getKullanicilar() {
		ArrayList<Kullanici> kullanicilar = new ArrayList<>();

		List<DBObject> basics = db.getCollection("KULLANICI").find().toArray();// tüm
																				// kullanýcýlarý
																				// arraye
																				// atýyor

		for (int i = 0; i < basics.size(); i++) {// o kullanýcýlarý arrayListte
													// KULLANICI objesi olarak
													// tutuyor.
			Kullanici kullanici = new Kullanici(Long.parseLong(basics.get(i).get("kullaniciId").toString()),
					basics.get(i).get("isim").toString(), basics.get(i).get("soyisim").toString(),
					Integer.parseInt(basics.get(i).get("yas").toString()));
			kullanicilar.add(kullanici);
		}
		return kullanicilar;
	}

	public ArrayList<Urun> getUrunler() {
		ArrayList<Urun> urunler = new ArrayList<>();

		List<DBObject> basics = db.getCollection("URUN").find().toArray();// tüm
																			// ürünleri
																			// arraye
																			// atýyor

		for (int i = 0; i < basics.size(); i++) {// o ürünleri arrayListte
													// URUN objesi olarak
													// tutuyor.
			Urun urun = new Urun(Long.parseLong(basics.get(i).get("urunId").toString()),
					basics.get(i).get("marka").toString(), basics.get(i).get("model").toString());
			urunler.add(urun);
		}
		return urunler;
	}

	public ArrayList<SatinAlma> getSatinAlmalar() {
		ArrayList<SatinAlma> satinalmalar = new ArrayList<>();

		List<DBObject> basics = db.getCollection("SATINALMA").find().toArray();// tüm
																				// satýn
																				// almalarý
																				// arraye
																				// atýyor

		for (int i = 0; i < basics.size(); i++) {// o satýnalmalarý arrayListte
													// Satýnalma objesi olarak
													// tutuyor.
			long Urunid = Long.parseLong(basics.get(i).get("urunId").toString());
			long Kullaniciid = Long.parseLong(basics.get(i).get("kullaniciId").toString());
			int u = Driver.getUrun(Urunid);
			int k = Driver.getKullanici(Kullaniciid);

			if(k==-1 || u ==-1){
				System.err.println("Bir hata oluþtu.");
			}
			satinalmalar.add(new SatinAlma(Driver.KULLANICILAR.get(k), Driver.URUNLER.get(u)));
//			k.satinAldigiUrunEkle(u);
//			Driver.getKullanicibyIsimSoyisim(k.isim, k.soyisim).satinAldigiUrunEkle(Driver.getUrunbyMarkaModel(u.marka, u.model));
//			Driver.getUrunbyMarkaModel(u.marka, u.model).satinAlanKullaniciEkle(Driver.getKullanicibyIsimSoyisim(k.isim, k.soyisim));
//			u.satinAlanKullaniciEkle(k);
//			System.out.println("adý:::::"+Driver.getKullanicibyIsimSoyisim(k.isim, k.soyisim).isim);
//			System.out.println("size.::"+Driver.getKullanicibyIsimSoyisim(k.isim, k.soyisim).getSatinAldigiUrunler().size());
		}
		return satinalmalar;
	}

	public long getMaxId(String collectionAdi, String fieldAdi) {

		DBCursor curr = db.getCollection(/* "KULLANICI" */collectionAdi).find()
				.sort(new BasicDBObject(/* "kullaniciId" */fieldAdi, -1)).limit(1);
		List<DBObject> arr = curr.toArray();
		try {
			long a = Long.parseLong(arr.get(0).get(/* "kullaniciId" */fieldAdi).toString());
			return a;
		} catch (Exception e) {
			return 0;
		}
	}

}
