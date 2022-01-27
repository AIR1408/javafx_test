public class Task2Item {
    private int detId;
    private float totalCost;

    public Task2Item(int d, float t){
        detId = d;
        totalCost = t;
    }

    public int getDetId() {
        return detId;
    }
    public float getTotalCost() {
        return totalCost;
    }
    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }
    public void setDetId(int detId) {
        this.detId = detId;
    }
}
