package repository;

import config.MysqlConfig;
import dto.StatusDTO;
import dto.TaskDTO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskRepository {
    public List<TaskDTO> findAllTasks() {
        List<TaskDTO> taskDTOList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = MysqlConfig.getConnection();
            String query = "SELECT t.id, t.name AS task_name, j.name AS job_name, u.fullname AS user_name, " +
                    "t.start_date, t.end_date, s.name AS status_name, j.leader_id AS leader_id, t.user_id " +
                    "FROM tasks t " +
                    "INNER JOIN users u ON t.user_id = u.id " +
                    "INNER JOIN jobs j ON j.id = t.job_id " +
                    "INNER JOIN status s ON s.id = t.status_id " +
                    "ORDER BY j.name ASC";

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                TaskDTO task = new TaskDTO();
                task.setId(resultSet.getInt("id"));
                task.setTaskName(resultSet.getString("task_name"));
                task.setGroupWorkName(resultSet.getString("job_name"));
                task.setUserName(resultSet.getString("user_name"));
                task.setStartDate(resultSet.getDate("start_date"));
                task.setEndDate(resultSet.getDate("end_date"));
                task.setStatusName(resultSet.getString("status_name"));
                task.setLeaderId(resultSet.getInt("leader_id"));
                task.setUserId(resultSet.getInt("user_id"));
                taskDTOList.add(task);
            }
        } catch (Exception e) {
            System.out.println("Error findAllTasks: " + e.getMessage());
        } finally {
            MysqlConfig.closeConnection(connection, "findAllTasks");
        }
        return taskDTOList;
    }

    public boolean deleteById(int id) {
        boolean isSuccess = false;
        Connection connection = null;

        try {
            connection = MysqlConfig.getConnection();
            String delete = "DELETE FROM tasks t WHERE t.id = ?";

            PreparedStatement statement = connection.prepareStatement(delete);
            statement.setInt(1, id);

            isSuccess = statement.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error at deleteById(TaskRepository): " + e.getMessage());
            ;
        } finally {
            MysqlConfig.closeConnection(connection, "deleteById(TaskRepository)");
        }
        return isSuccess;
    }

    public List<StatusDTO> findAllStatus() {
        Connection connection = null;
        List<StatusDTO> statusDTOList = new ArrayList<>();
        try {
            connection = MysqlConfig.getConnection();
            String query = "SELECT s.id, s.name AS status_name, COUNT(t.status_id) AS task_count " +
                    "FROM `status` s " +
                    "LEFT JOIN tasks t ON s.id = t.status_id " +
                    "GROUP BY s.id, s.name";

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                StatusDTO status = new StatusDTO();
                status.setId(resultSet.getInt("id"));
                status.setName(resultSet.getString("status_name"));
                status.setTaskCount(resultSet.getInt("task_count"));
                statusDTOList.add(status);
            }
        } catch (Exception e) {
            System.out.println("Error findAllStatus: " + e.getMessage());
        } finally {
            MysqlConfig.closeConnection(connection, "findAllStatus");
        }
        return statusDTOList;
    }

    public List<TaskDTO> findByUserIdAndStatusId(int userId, int statusId) {
        List<TaskDTO> taskDTOList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = MysqlConfig.getConnection();
            String query = "SELECT t.id, t.name AS task_name, j.name AS job_name, u.fullname AS user_name, " +
                    "t.start_date, t.end_date, s.name AS status_name " +
                    "FROM tasks t " +
                    "INNER JOIN users u ON t.user_id = u.id " +
                    "INNER JOIN jobs j ON j.id = t.job_id " +
                    "INNER JOIN status s ON s.id = t.status_id " +
                    "WHERE t.user_id = ? AND t.status_id = ?;";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setInt(2, statusId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                TaskDTO task = new TaskDTO();
                task.setId(resultSet.getInt("id"));
                task.setTaskName(resultSet.getString("task_name"));
                task.setGroupWorkName(resultSet.getString("job_name"));
                task.setUserName(resultSet.getString("user_name"));
                task.setStartDate(resultSet.getDate("start_date"));
                task.setEndDate(resultSet.getDate("end_date"));
                task.setStatusName(resultSet.getString("status_name"));
                taskDTOList.add(task);
            }
        } catch (Exception e) {
            System.out.println("Error findByUserIdAndStatusId: " + e.getMessage());
        } finally {
            MysqlConfig.closeConnection(connection, "findByUserIdAndStatusId");
        }
        return taskDTOList;
    }

    public List<TaskDTO> findByUserId(int userId) {
        List<TaskDTO> listTasks = new ArrayList<>();
        Connection connection = null;
        try {
            connection = MysqlConfig.getConnection();
            String query = "SELECT t.id, t.name AS task_name, j.name AS job_name, u.fullname AS user_name, " +
                    "t.start_date, t.end_date, s.name AS status_name " +
                    "FROM tasks t " +
                    "INNER JOIN users u ON t.user_id = u.id " +
                    "INNER JOIN jobs j ON j.id = t.job_id " +
                    "INNER JOIN status s ON s.id = t.status_id " +
                    "WHERE t.user_id = ?;";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                TaskDTO task = new TaskDTO();
                task.setId(resultSet.getInt("id"));
                task.setTaskName(resultSet.getString("task_name"));
                task.setGroupWorkName(resultSet.getString("job_name"));
                task.setUserName(resultSet.getString("user_name"));
                task.setStartDate(resultSet.getDate("start_date"));
                task.setEndDate(resultSet.getDate("end_date"));
                task.setStatusName(resultSet.getString("status_name"));
                listTasks.add(task);
            }
        } catch (Exception e) {
            System.out.println("Error findByUserId(TaskRepository): " + e.getMessage());
        } finally {
            MysqlConfig.closeConnection(connection, "findByUserId(TaskRepository)");
        }
        return listTasks;
    }

    public boolean updateProgress(int id, Date startDate, Date endDate, int statusId) {
        boolean isSuccess = false;
        Connection connection = null;
        try {
            connection = MysqlConfig.getConnection();
            String update = "UPDATE tasks " +
                    "SET start_date = ?, end_date = ?, status_id = ? " +
                    "WHERE id = ?";

            PreparedStatement statement = connection.prepareStatement(update);
            statement.setDate(1, startDate);
            statement.setDate(2, endDate);
            statement.setInt(3, statusId);
            statement.setInt(4, id);

            isSuccess = statement.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error updateProgress(TaskRepository): " + e.getMessage());
            ;
        } finally {
            MysqlConfig.closeConnection(connection, "updateProgress(TaskRepository)");
        }
        return isSuccess;
    }

    public List<TaskDTO> findByGroupWorkIdAndStatusId(int groupWorkId, int statusId) {
        List<TaskDTO> taskDTOList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = MysqlConfig.getConnection();
            String query = "SELECT t.id, t.name AS task_name, j.name AS job_name, u.fullname AS user_name, " +
                    "t.start_date, t.end_date, s.name AS status_name " +
                    "FROM tasks t " +
                    "INNER JOIN users u ON t.user_id = u.id " +
                    "INNER JOIN jobs j ON j.id = t.job_id " +
                    "INNER JOIN status s ON s.id = t.status_id " +
                    "WHERE j.id = ? AND t.status_id = ?;";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, groupWorkId);
            statement.setInt(2, statusId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                TaskDTO task = new TaskDTO();
                task.setId(resultSet.getInt("id"));
                task.setTaskName(resultSet.getString("task_name"));
                task.setGroupWorkName(resultSet.getString("job_name"));
                task.setUserName(resultSet.getString("user_name"));
                task.setStartDate(resultSet.getDate("start_date"));
                task.setEndDate(resultSet.getDate("end_date"));
                task.setStatusName(resultSet.getString("status_name"));
                taskDTOList.add(task);
            }
        } catch (Exception e) {
            System.out.println("Error findByGroupWorkIdAndStatusId: " + e.getMessage());
        } finally {
            MysqlConfig.closeConnection(connection, "findByGroupWorkIdAndStatusId");
        }
        return taskDTOList;
    }

    public Optional<TaskDTO> findById(int id) {
        Optional<TaskDTO> optionalTaskDTO = Optional.empty();
        Connection connection = null;

        try {
            connection = MysqlConfig.getConnection();
            String query = "SELECT t.id, t.name AS task_name, j.name AS job_name, u.fullname AS user_name, " +
                    "t.start_date, t.end_date, s.name AS status_name, j.leader_id AS leader_id, t.user_id, t.job_id " +
                    "FROM tasks t " +
                    "INNER JOIN users u ON t.user_id = u.id " +
                    "INNER JOIN jobs j ON j.id = t.job_id " +
                    "INNER JOIN status s ON s.id = t.status_id " +
                    "WHERE t.id = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                TaskDTO task = new TaskDTO();
                task.setId(resultSet.getInt("id"));
                task.setTaskName(resultSet.getString("task_name"));
                task.setGroupWorkName(resultSet.getString("job_name"));
                task.setUserName(resultSet.getString("user_name"));
                task.setStartDate(resultSet.getDate("start_date"));
                task.setEndDate(resultSet.getDate("end_date"));
                task.setStatusName(resultSet.getString("status_name"));
                task.setLeaderId(resultSet.getInt("leader_id"));
                task.setUserId(resultSet.getInt("user_id"));
                task.setGroupWorkId(resultSet.getInt("job_id"));
                optionalTaskDTO = Optional.of(task);
            }
        } catch (Exception e) {
            System.out.println("Error findById(TaskRepository): " + e.getMessage());
        } finally {
            MysqlConfig.closeConnection(connection, "findById(TaskRepository)");
        }
        return optionalTaskDTO;
    }

    public boolean update(int id, String name, Date startDate, Date endDate, int userId, int jobId, int statusId) {
        Connection connection = null;
        boolean isSuccess = false;

        try {
            connection = MysqlConfig.getConnection();
            String update = "UPDATE tasks t " +
                    "SET name = ?, start_date = ?, end_date = ?, user_id = ?, job_id = ?, status_id = ? " +
                    "WHERE t.id = ?";
            PreparedStatement statement = connection.prepareStatement(update);
            statement.setString(1, name);
            statement.setDate(2, startDate);
            statement.setDate(3, endDate);
            statement.setInt(4, userId);
            statement.setInt(5, jobId);
            statement.setInt(6, statusId);
            statement.setInt(7, id);

            isSuccess = statement.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error update(TaskRepository): " + e.getMessage());
        } finally {
            MysqlConfig.closeConnection(connection, " update(TaskRepository)");
        }
        return isSuccess;
    }

    public boolean insert(String taskName, Date startDate, Date endDate, int userId, int groupWorkId, int statusId) {
        boolean isSuccess = false;
        Connection connection = null;

        try {
            connection = MysqlConfig.getConnection();
            String insert = "INSERT INTO tasks(name, start_date, end_date, user_id, job_id, status_id) VALUES(?, ?, ?, ?, ?, ?) ";

            PreparedStatement statement = connection.prepareStatement(insert);
            statement.setString(1, taskName);
            statement.setDate(2, startDate);
            statement.setDate(3, endDate);
            statement.setInt(4, userId);
            statement.setInt(5, groupWorkId);
            statement.setInt(6, statusId);

            isSuccess = statement.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error insert(TaskRepository): " + e.getMessage());
        } finally {
            MysqlConfig.closeConnection(connection, "insert(TaskRepository)");
        }
        return isSuccess;
    }
}
