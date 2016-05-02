package CRC.features;

public class Parzystosc {

	private int[] orginalnaWiadomosc;
	private int[] finalneBity;
	private String finalnyTekst;
	private String wiadomoscOrginalnaTekst;

	public Parzystosc(int[] message) {
		// TODO Auto-generated constructor stub
		wiadomoscOrginalnaTekst = "";
		finalnyTekst = "";
		finalneBity = new int[message.length + 1];
		orginalnaWiadomosc = new int[message.length];

		for (int i = 0; i < message.length; i++) {
			orginalnaWiadomosc[i] = message[i];
			finalneBity[i + 1] = message[i];
		}
	}

	/**
	 * Funkcja oblicza bit parzystoœci
	 */
	public void obliczBitParzystosci() {

		int liczbaJedynek = 0;

		for (int i = 0; i < orginalnaWiadomosc.length; i++) {
			if (orginalnaWiadomosc[i] == 1) {
				liczbaJedynek++;
			}
		}
		if (liczbaJedynek % 2 == 0) {
			finalneBity[0] = 0;
		} else {
			finalneBity[0] = 1;
		}

		// Zamiana tablicy int na String
		for (int i : finalneBity) {
			finalnyTekst += String.valueOf(i);
		}
		for (int i : orginalnaWiadomosc) {
			wiadomoscOrginalnaTekst += String.valueOf(i);
		}
	}
	
	//Sprawdzanie poprawnoœci s³owa
	public boolean czyPoprawneSlowo(int[] message) {

		int jedynki = 0;

		for (int i = 0; i < message.length; i++) {
			
			if(message[i] == 1) {
				jedynki++;
			}			
		}
		if(jedynki % 2 == 1) {
			
			return false;
		}
		else {
			return true;
		}
	}
}
