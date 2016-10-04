package controllers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawer.DrawerDirection;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXToolbar;

import io.datafx.controller.FXMLController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.container.ContainerAnimations;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.AnimatedFlowContainer;
import models.Settings;

@FXMLController(value = "/resources/fxml/Main.fxml", title = "Material Design Example")
public class MainController{

  @FXMLViewFlowContext
  private ViewFlowContext context;

  @FXML
  private StackPane root;

  @FXML
  private BorderPane mainBorderPane;

  @FXML
  private VBox mainVBoxTop;

  @FXML
  private JFXToolbar mainToolbarTop;

  @FXML
  private JFXHamburger mainHamburgerTop;

  @FXML
  private JFXDrawer mainDrawer;
  private FlowHandler flowHandler;
  private FlowHandler sideMenuFlowHandler;

  private Settings settings = new Settings();

  @PostConstruct
  public void init () throws FlowException {
	  
	  	// Set the Drawer direction
		mainDrawer.setDirection(DrawerDirection.LEFT);
		
		// AlLow to add or not a filter which force you to click to remove the drawer before to have access at the content
		mainDrawer.setOverLayVisible(false);
		
		// Avoid to resize the drawer
		mainDrawer.setResizableOnDrag(false);	

		// Init the title hamburger icon
		mainDrawer.setOnDrawerOpening((e) -> {
			mainHamburgerTop.getAnimation().setRate(1);
			mainHamburgerTop.getAnimation().play();
		});
		mainDrawer.setOnDrawerClosing((e) -> {
			mainHamburgerTop.getAnimation().setRate(-1);
			mainHamburgerTop.getAnimation().play();
		});
		mainHamburgerTop.setOnMouseClicked((e)->{
			if (mainDrawer.isHidden() || mainDrawer.isHidding()) mainDrawer.open();
			else mainDrawer.close();
		});

      // Restore the settings if possible
      restoreSettings();
      
      //Open the Drawer when you start the app
      mainDrawer.open();
      Stage stage = (Stage) context.getRegisteredObject("Stage");
      
      // Set the default controller
      Flow innerFlow = new Flow(HomeController.class);
      flowHandler = innerFlow.createHandler(context);
      context.register("MainDrawer", mainDrawer);
      context.register("ContentFlowHandler", flowHandler);
      context.register("Settings", settings);
      context.register("Stage", stage);//?
      context.register("ContentFlow", innerFlow);
      mainDrawer.setContent(flowHandler.start(new AnimatedFlowContainer(Duration.millis(300), ContainerAnimations.SWIPE_RIGHT)));
      context.register("ContentPane", mainDrawer.getContent().get(0));

      // Side controller will add links to the content flow
      Flow sideMenuFlow = new Flow(SideMenuController.class);
      sideMenuFlowHandler = sideMenuFlow.createHandler(context);
      mainDrawer.setSidePane(sideMenuFlowHandler.start(new AnimatedFlowContainer(Duration.millis(300), ContainerAnimations.SWIPE_RIGHT)));
  }

  private void restoreSettings (){
    Gson gsonDeserialize = new GsonBuilder().create();

    BufferedReader br;
    try {
      br = new BufferedReader(new FileReader("settings.json"));
      Settings settingsObj = gsonDeserialize.fromJson(br, Settings.class);
      
      // Check if the parameters are correct
      if(settingsObj.getMovieName() <= settings.getMaxMovieName() && settingsObj.getSeasonEpisode() <= settings.getMaxSeasonEpisode() && settingsObj.getSepaMovieSeasonEp() <= settings.getMaxSepaMovieSeasonEp()){
        settings.setMovieName(settingsObj.getMovieName());
        settings.setSeasonEpisode(settingsObj.getSeasonEpisode());
        settings.setSepaMovieSeasonEp(settingsObj.getSepaMovieSeasonEp());
        settings.setRemoveTrack(settingsObj.getRemoveTrack());
      }
    } catch (FileNotFoundException e) {
      Logger.getLogger(MainController.class.getName()).log(Level.CONFIG, "File Not Found", e);
    }
  }
}
