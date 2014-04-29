/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Data;

import enums.CarrierType;
import java.sql.Connection;
import java.sql.Date;
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
public class TrackingInformationController extends Controller {
    public void insertTrackingInfo(TrackingInformation info) throws SQLException {
        Connection conn = getConnection();
        String sql = "insert into TrackingInformation (UserId, Carrier, TrackingNumber, DestZipCode, MailingDate) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        
        statement.setInt(1, info.getUserId());
        statement.setString(2, info.getCarrier().toString());
        statement.setString(3, info.getTrackingNumber());
        statement.setString(4, info.getDestZipCode());
        statement.setDate(5, new java.sql.Date(info.getMailingDate().getTime()));
        
        statement.executeUpdate();
        ResultSet rs = statement.getGeneratedKeys();
        if (!rs.next())
            throw new RuntimeException("Failed to get generated key.");
        info.setId(rs.getInt(1));
    }
    
    public TrackingInformation getTrackingInfo(int id) throws SQLException {
        Connection conn = getConnection();
        String sql = "select Id, UserId, Carrier, TrackingNumber, DestZipCode, MailingDate from TrackingInformation where id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        
        statement.setInt(1, id);
        
        ResultSet rs = statement.executeQuery();
        if (!rs.next())
            return null;
        
        TrackingInformation info = getFromResultSet(rs);
        return info;
    }
    
    public TrackingInformation getTrackingInfoByTrackingNumber(String trackingNumber) throws SQLException {
        Connection conn = getConnection();
        String sql = "select Id, UserId, Carrier, TrackingNumber, DestZipCode, MailingDate from TrackingInformation where TrackingNumber = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        
        statement.setString(1, trackingNumber);
        ResultSet rs = statement.executeQuery();
        if (!rs.next())
            return null;
        
        TrackingInformation info = getFromResultSet(rs);
        return info;
    }
    
    public void deleteTrackingInfo(int id) throws SQLException {
        Connection conn = getConnection();
        String sql = "delete from TrackingInformation where id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        
        statement.setInt(1, id);
        statement.execute();
    }
    
    public List<TrackingInformation> getTrackingInfo() throws SQLException {
        Connection conn = getConnection();
        String sql = "select Id, UserId, Carrier, TrackingNumber, DestZipCode, MailingDate from TrackingInformation";
        Statement statement = conn.createStatement();
        
        ResultSet rs = statement.executeQuery(sql);
        
        List<TrackingInformation> result = new ArrayList<>();
        while (rs.next()) {
            TrackingInformation info = getFromResultSet(rs);
            result.add(info);
        }
        
        return result;
    }
    
    public List<TrackingInformation> getTrackingInfoByLoginUser(int userId) throws SQLException {
        Connection conn = getConnection();
        String sql = "select Id, UserId, Carrier, TrackingNumber, DestZipCode, MailingDate from TrackingInformation where UserId = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        
        statement.setInt(1, userId);
        ResultSet rs = statement.executeQuery();
        
        List<TrackingInformation> result = new ArrayList<>();
        while (rs.next()) {
            TrackingInformation info = getFromResultSet(rs);
            result.add(info);
        }
        
        return result;
    }
    
    public void UpdateTrackingInformation(TrackingInformation ti) {
        throw new UnsupportedOperationException();
    }
    
    private TrackingInformation getFromResultSet(ResultSet rs) throws SQLException {
        TrackingInformation info = new TrackingInformation();
        
        info.setId(rs.getInt(1));
        info.setUserId(rs.getInt(2));
        info.setCarrier(CarrierType.valueOf(rs.getString(3)));
        info.setTrackingNumber(rs.getString(4));
        info.setDestZipCode(rs.getString(5));
        info.setMailingDate(rs.getDate(6));
        
        return info;
    }
}
