public class Road {
  private City source;
  private City desination;
  private double distance;

  public Road(City source, City desination, double distance) {
    this.source = source;
    this.desination = desination;
    this.distance = distance;
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
}
