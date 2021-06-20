package application;
	

import java.util.ArrayList;
import java.util.List;

import client.DAO;
import entity.Qualification;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Verwasoft extends Application {

	@Override
	public void start(Stage primaryStage) {
		
		List<Qualification> qList = new ArrayList<Qualification>();
		qList.add(new Qualification("ÖSDZA1", "ÖSD Zertifikat A1"));
		qList.add(new Qualification("ÖSDZA2", "ÖSD Zertifikat A2"));
		qList.add(new Qualification("ÖSDZB1", "ÖSD Zertifikat B1"));
		qList.add(new Qualification("ÖSDZB2", "ÖSD Zertifikat B2"));
		qList.add(new Qualification("ÖSDZC1", "ÖSD Zertifikat C1"));
		qList.add(new Qualification("ÖSDZC2", "ÖSD Zertifikat C2"));
		
		
		try {
			FXMLLoader loader= new FXMLLoader(getClass().getResource("Verwasoft.fxml"));
			AnchorPane root = loader.load();
			
			Scene scene = new Scene(root,900,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setTitle("VerwaSoft");
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setResizable(false);
			DAO.updateQualificationList(qList);
			DAO.updateStates();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
