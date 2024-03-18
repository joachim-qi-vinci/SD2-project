import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class arcSortants extends Graph {

  private final Map<City, Set<Road>> outputRoads;

  public arcSortants(File cities, File roads) {
    super();
    outputRoads = new HashMap<>();
    construireTxt(cities, roads);
  }

  @Override
  protected void addCity(City a) {
    correspondanceNomVille.put(a.getName(), a);
    outputRoads.put(a, new HashSet<>());
  }

  @Override
  protected void addRoad(Road r) {
    outputRoads.get(r.getSource()).add(r);
  }

  @Override
  public Set<Road> roadsFromCity(City c) {
    return outputRoads.get(c);
  }

  @Override
  public boolean areAdjoining(City c1, City c2) {
    Set<Road> roads = outputRoads.get(c1);
    for (Road r : roads) {
      if (r.getDestination().equals(c2)) {
        return true;
      }
    }
    return false;
  }
}
