package items;

public class LaborNorm {
    private int detId;    
    private int opId;
    private int profId;    
    private int profLevel;    
    private int tarifId;    
    private int prePostTime;    
    private int timePerPiece;

    public LaborNorm(int detId, int opId, int profId, int profLevel, int tarifId, 
            int prePostTime, int timePerPiece){
        this.detId = detId;
        this.opId = opId;
        this.profId = profId;
        this.profLevel = profLevel;
        this.tarifId = tarifId;
        this.prePostTime = prePostTime;
        this.timePerPiece = timePerPiece;
    }
    
    public int getDetId() {
        return detId;
    }
    public int getTimePerPiece() {
        return timePerPiece;
    }
    public void setTimePerPiece(int timePerPiece) {
        this.timePerPiece = timePerPiece;
    }
    public int getProfLevel() {
        return profLevel;
    }
    public void setProfLevel(int profLevel) {
        this.profLevel = profLevel;
    }
    public int getOpId() {
        return opId;
    }
    public void setOpId(int opId) {
        this.opId = opId;
    }
    public int getPrePostTime() {
        return prePostTime;
    }
    public void setPrePostTime(int prePostTime) {
        this.prePostTime = prePostTime;
    }
    public int getTarifId() {
        return tarifId;
    }
    public void setTarifId(int tarifId) {
        this.tarifId = tarifId;
    }
    public int getProfId() {
        return profId;
    }
    public void setProfId(int profId) {
        this.profId = profId;
    }
    public void setDetId(int detId) {
        this.detId = detId;
    }
}
