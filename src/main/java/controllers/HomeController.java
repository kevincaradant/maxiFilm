package controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import io.datafx.controller.FXMLController;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.util.VetoException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
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
                          createPopup();
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
                  createPopup();
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
  
  private void createPopup (){
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.initModality(Modality.WINDOW_MODAL);
    alert.setTitle("Confirmation Dialog with Custom Actions");
    alert.setHeaderText("Do you want rename it ?");
    alert.setContentText("Before : " + film.getNameWithoutExt() + "\nAfter : " + film.getCleanName()+"\n");
    alert.getDialogPane().getStylesheets().add(
    getClass().getResource("/resources/css/jfoenix-main-demo.css").toExternalForm());
    alert.getDialogPane().getStyleClass().add("myDialog");
    
    ButtonType buttonYes = new ButtonType("YES");
    ButtonType buttonNo = new ButtonType("NO", ButtonData.CANCEL_CLOSE);
    ButtonType buttonYesAll = new ButtonType("YES ALL");
    ButtonType buttonNoAll = new ButtonType("NO ALL");
    
    alert.getButtonTypes().setAll(buttonYes, buttonNo, buttonYesAll, buttonNoAll);
    
    Optional<ButtonType> result = alert.showAndWait();
    
    if (result.get() == buttonYes){
      film.applyRename(film.getCleanName());
    } else if (result.get() == buttonNoAll) {
      noAll = true;
    } else if (result.get() == buttonYesAll) {
      yesAll = true;
      film.applyRename(film.getCleanName());
    } else {
      alert.close();
    }
  }
}
