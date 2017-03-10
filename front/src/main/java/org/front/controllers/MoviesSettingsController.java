package org.front.controllers;
import javax.annotation.PostConstruct;

import org.commons.Settings;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;

import io.datafx.controller.FXMLController;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

@SuppressWarnings("restriction")
@FXMLController(value = "/resources/fxml/MoviesSettings.fxml" , title = "Material Design Example")
public class MoviesSettingsController {

  @FXML
  private GridPane gridSettingsMovies;

  @FXML
  private Label labelSettingsMovies;

  @FXML
  private Label labelFilesNameSettingsMovies;

  @FXML
  private JFXButton buttonCancelMoviesSettings;

  @FXML
  private JFXButton buttonOKMoviesSettings;

  @FXML
  private GridPane gridFilesName;

  @FXML
  private ToggleGroup groupFilesName;

  @FXML
  private JFXRadioButton radioButtonFiles5;

  @FXML
  private JFXRadioButton radioButtonFiles6;

  @FXML
  private JFXRadioButton radioButtonFiles3;

  @FXML
  private JFXRadioButton radioButtonFiles2;

  @FXML
  private JFXRadioButton radioButtonFiles1;

  @FXML
  private JFXRadioButton radioButtonFiles4;

  @FXMLViewFlowContext
  private ViewFlowContext context;

  private Settings settings;

  @PostConstruct
  public void init() {
    settings = (Settings) context.getRegisteredObject("Settings");

    for(Toggle toggle : groupFilesName.getToggles()){
      if(toggle.getUserData().equals(Integer.toString(settings.getMovieName())))
      toggle.setSelected(true);
    }
  }
  
  @FXML
  void saveSettingsMovies(ActionEvent event) {
    settings.setMovieName(Integer.parseInt(groupFilesName.getSelectedToggle().getUserData().toString()));
    Settings.saveSettings(settings);
  }
}
