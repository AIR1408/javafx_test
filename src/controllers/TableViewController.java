package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

import items.AltItem;

public class TableViewController implements Initializable{
    @FXML TableView<AltItem> tvView;

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

        showTable();
    }

    private void showTable(){
        ObservableList<AltItem> res = FXCollections.observableArrayList();
        String query = "SELECT * FROM union_table;";
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(serverURL, userName, password);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(String.format(query));
            while (rs.next()) 
                res.add(new AltItem(
                    rs.getInt("mtl_id"),
                    rs.getString("mtl_name"),
                    rs.getString("mtl_uom"),
                    rs.getFloat("mtl_price"),
                    rs.getInt("det_id"),
                    rs.getInt("op_id"),
                    rs.getFloat("norm"),
                    rs.getInt("prof_id"),
                    rs.getInt("prof_level"),
                    rs.getInt("tarif_id"),
                    rs.getInt("pre_post_time"),
                    rs.getInt("time_per_piece")));
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        tvView.setItems(res);
    }

    private static final String serverURL = "jdbc:postgresql://localhost:5432/tutorialdb";
    private static final String userName = "postgres";
    private static final String password = "000pppsql";
}
