package application;

import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import client.DAO;
import entity.Booking;
import entity.Course;
import entity.Student;
import entity.Trainer;
import entity.TrainerAssignment;
import enums.AssignmentStates;
import enums.BookingStates;
import enums.CourseStates;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AssignmentController implements Initializable{
	DecimalFormat currency = new DecimalFormat("0.00€");
    @FXML    private VBox vBoxAssignmentForm;
    @FXML    private GridPane gridPaneAssignment;
    @FXML    private Label courseNrLabel;
    @FXML    private Label courseNameLabel;
    @FXML    private Label assignmentTrainerLabel;
//    @FXML    private ComboBox<AssignmentStates> assignmentStateComboBox;
    @FXML    private TextField assignmentTrainerTextfield;
    @FXML    private TableView<Trainer> assignmentTrainerTableView;
    @FXML    private TableColumn<Trainer, String> trainerStaffNrCol;
    @FXML    private TableColumn<Trainer, String> trainerFirstnameCol;
    @FXML    private TableColumn<Trainer, String> trainerLastnameCol;
    @FXML    private TableColumn<Trainer, LocalDate> trainerBirthdayCol;
    @FXML    private TableColumn<Trainer, String> trainerPhoneCol;
    @FXML    private Button saveAssignmentButton;
    @FXML    private Button closeAssignmentButton;
    private Course currentCourse;
    private TrainerAssignment currentAssignment;
    private ObservableList<Trainer> assignmentTrainerTableViewData = FXCollections.observableArrayList();
    private ObservableList<AssignmentStates> assignmentStates =  FXCollections.observableArrayList(AssignmentStates.values());
    
    
    /**
     * eine neue Trainerzuordnung wird mit dem angegebenem Kurs und ausgewähltem Trainer erstellt.
     * @param student
     */
    public void newAssignment(Course course) {
    	currentCourse = course;
    	courseNrLabel.setText(currentCourse.getCourseNr());
    	courseNameLabel.setText(currentCourse.getCourseTitle());
    	DAO trainerDAO = new DAO();
    	
    	assignmentTrainerTableViewData.setAll(trainerDAO.trainerList().filtered(t->t.isActiv()));
    	trainerStaffNrCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStaffNr()));
    	trainerFirstnameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPersonalData().getFirstname()));
    	trainerLastnameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPersonalData().getLastname()));
    	trainerBirthdayCol.setCellValueFactory(cellData -> new SimpleObjectProperty<LocalDate>(cellData.getValue().getPersonalData().getDateOfBirth()));
    	trainerBirthdayCol.setCellFactory((TableColumn<Trainer, LocalDate> column) -> {
			   return new TableCell<Trainer, LocalDate>() {
			      @Override
			      protected void updateItem(LocalDate item, boolean empty) {
			         super.updateItem(item, empty);
			         if (item == null || empty) {
			            setText(null);
			         } else {
			            setText(item.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
			         }
			      }
			   };
			});
    	trainerPhoneCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPersonalData().getTelefon()));
		assignmentTrainerTableView.setItems(assignmentTrainerTableViewData);
		assignmentTrainerTextfield.textProperty().addListener((observable, oldValue, newValue) ->
		assignmentTrainerTableView.setItems(filteredTrainerList(assignmentTrainerTableViewData, newValue)));
		
		
	}
    
    /**
     * Methode reagiert auf ActionEvent
     * Das Formular wird mit den Daten von der angegebenen Trainerzuordnung geöffnet
     * @param event
     */
    @FXML
    public void editAssignment(TrainerAssignment assignment) {
    	currentAssignment = assignment;
    	courseNrLabel.setText(currentAssignment.getCourse().getCourseNr());
    	courseNameLabel.setText(currentAssignment.getCourse().getCourseTitle());
//    	assignmentStateComboBox.setItems(assignmentStates);
//    	assignmentStateComboBox.setValue(currentAssignment.getAssignmentState());
    	DAO trainerDAO = new DAO();
    	
    	assignmentTrainerTableViewData.setAll(trainerDAO.trainerList().filtered(t->t.isActiv()));
    	trainerStaffNrCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStaffNr()));
    	trainerFirstnameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPersonalData().getFirstname()));
    	trainerLastnameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPersonalData().getLastname()));
    	trainerBirthdayCol.setCellValueFactory(cellData -> new SimpleObjectProperty<LocalDate>(cellData.getValue().getPersonalData().getDateOfBirth()));
    	trainerBirthdayCol.setCellFactory((TableColumn<Trainer, LocalDate> column) -> {
			   return new TableCell<Trainer, LocalDate>() {
			      @Override
			      protected void updateItem(LocalDate item, boolean empty) {
			         super.updateItem(item, empty);
			         if (item == null || empty) {
			            setText(null);
			         } else {
			            setText(item.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
			         }
			      }
			   };
			});
    	trainerPhoneCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPersonalData().getTelefon()));
    	assignmentTrainerTableView.getSelectionModel().select(assignment.getTrainer());
		assignmentTrainerTableView.setItems(assignmentTrainerTableViewData);
		assignmentTrainerTextfield.textProperty().addListener((observable, oldValue, newValue) ->
		assignmentTrainerTableView.setItems(filteredTrainerList(assignmentTrainerTableViewData, newValue)));
    }
    /**
     * die als Parameter angegebene Trainerzuordnung wird gelöscht
     * @param assignment
     */
    @FXML
    public void removeAssignment(TrainerAssignment assignment) {
    	currentAssignment = assignment;
    	courseNrLabel.setText(currentAssignment.getCourse().getCourseNr());
    	courseNameLabel.setText(currentAssignment.getCourse().getCourseTitle());
//    	assignmentStateComboBox.setItems(assignmentStates);
//    	assignmentStateComboBox.setValue(currentAssignment.getAssignmentState());
    	DAO trainerDAO = new DAO();
    	
    	assignmentTrainerTableViewData.setAll(trainerDAO.trainerList().filtered(t->t.isActiv()));
    	trainerStaffNrCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStaffNr()));
    	trainerFirstnameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPersonalData().getFirstname()));
    	trainerLastnameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPersonalData().getLastname()));
    	trainerBirthdayCol.setCellValueFactory(cellData -> new SimpleObjectProperty<LocalDate>(cellData.getValue().getPersonalData().getDateOfBirth()));
    	trainerBirthdayCol.setCellFactory((TableColumn<Trainer, LocalDate> column) -> {
			   return new TableCell<Trainer, LocalDate>() {
			      @Override
			      protected void updateItem(LocalDate item, boolean empty) {
			         super.updateItem(item, empty);
			         if (item == null || empty) {
			            setText(null);
			         } else {
			            setText(item.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
			         }
			      }
			   };
			});
    	trainerPhoneCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPersonalData().getTelefon()));
    	assignmentTrainerTableView.getSelectionModel().select(assignment.getTrainer());
		assignmentTrainerTableView.setItems(assignmentTrainerTableViewData);
		assignmentTrainerTextfield.textProperty().addListener((observable, oldValue, newValue) ->
		assignmentTrainerTableView.setItems(filteredTrainerList(assignmentTrainerTableViewData, newValue)));
    }
    
    /**
	 * Die Trainerlist wird mit den angegebenen kriterien gefiltered und die gefilterte List wird zurückgegeben
	 * @param list
	 * @param searchText
	 * @return filteredList
	 */
    private ObservableList<Trainer> filteredTrainerList(List<Trainer> list, String searchText){
    	assignmentTrainerTableView.getSelectionModel().clearSelection();
		
	    List<Trainer> filteredList = new ArrayList<>();
	    for (Trainer trainer : list){
//	    	if(aktiveTrainerCheckBox.isSelected()) {
	        if(searchTrainer(trainer, searchText)) filteredList.add(trainer);
//	    	}
//	    	else {
//	    		if(searchActivTrainer(trainer, searchText)) filteredList.add(trainer);
	    	//}
	    }
	    return FXCollections.observableList(filteredList);
	}
    
    /**
	 * Überprüft ob der angegebene String mit Vor- oder Nachnamen des angegebenen Trainers übereinstimmt.
	 * falls ja, gibt true, wenn nein, gibt false zurück
	 * @param trainer
	 * @param name
	 * @return true/false
	 */	
	private boolean searchTrainer(Trainer trainer, String name){
	    return ((trainer.getPersonalData().getFirstname().toLowerCase().contains(name.toLowerCase())||trainer.getPersonalData().getLastname().toLowerCase().contains(name.toLowerCase())));
	}
	
	/**
	 * Methode reagiert auf ein ActionEvent
	 * Die Stage von Action wird ermittelt und diese wird geschlossen
	 * @param event
	 */
	@FXML
    void assignmentCloseButtonAction(ActionEvent event) {
		Stage stage = (Stage) closeAssignmentButton.getScene().getWindow();
        stage.close();
    }

	/**
     * Methode reagiert auf ActionEvent
     * Mit dem Formular angegebenen Daten für die Trainerzuordnung wird in der DB gespeichert bzw. vorhandene Zuordnung aktualisiert
     * @param event
     */
    @FXML
    void assignmentSaveButtonAction(ActionEvent event) {
    	try {

    		if(!assignmentTrainerTableView.getSelectionModel().isEmpty()) {
    			if(currentAssignment==null) {
    	    		TrainerAssignment newAssignment = new TrainerAssignment();
    	    		newAssignment.setCourse(currentCourse);
    	    		newAssignment.setTrainer(assignmentTrainerTableView.getSelectionModel().getSelectedItem());
//    	    		newAssignment.setAssignmentState(assignmentStateComboBox.getSelectionModel().getSelectedItem());
    	    		newAssignment.setAssignmentDate(LocalDate.now());;	
    	        	DAO.newTrainerAssignment(newAssignment);
    	    	} else {
    	    		
    	    		//currentAssignment.setCourse(currentCourse);
    	    		currentAssignment.setTrainer(assignmentTrainerTableView.getSelectionModel().getSelectedItem());
//    	    		currentAssignment.setAssignmentState(assignmentStateComboBox.getSelectionModel().getSelectedItem());;
    	    		DAO.updateTrainerAssignment(currentAssignment);
    	    	}
    			
    			Stage stage = (Stage) saveAssignmentButton.getScene().getWindow();
    	    	stage.close();
    		} else {
    			Alert alert = new Alert(AlertType.WARNING);
    			alert.setHeaderText("Ungültige Eingabe");
				alert.setContentText("");
				if(assignmentTrainerTableView.getSelectionModel().isEmpty()) {
					assignmentTrainerTableView.setStyle("-fx-border-color: #B22222; ");
					alert.setContentText(alert.getContentText()+"Einen Trainer muss ausgewählt werden\n");
				}
//				if(assignmentStateComboBox.getSelectionModel().isEmpty()) {
//					assignmentStateComboBox.setStyle("-fx-border-color: #B22222; ");
//					alert.setContentText(alert.getContentText()+"Auftragsstatus muss ausgewählt werden\n");
//				}
				
				
				alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
				alert.showAndWait();
    		}
        	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
    }

    /**
   	 * der angegebene String wird überprüft, ob er zu Datum parsable ist.
   	 * @param string
   	 * @return true/false
   	 */  
    private static boolean isParsableToDate(String string) {
        try {
    		LocalDate.parse(string, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            return true;
        } catch (DateTimeParseException exception) {
            return false;
        } 
       
    }
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		
	}

	
	

}
