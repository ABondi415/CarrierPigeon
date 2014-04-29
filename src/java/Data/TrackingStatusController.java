/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Data;

import enums.CarrierType;
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
public class TrackingStatusController extends Controller {
    public void insertTrackingStatus(TrackingStatus status) throws SQLException {
        Connection conn = getConnection();
        String sql = "insert into TrackingStatus (StatusDate, StatusCity, StatusState, TrackingInformationId) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        
        statement.setTimestamp(1, new java.sql.Timestamp(status.getStatusDate().getTime()));
        statement.setString(2, status.getStatusCity());
        statement.setString(3, status.getStatusState());
        statement.setInt(4, status.getTrackingInformationId());
        
        statement.executeUpdate();
        ResultSet rs = statement.getGeneratedKeys();
        if (!rs.next())
            throw new RuntimeException("Failed to get generated key.");
        status.setId(rs.getInt(1));
    }
    
    public TrackingStatus getTrackingStatus(int id) throws SQLException {
        Connection conn = getConnection();
        String sql = "select Id, StatusDate, StatusCity, StatusState, TrackingInformationId from TrackingStatus where id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        
        statement.setInt(1, id);
        
        ResultSet rs = statement.executeQuery();
        if (!rs.next())
            return null;
        
        TrackingStatus status = getFromResultSet(rs);
        return status;
    }
    
    public List<TrackingStatus> getTrackingStatusByTrackingInfoId(int trackingInfoId) throws SQLException {
        Connection conn = getConnection();
        String sql = "select Id, StatusDate, StatusCity, StatusState, TrackingInformationId from TrackingStatus where TrackingInformationId = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        
        statement.setInt(1, trackingInfoId);
        ResultSet rs = statement.executeQuery();
        
        List<TrackingStatus> result = new ArrayList<>();
        while (rs.next()) {
            TrackingStatus status = getFromResultSet(rs);
            result.add(status);
        }
        
        return result;
    }
    
    public void deleteTrackingStatus(int id) throws SQLException {
        Connection conn = getConnection();
        String sql = "delete from TrackingStatus where id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        
        statement.setInt(1, id);
        statement.execute();
    }
    
    public List<TrackingStatus> getTrackingStatus() throws SQLException {
        Connection conn = getConnection();
        String sql = "select Id, StatusDate, StatusCity, StatusState, TrackingInformationId from TrackingStatus";
        Statement statement = conn.createStatement();
        
        ResultSet rs = statement.executeQuery(sql);
        
        List<TrackingStatus> result = new ArrayList<>();
        while (rs.next()) {
            TrackingStatus status = getFromResultSet(rs);
            result.add(status);
        }
        
        return result;
    }
    
    public void UpdateTrackingStatus(TrackingStatus ts) {
        throw new UnsupportedOperationException();
    }
    
    private TrackingStatus getFromResultSet(ResultSet rs) throws SQLException {
        TrackingStatus status = new TrackingStatus();
        status.setId(rs.getInt(1));
        status.setStatusDate(rs.getTimestamp(2));
        status.setStatusCity(rs.getString(3));
        status.setStatusState(rs.getString(4));
        status.setTrackingInformationId(rs.getInt(5));
        return status;
    }
}
