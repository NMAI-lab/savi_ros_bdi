package savi_ros_java.savi_ros_bdi.navigation.a_star;

import savi_ros_java.savi_ros_bdi.navigation.range;

public class EarthLocation implements Location {

    private double latitude;
    private double longitude;

    public EarthLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public String toString() {
        return new String("Latitude = " + Double.toString(this.getLatitude()) + " Longitude = " + Double.toString(this.getLongitude()));
    }

    @Override
    public double range(Location otherLocation) {
        EarthLocation otherEarthLocation = (EarthLocation) otherLocation;
        return range.calculateRange(this.getLatitude(), this.getLongitude(), otherEarthLocation.getLatitude(), otherEarthLocation.getLongitude(),"meters");
    }
}
