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
import enums.BookingStates;
import enums.CourseStates;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
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

public class BookingController implements Initializable{
	DecimalFormat currency = new DecimalFormat("0.00€");
	@FXML	private VBox vBoxCourseForm;
    @FXML	private GridPane gridPaneCourse;
    @FXML	private ComboBox<BookingStates> bookingStateComboBox;
    @FXML	private DatePicker bookingDateDatePicker;
    @FXML	private TextField bookingKursTextfield;
    @FXML	private Label customerNrLabel;
    @FXML	private Label studentNameLabel;
    @FXML	private TableView<Course> bookingCourseTableView;
    @FXML	private TableColumn<Course, String> courseNrCol;
    @FXML   private TableColumn<Course, String> courseTitleCol;
    @FXML   private TableColumn<Course, LocalDate> courseStartCol;
    @FXML   private TableColumn<Course, LocalDate> courseEndCol;
    @FXML   private TableColumn<Course, String> coursePriceCol;
    @FXML	private Button saveBookingButton;
    @FXML	private Button closeBookingButton;
    private ObservableList<Course> bookingCourseTableViewData = FXCollections.observableArrayList();
    private ObservableList<BookingStates> bookingStates =  FXCollections.observableArrayList(BookingStates.values());
    private Student currentStudent;
    private Booking currentBooking;
    
    public void newBooking(Student student) {
    	currentStudent=student;
    	customerNrLabel.setText(currentStudent.getCustomerNr());
		studentNameLabel.setText(currentStudent.getPersonalData().getFirstname()+ " " + currentStudent.getPersonalData().getLastname());	
		bookingStateComboBox.setItems(bookingStates);
		bookingDateDatePicker.getEditor().setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
		DAO courseDAO = new DAO();		
		FilteredList<Course> filteredList=new FilteredList<>(courseDAO.courseList());
		filteredList.setPredicate(x -> x.getCourseState()!=CourseStates.completed&&x.getCourseState()!=CourseStates.canceled);
		bookingCourseTableViewData=filteredList;
		courseNrCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCourseNr()));
		courseTitleCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCourseTitle()));
		courseStartCol.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getCourseStart()));
		courseStartCol.setCellFactory((TableColumn<Course, LocalDate> column) -> {
			   return new TableCell<Course, LocalDate>() {
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
		courseEndCol.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getCourseEnd()));
		courseEndCol.setCellFactory((TableColumn<Course, LocalDate> column) -> {
			   return new TableCell<Course, LocalDate>() {
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
		coursePriceCol.setCellValueFactory(cellData -> {String coursePrice = currency.format(cellData.getValue().getCoursePrice());
			return new SimpleStringProperty(coursePrice);});
		
		bookingCourseTableView.setItems(bookingCourseTableViewData);
		
		bookingKursTextfield.textProperty().addListener((observable, oldValue, newValue) ->
		bookingCourseTableView.setItems(filteredCourseList(bookingCourseTableViewData, newValue)));
    	
    }
    
    @FXML
    public void saveBookingButtonAction(ActionEvent event) {
    	
    	
    	//currentStudent.addBooking(currentBooking);
    	//currentStudent.book(bookingCourseTableView.getSelectionModel().getSelectedItem(), bookingStateComboBox.getSelectionModel().getSelectedItem());
    	try {
//    		DAO studentDAO = new DAO();
//    		currentStudent = studentDAO.getStudent(currentStudent.getId());
//        	currentStudent.book(bookingCourseTableView.getSelectionModel().getSelectedItem(), bookingStateComboBox.getSelectionModel().getSelectedItem());
    		if(!bookingCourseTableView.getSelectionModel().isEmpty()&&!bookingStateComboBox.getSelectionModel().isEmpty()&&isParsableToDate(bookingDateDatePicker.getEditor().getText())) {
//    			currentStudent.book(bookingCourseTableView.getSelectionModel().getSelectedItem(),
//    					LocalDate.parse(bookingDateDatePicker.getEditor().getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy")), bookingStateComboBox.getSelectionModel().getSelectedItem());
//    			DAO.updateStudent(currentStudent);
    			if(currentBooking==null) {
    	    		Booking newBooking = new Booking();
    	        	newBooking.setCourse(bookingCourseTableView.getSelectionModel().getSelectedItem());
    	        	newBooking.setBookingDate(LocalDate.parse(bookingDateDatePicker.getEditor().getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    	        	newBooking.setBookingState(bookingStateComboBox.getSelectionModel().getSelectedItem());
    	        	newBooking.setStudent(currentStudent);	
    	        	DAO.createBooking(newBooking);
    	    	} else {
    	    		
    	    		currentBooking.setCourse(bookingCourseTableView.getSelectionModel().getSelectedItem());
    	    		currentBooking.setBookingDate(LocalDate.parse(bookingDateDatePicker.getEditor().getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    	    		currentBooking.setBookingState(bookingStateComboBox.getSelectionModel().getSelectedItem());
    	    		//currentBooking.setStudent(currentBooking.getStudent());	
    	    		DAO.updateBooking(currentBooking);
    	    	}
    			
    			Stage stage = (Stage) saveBookingButton.getScene().getWindow();
    	    	stage.close();
    		} else {
    			Alert alert = new Alert(AlertType.WARNING);
    			alert.setHeaderText("Ungültige Eingabe");
				alert.setContentText("");
				if(bookingCourseTableView.getSelectionModel().isEmpty()) {
					bookingCourseTableView.setStyle("-fx-border-color: #B22222; ");
					alert.setContentText(alert.getContentText()+"Ein Kurs muss ausgewählt werden\n");
				}
				if(bookingStateComboBox.getSelectionModel().isEmpty()) {
					bookingStateComboBox.setStyle("-fx-border-color: #B22222; ");
					alert.setContentText(alert.getContentText()+"Kursstatus muss ausgewählt werden\n");
				}
				if(!isParsableToDate(bookingDateDatePicker.getEditor().getText())) {
					bookingDateDatePicker.setStyle("-fx-border-color: #B22222; ");
					alert.setContentText(alert.getContentText()+"Buchungsdatum muss ausgewähl werden\n");
				}
				
				alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
				alert.showAndWait();
    		}
        	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
    }
    
    public void editBooking(Booking booking) {
    	currentBooking = booking;
    	customerNrLabel.setText(booking.getStudent().getCustomerNr());
		studentNameLabel.setText(booking.getStudent().getPersonalData().getFirstname()+ " " + booking.getStudent().getPersonalData().getLastname());	
		bookingStateComboBox.setItems(bookingStates);
		bookingStateComboBox.setValue(booking.getBookingState());
		bookingDateDatePicker.getEditor().setText(booking.getBookingDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
		DAO courseDAO = new DAO();		
		FilteredList<Course> filteredList=new FilteredList<>(courseDAO.courseList());
		filteredList.setPredicate(x -> x.getCourseState()!=CourseStates.completed&&x.getCourseState()!=CourseStates.canceled);
		bookingCourseTableViewData=filteredList;
		courseNrCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCourseNr()));
		courseTitleCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCourseTitle()));
		courseStartCol.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getCourseStart()));
		courseStartCol.setCellFactory((TableColumn<Course, LocalDate> column) -> {
			   return new TableCell<Course, LocalDate>() {
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
		courseEndCol.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getCourseEnd()));
		courseEndCol.setCellFactory((TableColumn<Course, LocalDate> column) -> {
			   return new TableCell<Course, LocalDate>() {
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
		coursePriceCol.setCellValueFactory(cellData -> {String coursePrice = currency.format(cellData.getValue().getCoursePrice());
			return new SimpleStringProperty(coursePrice);});
		
		bookingCourseTableView.setItems(bookingCourseTableViewData);
		
		bookingKursTextfield.textProperty().addListener((observable, oldValue, newValue) ->
		bookingCourseTableView.setItems(filteredCourseList(bookingCourseTableViewData, newValue)));
		bookingCourseTableView.getSelectionModel().select(booking.getCourse());;		
		
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
    void closeBookingButtonAction(ActionEvent event) {
    	Stage stage = (Stage) closeBookingButton.getScene().getWindow();
        stage.close();
    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		
	}


	private ObservableList<Course> filteredCourseList(List<Course> list, String searchText){
		bookingCourseTableView.getSelectionModel().clearSelection();
	    List<Course> filteredList = new ArrayList<>();
	    for (Course course : list){
	        if(searchCourse(course, searchText)) filteredList.add(course);
	    }
	    return FXCollections.observableList(filteredList);
	}
	
	private boolean searchCourse(Course course, String name){
	    return (course.getCourseTitle().toLowerCase().contains(name.toLowerCase())||course.getCourseNr().toLowerCase().contains(name.toLowerCase()));
	}


	/**
	 * @return the currentStudent
	 */
	public Student getCurrentStudent() {
		return currentStudent;
	}


	/**
	 * @param currentStudent the currentStudent to set
	 */
	public void setCurrentStudent(Student currentStudent) {
		this.currentStudent = currentStudent;
	}

	

}
