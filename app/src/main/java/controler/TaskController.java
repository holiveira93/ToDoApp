/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controler;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Task;
import util.ConnectionFactory;

/**
 *
 * @author grace
 */
public class TaskController {
    
    public void save (Task task) throws SQLException{
        String sql = "Insert into tasks (idProject, name, description,"
                + "completed, notes, deadline, createDate, updateDate)"
                + "values(?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try{
            connection = ConnectionFactory.getConnetion();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.getCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreateDate().getTime()));
            statement.setDate(8, new Date(task.getUpdateDate().getTime()));
            
            statement.execute();
            
        }catch(Exception ex){
            throw new RuntimeException("Erro ao salvar a tarefa", ex);
        }finally{
            ConnectionFactory.closeConnection(connection, statement);
        }
        
    }
    
    public void update(Task task){
        String sql = "Update tasks set idProject = ?, name = ?, description = ?,"
                + "completed = ?, notes = ?, deadline = ?, createDate = ?,"
                + "updateDate = ? where id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try{
            connection = ConnectionFactory.getConnetion();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.getCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreateDate().getTime()));
            statement.setDate(8, new Date(task.getUpdateDate().getTime()));
            
            statement.execute();
        }catch(Exception ex){
            throw new RuntimeException("Erro ao atualizar a tarefa", ex);
        }finally{
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public void removeById(int taskId) throws SQLException{
        String sql = "delete from tasks where id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        
        try{
            connection = ConnectionFactory.getConnetion();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, taskId);
            statement.execute();
        }catch(Exception ex){
            throw new RuntimeException("Erro ao deletar a tarefa", ex);
        }finally{
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public List<Task>getAll(int idProject){
        
        String sql = "select * from tasks where idProject = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        List<Task> tasks = new ArrayList<>();
        
        try{
            connection = ConnectionFactory.getConnetion();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, idProject);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setNotes(resultSet.getString("notes"));
                task.setCompleted(resultSet.getBoolean("completed"));
                task.setDeadline(resultSet.getDate("deadline"));
                task.setCreateDate(resultSet.getDate("createDate"));
                task.setUpdateDate(resultSet.getDate("updateDate"));
                
                tasks.add(task);
                
            }
            
        }catch(Exception ex){
            throw new RuntimeException("Erro ao deletar a tarefa", ex);
        }finally{
            ConnectionFactory.closeConnection(connection, statement);
        }
        
    }
}
