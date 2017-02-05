package org.front.controllers;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.PostConstruct;

import com.jfoenix.controls.JFXButton;

import io.datafx.controller.FXMLController;
import javafx.fxml.FXML;

@FXMLController(value = "/resources/fxml/Help.fxml" , title = "Material Design Example")
public class HelpController{

  @FXML
  private JFXButton githubButton;

  @FXML
  private JFXButton googlePlusButton;

  @FXML
  private JFXButton linkedInButton;
  
  @PostConstruct
  public void init(){
	  
	  githubButton.setOnAction(e -> {
		  if (Desktop.isDesktopSupported()) {
		      try {
		    	  URI uri = new URI("https://github.com/kevincaradant");
		        Desktop.getDesktop().browse(uri);
		      } catch (IOException | URISyntaxException e1) {}
		    } else {}
	  });
	  
	  googlePlusButton.setOnAction(e -> {
		  if (Desktop.isDesktopSupported()) {
		      try {
		    	  URI uri = new URI("https://plus.google.com/114137643265344852498");
		        Desktop.getDesktop().browse(uri);
		      } catch (IOException | URISyntaxException e1) {}
		    } else {}
	  });
	  
	  linkedInButton.setOnAction(e -> {
		  if (Desktop.isDesktopSupported()) {
		      try {
		    	  URI uri = new URI("https://fr.linkedin.com/in/kevincaradant");
		        Desktop.getDesktop().browse(uri);
		      } catch (IOException | URISyntaxException e1) {}
		    } else {}
	  }); 
  }
}