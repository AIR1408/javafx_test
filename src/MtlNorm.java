public class MtlNorm {
    private int detailId;
    private int materialId;
    private int operationId;
    private String normUom;
    private float norm;

    public MtlNorm(int detailId, int materialId, int operationId, String normUom, float norm){
        this.detailId = detailId;
        this.materialId = materialId;
        this.operationId = operationId;
        this.normUom = normUom;
        this.norm = norm;
    }

    public int getDetailId() {
        return detailId;
    }   
    public float getNorm() {
        return norm;
    }
    public void setNorm(float norm) {
        this.norm = norm;
    }
    public String getNormUom() {
        return normUom;
    }
    public void setNormUom(String normUom) {
        this.normUom = normUom;
    }
    public int getOperationId() {
        return operationId;
    }
    public void setOperationId(int operationId) {
        this.operationId = operationId;
    }
    public int getMaterialId() {
        return materialId;
    }
    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }
    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }
}