package items;

public class Material {
    private int mtlId;
    private String mtlName;
    private String mtlUom;
    private float mtlPrice;

    public Material(int mtlId, String mtlName, String mtlUom, float mtlPrice) {
        this.mtlId = mtlId;
        this.mtlName = mtlName;
        this.mtlUom = mtlUom;
        this.mtlPrice = mtlPrice;
    }

    public int getMtlId() {
        return mtlId;
    }

    public void setMtlId(int mtlId) {
        this.mtlId = mtlId;
    }

    public String getMtlName() {
        return mtlName;
    }

    public void setMtlName(String mtlName) {
        this.mtlName = mtlName;
    }

    public String getMtlUom() {
        return mtlUom;
    }

    public void setMtlUom(String mtlUom) {
        this.mtlUom = mtlUom;
    }

    public float getMtlPrice() {
        return mtlPrice;
    }

    public void setMtlPrice(float mtlPrice) {
        this.mtlPrice = mtlPrice;
    }
}