package dto;

import java.sql.Date;

public class TaskDTO {
    private int id;
    private String taskName;
    private String groupWorkName;
    private String userName;
    private Date startDate;
    private Date endDate;
    private String statusName;
    private int leaderId;
    private int userId;
    private int groupWorkId;

    public int getGroupWorkId() {
        return groupWorkId;
    }

    public void setGroupWorkId(int groupWorkId) {
        this.groupWorkId = groupWorkId;
    }

    public int getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(int leaderId) {
        this.leaderId = leaderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getGroupWorkName() {
        return groupWorkName;
    }

    public void setGroupWorkName(String groupWorkName) {
        this.groupWorkName = groupWorkName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public TaskDTO() {

    }
}
