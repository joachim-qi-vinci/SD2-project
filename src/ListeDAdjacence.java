import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ListeDAdjacence extends Graph {

  private Map<City, Set<Road>> outputRoads;

  public ListeDAdjacence(File cities, File roads ) {
    super();
    outputRoads = new HashMap<>();
    constructFromTXT(cities, roads);
  }

  @Override
  // Complexit�: o(1)
  protected void ajouterSommet(City a) {
    correspondanceNomVille.put(a.getName(), a);
    outputRoads.put(a, new HashSet<>());
  }

  @Override
  // Complexit�: o(1)
  protected void ajouterArc(Road r) {
    outputRoads.get(r.getSource()).add(r);
  }

  @Override
  // Complexit�: o(1)
  public Set<Road> arcsSortants(Road r) {
    return outputRoads.get(r);
  }

  @Override
  // Complexit�: o(m)
  public boolean sontAdjacents(Road r1, Road r2) {
    Set<Road> f1 = outputRoads.get(r1);
    for (Road f : f1) {
      if (f.getDestination().equals(r2)) {
        return true;
      }
    }

    Set<Road> f2 = outputRoads.get(r2);
    for (Road f : f2) {
      if (f.getDestination().equals(r1)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void calculerItineraireMinimisantKm(String city1, String city2) {

  }

  @Override
  public void calculerItineraireMinimisantNombreRoutes(String berlin, String madrid) {

  }

}
