import java.util.ArrayList;

public class Kullanici {

	long kullaniciId;
	String isim, soyisim;
	int yas;
	ArrayList<Urun> satinAldigiUrunler;

	public ArrayList<Urun> getSatinAldigiUrunler() {
		return satinAldigiUrunler;
	}

	public void satinAldigiUrunEkle(Urun urun) {
		this.satinAldigiUrunler.add(urun);
	}
	public boolean isExist(Urun urun){
		for (int i = 0; i < satinAldigiUrunler.size(); i++) {
			if(urun.urunId==satinAldigiUrunler.get(i).urunId)
				return true;
		}
		return false;
	}

	public Kullanici(long kullaniciId, String isim, String soyisim, int yas) {
		this.kullaniciId = kullaniciId;
		this.isim = isim;
		this.soyisim = soyisim;
		this.yas = yas;
		satinAldigiUrunler = new ArrayList<>();
	}
	

	public String toString() {
		return kullaniciId+", "+isim+" "+soyisim+", "+yas+", aldýðý ürün sayýsý: "+Driver.kullanicininAldigiUrunSayisi(this);
	}
	
	public boolean equals(Kullanici k){
		return (k.isim.equals(isim) && k.soyisim.equals(soyisim) && k.yas==yas);
	}

}
