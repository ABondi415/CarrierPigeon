/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mike
 */
public class UserController extends Controller {
    public void insertUser(User user) throws SQLException {
        Connection conn = getConnection();
        String sql = "insert into LoginUser (Email, Username, Password) VALUES (?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        
        statement.setString(1, user.getEmail());
        statement.setString(2, user.getUsername());
        statement.setString(3, user.getPassword());
        
        statement.executeUpdate();
        ResultSet rs = statement.getGeneratedKeys();
        if (!rs.next())
            throw new RuntimeException("Failed to get generated key.");
        user.setId(rs.getInt(1));
    }
    
    public User getUser(int id) throws SQLException {
        Connection conn = getConnection();
        String sql = "select Email, Username, Password from LoginUser where id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        
        statement.setInt(1, id);
        
        ResultSet rs = statement.executeQuery();
        if (!rs.next())
            return null;
        
        User user = new User();
        user.setId(id);
        user.setEmail(rs.getString(1));
        user.setUsername(rs.getString(2));
        user.setPassword(rs.getString(3));
        return user;
    }
    
    public void deleteUser(int id) throws SQLException {
        Connection conn = getConnection();
        String sql = "delete from LoginUser where id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        
        statement.setInt(1, id);
        statement.execute();
    }
    
    public List<User> getUsers() throws SQLException {
        Connection conn = getConnection();
        String sql = "select Id, Email, Username, Password from LoginUser";
        Statement statement = conn.createStatement();
        
        ResultSet rs = statement.executeQuery(sql);
        
        List<User> result = new ArrayList<>();
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt(1));
            user.setEmail(rs.getString(2));
            user.setUsername(rs.getString(3));
            user.setPassword(rs.getString(4));
            result.add(user);
        }
        
        return result;
    }
    
    public void updateUser(User u) throws SQLException {
        Connection conn = getConnection();
        String sql = "update LoginUser set Email = ?, Username = ?, Password = ? where Id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        
        statement.setString(1, u.getEmail());
        statement.setString(2, u.getUsername());
        statement.setString(3, u.getPassword());
        statement.setInt(4, u.getId());
        statement.execute();
    }
}
