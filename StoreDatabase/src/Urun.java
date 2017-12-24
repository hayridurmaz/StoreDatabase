import java.util.ArrayList;

public class Urun {

	long urunId;
	String marka, model;
	ArrayList<Kullanici> satinAlanKullanicilar;

	public ArrayList<Kullanici> getSatinAlanKullanicilar() {
		return satinAlanKullanicilar;
	}

	public void satinAlanKullaniciEkle(Kullanici kullanici) {
		this.satinAlanKullanicilar.add(kullanici);
	}

	public boolean isExist(Kullanici kullanici){
		for (int i = 0; i < satinAlanKullanicilar.size(); i++) {
			if(satinAlanKullanicilar.get(i).kullaniciId==kullanici.kullaniciId)
				return true;
		}
		return false;
	}
	public Urun(long urunId, String marka, String model) {
		this.urunId = urunId;
		this.marka = marka;
		this.model = model;
		satinAlanKullanicilar = new ArrayList<>();
	}
	public String toString(){
		return urunId+", "+marka+", "+model+" satýldýðý kullanýcý sayýsý: "+Driver.urunuAlanSayisi(this);
	}
	public boolean equals(Urun u){
		return u.marka.equals(marka) && u.model.equals(model);
	}

}
