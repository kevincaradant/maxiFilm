package org.front.controllers;
import javax.annotation.PostConstruct;

import org.commons.Settings;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;

import io.datafx.controller.FXMLController;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

@FXMLController(value = "/resources/fxml/MusicsSettings.fxml" , title = "Material Design Example")
public class MusicsSettingsController{


  @FXML
  private GridPane gridMusicsSettings;

  @FXML
  private Label titleMusicsSettings;

  @FXML
  private JFXButton buttonCancelMusicsSettings;

  @FXML
  private JFXButton buttonOKMusicsSettings;

  @FXML
  private JFXToggleButton removeTrackNumberMusicsSettings;

  @FXMLViewFlowContext
  private ViewFlowContext context;

  private Settings settings;

  @PostConstruct
  public void init() {
    settings = (Settings) context.getRegisteredObject("Settings");
    removeTrackNumberMusicsSettings.setSelected(settings.getRemoveTrack());

  }

  @FXML
  void saveSettingsMusics(ActionEvent event) {
    settings.setRemoveTrack(removeTrackNumberMusicsSettings.isSelected());
    Settings.saveSettings(settings);
  }

}
