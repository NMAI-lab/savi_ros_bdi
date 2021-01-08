package NavigationSupport;

public class Location {
    private double x;
    private double y;

    public Location(double x, double y) {
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


    public double range(double x, double y) {
        Location location = new Location(x,y);
        return this.range(location);
    }

    public double range(Location otherLocation) {
        double deltaX = this.getX() - otherLocation.getX();
        double deltaY = this.getY() - otherLocation.getY();
        return Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
    }

    public Boolean equals(Location otherLocation) {
        if (this.getX() != otherLocation.getX()) {
            return false;
        } else if (this.getY() != otherLocation.getY()) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean isUp(Location otherLocation) {
        if (this.getX() == otherLocation.getX() && this.getY() > otherLocation.getY()) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean isDown(Location otherLocation) {
        if (this.getX() == otherLocation.getX() && this.getY() < otherLocation.getY()) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean isLeft(Location otherLocation) {
        if (this.getX() < otherLocation.getX() && this.getY() == otherLocation.getY()) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean isRight(Location otherLocation) {
        if (this.getX() > otherLocation.getX() && this.getY() == otherLocation.getY()) {
            return true;
        } else {
            return false;
        }
    }
}
