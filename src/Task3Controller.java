import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

public class Task3Controller implements Initializable{
    @FXML TableView<LaborNorm> tvTask3;

    @FXML private TableColumn<LaborNorm, Integer> detId;
    @FXML private TableColumn<LaborNorm, Integer> opId;
    @FXML private TableColumn<LaborNorm, Integer> profId;
    @FXML private TableColumn<LaborNorm, Integer> profLevel;
    @FXML private TableColumn<LaborNorm, Integer> tarifId;
    @FXML private TableColumn<LaborNorm, Integer> prePostTime;
    @FXML private TableColumn<LaborNorm, Integer> timePerPiece;

    @FXML ChoiceBox<Integer> cbVar3;
    @FXML Button btnShowTask3;
    @FXML TextField tfPrice, tfNorm;

    private ObservableList<Material> materials;
    private ObservableList<MtlNorm> mtlNorms;
    private ObservableList<LaborNorm> laborNorms;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

        
        Integer[] vars = {0, 1};
        cbVar3.getItems().addAll(vars);
        cbVar3.setValue(0);
        showTable("100", "20");
    }

    @FXML
    private void handleButtonAction(ActionEvent event){
        if (event.getSource() == btnShowTask3)
            showTable(tfPrice.getText(), tfNorm.getText());
    }

    public void setMtls(ObservableList<Material> m){
        materials = m;
    }

    public void setMtlNorms(ObservableList<MtlNorm> m){
        mtlNorms = m;
    }

    public void setLaborNorms(ObservableList<LaborNorm> m){
        laborNorms = m;
    }

    private void showTable(String price, String norm){
        ObservableList<LaborNorm> res = FXCollections.observableArrayList();
        String query = "SELECT * FROM labor_norms ln WHERE NOT EXISTS( " +
            "SELECT * FROM materials m WHERE m.mtl_price > %s AND NOT EXISTS( " +
                "SELECT * FROM mtl_norms mn WHERE m.mtl_id = mn.mtl_id AND ln.op_id = mn.op_id " +
                    "AND ln.det_id = mn.det_id AND mn.norm > %s));";

        if (cbVar3.getValue() == 0){
            try {
                Class.forName("org.postgresql.Driver");
                Connection conn = DriverManager.getConnection(serverURL, userName, password);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(String.format(query, price, norm));
                while (rs.next()) 
                    res.add(new LaborNorm(
                        rs.getInt("det_id"), rs.getInt("op_id"), rs.getInt("prof_id"), rs.getInt("prof_level"),
                        rs.getInt("tarif_id"), rs.getInt("pre_post_time"), rs.getInt("time_per_piece")));
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (cbVar3.getValue() == 1){
            boolean b1, b2;
            int iprice = Integer.valueOf(price);
            int inorm = Integer.valueOf(norm);
            for (var ln : laborNorms){
                b1 = false;
                for (var m : materials){
                    if (m.getMtlPrice() <= iprice)
                        continue;
                    b2 = false;
                    for (var mn : mtlNorms)
                        if (m.getMtlId() == mn.getMaterialId() &&
                            ln.getOpId() == mn.getOperationId() &&
                            ln.getDetId() == mn.getDetailId() &&
                            mn.getNorm() > inorm){
                                b2 = true;
                                break;
                            }
                    if (!b2) {
                        b1 = true;
                        break;
                    }
                }
                if (!b1)
                    res.add(ln);
            }
        }
        tvTask3.setItems(res);
    }

    private static final String serverURL = "jdbc:postgresql://localhost:5432/tutorialdb";
    private static final String userName = "postgres";
    private static final String password = "000pppsql";
}
