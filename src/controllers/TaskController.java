package controllers;

import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import connectors.*;
import items.Material;
import items.MtlNorm;
import items.TaskItem;

public class TaskController implements Initializable {
    private int variant;
    private TableAdapter tAdapter;
    private ObservableList<Material> materials;
    private ObservableList<MtlNorm> mtlNorms;

    @FXML private TableView<TaskItem> tvTask1;

    @FXML private TableColumn<TaskItem, Integer> taskOpId;
    @FXML private TableColumn<TaskItem, String> taskMtlName;
    @FXML private TableColumn<TaskItem, Integer> taskDetId;
    @FXML private TableColumn<TaskItem, Float> taskNorm;

    @FXML private TextField tfTask1;
    @FXML private Button btnTask1;

    @FXML
    private void handleButtonAction(ActionEvent event){
        if (event.getSource() == btnTask1)
            tvTask1.setItems(solveTask1(Integer.valueOf(tfTask1.getText()), variant));
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        variant = 1;
        taskOpId.setCellValueFactory(new PropertyValueFactory<TaskItem, Integer>("taskOpId"));
        taskMtlName.setCellValueFactory(new PropertyValueFactory<TaskItem, String>("taskMtlName"));
        taskDetId.setCellValueFactory(new PropertyValueFactory<TaskItem, Integer>("taskDetId"));
        taskNorm.setCellValueFactory(new PropertyValueFactory<TaskItem, Float>("taskNorm"));

    }

    public void setVariant(int v){
        this.variant = v;
    }

    public void setTableAdapter(TableAdapter ta){
        tAdapter = ta;
    }

    public void setMtls(ObservableList<Material> m){
        materials = m;
    }

    public void setMtlNorms(ObservableList<MtlNorm> m){
        mtlNorms = m;
    }

    private ObservableList<TaskItem> solveTask1(int op_id, int variant){
        ObservableList<TaskItem> norms = FXCollections.observableArrayList();
        
        if (variant == 1){
            norms = tAdapter.Task1Var1(op_id);
        } 
        else if (variant == 2){
            for (var mtl_norm : mtlNorms)
                if (mtl_norm.getOperationId() == op_id)
                    for (var mtl : materials)
                        if (mtl.getMtlId() == mtl_norm.getMaterialId()){
                            norms.add(new TaskItem(mtl_norm.getOperationId(), 
                                mtl.getMtlName(), mtl_norm.getDetailId(), mtl_norm.getNorm()));
                            break;
                        }
        }
        else if (variant == 3){
            var filtered_norms = mtlNorms.stream().filter(item -> item.getOperationId() == op_id).collect(Collectors.toList());
            var mtl_ids = filtered_norms.stream().map(MtlNorm::getMaterialId).collect(Collectors.toList());
            var filtered_mtls = materials.stream().filter(item -> mtl_ids.contains(item.getMtlId())).collect(Collectors.toList());
            var mtl_names = filtered_mtls.stream().map(Material::getMtlName).collect(Collectors.toList());

            for (int i = 0; i < filtered_norms.size(); i++){
                norms.add(new TaskItem(
                    filtered_norms.get(i).getOperationId(),
                    mtl_names.get(i),
                    filtered_norms.get(i).getDetailId(),
                    filtered_norms.get(i).getNorm()
                ));
            }
        }
        else if (variant == 4){
            var altTable = tAdapter.getAlterTable();

            for (var item : altTable){
                if (item.getAltOpId() == op_id)
                norms.add(new TaskItem(
                    item.getAltOpId(),
                    item.getAltMtlName(),
                    item.getAltDetId(),
                    item.getAltNorm()
                ));
            }
        }
        else if (variant == 5){

            String query = "SELECT \"op_id\", \"mtl_name\", \"det_id\", \"norm\" " + 
            "FROM materials RIGHT JOIN mtl_norms mn ON materials.mtl_id = mn.mtl_id;";
            try {
                Class.forName("org.postgresql.Driver");
                Connection conn = DriverManager.getConnection(serverURL, userName, password);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(String.format(query, String.valueOf(op_id)));
                while (rs.next()) 
                    norms.add(new TaskItem(
                        rs.getInt("op_id"), rs.getString("mtl_name"),
                        rs.getInt("det_id"), rs.getFloat("norm")));
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return norms;
    }
    private static final String serverURL = "jdbc:postgresql://localhost:5432/tutorialdb";
    private static final String userName = "postgres";
    private static final String password = "000pppsql";
}
