package items;

public class AltItem{
    private int altMtlId;
    private String altMtlName;
    private String altUom;
    private float altPrice;
    private int altDetId;
    private int altOpId;
    private float altNorm;
    private int altProfId;
    private int altLevel;
    private int altTarifId;
    private int altPrePostTime;
    private int altTimePerPiece;

    public AltItem(int a, String b, String c, float d, int e, int f, float g, int h, int i, int j, int k, int l){
        altMtlId = a;
        altMtlName = b;
        altUom = c;
        altPrice = d;
        altDetId = e;
        altOpId = f;
        altNorm = g;
        altProfId = h;
        altLevel = i;
        altTarifId = j;
        altPrePostTime = k;
        altTimePerPiece = l;
    }

    public int getAltTimePerPiece() {
        return altTimePerPiece;
    }
    public String getAltMtlName() {
        return altMtlName;
    }
    public void setAltMtlName(String altMtlName) {
        this.altMtlName = altMtlName;
    }
    public int getAltMtlId() {
        return altMtlId;
    }
    public void setAltMtlId(int altMtlId) {
        this.altMtlId = altMtlId;
    }
    public String getAltUom() {
        return altUom;
    }
    public void setAltUom(String altUom) {
        this.altUom = altUom;
    }
    public float getAltPrice() {
        return altPrice;
    }
    public void setAltPrice(float altPrice) {
        this.altPrice = altPrice;
    }
    public int getAltDetId() {
        return altDetId;
    }
    public void setAltDetId(int altDetId) {
        this.altDetId = altDetId;
    }
    public int getAltOpId() {
        return altOpId;
    }
    public void setAltOpId(int altOpId) {
        this.altOpId = altOpId;
    }
    public float getAltNorm() {
        return altNorm;
    }
    public void setAltNorm(float altNorm) {
        this.altNorm = altNorm;
    }
    public int getAltProfId() {
        return altProfId;
    }
    public void setAltProfId(int altProfId) {
        this.altProfId = altProfId;
    }
    public int getAltLevel() {
        return altLevel;
    }
    public void setAltLevel(int altLevel) {
        this.altLevel = altLevel;
    }
    public int getAltTarifId() {
        return altTarifId;
    }
    public void setAltTarifId(int altTarifId) {
        this.altTarifId = altTarifId;
    }
    public int getAltPrePostTime() {
        return altPrePostTime;
    }
    public void setAltPrePostTime(int altPrePostTime) {
        this.altPrePostTime = altPrePostTime;
    }
    public void setAltTimePerPiece(int altTimePerPiece) {
        this.altTimePerPiece = altTimePerPiece;
    }
}