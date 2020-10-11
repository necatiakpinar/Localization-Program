//DATA STRUCTURES
import java.util.ArrayList;
import java.util.List;


//FILE 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;

//GUI
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene; //It is the inside of the Stage
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javafx.scene.control.ScrollPane;
import javafx.stage.Stage; //Main Window


import javax.swing.*;



public class Translator extends Application  {
	
	
	static List<Label> turkishTextLabelList;
	static List<TextField> translatedTextTextFieldList;
	static List<String> dataID;
	static List<String> turkishTextData;
	static List<String> translatedTextData;
	static String gameDataPath;
	
	//GUI
	Stage window;
	
	
	public static void main(String[] args) {
		
		//Variables
		dataID = new ArrayList<>();
		turkishTextData = new ArrayList<>();
		translatedTextData = new ArrayList<>();
		turkishTextLabelList = new ArrayList<>();
		translatedTextTextFieldList = new ArrayList<>();
		

		
		askForData();
		initializeTheData(gameDataPath);

		launch(args);
	}

	public static void askForData() {
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(null);
		File file = chooser.getSelectedFile();
		gameDataPath = file.getAbsolutePath().toString();
		
		chooser.cancelSelection();
	}
	public static void initializeTheData(String gameDataPath) {
		String line = "";
		try {
			Reader reader =new InputStreamReader(new FileInputStream(gameDataPath),"UTF-8");
			BufferedReader br= new BufferedReader(reader);
			
			while ((line = br.readLine()) != null) {
				
				String [] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
					dataID.add(values[0]);
					turkishTextData.add(values[1]);
					translatedTextData.add(values[2]);
			
			}
		
			reader.close();
			br.close();
	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void saveCSV(List<String> ID,List<String> turkishText,List<String> translatedText,String filePath) {
		try 
		{
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
				    new FileOutputStream(filePath), "UTF-8"
				));
			PrintWriter pw = new PrintWriter(bw);
			
			for (int i=0;i<ID.size()-1;i++) {
				translatedText.set(i+1,translatedTextTextFieldList.get(i).getText());
			}

				for (int i = 0; i<ID.size()-1;i++) {
					
				pw.println(ID.get(i)+","+turkishText.get(i)+","+translatedText.get(i));
				}
		
			
			pw.flush();
			pw.close();
			
		}catch (Exception E)
		{
			
		}
	}
	
	
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("Translator");
		window.setOnCloseRequest(e -> closeProgram());
		
		//Instantiate the Grid Pane
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(8);
		grid.setHgap(10);

	
		int turkishTextIndex=0;
		int translatedTextIndex=1;
		

		for (int i=0; i<dataID.size();i++) {
			if (i>0) {
				Label turkishTextVisualLabel  = new Label("Turkish->");
				GridPane.setConstraints(turkishTextVisualLabel,0,turkishTextIndex);
				Label translatedTextVisualLabel = new Label("English ->");
				GridPane.setConstraints(translatedTextVisualLabel,0,translatedTextIndex);
				
				Label turkishTextLabel  = new Label(turkishTextData.get(i));
				GridPane.setConstraints(turkishTextLabel,1,turkishTextIndex);
				
				
				turkishTextLabelList.add(turkishTextLabel);
				
				TextField translatedTextTextField = new TextField(translatedTextData.get(i));
				GridPane.setConstraints(translatedTextTextField,1,translatedTextIndex);
				
				translatedTextTextFieldList.add(translatedTextTextField);
				
				
				Button saveButton = new Button("Save");
				saveButton.setOnAction(e -> SaveText());
				GridPane.setConstraints(saveButton, 2,translatedTextIndex);
				
				grid.getChildren().addAll(turkishTextVisualLabel,turkishTextLabel,translatedTextVisualLabel,translatedTextTextField,saveButton);
				
				turkishTextIndex +=2;
				translatedTextIndex +=2;
				
			}
									
		}
		
		
		HBox layout= new HBox(10);
		layout.getChildren().addAll(grid);
		
		ScrollPane sb = new ScrollPane();
		sb.setContent(layout);
		
		Scene scene = new Scene(sb,750,750);
		window.setScene(scene);
		window.show();
	}
	


	//My Functions 
	public void SaveText() {
		saveCSV(dataID,turkishTextData,translatedTextData,gameDataPath);
	}
	
	public void closeProgram() {
		saveCSV(dataID,turkishTextData,translatedTextData,gameDataPath);
		window.close();
	}

}

