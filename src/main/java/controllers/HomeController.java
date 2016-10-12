package controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import com.jfoenix.controls.JFXDecorator;

import io.datafx.controller.FXMLController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.util.VetoException;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.MaxiFilm;
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

  @PostConstruct
  public void init() throws FlowException, VetoException {
    Settings settings = (Settings) context.getRegisteredObject("Settings");
    Stage stage = (Stage) context.getRegisteredObject("Stage");

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
  
  private void createPopup() throws FlowException, InterruptedException{
    	  Stage stage1 = new Stage();
    	  stage1.setResizable(false);
    	  Runnable closeJFXDecorator = new Runnable(){
    		  public void run(){
    			  stage1.close();
    		  }
    	  };
          ViewFlowContext flowContext1 = new ViewFlowContext();
          DefaultFlowContainer container1 = new DefaultFlowContainer();
          Flow flow2 = new Flow(Popup.class);
          flowContext1.register("YesAllButtonState", yesAllProperty);
          flowContext1.register("NoAllButtonState", noAllProperty);
          flowContext1.register("Film", film);
          flowContext1.register("Stage1", stage1);
          flow2.createHandler(flowContext1).start(container1);
          JFXDecorator decorator = new JFXDecorator(stage1, container1.getView(),false, false, false);
    		decorator.setOnCloseButtonAction(closeJFXDecorator);
    		Scene scene1 = new Scene(decorator, 400, 230);
          scene1.getStylesheets().add(MaxiFilm.class.getResource("/resources/css/jfoenix-main-demo.css").toExternalForm());
          stage1.setScene(scene1);
          stage1.initModality(Modality.APPLICATION_MODAL);
          stage1.showAndWait();	
  }
}
