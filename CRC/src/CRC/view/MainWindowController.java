package CRC.view;

import java.math.BigInteger;
import java.util.Random;

import CRC.features.CRC32SDLC;
import CRC.features.Hamming;
import CRC.features.MainGuiClass;
import CRC.features.Parzystosc;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class MainWindowController {

	private MainGuiClass mainClass;

	private String wiadomoscOdUzytkownika;
	private String wiadomoscBinarnie;
	private String wiadomoscZBledami;
	private int liczbaBledow;
	private String metoda;

	private CRC32SDLC korekcjaCrc;
	private Hamming mHamming;
	private Parzystosc kontrolaParzystosci;

	String rodzajCrc;
	private final String crc_32 = "100000100110000010001110110110111";
	private final String sdlc = "10001000000100001";

	boolean hammingPoprawnyBoolean;

	@FXML
	private Button generate;
	@FXML
	private Button submit;
	@FXML
	private Button check;
	@FXML
	private Button originalBits;
	@FXML
	private Button showAsText;
	@FXML
	private ChoiceBox<String> methods;
	@FXML
	private ChoiceBox<String> incorrectBits;
	@FXML
	private TextArea enterText;
	@FXML
	private TextArea generatedCode;

	public MainWindowController() {
		// TODO Auto-generated constructor stub		
	}

	public void setMainClass(MainGuiClass mainGuiClass) {
		// TODO Auto-generated method stub

		this.mainClass = mainGuiClass;
	}

	@FXML
	private void initialize() {		
		methods.getItems().addAll("Kontrola parzystoœci", "Kod Hamminga", "CRC 32", "SDLC");
		methods.getSelectionModel().selectFirst();

		incorrectBits.getItems().addAll("0", "1", "2", "3");
		incorrectBits.getSelectionModel().selectFirst();
	}

	@FXML
	private void generateClicked() {

		if (!enterText.getText().isEmpty()) {			
			hammingPoprawnyBoolean = false;
			mHamming = new Hamming(enterText.getText());
			
			int rodzaj = methods.getSelectionModel().getSelectedIndex();
			System.out.println("Rodzaj: " + rodzaj);
			switch (rodzaj) {
			case 0:
				metoda = "parz";
				break;
			case 1:
				metoda = "hamm";
				break;
			case 2:
				metoda = "crc32";
				rodzajCrc = crc_32;
				break;
			case 3:
				metoda = "sdlc";
				rodzajCrc = sdlc;
				break;
			default:
				metoda = "hamm";
			}
			wiadomoscOdUzytkownika = enterText.getText();
			System.out.println("Tekst u¿ytkownika: " + wiadomoscOdUzytkownika);
			if (wiadomoscOdUzytkownika.length() > 0) {
				wiadomoscBinarnie = new BigInteger(wiadomoscOdUzytkownika.getBytes()).toString(2);
				System.out.println("Wiadomoœæ binarnie: " + wiadomoscBinarnie);

				if (metoda.equals("parz")) {
					System.out.println("Metoda: " + "parzystoœæ");
					int[] tabl = new int[wiadomoscBinarnie.length()];
					for (int i = 0; i < wiadomoscBinarnie.length(); i++) {
						if (wiadomoscBinarnie.charAt(i) == '1')
							tabl[i] = 1;
						else if (wiadomoscBinarnie.charAt(i) == '0')
							tabl[i] = 0;
					}
					kontrolaParzystosci = new Parzystosc(tabl);
					System.out.println("Finalny tekst: " + kontrolaParzystosci.finalnyTekst);
					generatedCode.setText(kontrolaParzystosci.finalnyTekst);

				} 
				else if(metoda.equals("hamm")) {
					System.out.println("Metoda: " + "hamming");					
					mHamming.koduj();
					generatedCode.setText(mHamming.wygenerowane_bity);
				}
				else if (metoda.equals("crc32")) {
					System.out.println("Metoda: " + "crc32");
					korekcjaCrc = new CRC32SDLC(wiadomoscBinarnie, crc_32);
					korekcjaCrc.countCRC();
					System.out.println("Finalny tekst: " + korekcjaCrc.outputMessage);
					generatedCode.setText(korekcjaCrc.outputMessage);
				} 
				else if (metoda.equals("sdlc")) {
					System.out.println("Metoda: " + "sdlc");
					korekcjaCrc = new CRC32SDLC(wiadomoscBinarnie, sdlc);
					korekcjaCrc.countCRC();
					System.out.println("Finalny tekst: " + korekcjaCrc.outputMessage);
					generatedCode.setText(korekcjaCrc.outputMessage);
				}
			}

			incorrectBits.setDisable(false);
			submit.setDisable(false);
		}
	}

	@FXML
	private void submitClicked() {
		generate.setDisable(true);
		submit.setDisable(true);
		check.setDisable(false);
		incorrectBits.setDisable(true);
		
		wiadomoscZBledami = generatedCode.getText();
		liczbaBledow = incorrectBits.getSelectionModel().getSelectedIndex();
		
		if (liczbaBledow > 0) {
            String[] bity_podzial = new String[liczbaBledow];
            Random generator = new Random();
            for (int i = 0; i < liczbaBledow; i++) {
                int x = generator.nextInt(wiadomoscZBledami.length() - 4);
                bity_podzial[i] = Integer.toString(x);
            }
            if (bity_podzial.length > 0) {
                int bit_do_zmiany;
                char wartosc;
                for (int i = 0; i < 4; i++) {
                    if (bity_podzial.length > i) {
                        bit_do_zmiany = wiadomoscZBledami.length() - Integer.parseInt(bity_podzial[i]) - 1;
                        wartosc = wiadomoscZBledami.charAt(bit_do_zmiany);
                        if (wartosc == '0') {
                            wiadomoscZBledami = wiadomoscZBledami.substring(0, bit_do_zmiany) + '1' + wiadomoscZBledami.substring(bit_do_zmiany + 1);
                        } else if (wartosc == '1') {
                            wiadomoscZBledami = wiadomoscZBledami.substring(0, bit_do_zmiany) + '0' + wiadomoscZBledami.substring(bit_do_zmiany + 1);
                        } else {
                            System.err.println("B£¥D ZAMIANY!");
                        }
                    }
                }
            }
            generatedCode.setText(wiadomoscZBledami);
        }
	}

	@FXML
	private void checkClicked() {
		if(metoda.equals("parz")){
            wiadomoscZBledami = generatedCode.getText();
            int[] a = new int[wiadomoscZBledami.length()];
            char[] ch = wiadomoscZBledami.toCharArray();
            for (int i = 0; i < wiadomoscZBledami.length(); i++) 
                a[i] = Character.getNumericValue(ch[i]);
            if(kontrolaParzystosci.czyPoprawneSlowo(a)==true) {
                //JOptionPane.showMessageDialog(null, "Kod poprawny","Sprawdzenie parzystoœci", JOptionPane.INFORMATION_MESSAGE);
            	Alert alert = new Alert(AlertType.INFORMATION);
            	alert.setTitle("Information Dialog");
            	alert.setContentText("Kod poprawny Sprawdzenie parzystoœci");

            	alert.showAndWait();
            }	
            else {
                //JOptionPane.showMessageDialog(null, "Kod b³êdny","Sprawdzenie parzystoœci", JOptionPane.INFORMATION_MESSAGE);
            	Alert alert = new Alert(AlertType.INFORMATION);
            	alert.setTitle("Information Dialog");
            	alert.setContentText("Kod b³êdny Sprawdzenie parzystoœci");

            	alert.showAndWait();
            }
        }
        else if(metoda.equals("hamm")){
        	Random r = new Random();
        	int low = 1;
        	int high = mHamming.zakodowane_bity.length;
        	int result = r.nextInt(high-low) + low;
        	
        	mHamming.zmien_buffer(generatedCode.getText());        	
        	mHamming.przeklam_bit(result);
        	
        	if(mHamming.sprawdz_ciag() == 0) {
        		
        	}
        	else if(mHamming.sprawdz_ciag() == -1) {
        		Alert alert = new Alert(AlertType.INFORMATION);
            	alert.setTitle("Information Dialog");
            	alert.setContentText("Kod b³êdny wielokrotnie");

            	alert.showAndWait();
        	}
        	else {
        		Alert alert = new Alert(AlertType.INFORMATION);
            	alert.setTitle("Information Dialog");
            	alert.setContentText("Kod b³êdny B³¹d na pozycji " + mHamming.sprawdz_ciag());

            	alert.showAndWait();
        	}
        	
        } else if(metoda.equals("crc32") || metoda.equals("sdlc")){
                String do_sprawdzenia = generatedCode.getText();
                do_sprawdzenia = do_sprawdzenia.substring(0, do_sprawdzenia.length() - rodzajCrc.length() + 1);
                CRC32SDLC crc_spr = new CRC32SDLC(wiadomoscBinarnie, rodzajCrc);
                crc_spr.countCRC();
                if (generatedCode.getText().equals(crc_spr.outputMessage)) {
                    //JOptionPane.showMessageDialog(null, "CRC poprawne","Sprawdzenie CRC.", JOptionPane.INFORMATION_MESSAGE);
                	Alert alert = new Alert(AlertType.INFORMATION);
                	alert.setTitle("Information Dialog");
                	alert.setContentText("CRC poprawne Sprawdzenie CRC.");

                	alert.showAndWait();
                	
                    hammingPoprawnyBoolean = true;
                } else {
                    //JOptionPane.showMessageDialog(null, "CRC b³êdne", "Sprawdzenie CRC.", JOptionPane.INFORMATION_MESSAGE);
                	Alert alert = new Alert(AlertType.INFORMATION);
                	alert.setTitle("Information Dialog");
                	alert.setContentText("CRC b³êdne Sprawdzenie CRC.");

                	alert.showAndWait();
                    hammingPoprawnyBoolean = true;
                    
                    hammingPoprawnyBoolean = false;
                }
        }		
		
		check.setDisable(true);
		originalBits.setDisable(false);
	}

	@FXML
	private void originalBitsClicked() {
		originalBits.setDisable(true);
		showAsText.setDisable(false);
		
		if(metoda.equals("parz"))
            generatedCode.setText(kontrolaParzystosci.wiadomoscOrginalnaTekst);
        else if(metoda.equals("hamm")) {
        	mHamming.zmien_buffer(generatedCode.getText());
        	mHamming.odtworz_wiadomosc();
        	generatedCode.setText(mHamming.odtworzone_bity);
        }
        else if(metoda.equals("crc32") || metoda.equals("sdlc"))
        	generatedCode.setText(korekcjaCrc.outputMessage);
	}

	@FXML
	private void showAsTextClicked() {
		originalBits.setDisable(true);		
		
		String text = generatedCode.getText();
        if (text.length() % 8 == 0) {
            } else {
            for (int i = 8 - text.length() % 8; i > 0; i = i - 1) {
                text = "0" + text;
            }
        }
        String tresc = "";
        char nextChar;
        for (int i = 0; i <= text.length() - 8; i += 8) {
            nextChar = (char) Integer.parseInt(text.substring(i, i + 8), 2);
            tresc += nextChar;
        }
        generatedCode.setText(tresc);
        
        showAsText.setDisable(true);
        generate.setDisable(false);
	}
}
