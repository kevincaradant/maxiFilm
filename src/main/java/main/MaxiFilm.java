package main;


import com.jfoenix.controls.JFXDecorator;

import controllers.MainController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Settings;

public class MaxiFilm extends Application {

	@FXMLViewFlowContext private ViewFlowContext flowContext;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Settings settings;

		// building of the settings
		settings = new Settings();
		
		// trying to restore if there is one backup of them
		settings.restoreSettings();

		launch(args);
	}

	public void start(Stage stage) throws Exception {
		
		Flow flow = new Flow(MainController.class);
		DefaultFlowContainer container = new DefaultFlowContainer();
		flowContext = new ViewFlowContext();
		flowContext.register("Stage", stage);
		flow.createHandler(flowContext).start(container);

		JFXDecorator decorator = new JFXDecorator(stage, container.getView());
		decorator.setCustomMaximize(true);
		Scene scene = new Scene(decorator, 770, 690);
		scene.getStylesheets().add(MaxiFilm.class.getResource("/resources/css/jfoenix-main-demo.css").toExternalForm());
		stage.setWidth(770);
		stage.setHeight(690);
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}

}
