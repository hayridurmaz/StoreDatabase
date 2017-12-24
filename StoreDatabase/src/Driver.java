import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Driver {
	static ArrayList<Kullanici> KULLANICILAR;
	static ArrayList<Urun> URUNLER;
	static ArrayList<SatinAlma> SATINALMALAR;
	static Database db = new Database();

	public static void main(String[] args) {

		URUNLER = db.getUrunler();
	
		KULLANICILAR = db.getKullanicilar();
		
		SATINALMALAR = db.getSatinAlmalar();
		satinAlanKullaniciSatis(SATINALMALAR);
		arrayListYazdir(SATINALMALAR);
		arrayListYazdir(URUNLER);
		arrayListYazdir(KULLANICILAR);
		Scanner scan = new Scanner(System.in);
		int input = 0;
		while (true) {
			System.out.println(
					"MENU:\n(1)Kullan�c� Ekle\n(2)�r�n Ekle\n(3)Sat�nalma ekle\n(4)Analiz Al\n(5)Kullan�c� Listeleme\n(6)�r�n Listeleme\n(7)�IKI�");
			input = scan.nextInt();

			if (input == 1) {
				System.out.println("L�tfen isim girin.");
				String isim = scan.next();
				System.out.println("L�tfen soyisim girin:");
				String soyisim = scan.next();
				System.out.println("L�tfen ya�� girin:");
				int yas = scan.nextInt();
				long id = db.getMaxId("KULLANICI", "kullaniciId") + 1;
				Kullanici k = new Kullanici(id, isim, soyisim, yas);
				db.kullaniciEkle(k);

			} else if (input == 2) {
				System.out.println("L�tfen marka giriniz");
				String marka = scan.next();
				System.out.println("L�tfen model giriniz");
				String model = scan.next();
				Urun urun = new Urun(db.getMaxId("URUN", "urunId") + 1, marka, model);
				db.urunEkle(urun);
			} else if (input == 3) {
				System.out.println("�r�n marka giriniz.");
				String marka = scan.next();
				System.out.println("�r�n modeli giriniz.");
				String model = scan.next();
				int indexUrun = getUrunbyMarkaModel(marka, model);
				if (indexUrun == -1) {
					System.err.println("B�yle bir �r�n yok");
					continue;
				}
				System.out.println("Kullan�c�n�n ismini giriniz");
				String isim = scan.next();
				System.out.println("Kullan�c�n�n soyismini giriniz");
				String soyisim = scan.next();
				int indexKullanici = getKullanicibyIsimSoyisim(isim, soyisim);
				if (indexKullanici==-1) {
					System.err.println("B�yle bir kullan�c� yok");
					continue;
				}
				SatinAlma satinAlma = new SatinAlma(KULLANICILAR.get(indexKullanici), URUNLER.get(indexUrun));
				KULLANICILAR.get(indexKullanici).satinAldigiUrunEkle(URUNLER.get(indexUrun));
				URUNLER.get(indexUrun).satinAlanKullaniciEkle(KULLANICILAR.get(indexKullanici));
//				k.satinAldigiUrunEkle(u);
//				u.satinAlanKullaniciEkle(k);
				db.satinalmaEkle(satinAlma);
			} else if (input == 4) {
				System.out.println("Birinci �r�n�n markas�n� giriniz:");
				String marka1 = scan.next();
				System.out.println("Birinci �r�n�n modelini giriniz:");
				String model1 = scan.next();
				int u1 = getUrunbyMarkaModel(marka1, model1);
				if (u1 == -1) {
					System.err.println("B�yle bir �r�n yok");
					continue;
				}
				System.out.println("�kinci �r�n�n markas�n� giriniz");
				String marka2 = scan.next();
				System.out.println("�kinci �r�n�n modelini giriniz");
				String model2 = scan.next();
				int u2 = getUrunbyMarkaModel(marka2, model2);
				if (u2 == -1) {
					System.err.println("B�yle bir �r�n yok");
					continue;
				}
				int birinciUrunSayi = URUNLER.get(u1).getSatinAlanKullanicilar().size();
				int ikisideUrunSayi = ikisinideAlanSayisi(u1, u2);
				System.out.println("Birinci �r�n� alan kullan�c� say�s�: " + birinciUrunSayi);
				System.out.println("�ki �r�n� de alan kullan�c� say�s�: " + ikisideUrunSayi);
				double oran = (100.0 * birinciUrunSayi / ikisideUrunSayi);
				System.out.println("Birinci �r�n� alan kullan�c�lar�n y�zde " + oran);
			} else if (input == 6) {
				URUNLER = db.getUrunler();
				for (int i = 0; i < URUNLER.size(); i++) {
					System.out.println(URUNLER.get(i).marka + " - " + URUNLER.get(i).model);
				}
			} else if (input == 5) {
				KULLANICILAR = db.getKullanicilar();
				for (int i = 0; i < KULLANICILAR.size(); i++) {
					System.out.println(KULLANICILAR.get(i).isim + " id:" + KULLANICILAR.get(i).kullaniciId);

				}
			} else {
				break;
			}

		}
		scan.close();
	}

	public static int getUrun(long id) {
		URUNLER = db.getUrunler();
		for (int i = 0; i < URUNLER.size(); i++) {
			if (URUNLER.get(i).urunId == id) {
				return i;
			}
		}
		return -1;
	}

	public static int getUrunbyMarkaModel(String marka, String model) {
		URUNLER = db.getUrunler();
		for (int i = 0; i < URUNLER.size(); i++) {
			if (marka.equalsIgnoreCase(URUNLER.get(i).marka) && model.equalsIgnoreCase(URUNLER.get(i).model)) {
				return i;
			}
		}
		return -1;
	}

	public static int getKullanicibyIsimSoyisim(String isim, String soyisim) {
		KULLANICILAR = db.getKullanicilar();
		for (int i = 0; i < KULLANICILAR.size(); i++) {
			if (isim.equalsIgnoreCase(KULLANICILAR.get(i).isim)
					&& soyisim.equalsIgnoreCase(KULLANICILAR.get(i).soyisim)) {
				return i;
			}
		}
		return -1;
	}

	public static int getKullanici(long id) {
		KULLANICILAR = db.getKullanicilar();
		for (int i = 0; i < KULLANICILAR.size(); i++) {
			if (KULLANICILAR.get(i).kullaniciId == id) {
				return i;
			}
		}
		return -1;
	}

	public static int ikisinideAlanSayisi(int u1, int u2) {
		int count = 0;
		for (int i = 0; i < KULLANICILAR.size(); i++) {
			if (KULLANICILAR.get(i).isExist(URUNLER.get(u1)) && KULLANICILAR.get(i).isExist(URUNLER.get(u2))) {
				count++;
			}
		}
		return count;
	}
	
	public static void arrayListYazdir(ArrayList<?> a){
		System.out.println(a.size()+" eleman.");
		for (int i = 0; i < a.size(); i++) {
			System.out.println(a.get(i).toString());
		}
	}

	public static void satinAlanKullaniciSatis(ArrayList<SatinAlma> arr){
		//ArrayList<SatinAlma> temp = new ArrayList<>();
		for (SatinAlma satinAlma : arr) {
			int k=getKullanici(satinAlma.kullanici.kullaniciId);
			int u = getUrun(satinAlma.urun.urunId);
			URUNLER.get(u).satinAlanKullaniciEkle(KULLANICILAR.get(k));
			KULLANICILAR.get(k).satinAldigiUrunEkle(URUNLER.get(u));
		}
	}
}
