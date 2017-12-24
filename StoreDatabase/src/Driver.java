import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
	/*
	 * Bu class main metodu içeriyor ve çalýþtýrýyor.
	 */

	// Database verilerini senkronize eden arrayList'ler.
	static ArrayList<Kullanici> KULLANICILAR;
	static ArrayList<Urun> URUNLER;
	static ArrayList<SatinAlma> SATINALMALAR;
	static Database db = new Database();//Database objesi.

	public static void main(String[] args) {
		// Main metod

		URUNLER = db.getUrunler();

		KULLANICILAR = db.getKullanicilar();

		SATINALMALAR = db.getSatinAlmalar();

		// satinAlanKullaniciSatis();
		arrayListYazdir(SATINALMALAR);
		arrayListYazdir(URUNLER);
		arrayListYazdir(KULLANICILAR);
		Scanner scan = new Scanner(System.in);
		int input = 0;
		while (true) {
			System.out.println(
					"MENU:\n(1)Kullanýcý Ekle\n(2)Ürün Ekle\n(3)Satýnalma ekle\n(4)Analiz Al\n(5)Kullanýcý Listeleme\n(6)Ürün Listeleme\n(7)Satýþ Listeleme\n(8)ÇIKIÞ");
			input = scan.nextInt();

			if (input == 1) {
				System.out.println("Lütfen isim girin.");
				String isim = scan.next();
				System.out.println("Lütfen soyisim girin:");
				String soyisim = scan.next();
				System.out.println("Lütfen yaþý girin:");
				int yas = scan.nextInt();
				long id = db.getMaxId("KULLANICI", "kullaniciId") + 1;
				Kullanici k = new Kullanici(id, isim, soyisim, yas);
				if (isExistKullanici(k)) {
					System.err.println("Bu kullanýcý zaten var");
					continue;
				}
				db.kullaniciEkle(k);
				KULLANICILAR = db.getKullanicilar();

			} else if (input == 2) {
				System.out.println("Lütfen marka giriniz");
				String marka = scan.next();
				System.out.println("Lütfen model giriniz");
				String model = scan.next();
				Urun urun = new Urun(db.getMaxId("URUN", "urunId") + 1, marka, model);
				if (isExistUrun(urun)) {
					System.err.println("Bu ürün zaten var");
					continue;
				}
				db.urunEkle(urun);
				URUNLER = db.getUrunler();
			} else if (input == 3) {
				System.out.println("Ürün marka giriniz.");
				String marka = scan.next();
				System.out.println("Ürün modeli giriniz.");
				String model = scan.next();
				int indexUrun = getUrunbyMarkaModel(marka, model);
				if (indexUrun == -1) {
					System.err.println("Böyle bir ürün yok");
					continue;
				}
				System.out.println("Kullanýcýnýn ismini giriniz");
				String isim = scan.next();
				System.out.println("Kullanýcýnýn soyismini giriniz");
				String soyisim = scan.next();
				int indexKullanici = getKullanicibyIsimSoyisim(isim, soyisim);
				if (indexKullanici == -1) {
					System.err.println("Böyle bir kullanýcý yok");
					continue;
				}
				SatinAlma satinAlma = new SatinAlma(KULLANICILAR.get(indexKullanici), URUNLER.get(indexUrun));
				KULLANICILAR.get(indexKullanici).satinAldigiUrunEkle(URUNLER.get(indexUrun));
				URUNLER.get(indexUrun).satinAlanKullaniciEkle(KULLANICILAR.get(indexKullanici));
				// k.satinAldigiUrunEkle(u);
				// u.satinAlanKullaniciEkle(k);
				if (isExistSatis(KULLANICILAR.get(indexKullanici), URUNLER.get(indexUrun))) {
					System.err.println("Bu satýþ zaten eklendi");
					continue;
				}
				db.satinalmaEkle(satinAlma);
				SATINALMALAR = db.getSatinAlmalar();
			} else if (input == 4) {
				System.out.println("Birinci ürünün markasýný giriniz:");
				String marka1 = scan.next();
				System.out.println("Birinci ürünün modelini giriniz:");
				String model1 = scan.next();
				int u1 = getUrunbyMarkaModel(marka1, model1);
				if (u1 == -1) {
					System.err.println("Böyle bir ürün yok");
					continue;
				}
				System.out.println("Ýkinci ürünün markasýný giriniz");
				String marka2 = scan.next();
				System.out.println("Ýkinci ürünün modelini giriniz");
				String model2 = scan.next();
				int u2 = getUrunbyMarkaModel(marka2, model2);
				if (u2 == -1) {
					System.err.println("Böyle bir ürün yok");
					continue;
				}
				int birinciUrunSayi = /*
										 * URUNLER.get(u1).
										 * getSatinAlanKullanicilar().size();
										 */ urunuAlanSayisi(URUNLER.get(u1));
				int ikisideUrunSayi = ikisinideAlanSayisi(u1, u2);
				System.out.println("Birinci ürünü alan kullanýcý sayýsý: " + birinciUrunSayi);
				System.out.println("Ýki ürünü de alan kullanýcý sayýsý: " + ikisideUrunSayi);
				double oran = (100.0 * ikisideUrunSayi / birinciUrunSayi);
				System.out.println("Birinci ürünü alan kullanýcýlarýn yüzde " + oran + "'si ikinci ürünü de aldý. ");
			} else if (input == 6) {
				URUNLER = db.getUrunler();
				arrayListYazdir(URUNLER);
			} else if (input == 5) {
				KULLANICILAR = db.getKullanicilar();
				arrayListYazdir(KULLANICILAR);
			} else if (input == 7) {
				SATINALMALAR = db.getSatinAlmalar();
				arrayListYazdir(SATINALMALAR);
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

	public static int urunuAlanSayisi(Urun u) {
		int count = 0;
		for (int i = 0; i < SATINALMALAR.size(); i++) {
			if (SATINALMALAR.get(i).urun.equals(u)) {
				count++;
			}
		}
		return count;
	}

	public static int kullanicininAldigiUrunSayisi(Kullanici k) {
		int count = 0;
		for (int i = 0; i < SATINALMALAR.size(); i++) {
			if (SATINALMALAR.get(i).kullanici.equals(k))
				count++;
		}
		return count;
	}

	public static int ikisinideAlanSayisi(int u1, int u2) {
		int count = 0;
		for (int i = 0; i < KULLANICILAR.size(); i++) {
			if (isExistSatis(KULLANICILAR.get(i), URUNLER.get(u1))
					&& isExistSatis(KULLANICILAR.get(i), URUNLER.get(u2))) {
				count++;
			}
		}
		return count;
	}

	public static void arrayListYazdir(ArrayList<?> a) {
		System.out.println(a.size() + " eleman.");
		for (int i = 0; i < a.size(); i++) {
			System.out.println(a.get(i).toString());
		}
	}

	/*
	 * public static void satinAlanKullaniciSatis() { // ArrayList<SatinAlma>
	 * temp = new ArrayList<>(); // System.out.println(SATINALMALAR.size()); for
	 * (int i = 0; i < SATINALMALAR.size(); i++) { SatinAlma satinAlma =
	 * SATINALMALAR.get(i); int k =
	 * getKullanici(satinAlma.kullanici.kullaniciId); int u =
	 * getUrun(satinAlma.urun.urunId);
	 * URUNLER.get(u).satinAlanKullaniciEkle(KULLANICILAR.get(k));
	 * KULLANICILAR.get(k).satinAldigiUrunEkle(URUNLER.get(u));
	 * System.err.println(URUNLER.get(u).satinAlanKullanicilar.size());
	 * System.err.println(KULLANICILAR.get(k).isim + " " +
	 * KULLANICILAR.get(k).satinAldigiUrunler.size()); } }
	 */

	public static boolean isExistSatis(Kullanici k, Urun u) {
		for (SatinAlma satinAlma : SATINALMALAR) {
			if (satinAlma.kullanici.equals(k) && satinAlma.urun.equals(u)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isExistKullanici(Kullanici k) {
		for (Kullanici kullanici : KULLANICILAR) {
			if (k.equals(kullanici))
				return true;
		}
		return false;
	}

	public static boolean isExistUrun(Urun u) {
		for (Urun urun : URUNLER) {
			if (u.equals(urun))
				return true;
		}
		return false;
	}
}
