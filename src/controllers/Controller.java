package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

import items.Material;
import items.MtlNorm;
import items.LaborNorm;
import connectors.*;

public class Controller implements Initializable {
    @FXML private TableView<Material> tvMtls;
    @FXML private TableView<MtlNorm> tvMtlNorms;
    @FXML private TableView<LaborNorm> tvLaborNorms;

    @FXML private TableColumn<Material, Integer> mtlId;
    @FXML private TableColumn<Material, String> mtlName;
    @FXML private TableColumn<Material, String> mtlUom;
    @FXML private TableColumn<Material, Float> mtlPrice;

    @FXML private TableColumn<MtlNorm, Integer> detailId;
    @FXML private TableColumn<MtlNorm, Integer> materialId;
    @FXML private TableColumn<MtlNorm, Integer> operationId;
    @FXML private TableColumn<MtlNorm, String> normUom;
    @FXML private TableColumn<MtlNorm, Float> norm;

    @FXML private TableColumn<LaborNorm, Integer> detId;
    @FXML private TableColumn<LaborNorm, Integer> opId;
    @FXML private TableColumn<LaborNorm, Integer> profId;
    @FXML private TableColumn<LaborNorm, Integer> profLevel;
    @FXML private TableColumn<LaborNorm, Integer> tarifId;
    @FXML private TableColumn<LaborNorm, Integer> prePostTime;
    @FXML private TableColumn<LaborNorm, Integer> timePerPiece;

    @FXML private TextField 
        tfMtlId, tfMtlName, tfMtlUom, tfMtlPrice,
        tfDetailId, tfMaterialId, tfOperationId, tfNormUom, tfNorm,
        tfDetId, tfOpId, tfProfId, tfProfLevel, tfTarifId, tfPrePostTime, tfTimePerPiece,
        tfShowLessThan, tfCountMtls;
    
    @FXML private Button 
        btnInsertMtl, btnDeleteMtl, btnUpdateMtl, 
        btnInsertMtlNorm, btnUpdateMtlNorm, btnDeleteMtlNorm,
        btnInsertLaborNorm, btnUpdateLaborNorm, btnDeleteLaborNorm,
        btnShowLessThan, btnResetMtls, btnCountMtls,
        btnAltForm, btnMtlsFirst, btnMtlsLast, btnTask1, btnTask2, btnTask3, btnTableView;

    @FXML ChoiceBox<Integer> cbVariant;

    @FXML private Label labelCountMtls;

    private static final String url = "jdbc:postgresql://localhost:5432/tutorialdb";
    private static final String user = "postgres";
    private static final String pass = "000pppsql";
    
    private TableAdapter tAdapter = new TableAdapter(url, user, pass);

    @FXML
    private void handleButtonAction(ActionEvent event){
        if (event.getSource() == btnInsertMtl)
            tAdapter.insertRecordMtl(tfMtlId.getText(), tfMtlName.getText(),
                tfMtlUom.getText(), tfMtlPrice.getText());
        else if (event.getSource() == btnUpdateMtl)
            tAdapter.updateRecordMtl(tfMtlId.getText(), tfMtlName.getText(),
                tfMtlUom.getText(), tfMtlPrice.getText());
        else if (event.getSource() == btnDeleteMtl)
            tAdapter.deleteRecordMtl(tfMtlId.getText());
        else if (event.getSource() == btnInsertMtlNorm)
            tAdapter.insertRecordMtlNorms(tfDetailId.getText(), tfMaterialId.getText(),
                tfOperationId.getText(), tfNormUom.getText(), tfNorm.getText());
        else if (event.getSource() == btnUpdateMtlNorm)
            tAdapter.updateRecordMtlNorms(tfDetailId.getText(), tfMaterialId.getText(),
                tfOperationId.getText(), tfNormUom.getText(), tfNorm.getText());
        else if (event.getSource() == btnDeleteMtlNorm)
            tAdapter.deleteRecordMtlNorms(tfDetailId.getText(), tfMaterialId.getText(),
                tfOperationId.getText());
        else if (event.getSource() == btnInsertLaborNorm)
            tAdapter.insertRecordLaborNorms(tfDetId.getText(), tfOpId.getText(), tfProfId.getText(),
                tfProfLevel.getText(), tfTarifId.getText(), tfPrePostTime.getText(), tfTimePerPiece.getText());
        else if (event.getSource() == btnUpdateLaborNorm)
        tAdapter.updateRecordLaborNorms(tfDetId.getText(), tfOpId.getText(), tfProfId.getText(),
        tfProfLevel.getText(), tfTarifId.getText(), tfPrePostTime.getText(), tfTimePerPiece.getText());
        else if (event.getSource() == btnDeleteLaborNorm)
            tAdapter.deleteRecordLaborNorms(tfDetId.getText(), tfOpId.getText());
        else if (event.getSource() == btnCountMtls)
        labelCountMtls.setText(String.valueOf(tAdapter.countMtlsFunc(tfCountMtls.getText())));
        else if (event.getSource() == btnAltForm){
            Parent root;
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/alter.fxml"));
                root = loader.load();

                AlterController aController = loader.getController();
                aController.setMainController(this);
                
                Stage stage = new Stage();
                stage.setTitle("Альтернативная форма");
                stage.setScene(new Scene(root, 1100, 350));
                stage.show();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        
        showMaterials();
        showMtlNorms();
        showLaborNorms();
        if (event.getSource() == btnShowLessThan)
            tvMtls.setItems(tAdapter.showLessThanFunc(Integer.parseInt(tfShowLessThan.getText())));
        else if (event.getSource() == btnTask1){
            Parent root;
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/task.fxml"));
                root = loader.load();

                TaskController tController = loader.getController();
                tController.setVariant(cbVariant.getValue());
                tController.setTableAdapter(tAdapter);
                tController.setMtls(tvMtls.getItems());
                tController.setMtlNorms(tvMtlNorms.getItems());

                Stage stage = new Stage();
                stage.setTitle("Задача 1");
                stage.setScene(new Scene(root, 600, 400));
                stage.show();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        else if (event.getSource() == btnTask2){
            Parent root;
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/task2.fxml"));
                root = loader.load();

                Task2Controller tController = loader.getController();
                tController.setMtls(tvMtls.getItems());
                tController.setMtlNorms(tvMtlNorms.getItems());

                Stage stage = new Stage();
                stage.setTitle("Задача 2");
                stage.setScene(new Scene(root, 600, 400));
                stage.show();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        else if (event.getSource() == btnTask3){
            Parent root;
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/task3.fxml"));
                root = loader.load();

                Task3Controller tController = loader.getController();
                tController.setMtls(tvMtls.getItems());
                tController.setMtlNorms(tvMtlNorms.getItems());
                tController.setLaborNorms(tvLaborNorms.getItems());

                Stage stage = new Stage();
                stage.setTitle("Задача 3");
                stage.setScene(new Scene(root, 600, 270));
                stage.show();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        else if (event.getSource() == btnTableView){
            Parent root;
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tableView.fxml"));
                root = loader.load();

                Stage stage = new Stage();
                stage.setTitle("Табличное представление");
                stage.setScene(new Scene(root, 1110, 400));
                stage.show();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        else if (event.getSource() == btnMtlsFirst){
            tvMtls.requestFocus();
            tvMtls.getSelectionModel().selectFirst();
            tvMtls.scrollTo(0);
        }                      
        else if (event.getSource() == btnMtlsLast){
            tvMtls.requestFocus();
            tvMtls.getSelectionModel().selectLast();
            tvMtls.scrollTo(tvMtls.getItems().size() - 1);
        }                      
    }

    @FXML
    private void handleMouseAction(MouseEvent event){
        if (event.getSource() == tvMtls){
            Material mtl = tvMtls.getSelectionModel().getSelectedItem();
            if (mtl != null){
                tfMtlId.setText(String.valueOf(mtl.getMtlId()));
                tfMtlName.setText(mtl.getMtlName());
                tfMtlUom.setText(mtl.getMtlUom());
                tfMtlPrice.setText(String.valueOf(mtl.getMtlPrice()));
            }
        }
        else if (event.getSource() == tvMtlNorms){
            MtlNorm mtlNorm = tvMtlNorms.getSelectionModel().getSelectedItem();
            if (mtlNorm != null){
                tfDetailId.setText(String.valueOf(mtlNorm.getDetailId()));
                tfMaterialId.setText(String.valueOf(mtlNorm.getMaterialId()));
                tfOperationId.setText(String.valueOf(mtlNorm.getOperationId()));
                tfNormUom.setText(mtlNorm.getNormUom());
                tfNorm.setText(String.valueOf(mtlNorm.getNorm()));
            }
        }
        else if (event.getSource() == tvLaborNorms){
            LaborNorm laborNorm = tvLaborNorms.getSelectionModel().getSelectedItem();
            if (laborNorm != null){
                tfDetId.setText(String.valueOf(laborNorm.getDetId()));
                tfOpId.setText(String.valueOf(laborNorm.getOpId()));
                tfProfId.setText(String.valueOf(laborNorm.getProfId()));
                tfProfLevel.setText(String.valueOf(laborNorm.getProfLevel()));
                tfTarifId.setText(String.valueOf(laborNorm.getTarifId()));
                tfPrePostTime.setText(String.valueOf(laborNorm.getPrePostTime()));
                tfTimePerPiece.setText(String.valueOf(laborNorm.getTimePerPiece()));
            }
        }
    }

    @FXML
    private void handleKeyboardAction(TableColumn.CellEditEvent<?,?> event){
        Material mtl = tvMtls.getSelectionModel().getSelectedItem();
        MtlNorm mtlNorm = tvMtlNorms.getSelectionModel().getSelectedItem();
        LaborNorm laborNorm = tvLaborNorms.getSelectionModel().getSelectedItem();
        String newValue = String.valueOf( event.getNewValue());
        if (event.getSource() == mtlId)
            mtl.setMtlId(Integer.parseInt(newValue));
        else if (event.getSource() == mtlName)
            mtl.setMtlName(newValue);
        else if (event.getSource() == mtlUom)
            mtl.setMtlUom(newValue);
        else if (event.getSource() == mtlPrice)
            mtl.setMtlPrice(Float.parseFloat(newValue));
        else if (event.getSource() == detailId)
            mtlNorm.setDetailId(Integer.parseInt(newValue));
        else if (event.getSource() == materialId)
            mtlNorm.setMaterialId(Integer.parseInt(newValue));
        else if (event.getSource() == operationId)
            mtlNorm.setOperationId(Integer.parseInt(newValue));
        else if (event.getSource() == normUom)
            mtlNorm.setNormUom(newValue);
        else if (event.getSource() == norm)
            mtlNorm.setNorm(Integer.parseInt(newValue));
        else if (event.getSource() == detId)
            laborNorm.setDetId(Integer.parseInt(newValue));
        else if (event.getSource() == mtlId)
            laborNorm.setOpId(Integer.parseInt(newValue));
        else if (event.getSource() == profId)
            laborNorm.setProfId(Integer.parseInt(newValue));
        else if (event.getSource() == profLevel)
            laborNorm.setProfLevel(Integer.parseInt(newValue));
        else if (event.getSource() == tarifId)
            laborNorm.setTarifId(Integer.parseInt(newValue));
        else if (event.getSource() == prePostTime)
            laborNorm.setPrePostTime(Integer.parseInt(newValue));
        else if (event.getSource() == timePerPiece)
            laborNorm.setTimePerPiece(Integer.parseInt(newValue));
            
        if (mtl != null)
            tAdapter.updateRecordMtl(String.valueOf(mtl.getMtlId()), mtl.getMtlName(),
                mtl.getMtlUom(), String.valueOf(mtl.getMtlPrice()));
        else if (mtlNorm != null)
            tAdapter.updateRecordMtlNorms(String.valueOf(mtlNorm.getDetailId()), String.valueOf(mtlNorm.getMaterialId()),
                String.valueOf(mtlNorm.getOperationId()), mtlNorm.getNormUom(), String.valueOf(mtlNorm.getNorm()));
        else if (laborNorm != null)
            tAdapter.updateRecordLaborNorms(String.valueOf(laborNorm.getDetId()), String.valueOf(laborNorm.getOpId()),
                String.valueOf(laborNorm.getProfId()), String.valueOf(laborNorm.getProfLevel()), String.valueOf(laborNorm.getTarifId()), 
                String.valueOf(laborNorm.getPrePostTime()), String.valueOf(laborNorm.getTimePerPiece()));
        showMaterials();
        showMtlNorms();
        showLaborNorms();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mtlId.setCellValueFactory(new PropertyValueFactory<Material, Integer>("mtlId"));
        mtlName.setCellValueFactory(new PropertyValueFactory<Material, String>("mtlName"));
        mtlUom.setCellValueFactory(new PropertyValueFactory<Material, String>("mtlUom"));
        mtlPrice.setCellValueFactory(new PropertyValueFactory<Material, Float>("mtlPrice"));

        mtlId.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter())); 
        mtlName.setCellFactory(TextFieldTableCell.forTableColumn()); 
        mtlUom.setCellFactory(ComboBoxTableCell.forTableColumn("кг", "шт")); 
        //mtlUom.setCellFactory(TextFieldTableCell.forTableColumn()); 
        mtlPrice.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter())); 
        
        detailId.setCellValueFactory(new PropertyValueFactory<MtlNorm, Integer>("detailId"));
        materialId.setCellValueFactory(new PropertyValueFactory<MtlNorm, Integer>("materialId"));
        operationId.setCellValueFactory(new PropertyValueFactory<MtlNorm, Integer>("operationId"));
        normUom.setCellValueFactory(new PropertyValueFactory<MtlNorm, String>("normUom"));
        norm.setCellValueFactory(new PropertyValueFactory<MtlNorm, Float>("norm"));

        detailId.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter())); 
        materialId.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter())); 
        operationId.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter())); 
        normUom.setCellFactory(ComboBoxTableCell.forTableColumn("кг", "шт")); 
        //normUom.setCellFactory(TextFieldTableCell.forTableColumn()); 
        norm.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter())); 


        detId.setCellValueFactory(new PropertyValueFactory<LaborNorm, Integer>("detId"));
        opId.setCellValueFactory(new PropertyValueFactory<LaborNorm, Integer>("opId"));
        profId.setCellValueFactory(new PropertyValueFactory<LaborNorm, Integer>("profId"));
        profLevel.setCellValueFactory(new PropertyValueFactory<LaborNorm, Integer>("profLevel"));
        tarifId.setCellValueFactory(new PropertyValueFactory<LaborNorm, Integer>("tarifId"));
        prePostTime.setCellValueFactory(new PropertyValueFactory<LaborNorm, Integer>("prePostTime"));
        timePerPiece.setCellValueFactory(new PropertyValueFactory<LaborNorm, Integer>("timePerPiece"));

        detId.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter())); 
        opId.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter())); 
        profId.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter())); 
        profLevel.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter())); 
        tarifId.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter())); 
        prePostTime.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter())); 
        timePerPiece.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter())); 

        
        tvMtls.setEditable(true);     
        tvMtlNorms.setEditable(true);
        tvLaborNorms.setEditable(true);
        showMaterials();
        showMtlNorms();
        showLaborNorms();
        Integer[] vars = {1, 2, 3, 4, 5};
        cbVariant.getItems().addAll(vars);
        cbVariant.setValue(1);
    }

    public void selectMtl(int id){
        tvMtls.requestFocus();
        int index = -1;
        ObservableList<Material> list = tvMtls.getItems();
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).getMtlId() == id){
                index = i;
                break;
            }

        tvMtls.getSelectionModel().select(index);
        tvMtls.scrollTo(index);
    }

    public void selectNorm(int det_id, int mtl_id, int op_id){
        tvMtlNorms.requestFocus();
        int index = -1;
        ObservableList<MtlNorm> list = tvMtlNorms.getItems();
        for (int i = 0; i < list.size(); i++){
            var item = list.get(i);
            if (item.getDetailId()==det_id&&item.getMaterialId()==mtl_id&&item.getOperationId()==op_id){
                index = i;
                break;
            }
        }
        tvMtlNorms.getSelectionModel().select(index);
        tvMtlNorms.scrollTo(index);
    }
    
    public void selectLaborNorms(int det_id, int op_id){
        tvLaborNorms.requestFocus();
        int index = -1;
        ObservableList<LaborNorm> list = tvLaborNorms.getItems();
        for (int i = 0; i < list.size(); i++){
            var item = list.get(i);
            if (item.getDetId() == det_id && item.getOpId() == op_id){
                index = i;
                break;
            }
        }
        tvLaborNorms.getSelectionModel().select(index);
        tvLaborNorms.scrollTo(index);
    }
    
    private void showMaterials(){
        tvMtls.setItems(tAdapter.getMaterials());
    }
    private void showMtlNorms() {
        tvMtlNorms.setItems(tAdapter.getMtlNorms());
    }
    private void showLaborNorms() {
        tvLaborNorms.setItems(tAdapter.getLaborNorms());
    }
}