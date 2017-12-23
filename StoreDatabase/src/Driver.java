import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
	static ArrayList<Kullanici> KULLANICILAR;
	static ArrayList<Urun> URUNLER;
	static ArrayList<SatinAlma> SATINALMALAR;
	static Database db= new Database();

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		while (true) {
			System.out.println("MENU:\n(1)Kullanýcý Ekle\n(2)Ürün Ekle\n(3)Satýnalma ekle\n(4)Analiz Al\n(5)Çýkýþ");
			
			break;
		}
		scan.close();
	}
	public static Urun getUrun(long id){
		URUNLER = db.getUrunler();
		for (int i = 0; i < URUNLER.size(); i++) {
			if(URUNLER.get(i).urunId==id){
				return URUNLER.get(i);
			}
		}
		return null;
	}
	public static Kullanici getKullanici(long id){
		KULLANICILAR = db.getKullanicilar();
		for (int i = 0; i < KULLANICILAR.size(); i++) {
			if(KULLANICILAR.get(i).kullaniciId==id){
				return KULLANICILAR.get(i);
			}
		}
		return null;
	}
}
