package items;

public class TaskItem {
    private int taskOpId;
    private String taskMtlName;
    private int taskDetId;
    private Float taskNorm;

    public TaskItem(int op_id, String mtl_name, int det_id, Float mtl_norm){
        setTaskOpId(op_id);
        setTaskMtlName(mtl_name);
        setTaskDetId(det_id);
        setTaskNorm(mtl_norm);
    }

    public Float getTaskNorm() {
        return taskNorm;
    }

    public void setTaskNorm(Float taskNorm) {
        this.taskNorm = taskNorm;
    }

    public int getTaskDetId() {
        return taskDetId;
    }

    public void setTaskDetId(int taskDetId) {
        this.taskDetId = taskDetId;
    }

    public String getTaskMtlName() {
        return taskMtlName;
    }

    public void setTaskMtlName(String taskMtlName) {
        this.taskMtlName = taskMtlName;
    }

    public int getTaskOpId() {
        return taskOpId;
    }

    public void setTaskOpId(int taskOpId) {
        this.taskOpId = taskOpId;
    }
}
