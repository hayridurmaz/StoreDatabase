
public class SatinAlma {
	Kullanici kullanici;
	Urun urun;

	public SatinAlma(Kullanici kullanici, Urun urun) {
		this.kullanici = kullanici;
		this.urun = urun;
	}
	
	public String toString(){
		return kullanici.kullaniciId+" "+kullanici.isim+" "+kullanici.soyisim+" -> "+urun.urunId+" "+urun.marka+" "+urun.model;
	}

}
