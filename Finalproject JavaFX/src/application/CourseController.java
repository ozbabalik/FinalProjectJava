package application;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import entity.Course;
import enums.CourseCategories;
import enums.CourseStates;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CourseController implements Initializable{

	@FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private VBox vBoxCourseForm;
    @FXML private GridPane gridPaneCourse;
    @FXML ComboBox<CourseStates> courseStateComboBox;
    @FXML private TextField coursNrTextField;
    @FXML private TextField courseTitleTextField;
    @FXML private DatePicker courseStartDatePicker;
    @FXML private DatePicker courseEndDatePicker;
    @FXML private TextField coursePriceTextField;
    @FXML private ComboBox<CourseCategories> courseCategoryComboBox;
    @FXML private TextArea courseDescTextArea;
    @FXML private Button saveCourseDataButton;
    @FXML private Button closeCourseDataButton;
    private ObservableList<CourseStates> courseStates =  FXCollections.observableArrayList(CourseStates.values());
    private ObservableList<CourseCategories> courseCategories =  FXCollections.observableArrayList(CourseCategories.values());
    private Course currentCourse;
   
    @FXML
	private void createCourse() throws SQLException {
    	
		Course newCourse = new Course();
		newCourse.setCourseState(courseStateComboBox.getValue());
		newCourse.setCourseNr(coursNrTextField.getText());
		newCourse.setCourseTitle(courseTitleTextField.getText());
		newCourse.setCourseStart(LocalDate.parse(courseStartDatePicker.getEditor().getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
		newCourse.setCourseEnd(LocalDate.parse(courseEndDatePicker.getEditor().getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
		String coursePrice=coursePriceTextField.getText().replace(',', '.');
		newCourse.setCoursePrice(Float.parseFloat(coursePrice));
		newCourse.setCourseCategory(courseCategoryComboBox.getValue());
		newCourse.setCourseDescription(courseDescTextArea.getText());		
		DAO.createCourse(newCourse);
		
	}

 
    private static boolean isParsableToFloat(String string) {
        try {
        	string = string.replace(',', '.');
            Float.parseFloat(string);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        } 
       
    }
    

	private static boolean isParsableToDate(String string) {
        try {
    		LocalDate.parse(string, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            return true;
        } catch (DateTimeParseException exception) {
            return false;
        } 
       
    }
    @FXML
	public void editCourse(Course course) throws SQLException {
		currentCourse = course;
		courseStateComboBox.setValue(course.getCourseState());
		coursNrTextField.setText(course.getCourseNr());
		courseTitleTextField.setText(course.getCourseTitle());
		courseStartDatePicker.setValue(course.getCourseStart());
		courseEndDatePicker.setValue(course.getCourseEnd());
		coursePriceTextField.setText(String.valueOf(course.getCoursePrice()));
		courseCategoryComboBox.setValue(course.getCourseCategory());
		courseDescTextArea.setText(course.getCourseDescription());		
	}
    
    @FXML
    void saveCourseDataButtonAction(ActionEvent event) {
    	try {
    		if(isParsableToFloat(coursePriceTextField.getText())
			  &&isParsableToDate(courseStartDatePicker.getEditor().getText())
			  &&isParsableToDate(courseEndDatePicker.getEditor().getText())
			  &&!courseStateComboBox.getSelectionModel().isEmpty()
			  &&!coursNrTextField.getText().isBlank()
			  &&!courseTitleTextField.getText().isBlank()
			 ){
    			if(currentCourse==null) {
    	    		createCourse();
    	    		} else {
    	    			currentCourse.setCourseState(courseStateComboBox.getValue());
    	    			currentCourse.setCourseNr(coursNrTextField.getText());
    	    			currentCourse.setCourseTitle(courseTitleTextField.getText());
    	    			currentCourse.setCourseStart(LocalDate.parse(courseStartDatePicker.getEditor().getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    	    			currentCourse.setCourseEnd(LocalDate.parse(courseEndDatePicker.getEditor().getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    	    			String coursePrice=coursePriceTextField.getText().replace(',', '.');
    	    			currentCourse.setCoursePrice(Float.parseFloat(coursePrice));
    	    			currentCourse.setCourseCategory(courseCategoryComboBox.getValue());
    	    			currentCourse.setCourseDescription(courseDescTextArea.getText());
    	    			DAO.updateCourse(currentCourse);
    	    		}
    				Stage stage = (Stage) saveCourseDataButton.getScene().getWindow();
    		        stage.close();
    		} else {
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setHeaderText("Ungültige Eingabe");
				alert.setContentText("");
    			if(!isParsableToDate(courseStartDatePicker.getEditor().getText())) {
    				courseStartDatePicker.setStyle("-fx-border-color: #B22222; ");
    				alert.setContentText(alert.getContentText()+"Kursbeginn ist ungültig\n");
    			}
    			if(!isParsableToDate(courseEndDatePicker.getEditor().getText())) {
    				courseEndDatePicker.setStyle("-fx-border-color: #B22222;");
    				alert.setContentText(alert.getContentText()+"Kursende ist ungültig\n");

    			}
    			if(!isParsableToFloat(coursePriceTextField.getText())){
    				coursePriceTextField.setStyle("-fx-border-color: #B22222; -fx-focus-color: #B22222;");
    				alert.setContentText(alert.getContentText()+"Kurspreis ist ungültig");

    			}
    			if(courseStateComboBox.getSelectionModel().isEmpty()){
    				courseStateComboBox.setStyle("-fx-border-color: #B22222; -fx-focus-color: #B22222;");
    				alert.setContentText(alert.getContentText()+"Kursstauts muss ausgewählt werden\n");

    			}
    			
    			if(coursNrTextField.getText().isBlank()){
    				coursNrTextField.setStyle("-fx-border-color: #B22222; -fx-focus-color: #B22222;");
    				alert.setContentText(alert.getContentText()+"Kursnummer muss eingetragen werden\n");

    			}
    			
    			if(courseTitleTextField.getText().isBlank()){
    				courseTitleTextField.setStyle("-fx-border-color: #B22222; -fx-focus-color: #B22222;");
    				alert.setContentText(alert.getContentText()+"Kurstitel muss eingetragen werden\n");

    			}
    			
    			
    			
				alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
				alert.showAndWait();
    		}
    		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    @FXML
    void closeCourseDataButtonAction(ActionEvent event) {
    	Stage stage = (Stage) closeCourseDataButton.getScene().getWindow();
        stage.close();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		courseStartDatePicker.getEditor().setOnKeyTyped(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent arg0) {
				if (!isParsableToDate(courseStartDatePicker.getEditor().getText())) {
					courseStartDatePicker.setStyle("-fx-focus-color: #B22222; -fx-border-color: #B22222;");
					}
				else {
					courseStartDatePicker.setStyle("-fx-focus-color: #039ED3; -fx-border-color: none;");					
				}
			}			
		});
		courseEndDatePicker.getEditor().setOnKeyTyped(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent arg0) {
				if(!isParsableToDate(courseEndDatePicker.getEditor().getText())) {
					courseEndDatePicker.setStyle("-fx-focus-color: #B22222; -fx-border-color: #B22222;");    							
				} else {
					courseEndDatePicker.setStyle("-fx-focus-color: #039ED3;-fx-border-color: none;");    							
				}
			}			
		});
		
		coursePriceTextField.setOnKeyTyped(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent arg0) {
				if(!isParsableToFloat(coursePriceTextField.getText())) {
					coursePriceTextField.setStyle("-fx-focus-color: #B22222;-fx-border-color: #B22222;");    							
				} else {
					coursePriceTextField.setStyle("-fx-focus-color: #039ED3;-fx-border-color: none;");    							
				}
			}			
		});
		
		courseStateComboBox.setItems(courseStates);
		courseCategoryComboBox.setItems(courseCategories);
		
	}

}
