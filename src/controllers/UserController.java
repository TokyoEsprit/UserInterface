
package controllers;

import models.User;
import utils.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.PasswordUtils;


public class UserController {
    
        Connection con;
        PreparedStatement preparedStatement;
        ResultSet resultSet;

    public UserController() {
        con =Database.getInstance().getConnection();

    }
    
    
    
     private Statement ste;
  public void Add(User u) throws SQLException
    {
        PasswordUtils crypt = new PasswordUtils();
        PreparedStatement pre=con.prepareStatement("INSERT INTO `test1`.`user` (`login`, `pwd`, `email`, `lastName`, `firstName`, `role`) VALUES (?,?,?,?,?,?);");
        pre.setString(1, u.getLogin());
        pre.setString(2, crypt.hashPassword(u.getPassword()));
        pre.setString(3, u.getEmail());
        pre.setString(4, u.getLastname());
        pre.setString(5, u.getFirstname());
        pre.setString(6, u.getRole());

        pre.executeUpdate();
    }
      
     public List<User> readAll() throws SQLException {
    List<User> arr=new ArrayList<>();
    ste=con.createStatement();
    ResultSet rs=ste.executeQuery("select * from user");
     while (rs.next()) { 
               
               String lastname=rs.getString("lastName");
               String firstname=rs.getString("firstName");
               String role=rs.getString("role");
               User u=new User(lastname,firstname,role);
     arr.add(u);
     }
    return arr;
    }

    public boolean delete(User u) throws SQLException {
        ste = con.createStatement();
        String requeteDelete ="DELETE FROM  `test1`.`user` WHERE `user` . login ='"+u.getLogin()+"'";
        return ste.executeUpdate(requeteDelete)==1;
 
    }

  
    public boolean update(String email,String password) throws SQLException {
        String query  ="UPDATE `test1`.`user` SET `pwd`=?  WHERE email =?";
        con.setAutoCommit(false);
        preparedStatement = con.prepareStatement(query);        
        preparedStatement.setString(1, password);
        preparedStatement.setString(2, email);
        return preparedStatement.executeUpdate()==1;
        
        
    }
    
    public boolean RechercherParId(int id) throws SQLException
    {
        ste=con.createStatement();
        ResultSet rs=ste.executeQuery("SELECT * FROM user where id="+ id);
        if (rs.next()) { 
               
               return true;
        }
        return false;
    }
    
    public boolean RechercherParLogin(String login) throws SQLException
    {
        ste=con.createStatement();
        ResultSet rs=ste.executeQuery("SELECT * FROM user where login="+ login);
        if (rs.next()) { 
               
               return true;
        }
        return false;
    }
}
