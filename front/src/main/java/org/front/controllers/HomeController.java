package org.front.controllers;

import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.commons.RenameFiles;
import org.commons.Settings;

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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.Separator;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

@SuppressWarnings("restriction")
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
          success = true;
          noAllProperty.set(false);
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
        try {
			createPopup(arrayFile.iterator(), settings);
		} catch (FlowException | InterruptedException e) {
			e.printStackTrace();
		}
      }
    });
  }
  
  private void createPopup(Iterator<Path> iterator, Settings settings) throws FlowException, InterruptedException{
	  if (iterator.hasNext()) {
		film = new RenameFiles(new File(iterator.next().toString()), settings);
		JFXDialogLayout content = new JFXDialogLayout();
	  	JFXDialog dialog = new JFXDialog((StackPane) context.getRegisteredObject("ROOT"), content, JFXDialog.DialogTransition.CENTER);
	  	dialog.setOverlayClose(false);

	  	Text detailsHeaderLabelPopupRename = new Text("Do you want to rename ?\n");
 		content.setHeading(detailsHeaderLabelPopupRename);
 		content.setAlignment(Pos.CENTER);
 		GridPane  root = new GridPane ();
 		root.setId("gridPopup");
 		root.setVgap(20);
 		root.setHgap(14);
 		VBox vbox = new VBox();
 		vbox.setSpacing(1);
 		Label detailsBeforeLabelPopupRename = new Label("Before: "+film.getNameWithoutExt());
 		detailsBeforeLabelPopupRename.setId("detailsBeforeLabelPopupRename");
 		detailsBeforeLabelPopupRename.setAlignment(Pos.BASELINE_LEFT);
 		detailsBeforeLabelPopupRename.setFont(new Font("System Bold", 12));
 		detailsBeforeLabelPopupRename.setMinWidth(600);
 		detailsBeforeLabelPopupRename.setTextFill(Color.RED);
 		detailsBeforeLabelPopupRename.setPadding(new Insets(0,0,7,0));
 		Separator separator = new Separator();
 		Label detailsAfterLabelPopupRename = new Label("After: "+film.getCleanName());
 		detailsAfterLabelPopupRename.setId("messageLabelRenamePopup");
 		detailsAfterLabelPopupRename.setAlignment(Pos.BASELINE_LEFT);
 		detailsAfterLabelPopupRename.setFont(new Font("System Bold", 12));
 		detailsAfterLabelPopupRename.setPadding(new Insets(7,0,0,0));
 		detailsAfterLabelPopupRename.setMinWidth(600);
 		detailsAfterLabelPopupRename.setTextFill(Color.GREEN);
 		vbox.getChildren().addAll(detailsBeforeLabelPopupRename, separator, detailsAfterLabelPopupRename);
 		
 		HBox actionParent = new HBox();
 		actionParent.setId("actionParent");
 		actionParent.setAlignment(Pos.CENTER);
 		
 		HBox okParent = new HBox();
 		okParent.setId("okParent");
 		okParent.setSpacing(10);
 		okParent.setAlignment(Pos.CENTER);
 		
 		EventHandler<ActionEvent> handler = evt -> {
            Labeled source = (Labeled) evt.getSource();
            
            switch (source.getId()) {
			case "buttonYesPopupRename":
				
				film.applyRename(film.getCleanName());
				if (iterator.hasNext()) {
					film = new RenameFiles(new File(iterator.next().toString()), settings);
					detailsBeforeLabelPopupRename.setText("Before: "+film.getNameWithoutExt());
					detailsAfterLabelPopupRename.setText("After: "+film.getCleanName());
	            } else {
	            	dialog.close();
	            }
				
				break;
			case "buttonNoPopupRename":
				if (iterator.hasNext()) {
					film = new RenameFiles(new File(iterator.next().toString()), settings);
					detailsBeforeLabelPopupRename.setText("Before: "+film.getNameWithoutExt());
					detailsAfterLabelPopupRename.setText("After: "+film.getCleanName());
	            } else {
	            	dialog.close();
	            }		
				break;
			case "buttonYesAllPopupRename":
				film.applyRename(film.getCleanName());
				while (iterator.hasNext()) {
					film = new RenameFiles(new File(iterator.next().toString()), settings);
					film.applyRename(film.getCleanName());
	            } 
            	dialog.close();
	            	
				
				break;
			case "buttonNoAllPopupRename":
	            dialog.close();  
				break;
			default:
				break;
			}
        };
        
 		JFXButton buttonYesPopupRename = new JFXButton("Yes");
 		buttonYesPopupRename.setPrefHeight(30);
 		buttonYesPopupRename.setPrefWidth(70);
 		buttonYesPopupRename.setId("buttonYesPopupRename");
 		buttonYesPopupRename.setButtonType(ButtonType.RAISED);
 		buttonYesPopupRename.setOnAction(handler);
 		buttonYesPopupRename.setStyle("-fx-text-fill:WHITE;-fx-background-color:#5264AE;-fx-font-size:14px;");
 		
 		JFXButton buttonNoPopupRename = new JFXButton("No");
 		buttonNoPopupRename.setPrefHeight(30);
 		buttonNoPopupRename.setPrefWidth(70);
 		buttonNoPopupRename.setId("buttonNoPopupRename");
 		buttonNoPopupRename.setButtonType(ButtonType.RAISED);
 		buttonNoPopupRename.setOnAction(handler);
 		buttonNoPopupRename.setStyle("-fx-text-fill:WHITE;-fx-background-color:#5264AE;-fx-font-size:14px;");
 		
 		
 		JFXButton buttonYesAllPopupRename = new JFXButton("Yes All");
 		buttonYesAllPopupRename.setPrefHeight(30);
 		buttonYesAllPopupRename.setPrefWidth(70);
 		buttonYesAllPopupRename.setId("buttonYesAllPopupRename");
 		buttonYesAllPopupRename.setButtonType(ButtonType.RAISED);
 		buttonYesAllPopupRename.setOnAction(handler);
 		buttonYesAllPopupRename.setStyle("-fx-text-fill:WHITE;-fx-background-color:#5264AE;-fx-font-size:14px;");
 		
 		
 		JFXButton buttonNoAllPopupRename = new JFXButton("No All");
 		buttonNoAllPopupRename.setPrefHeight(30);
 		buttonNoAllPopupRename.setPrefWidth(70);
 		buttonNoAllPopupRename.setId("buttonNoAllPopupRename");
 		buttonNoAllPopupRename.setOnAction(handler);
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
	  }
  }
}
