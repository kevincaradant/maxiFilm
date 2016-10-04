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
import javafx.stage.Modality;
import javafx.stage.Stage;
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

    Stage stage;
    Boolean yesAll;
    Boolean noAll;
    RenameFiles film;
    
    @PostConstruct
    public void init(){
    	film = (RenameFiles) context.getRegisteredObject("Film");
    	stage = (Stage) context.getRegisteredObject("Stage1");
    	yesAll = (Boolean) context.getRegisteredObject("YesAllButtonState");
    	noAll = (Boolean) context.getRegisteredObject("NoAllButtonState");
    	detailsBeforeLabelPopupRename.setText("Before: "+film.getNameWithoutExt());
    	detailsAfterLabelPopupRename.setText("After: "+film.getCleanName());
    }
    
    
    @FXML
    void onNoAllButton(ActionEvent event) {
    	noAll = true;
    	context.register("NoAllButtonState", noAll);
    	stage.close();
    }
    
    @FXML
    void onNoButton(ActionEvent event) {
    	stage.close();
    }

    @FXML
    void onYesAllButton(ActionEvent event) {
    	yesAll = true;
    	film.applyRename(film.getCleanName());
    	context.register("YesAllButtonState", yesAll);
    	stage.close();
    }

    @FXML
    void onYesButton(ActionEvent event) {
    	film.applyRename(film.getCleanName());
    	stage.close();
    }
}
