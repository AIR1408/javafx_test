package connectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import items.AltItem;
import items.LaborNorm;
import items.Material;
import items.MtlNorm;
import items.TaskItem;

public class TableAdapter {
    private Connection conn;

    private void executeQuery(String query){
        try {
            Statement st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TableAdapter(String serverURL, String userName, String password){
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(serverURL, userName, password);
            System.out.println("Connection success!");
        } catch (Exception ex) {
            throw new RuntimeException("Error connection");
        }
    }

    public ObservableList<Material> getMaterials(){
        ObservableList<Material> mtls = FXCollections.observableArrayList();
        String query = "SELECT * FROM materials;";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) 
                mtls.add(new Material(
                    rs.getInt("mtl_id"), rs.getString("mtl_name"),
                    rs.getString("mtl_uom"), rs.getFloat("mtl_price")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mtls;
    }

    public ObservableList<MtlNorm> getMtlNorms(){
        ObservableList<MtlNorm> mtlNorms = FXCollections.observableArrayList();
        String query = "SELECT * FROM mtl_norms;";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) 
                mtlNorms.add(new MtlNorm(
                    rs.getInt("det_id"), rs.getInt("mtl_id"),
                    rs.getInt("op_id"), rs.getString("uom"), rs.getFloat("norm")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mtlNorms;
    }

    public ObservableList<LaborNorm> getLaborNorms(){
        ObservableList<LaborNorm> laborNorms = FXCollections.observableArrayList();
        String query = "SELECT * FROM labor_norms;";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) 
                laborNorms.add(new LaborNorm(
                    rs.getInt("det_id"), rs.getInt("op_id"), rs.getInt("prof_id"), rs.getInt("prof_level"),
                    rs.getInt("tarif_id"), rs.getInt("pre_post_time"), rs.getInt("time_per_piece")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return laborNorms;
    }

    public ObservableList<Material> showLessThanFunc(int price){
        ObservableList<Material> mtls = FXCollections.observableArrayList();
        String query = "SELECT * FROM SHOW_MTLS_LESS_THAN(%s);";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(String.format(query, price));
            while (rs.next()) 
                mtls.add(new Material(
                    rs.getInt("mtl_id"), rs.getString("mtl_name"),
                    rs.getString("mtl_uom"), rs.getFloat("mtl_price")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mtls;
    }

    public ObservableList<AltItem> getAlterTable(){
        ObservableList<AltItem> altTable = FXCollections.observableArrayList();
        String query = "SELECT * FROM materials RIGHT JOIN mtl_norms mn ON materials.mtl_id = mn.mtl_id " +
            "LEFT JOIN labor_norms ln ON mn.det_id = ln.det_id AND mn.op_id = ln.op_id;";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) 
                altTable.add(new AltItem(
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
                    rs.getInt("time_per_piece")
                ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return altTable;
    }

    public int countMtlsFunc(String detId){
        String query = "SELECT NEED_MTLS_COUNT(%s);";
        int count = 0;
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(String.format(query, detId));
            rs.next();
            count = rs.getInt("need_mtls_count");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public void insertRecordMtl(String id, String name, String uom, String price){
        String query = 
            "INSERT INTO materials(\"mtl_id\",\"mtl_name\", \"mtl_uom\", \"mtl_price\") " +
            "VALUES(%s, '%s', '%s', %s);";
        executeQuery(String.format(query, id, name, uom, price));
    }
    
    public void updateRecordMtl(String id, String name, String uom, String price){
        String query = 
            "UPDATE materials " +
            "SET \"mtl_name\" = '%s', \"mtl_uom\" = '%s', \"mtl_price\" = %s " + 
            "WHERE \"mtl_id\" = %s;";
        executeQuery(String.format(query, name, uom, price, id));
    }

    public void deleteRecordMtl(String id){
        String query = 
            "DELETE FROM materials " +
            "WHERE \"mtl_id\" = %s;";
        executeQuery(String.format(query, id));
    }

    public void insertRecordMtlNorms(String detId, String mtlId, String opId, String uom, String norm){
        String query = 
            "INSERT INTO mtl_norms(\"det_id\",\"mtl_id\", \"op_id\", \"uom\", \"norm\") " +
            "VALUES(%s, %s, %s, '%s', %s);";
        executeQuery(String.format(query, detId, mtlId, opId, uom, norm));
    }
    
    public void updateRecordMtlNorms(String detId, String mtlId, String opId, String uom, String norm){
        String query = 
            "UPDATE mtl_norms " +
            "SET \"uom\" = '%s', \"norm\" = %s " + 
            "WHERE \"det_id\" = %s AND \"mtl_id\" = %s AND \"op_id\" = %s;";
        executeQuery(String.format(query, uom, norm, detId, mtlId, opId));
    }

    public void deleteRecordMtlNorms(String detId, String mtlId, String opId){
        String query = 
            "DELETE FROM mtl_norms " +
            "WHERE \"det_id\" = %s AND \"mtl_id\" = %s AND \"op_id\" = %s;";
        executeQuery(String.format(query, detId, mtlId, opId));
    }

    public void insertRecordLaborNorms(String detId, String opId, String profId, 
            String profLevel, String tarifId, String prePostTime, String timePerPiece){
        String query = 
            "INSERT INTO labor_norms(\"det_id\",\"op_id\", \"prof_id\", \"prof_level\", " +
            "\"tarif_id\", \"pre_post_time\", \"time_per_piece\") " +
            "VALUES(%s, %s, %s, %s, %s, %s, %s);";
        executeQuery(String.format(query, detId, opId, profId, profLevel, tarifId, prePostTime, timePerPiece));
    }
    
    public void updateRecordLaborNorms(String detId, String opId, String profId, 
            String profLevel, String tarifId, String prePostTime, String timePerPiece){
        String query = 
            "UPDATE labor_norms " +
            "SET \"prof_id\" = %s, \"prof_level\" = %s, \"tarif_id\" = %s, \"pre_post_time\" = %s, \"time_per_piece\" = %s " + 
            "WHERE \"det_id\" = %s AND \"op_id\" = %s;";
        executeQuery(String.format(query, profId, profLevel, tarifId, prePostTime, timePerPiece, detId, opId));
    }

    public void deleteRecordLaborNorms(String detId, String opId){
        String query = 
            "DELETE FROM labor_norms " +
            "WHERE \"det_id\" = %s AND \"op_id\" = %s;";
        executeQuery(String.format(query, detId, opId));
    }

    public ObservableList<TaskItem> Task1Var1(int op_id){
        ObservableList<TaskItem> taskItems = FXCollections.observableArrayList();
        String query = "SELECT \"op_id\", \"mtl_name\", \"det_id\", \"norm\" FROM " + 
            "mtl_norms RIGHT JOIN materials ON mtl_norms.mtl_id = materials.mtl_id WHERE \"op_id\" = %s;";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(String.format(query, String.valueOf(op_id)));
            while (rs.next()) 
                taskItems.add(new TaskItem(
                    rs.getInt("op_id"), rs.getString("mtl_name"),
                    rs.getInt("det_id"), rs.getFloat("norm")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taskItems;
    }
}
