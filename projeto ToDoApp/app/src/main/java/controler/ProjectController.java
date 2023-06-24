/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controler;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import util.ConnectionFactory;

/**
 *
 * @author grace
 */
public class ProjectController {
    
    public void save (Project project){
        String sql = "Insert into projects (name, description, createDate, updateDate) "
                + "values(?, ?, ?, ?)";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try{
            connection = ConnectionFactory.getConnetion();
            statement = connection.prepareStatement(sql);
            
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreateDate().getTime()));
            statement.setDate(4, new Date(project.getUpdateDate().getTime()));
            
            statement.execute();
            
        }catch(Exception ex){
            throw new RuntimeException("Erro ao salvar o projeto", ex);
        }finally{
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public void update(Project project){
        String sql = "Update projects set name = ?, description = ?, "
                + "createDate = ?, updateDate = ? where id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try{
            //estabelecendo a conexão com o banco de dados
            connection = ConnectionFactory.getConnetion();
            
            //preparando a query
            statement = connection.prepareStatement(sql);
            
            //setando os valores do statement
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreateDate().getTime()));
            statement.setDate(4, new Date(project.getUpdateDate().getTime()));
            statement.setInt(5, project.getId());
            
            //executando a query
            statement.execute();
            
        }catch(Exception ex){
            throw new RuntimeException("Erro ao atualizar o projeto", ex);
        }finally{
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public void removeById(int projectId){
        String sql = "delete from projects where id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        
        try{
            //criação da conexão com o bando de dados
            connection = ConnectionFactory.getConnetion();
            
            //preparando a query
            statement = connection.prepareStatement(sql);
            
            //setando os valores
            statement.setInt(1, projectId);
            
            //executando a query
            statement.execute();
            
        }catch(Exception ex){
            throw new RuntimeException("Erro ao deletar o projeto", ex);
        }finally{
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public List<Project>getAll(){
        
        String sql = "select * from projects";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        //lista de tarefas que será devolvida quando o método for chamado
        List<Project> projects = new ArrayList<>();
        
        try{
            connection = ConnectionFactory.getConnetion();
            statement = connection.prepareStatement(sql);
            
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                project.setCreateDate(resultSet.getDate("createDate"));
                project.setUpdateDate(resultSet.getDate("updateDate"));
                
                projects.add(project);
            } 
            
        }catch(Exception ex){
            throw new RuntimeException("Erro ao deletar a tarefa", ex);
        }finally{
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
        
        return projects;
    }
}
