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
@FXMLController(value = "/resources/fxml/SeriesSettings.fxml" , title = "Material Design Example")
public class SeriesSettingsController{
  
  @FXML
  private Label labelSettingsSeries;

  @FXML
  private Label labelSerialNameSettingsSeries;

  @FXML
  private JFXButton buttonCancelSeriesSettings;

  @FXML
  private JFXButton buttonOKSeriesSettings;

  @FXML
  private JFXRadioButton radioButtonSeriesNumberName12;

  @FXML
  private ToggleGroup groupSeasonEpisodeName;

  @FXML
  private JFXRadioButton radioButtonSeriesNumberName11;

  @FXML
  private JFXRadioButton radioButtonSeriesNumberName10;

  @FXML
  private JFXRadioButton radioButtonSeriesNumberName9;

  @FXML
  private JFXRadioButton radioButtonSeriesNumberName8;

  @FXML
  private JFXRadioButton radioButtonSeriesNumberName7;

  @FXML
  private JFXRadioButton radioButtonSeriesNumberName6;

  @FXML
  private JFXRadioButton radioButtonSeriesNumberName5;

  @FXML
  private JFXRadioButton radioButtonSeriesNumberName4;

  @FXML
  private JFXRadioButton radioButtonSeriesNumberName3;

  @FXML
  private JFXRadioButton radioButtonSeriesNumberName2;

  @FXML
  private JFXRadioButton radioButtonSeriesNumberName1;

  @FXML
  private GridPane gridSeparatorSerialName;

  @FXML
  private JFXRadioButton radioButtonSerial5;

  @FXML
  private ToggleGroup groupSeparatorName;

  @FXML
  private JFXRadioButton radioButtonSerial4;

  @FXML
  private JFXRadioButton radioButtonSerial3;

  @FXML
  private JFXRadioButton radioButtonSerial2;

  @FXML
  private JFXRadioButton radioButtonSerial1;

  @FXMLViewFlowContext
  private ViewFlowContext context;

  private Settings settings;

  @PostConstruct
  public void init() {
    settings = (Settings) context.getRegisteredObject("Settings");

    for(Toggle toggle : groupSeasonEpisodeName.getToggles()){
      if(toggle.getUserData().equals(Integer.toString(settings.getSeasonEpisode()))){
        toggle.setSelected(true);
      }
    }

    for(Toggle toggle : groupSeparatorName.getToggles()){
      if(toggle.getUserData().equals(Integer.toString(settings.getSepaMovieSeasonEp())))
      toggle.setSelected(true);
    }
  }

  @FXML
  void saveSettingsSeries(ActionEvent event) {
    settings.setSeasonEpisode(Integer.parseInt(groupSeasonEpisodeName.getSelectedToggle().getUserData().toString()));
    settings.setSepaMovieSeasonEp(Integer.parseInt(groupSeparatorName.getSelectedToggle().getUserData().toString()));
    Settings.saveSettings(settings);
  }
}
