public class Road {
  private City source;
  private City desination;
  private double distance;

  public Road(City s, City d) {
    this.source = s;
    this.desination = d;
    this.distance = Util.distance(s.getLatitude(), s.getLongitude(), d.getLatitude(), d.getLongitude());
  }

  public City getDestination() {
    return desination;
  }

  public City getSource() {
    return source;
  }

  public double getDistance() {
    return distance;
  }

  @Override
  public String toString() {
    return "Road{" +
        "source=" + source +
        ", desination=" + desination +
        ", distance=" + distance +
        '}';
  }
}
