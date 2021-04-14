package application;
	

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Verwasoft extends Application {

	@Override
	public void start(Stage primaryStage) {
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("verwasoft");
//		EntityManager em = emf.createEntityManager();
//		EntityTransaction txn = em.getTransaction();

		try {
			FXMLLoader loader= new FXMLLoader(getClass().getResource("Verwasoft.fxml"));
			AnchorPane root = loader.load();
			
			Scene scene = new Scene(root,900,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setTitle("VerwaSoft");
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setResizable(false);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
