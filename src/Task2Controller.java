import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Task2Controller implements Initializable{
    @FXML TableView<Task2Item> tvTask2;
    @FXML TableColumn<Task2Item, Integer> colDetId;
    @FXML TableColumn<Task2Item, Float> colTotalCost;

    @FXML ChoiceBox<Integer> cbVar2;
    @FXML Button btnShowTask2;

    private ObservableList<Material> materials;
    private ObservableList<MtlNorm> mtlNorms;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colDetId.setCellValueFactory(new PropertyValueFactory<Task2Item, Integer>("detId"));
        colTotalCost.setCellValueFactory(new PropertyValueFactory<Task2Item, Float>("totalCost"));
        Integer[] vars = {0, 1};
        cbVar2.getItems().addAll(vars);
        cbVar2.setValue(0);
        showTable();
    }

    @FXML
    private void handleButtonAction(ActionEvent event){
        if (event.getSource() == btnShowTask2)
            showTable();
    }

    public void setMtls(ObservableList<Material> m){
        materials = m;
    }

    public void setMtlNorms(ObservableList<MtlNorm> m){
        mtlNorms = m;
    }

    private void showTable(){
        ObservableList<Task2Item> res = FXCollections.observableArrayList();
        String query = "SELECT a.det_id, sum(a.norm * b.mtl_price)" + 
            " FROM mtl_norms a LEFT JOIN materials b ON a.mtl_id = b.mtl_id GROUP BY det_id;";

        if (cbVar2.getValue() == 0){
            try {
                Class.forName("org.postgresql.Driver");
                Connection conn = DriverManager.getConnection(serverURL, userName, password);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(String.format(query));
                while (rs.next()) 
                    res.add(new Task2Item(rs.getInt("det_id"), rs.getFloat("sum")));
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (cbVar2.getValue() == 1){
            Map<Integer, List<MtlNorm>> map = mtlNorms.stream()
                .collect(Collectors.groupingBy(MtlNorm::getDetailId));

            for (var entry : map.entrySet()){
                float sum = 0;
                for (var record : entry.getValue())
                    for (var mtl : materials)
                        if (mtl.getMtlId() == record.getMaterialId())
                            sum += record.getNorm() * mtl.getMtlPrice();
                res.add(new Task2Item(entry.getKey(), sum));
            }
        }
        tvTask2.setItems(res);
    }

    private static final String serverURL = "jdbc:postgresql://localhost:5432/tutorialdb";
    private static final String userName = "postgres";
    private static final String password = "000pppsql";
}
