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

public class AssignmentEditController implements Initializable{
	DecimalFormat currency = new DecimalFormat("0.00€");
    @FXML    private VBox vBoxAssignmentForm;
    @FXML    private GridPane gridPaneAssignment;
    @FXML    private Label courseNrLabel;
    @FXML    private Label courseNameLabel;
    @FXML    private Label assignmentTrainerLabel;
    @FXML    private ComboBox<AssignmentStates> assignmentStateComboBox;
    @FXML    private TextField assignmentTrainerTextfield;
    @FXML    private Button saveAssignmentButton;
    @FXML    private Button closeAssignmentButton;
    private Course currentCourse;
    private TrainerAssignment currentAssignment;
    private ObservableList<AssignmentStates> assignmentStates =  FXCollections.observableArrayList(AssignmentStates.values());
    

    @FXML
    public void editAssignment(TrainerAssignment assignment) {
    	currentAssignment = assignment;
    	courseNrLabel.setText(currentAssignment.getCourse().getCourseNr());
    	courseNameLabel.setText(currentAssignment.getCourse().getCourseTitle());
    	assignmentStateComboBox.setItems(assignmentStates);
//    	assignmentStateComboBox.setValue(currentAssignment.getAssignmentState());
    	assignmentTrainerLabel.setText(currentAssignment.getTrainer().getPersonalData().getFirstname()+" "+ currentAssignment.getTrainer().getPersonalData().getLastname());
    }
    
    
    
	@FXML
    void assignmentCloseButtonAction(ActionEvent event) {
		Stage stage = (Stage) closeAssignmentButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void assignmentSaveButtonAction(ActionEvent event) {
    	try {

    		if(!assignmentStateComboBox.getSelectionModel().isEmpty()) {
//    	    		currentAssignment.setAssignmentState(assignmentStateComboBox.getSelectionModel().getSelectedItem());;
    	    		DAO.updateTrainerAssignment(currentAssignment);
    	    	    			
    			Stage stage = (Stage) saveAssignmentButton.getScene().getWindow();
    	    	stage.close();
    		} else {
    			Alert alert = new Alert(AlertType.WARNING);
    			alert.setHeaderText("Ungültige Eingabe");
				alert.setContentText("");
				
				if(assignmentStateComboBox.getSelectionModel().isEmpty()) {
					assignmentStateComboBox.setStyle("-fx-border-color: #B22222; ");
					alert.setContentText(alert.getContentText()+"Auftragsstatus muss ausgewählt werden\n");
				}
				
				
				alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
				alert.showAndWait();
    		}
        	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
    }
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		
	}

	
	

}
