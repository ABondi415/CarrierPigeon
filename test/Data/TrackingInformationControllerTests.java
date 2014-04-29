/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import static com.google.common.collect.Ranges.greaterThan;
import enums.CarrierType;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
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
public class TrackingInformationControllerTests {

    private static TrackingInformationController uut;
    private static UserController userController;
    private static int testUserId;
    private static int testUserId2;

    public TrackingInformationControllerTests() {
    }

    @BeforeClass
    public static void setUpClass() throws SQLException {
        uut = new TrackingInformationController();
        userController = new UserController();
        
        User u = new User();
        u.setUsername("testUser");
        u.setPassword("testPassword");
        userController.insertUser(u);
        testUserId = u.getId();
        
        u.setUsername("testUser2");
        u.setPassword("testPassword2");
        userController.insertUser(u);
        testUserId2 = u.getId();
    }

    @AfterClass
    public static void tearDownClass() throws SQLException {
        userController.deleteUser(testUserId);
        userController.deleteUser(testUserId2);
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void insertTrackingInfo() throws SQLException {
        int userId = testUserId;
        CarrierType carrier = CarrierType.FedEx;
        String trackingNumber = "9876";
        String destZipCode = "12345";
        Date mailingDate = new Date();

        TrackingInformation info = new TrackingInformation();
        info.setUserId(userId);
        info.setCarrier(carrier);
        info.setTrackingNumber(trackingNumber);
        info.setDestZipCode(destZipCode);
        info.setMailingDate(mailingDate);

        uut.insertTrackingInfo(info);

        TrackingInformation result = uut.getTrackingInfo(info.getId());

        assertThat(result.getUserId(), is(userId));
        assertThat(result.getCarrier(), is(carrier));
        assertThat(result.getTrackingNumber(), is(trackingNumber));
        assertThat(result.getDestZipCode(), is(destZipCode));
        assertThat(result.getMailingDate().getDay(), is(mailingDate.getDay()));

        // Test deleting created trackingInfo
        uut.deleteTrackingInfo(info.getId());

        // Verify that trackingInfo was deleted
        TrackingInformation deleteResult = uut.getTrackingInfo(info.getId());
        assertThat(deleteResult, is(nullValue()));
    }

    @Test
    public void getTrackingInfo() throws SQLException {
        int userId = testUserId;
        CarrierType carrier = CarrierType.FedEx;
        String trackingNumber = "9876";
        String destZipCode = "12345";
        Date mailingDate = new Date();

        TrackingInformation info = new TrackingInformation();
        info.setUserId(userId);
        info.setCarrier(carrier);
        info.setTrackingNumber(trackingNumber);
        info.setDestZipCode(destZipCode);
        info.setMailingDate(mailingDate);

        uut.insertTrackingInfo(info);
        List<TrackingInformation> trackingInfo = uut.getTrackingInfo();

        assertTrue(trackingInfo.size() > 0);

        int expectedNewSize = trackingInfo.size() - 1;
        uut.deleteTrackingInfo(info.getId());

        trackingInfo = uut.getTrackingInfo();
        assertTrue(trackingInfo.size() == expectedNewSize);
    }
    
    @Test
    public void getTrackingInformationByTrackingNumber() throws SQLException {
        // Insert trackingInformation
        int userId = testUserId;
        CarrierType carrier = CarrierType.FedEx;
        String trackingNumber = "9876";
        String destZipCode = "12345";
        Date mailingDate = new Date();

        TrackingInformation info = new TrackingInformation();
        info.setUserId(userId);
        info.setCarrier(carrier);
        info.setTrackingNumber(trackingNumber);
        info.setDestZipCode(destZipCode);
        info.setMailingDate(mailingDate);

        uut.insertTrackingInfo(info);
        
        // Retrieve by trackingnumber and check id
        TrackingInformation result = uut.getTrackingInfoByTrackingNumber(trackingNumber);
        
        assertThat(info.getId(), is(result.getId()));
        
        // Remove record to clean up from test
        uut.deleteTrackingInfo(result.getId());
    }
    
    @Test
    public void GetTrackingInfoByLoginUser() throws SQLException {
        // Insert trackingInformation
        int userId = testUserId;
        CarrierType carrier = CarrierType.FedEx;
        String trackingNumber = "9876";
        String destZipCode = "12345";
        Date mailingDate = new Date();

        TrackingInformation info = new TrackingInformation();
        info.setUserId(userId);
        info.setCarrier(carrier);
        info.setTrackingNumber(trackingNumber);
        info.setDestZipCode(destZipCode);
        info.setMailingDate(mailingDate);

        uut.insertTrackingInfo(info);
        
        // Insert another tracking information object
        TrackingInformation info2 = new TrackingInformation();
        info2.setUserId(userId);
        info2.setCarrier(carrier);
        info2.setTrackingNumber("12321");
        info2.setDestZipCode(destZipCode);
        info2.setMailingDate(mailingDate);
        
        uut.insertTrackingInfo(info2);
        
        // Retrieve the objects and verify both inserted id's are retrieved
        List<TrackingInformation> result = uut.getTrackingInfoByLoginUser(userId);
        
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getId(), is(info.getId()));
        assertThat(result.get(1).getId(), is(info2.getId()));
        
        // Clean up after test
        uut.deleteTrackingInfo(info.getId());
        uut.deleteTrackingInfo(info2.getId());
    }
    
    @Test
    public void UpdateTrackingInformation() throws SQLException {
        // Insert tracking information
        TrackingInformation info = new TrackingInformation();
        info.setUserId(testUserId);
        uut.insertTrackingInfo(info);
        
        // Change values
        int userId = testUserId2;
        CarrierType carrier = CarrierType.FedEx;
        String trackingNumber = "9876";
        String destZipCode = "12345";
        Date mailingDate = new Date();
        
        info.setUserId(userId);
        info.setCarrier(carrier);
        info.setTrackingNumber(trackingNumber);
        info.setDestZipCode(destZipCode);
        info.setMailingDate(mailingDate);
        uut.updateTrackingInformation(info);
        
        // Get updated object
        TrackingInformation result = uut.getTrackingInfo(info.getId());
        
        // Verify that values changed
        assertThat(result.getUserId(), is(userId));
        assertThat(result.getCarrier(), is(carrier));
        assertThat(result.getTrackingNumber(), is(trackingNumber));
        assertThat(result.getDestZipCode(), is(destZipCode));
        assertThat(result.getMailingDate().getDay(), is(mailingDate.getDay()));
        
        // Clean up from test
        uut.deleteTrackingInfo(info.getId());
    }
}
