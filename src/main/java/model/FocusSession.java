package model;

public class FocusSession {
    private int id;
    private String taskDescription;
    private String startTime;
    private String endTime;
    private int duration;
    private int energyLevel;
    private String status;

    public FocusSession() {}
    
    public FocusSession(int id, String taskDescription, String startTime, 
                       String endTime, int duration, int energyLevel, String status) {
        this.id = id;
        this.taskDescription = taskDescription;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.energyLevel = energyLevel;
        this.status = status;
    }
    
    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTaskDescription() { return taskDescription; }
    public void setTaskDescription(String taskDescription) { this.taskDescription = taskDescription; }
    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
    public int getEnergyLevel() { return energyLevel; }
    public void setEnergyLevel(int energyLevel) { this.energyLevel = energyLevel; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
