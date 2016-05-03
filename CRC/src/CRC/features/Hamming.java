package CRC.features;

import java.math.BigInteger;

public class Hamming {

	String wiadomosc_Binarnie; // wiadomosc binarnie String
	public int[] odebrane_bity; // wiadomosc binarnie int[]
	public int[] zakodowane_bity; // przetwarzane bity
	public String wygenerowane_bity; // wiadomosc po zakodowaniu
	public String poprawione_bity; // wiadomosc po poprawieniu bledow
	public String odtworzone_bity; // wiadomosc po zdekodowaniu
	public String odtworzona_wiadomosc; // odtworzony tekst

	public Hamming(String tekst) {

		// TODO Auto-generated constructor stub
		String binarnie; // zmienna pomocnicza
		binarnie = new BigInteger(tekst.getBytes()).toString(2); //zapisanie tekstu w postaci ci¹gu bitów
		wiadomosc_Binarnie = binarnie;
		String temp = new StringBuffer(binarnie).reverse().toString(); //odwrócenie ci¹gu, w celu ³atwiejszych operacji
		binarnie = temp;

		odebrane_bity = new int[binarnie.length()]; //zapisanie ci¹gu bitów do tablicy int

		for (int i = 0; i < binarnie.length(); i++) {
			if (binarnie.charAt(i) == '1') {
				odebrane_bity[i] = 1;
			} 
			else if (binarnie.charAt(i) == '0') {
				odebrane_bity[i] = 0;
			}
		}
	}

	// modyfikacja danych w dowolnym momencie
	public void zmien_buffer(String ciag) {

		String binarnie;

		String temp = new StringBuffer(ciag).reverse().toString(); //odwrócenie ci¹gu, w celu ³atwiejszych operacji
		binarnie = temp;

		odebrane_bity = new int[binarnie.length()]; //zapisanie ci¹gu bitów do tablicy int
		
		for (int i = 0; i < binarnie.length(); i++) {
			if (binarnie.charAt(i) == '1') {
				odebrane_bity[i] = 1;
			} 
			else if (binarnie.charAt(i) == '0') {
				odebrane_bity[i] = 0;
			}
		}
	}

	public void koduj() {
		// okreœlenie liczby bitów parzystoœci
		int liczba_bitow_parzystosci = 0;
		int j = 0;
		while (j < odebrane_bity.length) {
			if (Math.pow(2, liczba_bitow_parzystosci) == j + liczba_bitow_parzystosci + 1) {
				liczba_bitow_parzystosci++;
			} else {
				j++;
			}
		}

		// deklaracja d³ugoœci ci¹gu
		zakodowane_bity = new int[odebrane_bity.length + liczba_bitow_parzystosci];

		// umieszczenie w tablicy bitów wiadomosci i miejsca na bity parzystoœci
		// (pocz¹tkowo bit parystoœci = '2')
		int potega = 0;
		int licznik_wszystkich_bitow = 0;
		for (int i = 1; i <= zakodowane_bity.length; i++) {
			if (Math.pow(2, potega) == i) {
				zakodowane_bity[i - 1] = 2;
				potega++;
			} else {
				zakodowane_bity[licznik_wszystkich_bitow + potega] = odebrane_bity[licznik_wszystkich_bitow];
				licznik_wszystkich_bitow++;
			}
		}

		// obliczenie wartoœci bitów parzystoœci
		for (int i = 0; i < liczba_bitow_parzystosci; i++) {
			zakodowane_bity[(int) (Math.pow(2, i) - 1)] = generuj_bit_parzystosci(zakodowane_bity, i);
		}

		// zapisanie do zmiennej obecnych rezultatów
		String ciag = "";
		for (int i = 0; i < zakodowane_bity.length; i++) {
			ciag += zakodowane_bity[i];
		}

		String temp = new StringBuffer(ciag).reverse().toString();
		ciag = temp;

		wygenerowane_bity = ciag;
	}

	// funkcja wyliczaj¹ca wartoœæ danego bitu parzystoœci
	public int generuj_bit_parzystosci(int[] tablica, int potega) {
		int parzystosc = 0; // licznik bitów znacz¹cych o wartoœci "1"
		boolean znaczace = true; // zmienna pomagaj¹ca pomijaæ bity nie brane
									// pod uwagê
		int pozycja = (int) Math.pow(2, potega); // okreœlenie, o który bit
													// parzystoœci chodzi
		int i = pozycja - 1;
		while (i < tablica.length) { // zliczanie znacz¹cych "jedynek"
			if (znaczace) {
				for (int j = 0; j < pozycja && i < tablica.length; j++) {

					if (tablica[i] == 1 && (Math.log(i + 1) / Math.log(2)) % 1 != 0) // pomijanie
																						// zer
																						// i
																						// bitów
																						// parzystoœci
					{
						parzystosc++;

					}
					i++;

				}

				znaczace = false;
			} else {
				i = i + pozycja;
				znaczace = true;
			}

		}
		// je¿eli liczba jedynek jest parzysta - zwraca "0"
		// nieparzysta - "1"
		return (parzystosc % 2);
	}

	// procedura do przek³amywania bitu na n-tej pozycji
	public void przeklam_bit(int pozycja) {
		if (zakodowane_bity[pozycja - 1] == 0)
			zakodowane_bity[pozycja - 1] = 1;
		else
			zakodowane_bity[pozycja - 1] = 0;
	}

	// funkcja sprawdzaj¹ca poprawnoœci kodu
	// je¿eli wykryje b³¹d, zwraca jego pozycjê (od 1 do n)
	// je¿eli nie znajdzie b³êdu, zwraca 0
	// w innym wypadku -1
	public int sprawdz_ciag() {
		int blad1;
		blad1 = poprawnosc(zakodowane_bity);
		if (blad1 == 0)
			return 0;
		else if (blad1 > 0 && blad1 <= zakodowane_bity.length)
			return blad1;
		else
			return -1;
	}

	// funkcja szukaj¹ca b³êdu
	// zwraca jego pozycjê, b¹dŸ "0"
	public int poprawnosc(int[] tablica) {
		int suma_bitow_parzystosci = 0;
		int index_bitu;
		int liczba_bitow_parzystosci;
		double temp = Math.log(tablica.length) / Math.log(2);
		liczba_bitow_parzystosci = (int) temp + 1;
		for (int i = 0; i < liczba_bitow_parzystosci; i++) {
			index_bitu = (int) Math.pow(2, i);
			// je¿eli obliczony na nowo bit parzystoœci nie zgadza siê
			// z tym co jest w ci¹gu - jego pozycja jest sumowana
			if (tablica[index_bitu - 1] != generuj_bit_parzystosci(tablica, i))
				suma_bitow_parzystosci += index_bitu;
		}
		// suma niezgodnych bitów parzystoœci jest pozycj¹ przek³amanego bitu
		return suma_bitow_parzystosci;
	}

	// procedura poprawiaj¹ca przek³amany bit na n-tej pozycji
	// oraz zapisuj¹ca rezultat w zmiennej
	public void popraw(int pozycja) {
		if (zakodowane_bity[pozycja - 1] == 0)
			zakodowane_bity[pozycja - 1] = 1;
		else
			zakodowane_bity[pozycja - 1] = 0;

		String ciag = "";
		for (int i = 0; i < zakodowane_bity.length; i++) {
			ciag += zakodowane_bity[i];
		}
		String temp = new StringBuffer(ciag).reverse().toString();
		ciag = temp;

		poprawione_bity = ciag;
	}

	// procedura dekoduj¹ca ci¹g bitów do oryginalego ci¹gu
	// oraz odtwarzaj¹ca tekst
	public void odtworz_wiadomosc() {
		String ciag = "";
		char znak;
		String tresc = "";
		int potega = 0;

		// usuniêcie bitów parzystoœci z ci¹gu
		for (int i = 0; i < zakodowane_bity.length; i++) {

			if (Math.pow(2, potega) == i + 1)
				potega++;
			else
				ciag += zakodowane_bity[i];

		}

		String temp = new StringBuffer(ciag).reverse().toString();
		ciag = temp;
		odtworzone_bity = ciag;

		// odtworzenie znaków tekstu
		if (ciag.length() % 8 != 0) {
			for (int i = 8 - ciag.length() % 8; i > 0; i = i - 1)
				ciag = "0" + ciag;

		}

		for (int i = 0; i <= ciag.length() - 8; i += 8) {
			znak = (char) Integer.parseInt(ciag.substring(i, i + 8), 2);
			tresc += znak;

		}
		
		odtworzona_wiadomosc = tresc;
	}
}
