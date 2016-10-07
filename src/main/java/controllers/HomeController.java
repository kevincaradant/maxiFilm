package controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton.ButtonType;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import io.datafx.controller.FXMLController;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.util.VetoException;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.RenameFiles;
import models.Settings;

@FXMLController(value = "/resources/fxml/Home.fxml" , title = "Material Design Example")
public class HomeController {
  
  
  @FXML
  private Label home;
  
  @FXMLViewFlowContext
  private ViewFlowContext context;
  
  RenameFiles film;
  SimpleBooleanProperty  noAllProperty = new SimpleBooleanProperty(false);
  SimpleBooleanProperty  yesAllProperty = new SimpleBooleanProperty(false);
  ArrayList<Path> arrayFile = new ArrayList<Path>();
  Stage stage;
  @FXML
  JFXDialogLayout content = new JFXDialogLayout();
  
  @PostConstruct
  public void init() throws FlowException, VetoException {
    Settings settings = (Settings) context.getRegisteredObject("Settings");
    
    home.setOnDragOver(new EventHandler<DragEvent>() {
      @Override
      public void handle(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasFiles()) {
          event.acceptTransferModes(TransferMode.ANY);
        } else {
          event.consume();
        }
      }
    });
    
    // Dropping over surface
    home.setOnDragDropped(new EventHandler<DragEvent>() {
      @Override
      public void handle(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        boolean success = false;
        if (dragboard.hasFiles()) {
        	arrayFile = new ArrayList<Path>();
        	noAllProperty.set(false);
        	success = true;
        	yesAllProperty.set(false);
        	dragboard.getFiles().forEach(file -> {
			  if(Files.isDirectory(file.toPath())){
			    try(Stream<Path> paths = Files.walk(Paths.get(file.toURI()))) {
			      paths.forEach(filePath -> {
			          if (Files.isRegularFile(filePath)) 
			        	 arrayFile.add(filePath);
			      });
			    } catch (IOException e) {
			      e.printStackTrace();
			    } 
			  }else if (Files.isRegularFile(file.toPath()))
				  arrayFile.add(file.toPath());
            
          });
        }
        event.setDropCompleted(success);
        event.consume();
        arrayFile.forEach(file -> {
        	film = new RenameFiles(new File(file.toString()), settings);
        	if(!noAllProperty.getValue()){
	        	if(film.getNameWithoutExt().length() > 0){
        			if(!yesAllProperty.getValue()){
	        			try {
	    					createPopup();
	    				} catch (FlowException | InterruptedException e) {
	    					// TODO Auto-generated catch block
	    					e.printStackTrace();
	    				}
	        		}else{
	        			film.applyRename(film.getCleanName());
	        		}
        		}
        	}
		});
      }
    });
  }
  
  private void createPopup () throws FlowException, InterruptedException{

// Example 1 with JDialog, so without any FXML file
// Problem with the draw menu opened
// The rest of the app is not modal
	  
	  
	  	JFXDialog dialog = new JFXDialog((StackPane) context.getRegisteredObject("ROOT"), content, JFXDialog.DialogTransition.CENTER);
	  	dialog.setOverlayClose(false);
	  	Text detailsHeaderLabelPopupRename = new Text("Do you want to rename ?\n");
   		content.setHeading(detailsHeaderLabelPopupRename);
   		content.setAlignment(Pos.CENTER);
   		GridPane  root = new GridPane ();
   		root.setId("gridPopup");
   		root.setPrefHeight(110);
   		root.setPrefWidth(500);
   		root.setVgap(20);
   		root.setHgap(14);
   		
   		VBox vbox = new VBox();
   		vbox.setPrefWidth(500);
   		vbox.setSpacing(1);
   		
   		Label detailsAfterLabelPopupRename = new Label("Before: "+film.getNameWithoutExt());
   		detailsAfterLabelPopupRename.setId("messageLabelRenamePopup");
   		detailsAfterLabelPopupRename.setAlignment(Pos.CENTER);
   		detailsAfterLabelPopupRename.setFont(new Font("System Bold", 12));
   		
   		Label detailsBeforeLabelPopupRename = new Label("After: "+film.getCleanName());
   		detailsBeforeLabelPopupRename.setId("detailsBeforeLabelPopupRename");
   		detailsBeforeLabelPopupRename.setAlignment(Pos.CENTER);
   		detailsBeforeLabelPopupRename.setFont(new Font("System Bold", 12));
   		
   		vbox.getChildren().addAll(detailsBeforeLabelPopupRename, detailsAfterLabelPopupRename);
   		
   		HBox actionParent = new HBox();
   		actionParent.setId("actionParent");
   		actionParent.setAlignment(Pos.CENTER);
   		
   		HBox okParent = new HBox();
   		okParent.setId("okParent");
   		okParent.setSpacing(10);
   		okParent.setAlignment(Pos.CENTER);
   		
   		
   		JFXButton buttonYesPopupRename = new JFXButton("Yes");
   		buttonYesPopupRename.setPrefHeight(30);
   		buttonYesPopupRename.setPrefWidth(70);
   		buttonYesPopupRename.setId("buttonYesPopupRename");
   		buttonYesPopupRename.setButtonType(ButtonType.RAISED);
   		buttonYesPopupRename.setOnAction(e -> { dialog.close(); dialog.setOverlayClose(true);film.applyRename(film.getCleanName());});
   		buttonYesPopupRename.setStyle("-fx-text-fill:WHITE;-fx-background-color:#5264AE;-fx-font-size:14px;");
   		
   		JFXButton buttonNoPopupRename = new JFXButton("No");
   		buttonNoPopupRename.setPrefHeight(30);
   		buttonNoPopupRename.setPrefWidth(70);
   		buttonNoPopupRename.setId("buttonNoPopupRename");
   		buttonNoPopupRename.setButtonType(ButtonType.RAISED);
   		buttonNoPopupRename.setOnAction(e -> {dialog.close();});
   		buttonNoPopupRename.setStyle("-fx-text-fill:WHITE;-fx-background-color:#5264AE;-fx-font-size:14px;");
   		
   		
   		JFXButton buttonYesAllPopupRename = new JFXButton("Yes All");
   		buttonYesAllPopupRename.setPrefHeight(30);
   		buttonYesAllPopupRename.setPrefWidth(70);
   		buttonYesAllPopupRename.setId("buttonYesAllPopupRename");
   		buttonYesAllPopupRename.setButtonType(ButtonType.RAISED);
   		buttonYesAllPopupRename.setOnAction(e-> yesAllProperty.setValue(true));
   		buttonYesAllPopupRename.setStyle("-fx-text-fill:WHITE;-fx-background-color:#5264AE;-fx-font-size:14px;");
   		
   		
   		JFXButton buttonNoAllPopupRename = new JFXButton("No All");
   		buttonNoAllPopupRename.setPrefHeight(30);
   		buttonNoAllPopupRename.setPrefWidth(70);
   		buttonNoAllPopupRename.setId("buttonNoAllPopupRename");
   		buttonNoAllPopupRename.setOnAction(e -> noAllProperty.setValue(true));
   		buttonNoAllPopupRename.setButtonType(ButtonType.RAISED);
   		buttonNoAllPopupRename.setStyle("-fx-text-fill:WHITE;-fx-background-color:#5264AE;-fx-font-size:14px;");
   		
   		
   		
   		okParent.getChildren().addAll(buttonNoAllPopupRename, buttonYesAllPopupRename, buttonNoPopupRename, buttonYesPopupRename);
   		
   		GridPane.setColumnIndex(okParent, 1);
   		GridPane.setRowIndex(okParent, 1);
   		GridPane.setColumnIndex(vbox, 1);
   		GridPane.setRowIndex(vbox, 0);
   		GridPane.setMargin(vbox, new Insets(0,0,0,50));
   		root.getChildren().addAll(vbox, okParent);
   		content.setBody(root);
   		dialog.show();
	  
// Example 2 with the pass context to the child
	  // Pb to pass and get the state of yesAll and noAll with PopopOthrt.java and fxml

	  
//	  Stage stage1 = new Stage();
//
//		stage1.setResizable(false);
//	  Runnable closeJFXDecorator = new Runnable(){
//		   public void run(){
//		        stage1.close();
//		     }
//	  };
//      ViewFlowContext flowContext1 = new ViewFlowContext();
//      DefaultFlowContainer container1 = new DefaultFlowContainer();
//      Flow flow2 = new Flow(PopupOthrt.class);
//      flowContext1.register("YesAllButtonState", yesAllProperty);
//      flowContext1.register("NoAllButtonState", noAllProperty);
//      flowContext1.register("Film", film);
//      flowContext1.register("Stage1", stage1);
//      flow2.createHandler(flowContext1).start(container1);
//      JFXDecorator decorator = new JFXDecorator(stage1, container1.getView(),false, false, false);
//		decorator.setOnCloseButtonAction(closeJFXDecorator);
//		Scene scene1 = new Scene(decorator, 400, 230);
//      scene1.getStylesheets().add(MaxiFilm.class.getResource("/resources/css/jfoenix-main-demo.css").toExternalForm());
//      stage1.setScene(scene1);
//      stage1.showAndWait();
      
  		
}
  }
