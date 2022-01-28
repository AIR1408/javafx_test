package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

import items.AltItem;
import connectors.*;

public class AlterController implements Initializable {
    @FXML private TableView<AltItem> tvAlter;

    @FXML private TableColumn<AltItem, Integer> altMtlId;
    @FXML private TableColumn<AltItem, String> altMtlName;
    @FXML private TableColumn<AltItem, String> altUom;
    @FXML private TableColumn<AltItem, Float> altPrice;
    @FXML private TableColumn<AltItem, Integer> altDetId;
    @FXML private TableColumn<AltItem, Integer> altOpId;
    @FXML private TableColumn<AltItem, Float> altNorm;
    @FXML private TableColumn<AltItem, Integer> altProfId;
    @FXML private TableColumn<AltItem, Integer> altLevel;
    @FXML private TableColumn<AltItem, Integer> altTarifId;
    @FXML private TableColumn<AltItem, Integer> altPrePostTime;
    @FXML private TableColumn<AltItem, Integer> altTimePerPiece;

    @FXML private Button btnSelectMtl, btnSelectNorm, btnSelectLaborCost;

    private static final String url = "jdbc:postgresql://localhost:5432/tutorialdb";
    private static final String user = "postgres";
    private static final String pass = "000pppsql";
    
    private TableAdapter tAdapter = new TableAdapter(url, user, pass);
    private Controller mController;

    @FXML
    private void handleButtonAction(ActionEvent event){
        AltItem aItem = tvAlter.getSelectionModel().getSelectedItem();
        if (event.getSource() == btnSelectMtl)
            mController.selectMtl(aItem.getAltMtlId());
        else if (event.getSource() == btnSelectNorm)
            mController.selectNorm(aItem.getAltDetId(), aItem.getAltMtlId(), aItem.getAltOpId());
        else if (event.getSource() == btnSelectLaborCost)
            mController.selectLaborNorms(aItem.getAltDetId(), aItem.getAltOpId());
        
        
    }

    public void setMainController(Controller mc){
        mController = mc;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        altMtlId.setCellValueFactory(new PropertyValueFactory<AltItem, Integer>("altMtlId"));
        altMtlName.setCellValueFactory(new PropertyValueFactory<AltItem, String>("altMtlName"));
        altUom.setCellValueFactory(new PropertyValueFactory<AltItem, String>("altUom"));
        altPrice.setCellValueFactory(new PropertyValueFactory<AltItem, Float>("altPrice"));

        altMtlId.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter())); 
        altMtlName.setCellFactory(TextFieldTableCell.forTableColumn()); 
        altUom.setCellFactory(ComboBoxTableCell.forTableColumn("кг", "шт")); 
        altPrice.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter())); 
        
        altDetId.setCellValueFactory(new PropertyValueFactory<AltItem, Integer>("altDetId"));
        altOpId.setCellValueFactory(new PropertyValueFactory<AltItem, Integer>("altOpId"));
        altNorm.setCellValueFactory(new PropertyValueFactory<AltItem, Float>("altNorm"));

        altDetId.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter())); 
        altOpId.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter())); 
        altNorm.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter())); 

        altProfId.setCellValueFactory(new PropertyValueFactory<AltItem, Integer>("altProfId"));
        altLevel.setCellValueFactory(new PropertyValueFactory<AltItem, Integer>("altLevel"));
        altTarifId.setCellValueFactory(new PropertyValueFactory<AltItem, Integer>("altTarifId"));
        altPrePostTime.setCellValueFactory(new PropertyValueFactory<AltItem, Integer>("altPrePostTime"));
        altTimePerPiece.setCellValueFactory(new PropertyValueFactory<AltItem, Integer>("altTimePerPiece"));

        altProfId.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter())); 
        altLevel.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter())); 
        altTarifId.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter())); 
        altPrePostTime.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter())); 
        altTimePerPiece.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter())); 

        
        tvAlter.setEditable(true);
        showAlter();
    }

    private void showAlter(){
        tvAlter.setItems(FXCollections.observableArrayList(tAdapter.getAlterTable()));
    }   
}