package CRC.features;

public class CRC32SDLC {

	String inputMessage;
	String outputMessage = "";
	int[] generator;
	int[] message;
	int[] table;

	public CRC32SDLC(String message, String crcType) {
		inputMessage = message;
		this.message = new int[message.length()];
		this.generator = new int[crcType.length()];

		char[] charForMessage = message.toCharArray();
		char[] charForCrcType = crcType.toCharArray();
		for (int i = 0; i < message.length(); i++) {
			this.message[i] = Character.getNumericValue(charForMessage[i]);
		}
		for (int i = 0; i < crcType.length(); i++) {
			this.generator[i] = Character.getNumericValue(charForCrcType[i]);
		}
	}

	// metoda wyznacza wiadomoœæ wyjœciow¹
	void countCRC() {
		table = new int[generator.length + message.length - 1];
		for (int i = 0; i < message.length; i++)
			table[i] = message[i];
		table = count(generator, table);
		for (int i = 0; i < message.length; i++) {
			outputMessage += message[i] ^ table[i];
		}
	}

	// metoda pomocnicza w której wykorzystywana jest operacja alternatywy
	// wykluczaj¹cej
	int[] count(int g[], int t[]) {
		int thiss = 0;
		while (true) {
			for (int i = 0; i < g.length; i++)
				t[thiss + i] = (t[thiss + i] ^ g[i]);
			while (t[thiss] == 0 && thiss != t.length - 1)
				thiss++;
			if ((t.length - thiss) < g.length)
				break;
		}
		return t;
	}
}