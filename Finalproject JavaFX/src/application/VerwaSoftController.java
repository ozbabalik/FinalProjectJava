package application;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import entity.Address;
import entity.Booking;
import entity.Course;
import entity.PersonalData;
import entity.Qualification;
import entity.Student;
import entity.Trainer;
import entity.TrainerAssignment;
import enums.BookingStates;
import enums.CourseCategories;
import enums.CourseStates;
import enums.Genders;
import enums.Titles;
import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VerwaSoftController implements Initializable{
	
	DecimalFormat currency = new DecimalFormat("0.00€");
	
	@FXML private AnchorPane anchorPane;
	@FXML private VBox vBoxMenu;
	@FXML private MenuBar menuBar1;
	@FXML private Menu adminStudent;
	@FXML private Menu adminCourse;
	@FXML private Menu adminTrainer;
	@FXML private MenuItem newStudent;
	@FXML private MenuItem newCourse;
	@FXML private MenuItem newTrainer;
	@FXML private MenuItem studentList;
	
//*********************	
    @FXML	private Button editCourseButton;
    @FXML   private AnchorPane tableViewAnchorPane;
    @FXML	private Button searchTrainerResetButton;
    @FXML	private Button searchTrainerButton;
    @FXML	private Button searchStudentButton;
    @FXML	private Button searchStudentResetButton;
    @FXML	private Button searchCourseResetButton;
    @FXML 	private TextField trainerSearchTextField;
    @FXML 	private TextField studentSearchTextField;
    @FXML 	private TextField courseSearchTextField;
    @FXML	private Button newBookingButton;

    @FXML   private HBox studentTableViewHBox;
    @FXML   private MenuItem trainerList;
    @FXML   private MenuItem courseList;
    @FXML   private HBox trainerTableViewHBox;
    @FXML   private TableView<Trainer> trainerTableView;
    @FXML   private TableColumn<Trainer, String> staffNrCoulumn = new TableColumn<>("Personal-No");
    @FXML   private TableColumn<Trainer, String> firstnameCoulumnTr = new TableColumn<>("Vorname");
    @FXML   private TableColumn<Trainer, String> lastnameCoulumnTr = new TableColumn<>("Nachname");
    @FXML   private TableColumn<Trainer, LocalDate> dateOfBirthCoulumnTr = new TableColumn<>("Geburtsdatum");
    @FXML   private TableColumn<Trainer, String> phoneCoulumnTr = new TableColumn<>("Telefon");
    private ObservableList<Trainer> trainerTableViewData = FXCollections.observableArrayList();
    @FXML   private VBox vBoxTrainerDetails;
    @FXML   private GridPane gridPaneTrainerDetails;
    @FXML   private ColumnConstraints studentDetailsCol1;
    @FXML   private Label staffNrLabel;
    @FXML   private Label socialSecurityNrLabel;
    @FXML   private Label titleLabelTr;
    @FXML   private Label genderLabelTr;
    @FXML   private Label firstNameLabelTr;
    @FXML   private Label lastNameLabelTr;
    @FXML   private Label dateOfBirthLabelTr;
    @FXML   private Label phoneLabelTr;
    @FXML   private Label emailLabelTr;
    @FXML   private Label streetLabelTr;
    @FXML   private Label zipCodeCityLabelTr;
    @FXML   private Label countryLabelTr;
    @FXML 	VBox vBoxStudentDetails;
    @FXML   private ColumnConstraints studentDetailsCol;
    @FXML   private GridPane gridPaneStudentDetails;
    @FXML   private Label customerNrLabel;
    @FXML   private Label titleLabel;
    @FXML   private Label genderLabel;
    @FXML   private Label firstNameLabel;
    @FXML   private Label lastNameLabel;
    @FXML   private Label dateOfBirthLabel;
    @FXML   private Label phoneLabel;
    @FXML   private Label emailLabel;
    @FXML   private Label streetLabel;
    @FXML   private Label zipCodeCityLabel;
    @FXML	private CheckBox showInactiveTrainersCheckBox;
    @FXML	private Button newTrainerButton;
    @FXML	private CheckBox showInactiveStudentsCheckBox;
    @FXML	private Button newStudentButton;
    @FXML	private Button newCourseButton;
    @FXML	private CheckBox showAllCoursesCheckBox;
    @FXML	private Button assignTrainerButton;
    @FXML	private Button editAssignmentButton;
    @FXML	private Button removeAssignmentButton;
    @FXML	private HBox hBoxAssignment;
    
    @FXML   private Label countryLabel;
    private Stage primaryStage = null;
	private  Stage secondStage =null;	
	@FXML private TableView<Student> studentTableView = new TableView<Student>();
	@FXML private TableColumn<Student, String> firstnameCoulumn = new TableColumn<>("Vorname");
	@FXML private TableColumn<Student, String> lastnameCoulumn  = new TableColumn<>("Nachname");
	@FXML private TableColumn<Student, LocalDate> dateOfBirthCoulumn = new TableColumn<>("Geburtsdatum");
	@FXML private TableColumn<Student, String> phoneCoulumn = new TableColumn<>("Telefon");
	@FXML private TableColumn<Student, String> customerNrCoulumn = new TableColumn<>("Kundennr");
	private ObservableList<Student> studentTableViewData = FXCollections.observableArrayList();

    @FXML   private HBox courseTableViewHBox;
    @FXML   private TableView<Course> courseTableView;
    @FXML   private TableColumn<Course, String> courseNrCol;
    @FXML   private TableColumn<Course, String> courseTitleCol;
    @FXML   private TableColumn<Course, LocalDate> courseStartCol;
    @FXML   private TableColumn<Course, LocalDate> courseEndCol;
    @FXML   private TableColumn<Course, String> coursePriceCol;
    @FXML   private VBox vBoxCourseDetails;
    @FXML   private GridPane gridPaneCourseDetails;
    @FXML   private Label courseNrLabel;
    @FXML   private Label courseTitleLabel;
    @FXML   private Label courseCategoryLabel;
    @FXML   private Label courseStateLabel;
    @FXML   private Label coursePriceLabel;
    @FXML   private Label courseStartLabel;
    @FXML   private Label courseEndLabel;
    @FXML   private Label courseDescLabel;
    @FXML	private Label trainerLabel;
    @FXML   private ColumnConstraints studentDetailsCol11;
    @FXML	private TableView<Booking> studentBookingListTableView;
    private ObservableList<Booking> studentBookingListTableViewData = FXCollections.observableArrayList();
    @FXML	private TableColumn<Booking, String> studentBookingNrCol;
    @FXML	private TableColumn<Booking, String> studentCourseNrCol;
    @FXML	private TableColumn<Booking, String> studentCourseTitleCol;
    @FXML	private TableColumn<Booking, String> studentBookingStateCol;
    
    private ObservableList<Qualification> trainerQualificationsTableViewData = FXCollections.observableArrayList();
    @FXML	private TableView<Qualification> trainerQualificationsTableView;
    @FXML	private TableColumn<Qualification, String> trainerQualificationsTitle;
    @FXML	private TableColumn<Qualification, String> trainerQualificationsDesc;

    @FXML	private TableView<Booking> courseBookingListTableView;
    private ObservableList<Booking> courseBookingListTableViewData = FXCollections.observableArrayList();
    @FXML	private TableColumn<Booking, String> courseBookingNrCol;
    @FXML	private TableColumn<Booking, String> courseStudentFirstNameCol;
    @FXML	private TableColumn<Booking, String> courseStudentLastNameCol;
    @FXML	private TableColumn<Booking, String> courseBookingStateCol;
    private ObservableList<Course> courseTableViewData = FXCollections.observableArrayList();
//    private CheckBox aktiveTrainerCheckBox;
    
    @FXML
    void onMouseClickedTainerTableView() {
    	trainerTableView.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				if(!trainerTableView.getSelectionModel().isEmpty()) {
					   Trainer selectedTrainer=trainerTableView.getSelectionModel().getSelectedItem();
					   if(selectedTrainer.getStaffNr()!=null) {
					         staffNrLabel.setText(selectedTrainer.getStaffNr());
						   } else {
							   staffNrLabel.setText("");  
						   }
					   if(selectedTrainer.getSocialSecurityNr()!=null) {
						   socialSecurityNrLabel.setText(selectedTrainer.getSocialSecurityNr());
						   } else {
							   socialSecurityNrLabel.setText("");  
						   }
					   if(selectedTrainer.getPersonalData().getTitle()!=null) {
				         titleLabelTr.setText(selectedTrainer.getPersonalData().getTitle().getLabel());
					   } else {
						   titleLabelTr.setText("");  
					   }
					   if(selectedTrainer.getPersonalData().getGender()!=null) {
				         genderLabelTr.setText(selectedTrainer.getPersonalData().getGender().getLabel());
					   } else {
						   genderLabelTr.setText("");  
					   }
				         firstNameLabelTr.setText(selectedTrainer.getPersonalData().getFirstname());

				         lastNameLabelTr.setText(selectedTrainer.getPersonalData().getLastname());
					   
				       if(selectedTrainer.getPersonalData().getDateOfBirth()!=null) {
					         dateOfBirthLabelTr.setText(selectedTrainer.getPersonalData().getDateOfBirth().format(DateTimeFormatter.ofPattern("dd.MM.YYYY")));			        	 
				       } else {
				    	   dateOfBirthLabelTr.setText("");
				       }
				         phoneLabelTr.setText(selectedTrainer.getPersonalData().getTelefon());

				         emailLabelTr.setText(selectedTrainer.getPersonalData().getEmail());	

				         streetLabelTr.setText(selectedTrainer.getAddress().getStreet()+" "+selectedTrainer.getAddress().getHouseNr());


				         zipCodeCityLabelTr.setText(selectedTrainer.getAddress().getZipcode()+" "+selectedTrainer.getAddress().getCity());

				         countryLabelTr.setText(selectedTrainer.getAddress().getCountry());
				         
				         trainerQualificationsTableViewData.setAll(selectedTrainer.getQualifications());
				         trainerQualificationsTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
				         trainerQualificationsDesc.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
				         trainerQualificationsTableView.setItems(trainerQualificationsTableViewData);
				         vBoxTrainerDetails.setVisible(true);
		         				      
				   }
				
			}
    		
    		
    	});
    };
    @FXML
    void onMouseClickedCourseTableView(MouseEvent event) {
      	courseTableView.setOnMouseClicked(new EventHandler<MouseEvent>(){

    			@Override
    			public void handle(MouseEvent arg0) {
    					if(!courseTableView.getSelectionModel().isEmpty()) {
    						Course selectedCourse=courseTableView.getSelectionModel().getSelectedItem();
     					   if(selectedCourse.getCourseNr()!=null) {
     					         courseNrLabel.setText(selectedCourse.getCourseNr());
     						   } else {
     							   courseNrLabel.setText("");  
     						   }
     					   if(selectedCourse.getCourseTitle()!=null) {
     						   courseTitleLabel.setText(selectedCourse.getCourseTitle());
     						   } else {
     							   courseTitleLabel.setText("");  
     						   }
     					   if(selectedCourse.getCourseCategory()!=null) {
     						   courseCategoryLabel.setText(selectedCourse.getCourseCategory().getLabel());
     					   } else {
     						   courseCategoryLabel.setText("");  
     					   }
     					   if(selectedCourse.getCourseState()!=null) {
     						   courseStateLabel.setText(selectedCourse.getCourseState().getLabel());
     					   } else {
     						   courseStateLabel.setText("");  
     					   }
     					   
     					   if(selectedCourse.getCourseStart()!=null) {
     						   courseStartLabel.setText(selectedCourse.getCourseStart().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
     					   } else {
     						   courseStartLabel.setText("");  
     					   }
     					   
     					   if(selectedCourse.getCourseEnd()!=null) {
     						   courseEndLabel.setText(selectedCourse.getCourseEnd().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
     					   } else {
     						   courseEndLabel.setText("");  
     					   }
     					   
     						   coursePriceLabel.setText(currency.format(selectedCourse.getCoursePrice()));

     					   if(selectedCourse.getCourseDescription()!=null) {
     						   courseDescLabel.setText(selectedCourse.getCourseDescription());
     					   } else {
     						   courseDescLabel.setText("");  
     					   }
     					  try {
     						 if(selectedCourse.getAssignment()!=null) {
//     							 Label label[] = new Label[10];
//     							 Button button[] = new Button[10];
//     							for(int i=0; i<selectedCourse.getAssignments().size();i++) {
//     								label[i].setText(selectedCourse.getAssignments().get(i).getTrainer().getPersonalData().getFirstname()+" "+selectedCourse.getAssignments().get(i).getTrainer().getPersonalData().getLastname());
//     								hBoxAssignment.getChildren().add(label[i]);
//     								button[i].setText("Bearbeiten");
//     								button[i].setOnAction(new EventHandler<ActionEvent>() {
//     						            @Override
//     						            public void handle(ActionEvent event) {
//     						                editAssignmentButtonAction(event);;
//     						            }
//     						        });
//     								hBoxAssignment.getChildren().add(button[i]);
//     								
//      							}
     							 
      						   trainerLabel.setText(selectedCourse.getAssignment().getTrainer().getPersonalData().getFirstname()+" "+selectedCourse.getAssignment().getTrainer().getPersonalData().getLastname());
      						   editAssignmentButton.setVisible(true);
      						   removeAssignmentButton.setVisible(true);

     						 } else {
    				        	trainerLabel.setText("");
    				        	editAssignmentButton.setVisible(false);
    				        	removeAssignmentButton.setVisible(false);
      					   }     				          
     				        } catch (ArrayIndexOutOfBoundsException exception) {
     				        	trainerLabel.setText("");  
     				        } 
//     					  if(selectedCourse.getAssignments()!=null) {
//    						   trainerLabel.setText(selectedCourse.getAssignments().get(0).getTrainer().getPersonalData().getFirstname()+" "+selectedCourse.getAssignments().get(0).getTrainer().getPersonalData().getLastname());
//    					   } else {
//    						   trainerLabel.setText("");  
//    					   }
     					   courseBookingListTableViewData.setAll(selectedCourse.getBookings());
     					   courseBookingNrCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBookingNr()));
     					   courseStudentFirstNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStudent().getPersonalData().getFirstname()));
     					   courseStudentLastNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStudent().getPersonalData().getLastname()));
     					   courseBookingStateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBookingState().toString()));
     					   courseBookingListTableView.setItems(courseBookingListTableViewData);
     					   vBoxCourseDetails.setVisible(true);
    					}
    					
    					   
    		         				      
    				 //  }				
    			}
        		
        		
        	});    	
    }
    @FXML
    void editCourse(ActionEvent event) {
    	try {
			secondStage = new Stage();
			
			FXMLLoader loader= new FXMLLoader(getClass().getResource("Courseform.fxml"));
			VBox root = loader.load();
			CourseController courseController=loader.getController();
			
			if(!courseTableView.getSelectionModel().isEmpty()) {
				courseController.editCourse(courseTableView.getSelectionModel().getSelectedItem());
			} else {
				System.out.println("No item is selected");
				return;
			}		
			
			Scene scene = new Scene(root,630,550);
			//root.setId(courseTableView.getSelectionModel().getSelectedItem().getId().toString());
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			secondStage.setTitle("Kurs editieren");
			secondStage.setScene(scene);
			secondStage.show();
			secondStage.setResizable(false);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    @FXML
    void onMouseClickedStudentTableView(MouseEvent event) {
    	
    	
    	studentTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			   @Override 
			   public void handle(MouseEvent e) {
				   if(!studentTableView.getSelectionModel().isEmpty()) {
				   vBoxStudentDetails.setVisible(true);
				   Student selectedStudent=studentTableView.getSelectionModel().getSelectedItem();
				   if(selectedStudent.getCustomerNr()!=null) {
				         customerNrLabel.setText(selectedStudent.getCustomerNr());
					   } else {
						   customerNrLabel.setText("");  
					   }
				   if(selectedStudent.getPersonalData().getTitle()!=null) {
			         titleLabel.setText(selectedStudent.getPersonalData().getTitle().getLabel());
				   } else {
					   titleLabel.setText("");  
				   }
				   if(selectedStudent.getPersonalData().getGender()!=null) {
			         genderLabel.setText(selectedStudent.getPersonalData().getGender().getLabel());
				   } else {
					   genderLabel.setText("");  
				   }
			         firstNameLabel.setText(selectedStudent.getPersonalData().getFirstname());
			         lastNameLabel.setText(selectedStudent.getPersonalData().getLastname());
			       if(selectedStudent.getPersonalData().getDateOfBirth()!=null) {
				         dateOfBirthLabel.setText(selectedStudent.getPersonalData().getDateOfBirth().format(DateTimeFormatter.ofPattern("dd.MM.YYYY")));			        	 
			       } else {
			    	   dateOfBirthLabel.setText("");
			       }
			         phoneLabel.setText(selectedStudent.getPersonalData().getTelefon());
			         emailLabel.setText(selectedStudent.getPersonalData().getEmail());	
			         streetLabel.setText(selectedStudent.getAddress().getStreet()+" "+selectedStudent.getAddress().getHouseNr());

			         zipCodeCityLabel.setText(selectedStudent.getAddress().getZipcode()+" "+selectedStudent.getAddress().getCity());

			         countryLabel.setText(selectedStudent.getAddress().getCountry());
			         
			         studentBookingListTableViewData.setAll(selectedStudent.getBookings());
					   studentBookingNrCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBookingNr()!=null?cellData.getValue().getBookingNr():" "));
					   studentCourseNrCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCourse().getCourseNr()!=null?cellData.getValue().getCourse().getCourseNr():" "));
					   studentCourseTitleCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCourse().getCourseTitle()!=null?cellData.getValue().getCourse().getCourseTitle():" "));
					   studentBookingStateCol.setCellValueFactory(cellData -> new SimpleStringProperty(!cellData.getValue().getBookingState().toString().isEmpty()?cellData.getValue().getBookingState().toString():" "));
					   studentBookingListTableView.setItems(studentBookingListTableViewData);
					   vBoxStudentDetails.setVisible(true);
		         
			   }
			   }
			});

    }
	
    
	@FXML
	public void showStudentDetails(ActionEvent event) {
		studentTableView.selectionModelProperty().addListener((Observable obs) -> {
	        vBoxStudentDetails.setVisible(true);
		});
	}
	
	


	public void setPrimaryStage(Stage primaryStage) {
		
		this.primaryStage = primaryStage;
	}

	
	@FXML
	public void newStudentAction(ActionEvent event) {
		try {
			secondStage = new Stage();
			//VBox root = (VBox)FXMLLoader.load(getClass().getResource("Studentform.fxml"));
			
			FXMLLoader loader= new FXMLLoader(getClass().getResource("Studentform.fxml"));
			VBox root = loader.load();
			StudentController studentController=loader.getController();
			
			studentController.setVwsController(this);
			Scene scene = new Scene(root,600,550);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			secondStage.setTitle("Teilnehmer anlegen");
			secondStage.setScene(scene);
			secondStage.show();
			secondStage.setResizable(false);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
    private ObservableList<CourseStates> courseStates =  FXCollections.observableArrayList(CourseStates.values());

	@FXML
	public void newCourseAction(ActionEvent event) {
		try {
			secondStage = new Stage();
			//VBox root = (VBox)FXMLLoader.load(getClass().getResource("Courseform.fxml"));
			
			
			FXMLLoader loader= new FXMLLoader(getClass().getResource("Courseform.fxml"));
			VBox root = loader.load();
			CourseController courseController=loader.getController();
			courseController.courseStateComboBox.setItems(courseStates);
			Scene scene = new Scene(root,630,550);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			secondStage.setTitle("Kurs anlegen");
			secondStage.setScene(scene);
			secondStage.show();
			secondStage.setResizable(false);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void newTrainerAction(ActionEvent event) {
		try {
			secondStage = new Stage();
			FXMLLoader loader= new FXMLLoader(getClass().getResource("Trainerform.fxml"));
			VBox root = loader.load();
			TrainerController trainerController=loader.getController();
			trainerController.setVwsController(this);
			Scene scene = new Scene(root,900,550);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			secondStage.setTitle("Trainer anlegen");
			secondStage.setScene(scene);
			secondStage.show();
			secondStage.setResizable(false);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void newBookingButtonAction(ActionEvent event) {
		try {
			secondStage = new Stage();
			
			FXMLLoader loader= new FXMLLoader(getClass().getResource("Bookingform.fxml"));
			VBox root = loader.load();
			BookingController bookingController = loader.getController();
			
			bookingController.newBooking(studentTableView.getSelectionModel().getSelectedItem());
			
			Scene scene = new Scene(root,520,550);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			secondStage.setTitle("Neue Buchung");
			secondStage.setScene(scene);
			secondStage.show();
			secondStage.setResizable(false);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void assignTrainerButtonAction(ActionEvent event) {
		try {
			secondStage = new Stage();
			
			FXMLLoader loader= new FXMLLoader(getClass().getResource("TrainerAssignmentForm.fxml"));
			VBox root = loader.load();
			AssignmentController assignmentController = loader.getController();
			
			assignmentController.newAssignment(courseTableView.getSelectionModel().getSelectedItem());
			
			Scene scene = new Scene(root,520,550);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			secondStage.setTitle("Trainer Zuordnen");
			secondStage.setScene(scene);
			secondStage.show();
			secondStage.setResizable(false);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void editAssignmentButtonAction(ActionEvent event) {
		try {
			secondStage = new Stage();
			
			FXMLLoader loader= new FXMLLoader(getClass().getResource("TrainerAssignmentForm.fxml"));
			VBox root = loader.load();
			AssignmentController assignmentController = loader.getController();
			
			assignmentController.editAssignment(courseTableView.getSelectionModel().getSelectedItem().getAssignment());
			
			Scene scene = new Scene(root,520,550);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			secondStage.setTitle("Trainer Bearbeiten");
			secondStage.setScene(scene);
			secondStage.show();
			secondStage.setResizable(false);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void removeAssignmentButtonAction(ActionEvent event) throws SQLException {
		
		DAO.removeTrainerAssignment(courseTableView.getSelectionModel().getSelectedItem().getAssignment());
		courseTableView.getSelectionModel().getSelectedItem().setAssignment(null);
		trainerLabel.setText("");
    	editAssignmentButton.setVisible(false);
    	removeAssignmentButton.setVisible(false);
	}
	@FXML
	public void editBookingAction(Booking booking) {
		try {
			secondStage = new Stage();
			
			FXMLLoader loader= new FXMLLoader(getClass().getResource("Bookingform.fxml"));
			VBox root = loader.load();
			BookingController bookingController = loader.getController();
			
			bookingController.editBooking(booking);
			
			Scene scene = new Scene(root,520,550);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			secondStage.setTitle("Buchung editieren");
			secondStage.setScene(scene);
			secondStage.show();
			secondStage.setResizable(false);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void editTrainer(ActionEvent event ) {

		try {
					
			secondStage = new Stage();
			FXMLLoader loader= new FXMLLoader(getClass().getResource("Trainerform.fxml"));
			VBox root = loader.load();
			TrainerController trainerController=loader.getController();
			trainerController.setVwsController(this);
			if(!trainerTableView.getSelectionModel().isEmpty()) {
			trainerController.editTrainer(trainerTableView.getSelectionModel().getSelectedItem());
			} else {
				System.out.println("No item is selected");
				return;
			}
			Scene scene = new Scene(root,900,550);
			//root.setId(trainerTableView.getSelectionModel().getSelectedItem().getId().toString());
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			trainerController.setPrimaryStage((Stage)((Node)event.getSource()).getScene().getWindow());
			secondStage.setTitle("Trainer editieren");
			secondStage.setScene(scene);
			secondStage.show();
			secondStage.setResizable(false);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
		
		
	@FXML
	public void editStudent(ActionEvent event) {
		try {
			
			secondStage = new Stage();
			FXMLLoader loader= new FXMLLoader(getClass().getResource("Studentform.fxml"));
			VBox root = loader.load();
			StudentController studentController=loader.getController();
			studentController.setVwsController(this);
			if(!studentTableView.getSelectionModel().isEmpty()) {
				studentController.editStudent(studentTableView.getSelectionModel().getSelectedItem());
			} else {
				System.out.println("No item is selected");
				return;
			}
			Scene scene = new Scene(root,600,550);
//			root.setId(studentTableView.getSelectionModel().getSelectedItem().getId().toString());
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			studentController.setPrimaryStage((Stage)((Node)event.getSource()).getScene().getWindow());
			secondStage.setTitle("Teilnehmer editieren");
			secondStage.setScene(scene);
			secondStage.show();
			secondStage.setResizable(false);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}	
		
	}
	
	@FXML
    void setStudentTableViewVisible() {
		studentTableViewHBox.setVisible(true);
		studentTableView.setVisible(true);
		studentTableViewHBox.setDisable(false);
		setTrainerTableViewInVisible();
		setCourseTableViewInVisible();
		
    }
	
	@FXML
    void setStudentTableViewInVisible() {
		studentTableViewHBox.setVisible(false);
		studentTableView.setVisible(false);
		studentTableViewHBox.setDisable(true);
    }

	@FXML
    void setTrainerTableViewVisible() {
		trainerTableViewHBox.setVisible(true);
		trainerTableView.setVisible(true);
		trainerTableViewHBox.setDisable(false);
		setStudentTableViewInVisible();
		setCourseTableViewInVisible();

    }

	@FXML
    void setCourseTableViewVisible() {
		courseTableViewHBox.setVisible(true);
		courseTableView.setVisible(true);
		courseTableViewHBox.setDisable(false);
		setStudentTableViewInVisible();
		setTrainerTableViewInVisible();

    }
	
	@FXML
    void setCourseTableViewInVisible() {
		courseTableViewHBox.setVisible(false);
		courseTableView.setVisible(false);
		courseTableViewHBox.setDisable(true);
    }
	
	@FXML
    void setTrainerTableViewInVisible() {
		trainerTableViewHBox.setVisible(false);
		trainerTableView.setVisible(false);
		trainerTableViewHBox.setDisable(true);
    }
	
	@FXML
	public void searchStudent(Event event) {
	}

//	@FXML
//	public void searchStudentReset(Event event) {
//		studentSearchTextField.clear();
//	}
//	
	private ObservableList<Student> filteredStudentList(List<Student> list, String searchText){
		studentTableView.getSelectionModel().clearSelection();
		vBoxStudentDetails.setVisible(false);
	    List<Student> filteredList = new ArrayList<>();
	    for (Student student : list){
	    	if(searchStudent(student, searchText)) filteredList.add(student);   	 	        
	    }
	    return FXCollections.observableList(filteredList);
	}
	
	private boolean searchStudent(Student student, String name){
	    return (student.getPersonalData().getFirstname().toLowerCase().contains(name.toLowerCase())||student.getPersonalData().getLastname().toLowerCase().contains(name.toLowerCase()));
	}
	
	
	
	public void loadStudents() {
		
		studentTableView.getSelectionModel().clearSelection();
		vBoxStudentDetails.setVisible(false);
		setStudentTableViewVisible();
		DAO studentDAO = new DAO();
		studentTableViewData.setAll(studentDAO.studentList().filtered(s->s.isActiv()));
		customerNrCoulumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerNr()));
		firstnameCoulumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPersonalData().getFirstname()));
		lastnameCoulumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPersonalData().getLastname()));
		dateOfBirthCoulumn.setCellValueFactory(cellData -> new SimpleObjectProperty<LocalDate>(cellData.getValue().getPersonalData().getDateOfBirth()));
		dateOfBirthCoulumn.setCellFactory((TableColumn<Student, LocalDate> column) -> {
			   return new TableCell<Student, LocalDate>() {
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
		phoneCoulumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPersonalData().getTelefon()));
		studentTableView.setItems(studentTableViewData);
		showInactiveStudentsCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue ov,Boolean old_val, Boolean new_val) {
            	if(!new_val) {
                	studentTableViewData.setAll(studentDAO.studentList().filtered(s->s.isActiv()!=new_val));            		
            	}
            	else
            		studentTableViewData.setAll(studentDAO.studentList());
            }
        });
		
		studentSearchTextField.textProperty().addListener((observable, oldValue, newValue) ->
		studentTableView.setItems(filteredStudentList(studentTableViewData, newValue)));
		
		searchStudentResetButton.setOnAction(new EventHandler<ActionEvent>() {
			 
		    @Override
		    public void handle(ActionEvent event) {
		    	studentSearchTextField.clear();
		    }
		});
		
		studentBookingListTableView.setOnMousePressed(new EventHandler<MouseEvent>() {
			   @Override 
			   public void handle(MouseEvent e) {
				   if(!studentBookingListTableView.getSelectionModel().isEmpty()) {
			      if (e.isPrimaryButtonDown() && e.getClickCount() == 2) {
			    	  if(studentBookingListTableView.getSelectionModel().getSelectedItem().getCourse().getCourseState()==CourseStates.canceled
			    			  ||studentBookingListTableView.getSelectionModel().getSelectedItem().getCourse().getCourseState()==CourseStates.completed) {
			    		  Alert alert = new Alert(AlertType.ERROR);
			    			alert.setHeaderText("Diese Buchung kann nicht geändert werden!");
							alert.setContentText("Dieser Kurs ist " + studentBookingListTableView.getSelectionModel().getSelectedItem().getCourse().getCourseState());
														
							alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
							alert.showAndWait();
			    	  } else	  {
			    		  
							editBookingAction(studentBookingListTableView.getSelectionModel().getSelectedItem());
			    	  }
			    	  
			    	 
			      }
			   }
			   }
			});
    }
	
	@FXML
	public void searchTrainer(Event event) {

	}

//	@FXML
//	public void searchTrainerReset(Event event) {
//		trainerSearchTextField.clear();
//	}
	
	private ObservableList<Trainer> filteredTrainerList(List<Trainer> list, String searchText){
		trainerTableView.getSelectionModel().clearSelection();
		vBoxTrainerDetails.setVisible(false);
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
	
	private boolean searchTrainer(Trainer trainer, String name){
	    return ((trainer.getPersonalData().getFirstname().toLowerCase().contains(name.toLowerCase())||trainer.getPersonalData().getLastname().toLowerCase().contains(name.toLowerCase())));
	}
	
	private boolean searchActivTrainer(Trainer trainer, String name){
	    return ((trainer.getPersonalData().getFirstname().toLowerCase().contains(name.toLowerCase())||trainer.getPersonalData().getLastname().toLowerCase().contains(name.toLowerCase()))&&trainer.isActiv());
	}
	
	
	@FXML
	public void loadTrainers() {
		
		trainerTableView.getSelectionModel().clearSelection();
		vBoxTrainerDetails.setVisible(false);
		setTrainerTableViewVisible();
		DAO trainerDAO = new DAO();
	
		trainerTableViewData.setAll(trainerDAO.trainerList().filtered(t->t.isActiv()));
		staffNrCoulumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStaffNr()));
		firstnameCoulumnTr.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPersonalData().getFirstname()));
		lastnameCoulumnTr.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPersonalData().getLastname()));
		dateOfBirthCoulumnTr.setCellValueFactory(cellData -> new SimpleObjectProperty<LocalDate>(cellData.getValue().getPersonalData().getDateOfBirth()));
		dateOfBirthCoulumnTr.setCellFactory((TableColumn<Trainer, LocalDate> column) -> {
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
		phoneCoulumnTr.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPersonalData().getTelefon()));
		trainerTableView.setItems(trainerTableViewData);
		showInactiveTrainersCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue ov,Boolean old_val, Boolean new_val) {
				if(!new_val) {
            	trainerTableViewData.setAll(trainerDAO.trainerList().filtered(t->t.isActiv()!=new_val));
				}
				else
					trainerTableViewData.setAll(trainerDAO.trainerList());
            }
        });
		trainerSearchTextField.textProperty().addListener((observable, oldValue, newValue) ->
		trainerTableView.setItems(filteredTrainerList(trainerTableViewData, newValue)));
		searchTrainerResetButton.setOnAction(new EventHandler<ActionEvent>() {
			 
		    @Override
		    public void handle(ActionEvent event) {
		    	trainerSearchTextField.clear();
		    }
		});
	}
	
	@FXML
	public void searchCourse(Event event) {
	}

//	@FXML
//	public void searchCourseReset(Event event) {
//		courseSearchTextField.clear();
//	}
	
	private ObservableList<Course> filteredCourseList(List<Course> list, String searchText){
		courseTableView.getSelectionModel().clearSelection();
		vBoxCourseDetails.setVisible(false);
	    List<Course> filteredList = new ArrayList<>();
	    for (Course course : list){
	        if(searchCourse(course, searchText)) filteredList.add(course);
	    }
	    return FXCollections.observableList(filteredList);
	}
	
	private boolean searchCourse(Course course, String name){
	    return (course.getCourseTitle().toLowerCase().contains(name.toLowerCase())||course.getCourseNr().toLowerCase().contains(name.toLowerCase()));
	}
	
	public void loadCourses() {
		setCourseTableViewVisible();		
		DAO courseDAO = new DAO();		
//		FilteredList<Course> filteredList=new FilteredList<>(courseDAO.courseList());
//		filteredList.setPredicate(x -> x.getCourseState()!=CourseStates.completed&&x.getCourseState()!=CourseStates.canceled);
		courseTableViewData.setAll(courseDAO.courseList().filtered(x -> x.getCourseState()!=CourseStates.completed&&x.getCourseState()!=CourseStates.canceled));
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
		
		courseTableView.setItems(courseTableViewData);
		showAllCoursesCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue ov,Boolean old_val, Boolean new_val) {
				if(!new_val) {
					courseTableViewData.setAll(courseDAO.courseList().filtered(x -> x.getCourseState()!=CourseStates.completed&&x.getCourseState()!=CourseStates.canceled));
				}
				else
					courseTableViewData.setAll(courseDAO.courseList());
            }
        });
		
		
		courseSearchTextField.textProperty().addListener((observable, oldValue, newValue) ->
		courseTableView.setItems(filteredCourseList(courseTableViewData, newValue)));
		
		courseBookingListTableView.setOnMousePressed(new EventHandler<MouseEvent>() {
			   @Override 
			   public void handle(MouseEvent e) {
				   if(!courseBookingListTableView.getSelectionModel().isEmpty()) {
			      if (e.isPrimaryButtonDown() && e.getClickCount() == 2) {
			    	  if(courseBookingListTableView.getSelectionModel().getSelectedItem().getCourse().getCourseState()==CourseStates.canceled
			    			  ||courseBookingListTableView.getSelectionModel().getSelectedItem().getCourse().getCourseState()==CourseStates.completed) {
			    		  Alert alert = new Alert(AlertType.ERROR);
			    			alert.setHeaderText("Diese Buchung kann nicht mehr geändert werden!");
							alert.setContentText("Dieser Kurs ist " + courseBookingListTableView.getSelectionModel().getSelectedItem().getCourse().getCourseState());
														
							alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
							alert.showAndWait();
			    	  } else	  {
			    		  
							editBookingAction(courseBookingListTableView.getSelectionModel().getSelectedItem());
			    	  }
			    	  
			    	 
			      }
			   }
			   }
			});
		
		searchCourseResetButton.setOnAction(new EventHandler<ActionEvent>() {
			 
		    @Override
		    public void handle(ActionEvent event) {
		    	courseSearchTextField.clear();
		    }
		});
	}
	
	public static boolean isValidEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
	
	public static boolean isParsableToDate(String string) {
        try {
    		LocalDate.parse(string, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            return true;
        } catch (DateTimeParseException exception) {
            return false;
        } 
       
    }
	public static boolean isParsableToFloat(String string) {
		 try {
	        	string = string.replace(',', '.');
	            Float.parseFloat(string);
	            return true;
	        } catch (NumberFormatException exception) {
	            return false;
	        } 
		 }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
}
