package org.front.controllers;

import javax.annotation.PostConstruct;

import com.jfoenix.controls.JFXListView;

import io.datafx.controller.FXMLController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.util.VetoException;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
@FXMLController(value = "/resources/fxml/SideMenu.fxml", title = "Material Design Example")
public class SideMenuController {


  @FXML
  private StackPane root;

  @FXML
  private VBox listViewDrawer;

  @FXML
  private ImageView imageTopDrawer;

  @FXML
  private JFXListView<?> sideList;

  @FXML
  private Label home;

  @FXML
  private JFXListView<?> subList;

  @FXML
  private Label musics;

  @FXML
  private Label settings;

  @FXML
  private Label movies;

  @FXML
  private Label series;

  @FXML
  private Label help;

  @FXML
  private Label exit;

  @FXMLViewFlowContext
  private ViewFlowContext context;
  
  @PostConstruct
  public void init() throws FlowException, VetoException {
	  
    sideList.propagateMouseEventsToParent();
    FlowHandler contentFlowHandler = (FlowHandler) context.getRegisteredObject("ContentFlowHandler");
    Flow contentFlow = (Flow) context.getRegisteredObject("ContentFlow");
    Stage stage = (Stage) context.getRegisteredObject("Stage");
    
    bindNodeToController(home, HomeController.class, contentFlow, contentFlowHandler);
    bindNodeToController(musics, MusicsSettingsController.class, contentFlow, contentFlowHandler);
    bindNodeToController(movies, MoviesSettingsController.class, contentFlow, contentFlowHandler);
    bindNodeToController(series, SeriesSettingsController.class, contentFlow, contentFlowHandler);
    bindNodeToController(help, HelpController.class, contentFlow, contentFlowHandler);
    
    exit.setOnMouseClicked((e)->{stage.close();});
  }
  
  private void bindNodeToController(Node node, Class<?> controllerClass, Flow flow, FlowHandler flowHandler) {
    flow.withGlobalLink(node.getId(), controllerClass);
    node.setOnMouseClicked((e) -> {
      try {
        flowHandler.handle(node.getId());
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    });
  }


}
