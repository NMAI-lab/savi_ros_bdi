package savi_ros_java.savi_ros_bdi.navigation.a_star;

public interface Location {

    public String toString();

    public double range(Location otherLocation);


}
