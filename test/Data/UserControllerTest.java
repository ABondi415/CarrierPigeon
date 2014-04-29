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
    
    private static UserController uut;
    
    public UserControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        uut = new UserController();
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
        
        uut.insertUser(user);

        User result = uut.getUser(user.getId());
        
        assertThat(result.getEmail(), is(email));
        assertThat(result.getUsername(), is(username));
        assertThat(result.getPassword(), is(password));
                
        // Test deleting created User
        uut.deleteUser(user.getId());
        
        // Verify that trackingStatus was deleted
        User deleteResult = uut.getUser(user.getId());
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
        
        uut.insertUser(user);
        List<User> users = uut.getUsers();

        assertTrue(users.size() > 0);

        int expectedNewSize = users.size() - 1;
        uut.deleteUser(user.getId());

        users = uut.getUsers();
        assertTrue(users.size() == expectedNewSize);
    }
    
    @Test
    public void updateUser() throws SQLException {
        // Insert user
        User u = new User();
        u.setUsername("user1");
        u.setPassword("password1");
        u.setEmail("test@test.com");
        uut.insertUser(u);
        
        // Change values
        String username = "user2";
        String password = "password2";
        String email = "test2@test.com";
        u.setUsername(username);
        u.setPassword(password);
        u.setEmail(email);
        uut.updateUser(u);
        
        // Get updated object
        User result = uut.getUser(u.getId());
        
        // Verify that values changed
        assertThat(result.getEmail(), is(email));
        assertThat(result.getUsername(), is(username));
        assertThat(result.getPassword(), is(password));
        
        // Clean up from test
        uut.deleteUser(u.getId());
    }
}
