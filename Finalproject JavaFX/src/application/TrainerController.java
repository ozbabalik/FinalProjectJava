package application;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entity.Address;
import entity.PersonalData;
import entity.Qualification;
import entity.Trainer;
import enums.Genders;
import enums.Titles;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TrainerController implements Initializable{
	
//	private final ObservableList<Qualification> qualifications =
//	        FXCollections.observableArrayList();
	private Trainer currentTrainer;
	private ObservableList<Trainer> trainers = FXCollections.observableArrayList();
    @FXML    private VBox vBoxTrainerForm;
    @FXML    private Tab personalDataTab;
    @FXML    private GridPane personalDataGridPane;
    @FXML    private ComboBox<Titles> titleComboBox;
    @FXML    private ComboBox<Genders> genderComboBox;
    @FXML    private TextField firstnameTextfield;
    @FXML    private TextField lastnameTextfield;
    @FXML    private DatePicker dateOfBirthDatePicker;    
    @FXML    private TextField socialSecurityNrTextField;
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

    @FXML private TableView<Qualification> trainerQualTableViewList  = new TableView<Qualification>();
    @FXML private TableColumn<Qualification, String> qualListTitelCol  = new TableColumn<>("Titel");
    @FXML private TableColumn<Qualification, String> qualListDescCol = new TableColumn<>("Beschreibung");
    private ObservableList<Qualification> trainerQualTableViewListData = FXCollections.observableArrayList();
    
    @FXML private TableView<Qualification> trainerQualTableView = new TableView<Qualification>();
    @FXML private TableColumn<Qualification, String> qualTitelCol = new TableColumn<>("Titel");
    @FXML private TableColumn<Qualification, String> qualDescCol = new TableColumn<>("Beschreibung");
    private ObservableList<Qualification> trainerQualTableViewData = FXCollections.observableArrayList();
    private VerwaSoftController vwsController;
    private Stage primaryStage;
    private Stage secondaryStage;
    
    
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
				||!VerwaSoftController.isValidEmail(eString)|| genderSelected || !VerwaSoftController.isParsableToDate(bdString) ;

	
		if (!saveButtonDisable) {
			savePersonalDataButton.setDisable(false);
		} else {
			savePersonalDataButton.setDisable(true);

		}

	}
    
    @FXML
	private void createTrainer() throws SQLException {
		PersonalData pd = new PersonalData();
		pd.setTitle(titleComboBox.getValue());
		pd.setGender(genderComboBox.getValue());
		pd.setFirstname(firstnameTextfield.getText());
		pd.setLastname(lastnameTextfield.getText());
		
//		if(dateOfBirthDatePicker.getValue()!=null) {
//			LocalDate ld=dateOfBirthDatePicker.getValue();
//			pd.setDateOfBirth(ld.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
//		}
		pd.setDateOfBirth(LocalDate.parse(dateOfBirthDatePicker.getEditor().getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
		pd.setEmail(emailTextfield.getText());
		pd.setTelefon(phoneTextfield.getText());
		
		Address ad = new Address();
		ad.setStreet(streetTextField.getText());
		ad.setHouseNr(houseNrTextField.getText());
		ad.setZipcode(zipCodeTextField.getText());
		ad.setCity(cityTextField.getText());
		ad.setCountry(countryTextField.getText());
		
		String vn=socialSecurityNrTextField.getText();
		
		Trainer newTrainer = new Trainer(vn, pd, ad);
		newTrainer.setQualifications(trainerQualTableViewData);
		newTrainer.setActiv(isActiv.isSelected());
		DAO.createTrainer(newTrainer);
		//loadTrainers();
	}
    
	void editTrainer(Trainer trainer) throws SQLException {
		currentTrainer=trainer;		
		titleComboBox.setValue(trainer.getPersonalData().getTitle());;
		genderComboBox.setValue(trainer.getPersonalData().getGender());
		firstnameTextfield.setText(trainer.getPersonalData().getFirstname());
		lastnameTextfield.setText(trainer.getPersonalData().getLastname());
		dateOfBirthDatePicker.setValue(trainer.getPersonalData().getDateOfBirth());
		emailTextfield.setText(trainer.getPersonalData().getEmail());
		phoneTextfield.setText(trainer.getPersonalData().getTelefon());		
		streetTextField.setText(trainer.getAddress().getStreet());
		houseNrTextField.setText(trainer.getAddress().getHouseNr());
		zipCodeTextField.setText(trainer.getAddress().getZipcode());
		cityTextField.setText(trainer.getAddress().getCity());
		countryTextField.setText(trainer.getAddress().getCountry());	
		socialSecurityNrTextField.setText(trainer.getSocialSecurityNr());
		trainerQualTableViewData.setAll(trainer.getQualifications());
		trainerQualTableView.setItems(trainerQualTableViewData);
	    trainerQualTableViewList.setItems(trainerQualTableViewListData);
		savePersonalDataButton.setDisable(false);
		isActiv.setSelected(trainer.isActiv());
		
	}

    @FXML
    void closePersonalDataButtonAction(ActionEvent event) {

    	Stage stage = (Stage) closePersonalDataButton.getScene().getWindow();
        stage.close();    	
    }



    @FXML
    void savePersonalDataButtonAction(ActionEvent event) {
    	try {
    		if(currentTrainer==null) {
			createTrainer();
    		} else {
    			//long id = Long.parseLong(vBoxTrainerForm.getId());
    			String socialSecurityNr = socialSecurityNrTextField.getText();
    			PersonalData pd = new PersonalData();
    			pd.setTitle(titleComboBox.getValue());;
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
    			List<Qualification> qualList = new ArrayList<Qualification>();
    			qualList.addAll(trainerQualTableViewData);
    			currentTrainer.setPersonalData(pd);
    			currentTrainer.setQualifications(qualList);
    			currentTrainer.setAddress(ad);
    			currentTrainer.setSocialSecurityNr(socialSecurityNr);
    			currentTrainer.setActiv(isActiv.isSelected());
    			DAO.updateTrainer(currentTrainer);
    			
    		}
			Stage stage = (Stage) closePersonalDataButton.getScene().getWindow();
	        stage.close();
	        vwsController.loadTrainers();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }



    
//    public static void loadTrainers() {
//    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("verwasoft");
//		EntityManager em = emf.createEntityManager();
//		EntityTransaction txn = em.getTransaction();
//		
//		try {
//			txn.begin();
//			TypedQuery<Trainer>	trainertQuery=em.createQuery(
//					"SELECT e FROM Trainer e", Trainer.class);
//			ObservableList<Trainer> trainers=FXCollections.observableArrayList(trainertQuery.getResultStream().
//												collect(Collectors.toList()));
//			TypedQuery<Qualification>	qualQuery=em.createQuery(
//			"SELECT e FROM Qualification e", Qualification.class);
//	
//			ObservableList<Qualification> qualList=FXCollections.observableArrayList(qualQuery.getResultStream().
//					collect(Collectors.toList()));
//
//			txn.commit();
//			
//		}	catch(Exception e) {
//    			if(txn != null) { txn.rollback(); }
//    			e.printStackTrace();
//		}	finally {
//				if(em != null) { em.close(); }
//		}
//    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("verwasoft");
		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();
		
		try {
			txn.begin();
			TypedQuery<Qualification>	qualQuery=em.createQuery(
					"SELECT e FROM Qualification e", Qualification.class);
			
			trainerQualTableViewListData.addAll(qualQuery.getResultStream().
					collect(Collectors.toList()));
			
			
		}catch(Exception e) {
			if(txn != null) { txn.rollback(); }
			e.printStackTrace();
	}	finally {
			if(em != null) { em.close(); }
	}

		qualListTitelCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		qualListDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
		trainerQualTableViewList.setItems(trainerQualTableViewListData);
		genderComboBox.setItems(FXCollections.observableArrayList(Genders.values()));
		titleComboBox.setItems(FXCollections.observableArrayList(Titles.values()));
	    qualTitelCol.setCellValueFactory(new PropertyValueFactory<>("name"));
	    qualDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));	    
	    trainerQualTableView.setItems(trainerQualTableViewData);
  
		trainerQualTableViewList.setOnMousePressed(new EventHandler<MouseEvent>() {
			
			   @Override 
			   public void handle(MouseEvent e) {
			      if (e.isPrimaryButtonDown() && e.getClickCount() == 2) {
			    	  Qualification selectedQual=trainerQualTableViewList.getSelectionModel().getSelectedItem();
			    	  if(!trainerQualTableViewData.contains(selectedQual)) {
				    	  trainerQualTableViewData.add(selectedQual);	
				    	  //trainerQualTableViewListData.removeAll(selectedQual);
			    	  }
			      }
			   }
			});
		
		trainerQualTableView.setOnMousePressed(new EventHandler<MouseEvent>() {
			   @Override 
			   public void handle(MouseEvent e) {
			      if (e.isPrimaryButtonDown() && e.getClickCount() == 2) {
			    	  Qualification selectedQual=trainerQualTableView.getSelectionModel().getSelectedItem();
			    	  //if(!trainerQualTableViewListData.contains(selectedQual)) {
				    	  trainerQualTableViewData.removeAll(selectedQual);
				    	  //trainerQualTableViewListData.add(selectedQual);
			    	  //}
			      }
			   }
			});
		
		
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public Stage getSecondaryStage() {
		return secondaryStage;
	}

	public void setSecondaryStage(Stage secondaryStage) {
		this.secondaryStage = secondaryStage;
	}

	public VerwaSoftController getVwsController() {
		return vwsController;
	}

	public void setVwsController(VerwaSoftController vwsController) {
		this.vwsController = vwsController;
	}

}
