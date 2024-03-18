import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Dijkstra extends arcSortants {

  public Dijkstra(File cities, File roads) {
    super(cities, roads);
  }

  public void calculerItineraireMinimisantKm(String city1, String city2) {

    // Get the cities from the graph
    City c1 = correspondanceNomVille.get(city1);
    City c2 = correspondanceNomVille.get(city2);

    // List to store the label (= shortest road from the source)
    List<Road> finalLabel = new ArrayList<Road>(correspondanceNomVille.size()-1);
    // List to store the temporary label
    List<Road> temporaryLabel = new ArrayList<Road>(correspondanceNomVille.size()-1);

    System.out.println(finalLabel);
    // Map to store the previous road for each city
    HashMap<City, Road> previousRoad = new HashMap<>();

    temporaryLabel.add(c1.getId() -1, null);
    finalLabel.add(c1.getId() -1, null);

    // Starting at city1
    Road smallestRoad = null;
    for (Road r : roadsFromCity(c1)) {
      if(smallestRoad == null || r.getDistance() < smallestRoad.getDistance()){
        smallestRoad = r;
      }
      temporaryLabel.add(r.getDestination().getId() -1, r);
    }

    // No roads found
    if(smallestRoad == null){
      System.out.println("Pas de routes à partir de la ville de départ");
      return;
    }

    // Add city 1 to final label
    int idCity = smallestRoad.getDestination().getId() -1;
    finalLabel.add(idCity, smallestRoad);

    // add all cities to finalLabel until city2 filled or empty
    do {
      for (Road r : roadsFromCity(smallestRoad.getSource())) {
        if(r.getDistance() < smallestRoad.getDistance()){
          smallestRoad = r;
        }

        idCity = smallestRoad.getDestination().getId() -1;

        // If the city isn't in the finalLabel and smaller than the previous road
        // TODO Vérifier la taille totale des routes, pas la taille de la route
        if(finalLabel.get(idCity) == null && r.getDistance() < temporaryLabel.get(r.getDestination().getId() -1).getDistance()) {
          temporaryLabel.add(r.getDestination().getId() - 1, r);
        }

      }

      finalLabel.add(idCity, smallestRoad);

    } while (finalLabel.get(c2.getId() - 1) != null && finalLabel.size() <= correspondanceNomVille.size());









    // Unstack the queue to find the way
    ArrayDeque<Road> way = new ArrayDeque<>();
    Road lastRoad = finalLabel.get(c2.getId() -1);
    double km = 0.0;
    do {
      // Add the road to the way
      way.addFirst(lastRoad);
      // Add the distance to the total distance
      km += lastRoad.getDistance();
      // Get the previous road
      lastRoad = finalLabel.get(lastRoad.getSource().getId()-1);
    } while (!way.getFirst().getSource().equals(c1));

    // Display Menu
    System.out.println(
        "\nTrajet de " + c1.getName() + " à " + c2.getName() + ": " + way.size() + " routes "
            + " et " + km + " kms");

    // Display the way
    int size = way.size();
    for (int i = 0; i < size; i++) {
      Road currentRoad = way.removeFirst();
      System.out.println(
          currentRoad.getSource().getName() + " -> " + currentRoad.getDestination().getName() + " ("
              + currentRoad.getDistance() + " km)");
    }


    /*
     *
     * Garder en mémoire les chemins les plus courts
     *
     * Remplir étiquette provisoir (distance) pour chaque ville
     * Regarder moins couteux et mettre à jour
     * Aller vers le moins couteux
     * Refaire pour chaque ville juqu'à empty ou la ville cible
     *
     */

  }

}
