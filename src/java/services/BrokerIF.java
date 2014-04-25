package services;

import trackinginformation.TrackingInformation;

/**
 *
 * @author A9712
 */
public interface BrokerIF {
    void route(TrackingInformation information);
}
