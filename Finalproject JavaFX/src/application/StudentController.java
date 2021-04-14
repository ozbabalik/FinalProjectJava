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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
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
    
    private Student currentStudent;
    private Stage primaryStage;
    private Stage secondaryStage;
    private VerwaSoftController vwsController;
    
    @FXML
	void keyTypedProperty(KeyEvent event) {
		String fString = firstnameTextfield.getText();
		String sString = lastnameTextfield.getText();
		String pString = phoneTextfield.getText();
		String eString = emailTextfield.getText();
		String bdString = dateOfBirthDatePicker.getEditor().getText();
		boolean genderSelected = genderComboBox.getSelectionModel().isEmpty();
		
		boolean saveButtonDisable = (fString.isEmpty() || fString.trim().isEmpty())
				|| (sString.isEmpty() || sString.trim().isEmpty()) || (pString.isEmpty() || pString.trim().isEmpty())
				|| !VerwaSoftController.isValidEmail(eString)|| genderSelected || !VerwaSoftController.isParsableToDate(bdString);

	
		if (!saveButtonDisable) {
			savePersonalDataButton.setDisable(false);
		} else {
			savePersonalDataButton.setDisable(true);

		}

	}
    
    @FXML void studentActivationAction(MouseEvent event){
    	savePersonalDataButton.setDisable(false);
    }
    
    @FXML void titelChangeAction(MouseEvent event) {
    	savePersonalDataButton.setDisable(false);
    }
    
    @FXML
	private void createStudent() throws SQLException {
    	
		PersonalData pd = new PersonalData();
		pd.setTitle(titleComboBox.getValue());
		pd.setGender(genderComboBox.getValue());
		pd.setFirstname(firstnameTextfield.getText());
		pd.setLastname(lastnameTextfield.getText());
		
		pd.setDateOfBirth(LocalDate.parse(dateOfBirthDatePicker.getEditor().getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
		
//		if(dateOfBirthDatePicker.getValue()!=null) {
//			LocalDate ld=dateOfBirthDatePicker.getValue();
//			pd.setDateOfBirth(ld.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
//		} 
//		//System.out.println("Date1: "+ dateOfBirthDatePicker.getEditor().getText());
//		else {
//			LocalDate ld=LocalDate.parse(dateOfBirthDatePicker.getEditor().getText(), new DateTimeFormatter.ofPattern("dd.MM.yyyy"));
//			pd.setDateOfBirth(ld.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
//		}
		
		
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
		DAO.createStudent(newStudent);
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

	@FXML
    void closePersonalDataButtonAction(ActionEvent event) {
    	
    	Stage stage = (Stage) closePersonalDataButton.getScene().getWindow();
        stage.close();    	
    }



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
    			
    			//loadStudents();
    		}
			
			Stage stage = (Stage) closePersonalDataButton.getScene().getWindow();
			//primaryStage.close();
	        stage.close();
	        vwsController.loadStudents();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }



    
    public static void loadStudents() {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("verwasoft");
		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();
		
		try {
			txn.begin();
			TypedQuery<Student>	studentQuery=em.createQuery(
					"SELECT e FROM Student e", Student.class);
			//List<Student> students=studentQuery.getResultList();
			ObservableList<Student> students=FXCollections.observableArrayList(studentQuery.getResultStream().
												collect(Collectors.toList()));
			
			txn.commit();
			
		}	catch(Exception e) {
    			if(txn != null) { txn.rollback(); }
    			e.printStackTrace();
		}	finally {
				if(em != null) { em.close(); }
		}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		genderComboBox.setItems(FXCollections.observableArrayList(Genders.values()));
		titleComboBox.setItems(FXCollections.observableArrayList(Titles.values()));
		
	}

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