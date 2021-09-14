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
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import client.DAO;
import entity.Booking;
import entity.Course;
import entity.Qualification;
import entity.Student;
import enums.BookingStates;
import enums.CourseStates;
import enums.Genders;
import enums.Titles;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
    private ObservableList<Booking> bookings =  FXCollections.observableArrayList();

    
    /**
     * eine neue Buchung wird mit aktuellen Kurs und als Parameter angegebenen Teilnehmer erstellt.
     * @param student
     */
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
    
    /**
     * Methode reagiert auf ActionEvent
     * Mit dem Buchungsformular angegebenen Daten wird eine Buchung in der DB gespeichert bzw. vorhandene Buchung aktualisiert
     * @param event
     */
    @FXML
    public void saveBookingButtonAction(ActionEvent event) {
    	
    	try {
    		if(!bookingCourseTableView.getSelectionModel().isEmpty()&&!bookingStateComboBox.getSelectionModel().isEmpty()&&isParsableToDate(bookingDateDatePicker.getEditor().getText())) {
    			if(currentBooking==null) {
    	    		Booking newBooking = new Booking();
    	        	newBooking.setCourse(bookingCourseTableView.getSelectionModel().getSelectedItem());
    	        	newBooking.setBookingDate(LocalDate.parse(bookingDateDatePicker.getEditor().getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    	        	newBooking.setBookingState(bookingStateComboBox.getSelectionModel().getSelectedItem());
    	        	newBooking.setStudent(currentStudent);
    	        	if(bookings.contains(newBooking)) {
    	        		Alert alert = new Alert(AlertType.WARNING);
    	    			alert.setHeaderText("Diese Buchung existiert bereits!");
    					alert.setContentText("");
    					alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
    					alert.showAndWait();
    	        	} else {
        	        	DAO.createBooking(newBooking);
        	        	Stage stage = (Stage) saveBookingButton.getScene().getWindow();
            	    	stage.close();
    	        	}
    	    	} else {
    	    		
    	    		currentBooking.setCourse(bookingCourseTableView.getSelectionModel().getSelectedItem());
    	    		currentBooking.setBookingDate(LocalDate.parse(bookingDateDatePicker.getEditor().getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    	    		currentBooking.setBookingState(bookingStateComboBox.getSelectionModel().getSelectedItem());
    	    		DAO.updateBooking(currentBooking);
    	    		Stage stage = (Stage) saveBookingButton.getScene().getWindow();
        	    	stage.close();
    	    	}
    			

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
    /**
     * das Buchungsformular wird mit den Daten von angegebenen Buchungsdaten zum Editieren geöffnet
     * @param booking
     */
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
    
    
    /**
	 * Methode reagiert auf ein ActionEvent
	 * Die Stage von Action wird ermittelt und diese wird geschlossen
	 * @param event
	 */
    @FXML
    void closeBookingButtonAction(ActionEvent event) {
    	Stage stage = (Stage) closeBookingButton.getScene().getWindow();
        stage.close();
    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		DAO dao = new DAO();
		bookings = dao.bookingList();
	}


	/**
	 * Die Kurslist wird mit den angegebenen kriterien gefiltered und die gefilterte List wird zurückgegeben
	 * @param list
	 * @param searchText
	 * @return filteredList
	 */
	private ObservableList<Course> filteredCourseList(List<Course> list, String searchText){
		bookingCourseTableView.getSelectionModel().clearSelection();
	    List<Course> filteredList = new ArrayList<>();
	    for (Course course : list){
	        if(searchCourse(course, searchText)) filteredList.add(course);
	    }
	    return FXCollections.observableList(filteredList);
	}
	
	/**
	 * Überprüft ob der angegebene String mit Titel bzw Kursnr des Kurses übereinstimmt.
	 * falls ja, gibt true, wenn nein, gibt false zurück
	 * @param course
	 * @param name
	 * @return true/false
	 */
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
