package CRC.view;

import CRC.features.CRC32SDLC;
import CRC.features.Hamming;
import CRC.features.MainGuiClass;
import CRC.features.Parzystosc;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
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
    private Hamming korekcjaHamminga;
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
		
		if(!enterText.getText().isEmpty()) {
			generate.setDisable(true);
			submit.setDisable(false);
		}	
	}	
	
	@FXML
	private void submitClicked() {	
		submit.setDisable(true);
		check.setDisable(false);
	}
	
	@FXML
	private void checkClicked() {	
		check.setDisable(true);
		originalBits.setDisable(false);
	}
	
	@FXML
	private void originalBitsClicked() {	
		originalBits.setDisable(true);
		showAsText.setDisable(false);
	}
	
	@FXML
	private void showAsTextClicked() {	
		originalBits.setDisable(true);
		showAsText.setDisable(true);
	}
}
