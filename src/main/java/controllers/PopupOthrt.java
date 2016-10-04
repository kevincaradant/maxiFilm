package controllers;
		
import javax.annotation.PostConstruct;

import com.jfoenix.controls.JFXButton;

import io.datafx.controller.FXMLController;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import models.RenameFiles;

@FXMLController(value = "/resources/fxml/PopupOthrt.fxml" , title = "Material Design Example")
public class PopupOthrt{

    @FXML
    private GridPane gridPopup;

    @FXML
    private ImageView logoPopupRename;

    @FXML
    private Label messageLabelRenamePopup;

    @FXML
    private Label detailsBeforeLabelPopupRename;

    @FXML
    private Label detailsAfterLabelPopupRename;

    @FXML
    private HBox actionParent;

    @FXML
    private HBox okParent;

    @FXML
    private JFXButton buttonYesPopupRename;

    @FXML
    private JFXButton buttonNoPopupRename;

    @FXML
    private JFXButton buttonYesAllPopupRename;

    @FXML
    private JFXButton buttonNoAllPopupRename;

    @FXMLViewFlowContext
    private ViewFlowContext context;
    
    @FXML
    void onNoAllButton(ActionEvent event) {

    }
    Boolean yesAll;
    Boolean noAll;
    
    @PostConstruct
    public void init(){
    	RenameFiles film = (RenameFiles) context.getRegisteredObject("Film");
    	yesAll = (Boolean) context.getRegisteredObject("YesAllButtonState");
    	System.out.println("1: "+yesAll);
    	noAll = (Boolean) context.getRegisteredObject("NoAllButtonState");
    	detailsBeforeLabelPopupRename.setText("Before: "+film.getNameWithoutExt());
    	detailsAfterLabelPopupRename.setText("After: "+film.getCleanName());
    }
    
    @FXML
    void onNoButton(ActionEvent event) {

    }

    @FXML
    void onYesAllButton(ActionEvent event) {
    	//How to access at yesAll in init
    	System.out.println("2: "+yesAll);
    	yesAll = true;
    	System.out.println("3: "+yesAll);
    	context.register("YesAllButtonState", yesAll);
    }

    @FXML
    void onYesButton(ActionEvent event) {

    }
}
