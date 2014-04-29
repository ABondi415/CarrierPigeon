/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Data;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mike
 */
public class UserControllerTest {
    
    private static UserController controller;
    
    public UserControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        controller = new UserController();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void insertUser() throws SQLException {
        String email = "tester@test.com";
        String username = "user";
        String password = "password";
        
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        
        controller.insertUser(user);

        User result = controller.getUser(user.getId());
        
        assertThat(result.getEmail(), is(email));
        assertThat(result.getUsername(), is(username));
        assertThat(result.getPassword(), is(password));
                
        // Test deleting created User
        controller.deleteUser(user.getId());
        
        // Verify that trackingStatus was deleted
        User deleteResult = controller.getUser(user.getId());
        assertThat(deleteResult, is(nullValue()));
    }
    
    @Test
    public void getTrackingInfo() throws SQLException {
        String email = "tester@test.com";
        String username = "user";
        String password = "password";
        
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        
        controller.insertUser(user);
        List<User> users = controller.getUsers();

        assertTrue(users.size() > 0);

        int expectedNewSize = users.size() - 1;
        controller.deleteUser(user.getId());

        users = controller.getUsers();
        assertTrue(users.size() == expectedNewSize);
    }
}
