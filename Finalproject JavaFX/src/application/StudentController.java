package application;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import client.DAO;
import entity.Address;
import entity.PersonalData;
import entity.Student;
import enums.Genders;
import enums.Titles;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StudentController implements Initializable{

    @FXML    private VBox vBox;
    @FXML    private Tab personalDataTab;
    @FXML    private GridPane personalDataGridPane;
    @FXML    private ComboBox<Titles> titleComboBox;
    @FXML    private ComboBox<Genders> genderComboBox;
    @FXML    private TextField firstnameTextfield;
    @FXML    private TextField lastnameTextfield;
    @FXML    private DatePicker dateOfBirthDatePicker;
    @FXML    private TextField phoneTextfield;
    @FXML    private TextField emailTextfield;
    @FXML    private Button savePersonalDataButton;
    @FXML    private Button closePersonalDataButton;
    @FXML    private Tab addressTab;
    @FXML    private GridPane addressGridPane;
    @FXML    private TextField streetTextField;
    @FXML    private TextField houseNrTextField;
    @FXML    private TextField zipCodeTextField;
    @FXML    private TextField cityTextField;
    @FXML    private TextField countryTextField;
    @FXML    private CheckBox isActiv;
    private ObservableList<Student> studentList = FXCollections.observableArrayList();
    private Student currentStudent;
    private Stage primaryStage;
    private Stage secondaryStage;
    private VerwaSoftController vwsController;
    
    
    /**
     * Diese Methode reagiert auf ein ActionEvent
     * Mit Tastatur eingegebene Strings werden nach bestimmten Kriterien überprüft. Wennn die Kriterien erfüllt werden, wird der Button "Speichern" aktiviert, die Teilnehmerdaten speichern zu können.
     * @param event
     */
    @FXML
	void keyTypedProperty(KeyEvent event) {

			savePersonalDataButton.setDisable(isValidInput());


	}
    
    /**
     * in das Formula eingegebene Daten werden nach bestimmten Kriterien überprüft. Wenn die Kriterien erfüllt werden, gibt true zurück, sonst false
     * @return true/false
     */
    public boolean isValidInput() {
    	String fString = firstnameTextfield.getText();
		String sString = lastnameTextfield.getText();
		String pString = phoneTextfield.getText();
		String eString = emailTextfield.getText();
		String bdString = dateOfBirthDatePicker.getEditor().getText();
		boolean genderSelected = genderComboBox.getSelectionModel().isEmpty();
		
		boolean saveButtonDisable = (fString.isEmpty() || fString.trim().isEmpty())
				|| (sString.isEmpty() || sString.trim().isEmpty()) || (pString.isEmpty() || pString.trim().isEmpty())
				|| !VerwaSoftController.isValidEmail(eString)|| genderSelected || !VerwaSoftController.isParsableToDate(bdString);

    	return saveButtonDisable;
    }
    
    /**
     * Methode reagiert auf ActionEvent
     * Wenn auf die Checkbox geklickt wird, wird der Button Speichern aktiviert, um die Änderungen speichern zu können
     * @param event
     */
    @FXML void studentActivationAction(MouseEvent event){

    	savePersonalDataButton.setDisable(isValidInput());
    }
    
    /**
     * Wenn auf Dropmenü "Titel" geklickt wird, wird der Button "Speichern" aktiviert für die Änderungen
     * @param event
     */
    @FXML void titelChangeAction(MouseEvent event) {
    	savePersonalDataButton.setDisable(isValidInput());
    }
    
    
    /**
     * mit den in das Teilnehmerformular eingegebene Daten wird ein Trainer erstellt und in der DB gespeichert.
     * @throws SQLException
     */
    @FXML
	private void createStudent() throws SQLException {
    	
		PersonalData pd = new PersonalData();
		pd.setTitle(titleComboBox.getValue());
		pd.setGender(genderComboBox.getValue());
		pd.setFirstname(firstnameTextfield.getText());
		pd.setLastname(lastnameTextfield.getText());
		
		pd.setDateOfBirth(LocalDate.parse(dateOfBirthDatePicker.getEditor().getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));	
		pd.setEmail(emailTextfield.getText());
		pd.setTelefon(phoneTextfield.getText());
		
		Address ad = new Address();
		ad.setStreet(streetTextField.getText());
		ad.setHouseNr(houseNrTextField.getText());
		ad.setZipcode(zipCodeTextField.getText());
		ad.setCity(cityTextField.getText());
		ad.setCountry(countryTextField.getText());
		
		Student newStudent = new Student(pd, ad);
		newStudent.setActiv(isActiv.isSelected());
		if(studentList.contains(newStudent)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("Dieser Teilnehmer ist bereits im System vorhanden!");
			//alert.setContentText("");
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.showAndWait();		
		} else {			
			
			DAO.createStudent(newStudent);
			Stage stage = (Stage) closePersonalDataButton.getScene().getWindow();
			
	        stage.close();
		}
		
		
		vwsController.loadStudents();
	}
    


    /**
	 * @return the primaryStage
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * @param primaryStage the primaryStage to set
	 */
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	/**
	 * @return the secondaryStage
	 */
	public Stage getSecondaryStage() {
		return secondaryStage;
	}

	/**
	 * @param secondaryStage the secondaryStage to set
	 */
	public void setSecondaryStage(Stage secondaryStage) {
		this.secondaryStage = secondaryStage;
	}
	
	
	/**
	 * Methode reagiert auf ein ActionEvent
	 * Die Stage von Action wird ermittelt und diese wird geschlossen
	 * @param event
	 */
	@FXML
    void closePersonalDataButtonAction(ActionEvent event) {
    	
    	Stage stage = (Stage) closePersonalDataButton.getScene().getWindow();
        stage.close();    	
    }


	/**
     * Methode reagiert auf ein ActionEvent
     * Die Teilnehmerdaten im Formular wird in der DB gespeichert bzw. aktualisiert.
     * @param event
     */
    @FXML
    void savePersonalDataButtonAction(ActionEvent event) {
    	try {
    		if(currentStudent==null) {
    			
    			 		
    				
    				createStudent();
    			
    		} else {
    			PersonalData pd = new PersonalData();
    			pd.setTitle(titleComboBox.getValue());
    			pd.setGender(genderComboBox.getValue());
    			pd.setFirstname(firstnameTextfield.getText());
    			pd.setLastname(lastnameTextfield.getText());   			
    			pd.setDateOfBirth(LocalDate.parse(dateOfBirthDatePicker.getEditor().getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
			  	pd.setEmail(emailTextfield.getText());
    			pd.setTelefon(phoneTextfield.getText());    			
    			Address ad = new Address();
    			ad.setStreet(streetTextField.getText());
    			ad.setHouseNr(houseNrTextField.getText());
    			ad.setZipcode(zipCodeTextField.getText());
    			ad.setCity(cityTextField.getText());
    			ad.setCountry(countryTextField.getText());
    			currentStudent.setPersonalData(pd);
    			currentStudent.setAddress(ad);
    			currentStudent.setActiv(isActiv.isSelected());
    			DAO.updateStudent(currentStudent);
    			Stage stage = (Stage) closePersonalDataButton.getScene().getWindow();

    			//loadStudents();
    		}
			
//			Stage stage = (Stage) closePersonalDataButton.getScene().getWindow();
//			//primaryStage.close();
//	        stage.close();
	        vwsController.loadStudents();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		genderComboBox.setItems(FXCollections.observableArrayList(Genders.values()));
		titleComboBox.setItems(FXCollections.observableArrayList(Titles.values()));
		DAO dao = new DAO();
		studentList = dao.studentList();		
	}
	/**
     * das Teilnehmerformular wird mit den angegebenen Teilnehmerdaten zum Editieren geöffnet
     * @param trainer
     * @throws SQLException
     */
	public void editStudent(Student student) {
		currentStudent = student;		
		titleComboBox.setValue(student.getPersonalData().getTitle());;
		genderComboBox.setValue(student.getPersonalData().getGender());
		firstnameTextfield.setText(student.getPersonalData().getFirstname());
		lastnameTextfield.setText(student.getPersonalData().getLastname());
		dateOfBirthDatePicker.setValue(student.getPersonalData().getDateOfBirth());
		emailTextfield.setText(student.getPersonalData().getEmail());
		phoneTextfield.setText(student.getPersonalData().getTelefon());		
		streetTextField.setText(student.getAddress().getStreet());
		houseNrTextField.setText(student.getAddress().getHouseNr());
		zipCodeTextField.setText(student.getAddress().getZipcode());
		cityTextField.setText(student.getAddress().getCity());
		countryTextField.setText(student.getAddress().getCountry());
		isActiv.setSelected(student.isActiv());
	}

	/**
	 * @return the vwsController
	 */
	public VerwaSoftController getVwsController() {
		return vwsController;
	}

	/**
	 * @param vwsController the vwsController to set
	 */
	public void setVwsController(VerwaSoftController vwsController) {
		this.vwsController = vwsController;
	}

}
