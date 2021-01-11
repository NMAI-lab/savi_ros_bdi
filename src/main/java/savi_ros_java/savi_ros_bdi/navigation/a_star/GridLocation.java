package savi_ros_java.savi_ros_bdi.navigation.a_star;

public class GridLocation implements Location {
    private double x;
    private double y;

    public GridLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public String toString() {
        return new String("X = " + Double.toString(x) + " Y = " + Double.toString(y));
    }

    public double range(Location otherLocation) {
        GridLocation otherGridLocation = (GridLocation)otherLocation;
        double deltaX = this.getX() - otherGridLocation.getX();
        double deltaY = this.getY() - otherGridLocation.getY();
        return Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
    }
}
