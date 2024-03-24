package domaine;

import domaine.utils.Util;

public class Road {

  private final City source;
  private final City desination;
  private final double distance;

  public Road(City s, City d) {
    this.source = s;
    this.desination = d;
    this.distance = Util.distance(s.getLatitude(), s.getLongitude(), d.getLatitude(),
        d.getLongitude());
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
    return "domaine.Road{" +
        "source=" + source +
        ", desination=" + desination +
        ", distance=" + distance +
        '}';
  }
}
