package controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import com.jfoenix.controls.JFXDialogLayout;

import io.datafx.controller.FXMLController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.util.VetoException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.MaxiFilm;
import models.RenameFiles;
import models.Settings;

@FXMLController(value = "/resources/fxml/Home.fxml" , title = "Material Design Example")
public class HomeController {
  
  @FXML
  private StackPane root;
  
  @FXML
  private Label home;
  
  @FXMLViewFlowContext
  private ViewFlowContext context;
  
  RenameFiles film;
  Boolean noAll;
  Boolean yesAll;
  
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
          Integer cptPopup = 0;
          noAll = false;
          success = true;
          yesAll = false;
          dragboard.getFiles().forEach(file -> {
            if(!noAll){
              if(Files.isDirectory(file.toPath())){
                try(Stream<Path> paths = Files.walk(Paths.get(file.toURI()))) {
                  paths.forEach(filePath -> {
                    if(!noAll){
                      if (Files.isRegularFile(filePath)) {
                        film = new RenameFiles(new File(filePath.toString()), settings);
                        if(!yesAll){
                          if(film.getNameWithoutExt().length() > 0)
							try {
								createPopup();
							} catch (FlowException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                        }else{
                          if(film.getNameWithoutExt().length() > 0)
                          film.applyRename(film.getCleanName());
                        }
                      }
                    }
                  });
                } catch (IOException e) {
                  e.printStackTrace();
                } 
              }else if (Files.isRegularFile(dragboard.getFiles().get(cptPopup).toPath())){
                film = new RenameFiles(new File(dragboard.getFiles().get(cptPopup).toString()), settings);
                if(!yesAll){
                  if(film.getNameWithoutExt().length() > 0)
					try {
						createPopup();
					} catch (FlowException e) {
						e.printStackTrace();
					}
                }else{
                  if(film.getNameWithoutExt().length() > 0)
                  film.applyRename(film.getCleanName());
                }
                
              }
            }
          });
          
        }
        event.setDropCompleted(success);
        event.consume();
      }
    });
  }
  
  private void createPopup () throws FlowException{

// Example 1 with JDialog, so without any FXML file
// Problem with the draw menu opened
// The rest of the app is not modal
	  
	  
//	  	JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);
//	  	dialog.setOverlayClose(false);
//	  	Text detailsHeaderLabelPopupRename = new Text("Do you want to rename ?\n");
//   		content.setHeading(detailsHeaderLabelPopupRename);
//   		content.setAlignment(Pos.CENTER);
//   		GridPane  root = new GridPane ();
//   		root.setId("gridPopup");
//   		root.setPrefHeight(110);
//   		root.setPrefWidth(500);
//   		root.setVgap(20);
//   		root.setHgap(14);
//   		
//   		
//   		
//   		VBox vbox = new VBox();
//   		vbox.setPrefWidth(500);
//   		vbox.setSpacing(1);
//   		
//   		Label detailsAfterLabelPopupRename = new Label("Before: "+film.getNameWithoutExt());
//   		detailsAfterLabelPopupRename.setId("messageLabelRenamePopup");
//   		detailsAfterLabelPopupRename.setAlignment(Pos.CENTER);
//   		detailsAfterLabelPopupRename.setFont(new Font("System Bold", 12));
//   		
//   		Label detailsBeforeLabelPopupRename = new Label("After: "+film.getCleanName());
//   		detailsBeforeLabelPopupRename.setId("detailsBeforeLabelPopupRename");
//   		detailsBeforeLabelPopupRename.setAlignment(Pos.CENTER);
//   		detailsBeforeLabelPopupRename.setFont(new Font("System Bold", 12));
//   		
//   		vbox.getChildren().addAll(detailsBeforeLabelPopupRename, detailsAfterLabelPopupRename);
//   		
//   		HBox actionParent = new HBox();
//   		actionParent.setId("actionParent");
//   		actionParent.setAlignment(Pos.CENTER);
//   		
//   		HBox okParent = new HBox();
//   		okParent.setId("okParent");
//   		okParent.setSpacing(10);
//   		okParent.setAlignment(Pos.CENTER);
//   		
//   		
//   		JFXButton buttonYesPopupRename = new JFXButton("Yes");
//   		buttonYesPopupRename.setPrefHeight(30);
//   		buttonYesPopupRename.setPrefWidth(70);
//   		buttonYesPopupRename.setId("buttonYesPopupRename");
//   		buttonYesPopupRename.setButtonType(ButtonType.RAISED);
//   		buttonYesPopupRename.setOnAction(e -> {film.applyRename(film.getCleanName()); dialog.close();});
//   		buttonYesPopupRename.setStyle("-fx-text-fill:WHITE;-fx-background-color:#5264AE;-fx-font-size:14px;");
//   		
//   		JFXButton buttonNoPopupRename = new JFXButton("No");
//   		buttonNoPopupRename.setPrefHeight(30);
//   		buttonNoPopupRename.setPrefWidth(70);
//   		buttonNoPopupRename.setId("buttonNoPopupRename");
//   		buttonNoPopupRename.setButtonType(ButtonType.RAISED);
//   		buttonNoPopupRename.setOnAction(e -> dialog.close());
//   		buttonNoPopupRename.setStyle("-fx-text-fill:WHITE;-fx-background-color:#5264AE;-fx-font-size:14px;");
//   		
//   		
//   		JFXButton buttonYesAllPopupRename = new JFXButton("Yes All");
//   		buttonYesAllPopupRename.setPrefHeight(30);
//   		buttonYesAllPopupRename.setPrefWidth(70);
//   		buttonYesAllPopupRename.setId("buttonYesAllPopupRename");
//   		buttonYesAllPopupRename.setButtonType(ButtonType.RAISED);
//   		buttonYesAllPopupRename.setOnAction(e-> yesAll = true);
//   		buttonYesAllPopupRename.setStyle("-fx-text-fill:WHITE;-fx-background-color:#5264AE;-fx-font-size:14px;");
//   		
//   		
//   		JFXButton buttonNoAllPopupRename = new JFXButton("No All");
//   		buttonNoAllPopupRename.setPrefHeight(30);
//   		buttonNoAllPopupRename.setPrefWidth(70);
//   		buttonNoAllPopupRename.setId("buttonNoAllPopupRename");
//   		buttonNoAllPopupRename.setOnAction(e -> noAll = true);
//   		buttonNoAllPopupRename.setButtonType(ButtonType.RAISED);
//   		buttonNoAllPopupRename.setStyle("-fx-text-fill:WHITE;-fx-background-color:#5264AE;-fx-font-size:14px;");
//   		
//   		
//   		
//   		okParent.getChildren().addAll(buttonNoAllPopupRename, buttonYesAllPopupRename, buttonNoPopupRename, buttonYesPopupRename);
//   		
//   		GridPane.setColumnIndex(okParent, 1);
//   		GridPane.setRowIndex(okParent, 1);
//   		GridPane.setColumnIndex(vbox, 1);
//   		GridPane.setRowIndex(vbox, 0);
//   		GridPane.setMargin(vbox, new Insets(0,0,0,50));
//   		root.getChildren().addAll(vbox, okParent);
//   		content.setBody(root);
//   		dialog.show();
	  
// Example 2 with the pass context to the child
	  // Pb to pass and get the state of yesAll and noAll with PopopOthrt.java and fxml
	  Stage stage1 = new Stage();
      ViewFlowContext flowContext1 = new ViewFlowContext();
      DefaultFlowContainer container1 = new DefaultFlowContainer();
      Flow flow2 = new Flow(PopupOthrt.class);
      flowContext1.register("YesAllButtonState", yesAll);
      flowContext1.register("noAllButtonState", noAll);
      flowContext1.register("Film", film);
      flowContext1.register("Stage", stage1);
      flow2.createHandler(flowContext1).start(container1);
      Scene scene1 = new Scene(container1.getView(), 400, 230);
      scene1.getStylesheets().add(MaxiFilm.class.getResource("/resources/css/jfoenix-main-demo.css").toExternalForm());
      stage1.setScene(scene1);
      stage1.showAndWait();
  }
}
