import java.io.File;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class BFS extends Graph {

  private Map<City, Set<Road>> outputRoads;

  public BFS(File cities, File roads) {
    super();
    outputRoads = new HashMap<>();
    construireTxt(cities, roads);
  }

  @Override
  protected void ajouterSommet(City a) {
    correspondanceNomVille.put(a.getName(), a);
    outputRoads.put(a, new HashSet<>());
  }

  @Override
  protected void ajouterArc(Road r) {
    outputRoads.get(r.getSource()).add(r);
  }

  @Override
  public Set<Road> arcsSortants(City c) {
    return outputRoads.get(c);
  }

  @Override
  public boolean sontAdjacents(City c1, City c2) {
    Set<Road> roads = outputRoads.get(c1);
    for (Road r : roads) {
      if (r.getDestination().equals(c2)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void calculerItineraireMinimisantNombreRoutes(String city1, String city2) {

    // GET les objets "city"
    City c1 = correspondanceNomVille.get(city1);
    City c2 = correspondanceNomVille.get(city2);

    // Si city1 et city2 sont adjacent -> chemin le plus court trouvé
    if (sontAdjacents(c1, c2)) {
      System.out.println("Trajet de " + city1 + " " + city2 +
          " le chemin le plus court est direct en 1 route");
      return;
    }

    // File bfs
    ArrayDeque<City> queue = new ArrayDeque<>();
    // Map qui retient la ville et la route vers la ville précédente
    HashMap<City, Road> previousRoad = new HashMap<>();

    City nextCity = c1;
    // Next
    do {
      // Remplissage de la file
      for (Road r : arcsSortants(nextCity)) {
        if(!queue.contains(r.getDestination())){
          queue.add(r.getDestination());
          // Ajout des routes précédentes
          previousRoad.put(r.getDestination(), r);
        }
      }
      if(!queue.isEmpty()){
        nextCity = queue.removeFirst();
      }

    } while (!queue.isEmpty() || !nextCity.equals(c2));

    if(previousRoad.isEmpty()){
      System.out.println("Aucun chemin existant");
      throw new NoSuchElementException();
    }

    // Depillage du chemin
    ArrayDeque<Road> way = new ArrayDeque<>();
    Road lastRoad = previousRoad.get(c2);
    Double km = 0.0;
    do {
      way.addFirst(lastRoad);
      km += lastRoad.getDistance();
      lastRoad = previousRoad.get(lastRoad.getSource());
    } while (!lastRoad.getSource().equals(c1));
    way.addFirst(lastRoad);

    // Display Menu
    System.out.println("Trajet de "+c1.getName()+" à "+c2.getName()+": "+way.size()+ " routes "+ " et "+ km +" kms");
    for (int i = 0; i < way.size(); i++) {
      Road currentRoad = way.removeFirst();
      System.out.println(currentRoad.getSource().getName()+ " -> " + currentRoad.getDestination().getName()+" ("+ currentRoad.getDistance() + " km)");
    }

  }

  @Override
  public void calculerItineraireMinimisantKm(String city1, String city2){
    // useless in BFS
  }

}
