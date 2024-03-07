public class Road {
  private City source;
  private City desination;

  public Road(City source, City desination) {
    this.source = source;
    this.desination = desination;
  }

  public City getDestination() {
    return desination;
  }

  public City getSource() {
    return source;
  }


  @Override
  public String toString() {
    return "Road{" +
        "source=" + source +
        ", desination=" + desination +
        '}';
  }
}
