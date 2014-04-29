/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Data;

import enums.CarrierType;
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
public class TrackingStatusControllerTest {
    
    private static TrackingStatusController uut;
    private static UserController userController;
    private static TrackingInformationController trackingInfoController;
    private static int testUserId;
    private static int testTrackingInfoId;
    private static int testTrackingInfoId2;
    
    public TrackingStatusControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws SQLException {
        uut = new TrackingStatusController();
        userController = new UserController();
        trackingInfoController = new TrackingInformationController();
        
        User u = new User();
        u.setUsername("testUser");
        u.setPassword("testPassword");
        userController.insertUser(u);
        testUserId = u.getId();
        
        TrackingInformation info = new TrackingInformation();
        info.setUserId(testUserId);
        info.setCarrier(CarrierType.FedEx);
        info.setTrackingNumber("12345");
        info.setDestZipCode("16501");
        info.setMailingDate(new Date());
        trackingInfoController.insertTrackingInfo(info);
        testTrackingInfoId = info.getId();
        
        info.setUserId(testUserId);
        info.setCarrier(CarrierType.UPS);
        info.setTrackingNumber("123450");
        info.setDestZipCode("165010");
        info.setMailingDate(new Date());
        trackingInfoController.insertTrackingInfo(info);
        testTrackingInfoId2 = info.getId();
    }
    
    @AfterClass
    public static void tearDownClass() throws SQLException {
        trackingInfoController.deleteTrackingInfo(testTrackingInfoId);
        trackingInfoController.deleteTrackingInfo(testTrackingInfoId2);
        userController.deleteUser(testUserId);
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void insertTrackingInfo() throws SQLException {
        String city = "testCity";
        Date statusDate = new Date();
        String state = "testState";
        int informationId = testTrackingInfoId;
        
        TrackingStatus status = new TrackingStatus();
        status.setStatusCity(city);
        status.setStatusDate(statusDate);
        status.setStatusState(state);
        status.setTrackingInformationId(informationId);
        
        uut.insertTrackingStatus(status);
        
        TrackingStatus result = uut.getTrackingStatus(status.getId());
        
        assertThat(result.getStatusCity(), is(city));
        assertThat(result.getStatusDate(), is(statusDate));
        assertThat(result.getStatusState(), is(state));
        assertThat(result.getTrackingInformationId(), is(informationId));
        
        // Test deleting created trackingInfo
        uut.deleteTrackingStatus(status.getId());
        
        // Verify that trackingStatus was deleted
        TrackingStatus deleteResult = uut.getTrackingStatus(status.getId());
        assertThat(deleteResult, is(nullValue()));
    }
    
    @Test
    public void getTrackingInfo() throws SQLException {
        String city = "testCity";
        Date statusDate = new Date();
        String state = "testState";
        int informationId = testTrackingInfoId;
        
        TrackingStatus status = new TrackingStatus();
        status.setStatusCity(city);
        status.setStatusDate(statusDate);
        status.setStatusState(state);
        status.setTrackingInformationId(informationId);
        
        uut.insertTrackingStatus(status);
        List<TrackingStatus> trackingStatus = uut.getTrackingStatus();

        assertTrue(trackingStatus.size() > 0);

        int expectedNewSize = trackingStatus.size() - 1;
        uut.deleteTrackingStatus(status.getId());

        trackingStatus = uut.getTrackingStatus();
        assertTrue(trackingStatus.size() == expectedNewSize);
    }
    
    @Test
    public void getTrackingStatusByTrackingInfoId() throws SQLException {
        TrackingStatus status = new TrackingStatus();
        status.setStatusCity("testCity");
        status.setStatusDate(new Date());
        status.setStatusState("testState");
        status.setTrackingInformationId(testTrackingInfoId);
        uut.insertTrackingStatus(status);
        
        TrackingStatus status2 = new TrackingStatus();
        status2.setStatusCity("testCity2");
        status2.setStatusDate(new Date());
        status2.setStatusState("testState2");
        status2.setTrackingInformationId(testTrackingInfoId);
        uut.insertTrackingStatus(status2);
        
        List<TrackingStatus> result = uut.getTrackingStatusByTrackingInfoId(testTrackingInfoId);
        
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getId(), is(status.getId()));
        assertThat(result.get(1).getId(), is(status2.getId()));
        
        // Clean up after test
        uut.deleteTrackingStatus(status.getId());
        uut.deleteTrackingStatus(status2.getId());
    }
    
    @Test
    public void UpdateTrackingStatus() throws SQLException {
        // Insert tracking status
        TrackingStatus status = new TrackingStatus();
        status.setTrackingInformationId(testTrackingInfoId);
        uut.insertTrackingStatus(status);
        
        // Change values
        String city = "testCity";
        Date statusDate = new Date();
        String state = "testState";
        int informationId = testTrackingInfoId;
        
        status.setStatusCity(city);
        status.setStatusDate(statusDate);
        status.setStatusState(state);
        status.setTrackingInformationId(informationId);
        uut.updateTrackingStatus(status);
        
        // Get updated object
        TrackingStatus result = uut.getTrackingStatus(status.getId());
        
        // Verify that values changed
        assertThat(result.getStatusCity(), is(city));
        assertThat(result.getStatusDate(), is(statusDate));
        assertThat(result.getStatusState(), is(state));
        assertThat(result.getTrackingInformationId(), is(informationId));
        
        // Clean up from test
        uut.deleteTrackingStatus(status.getId());
    }
}
