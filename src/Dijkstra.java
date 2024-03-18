import java.io.File;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class Dijkstra extends arcSortants {

  public Dijkstra(File cities, File roads) {
    super(cities, roads);
  }

  public void calculerItineraireMinimisantKm(String city1, String city2) {

    // Get the cities from the graph
    City c1 = correspondanceNomVille.get(city1);
    City c2 = correspondanceNomVille.get(city2);

    // Set to store Cities to visit
    Set<City> toVisit = new HashSet<>();

    // Set to store visited Cities
    Set<City> visited = new HashSet<>();

    // Map to store distance from the city to c1
    Map<City, Double> cityDistance = new HashMap<>();

    // Map to store the previous road for each city
    Map<City, Road> previousRoad = new HashMap<>();

    City nextCity = c1;
    cityDistance.put(c1, 0.0);
    previousRoad.put(c1, null);

    double shortestDistance;

    // While the queue is not empty and the city c2 is not visited
    do {

      // For each road from the city
      for (Road r : roadsFromCity(nextCity)) {

        // If the city is not present in the map
        if (!visited.contains(r.getDestination())) {

          // get the road distance
          double distance = r.getDistance();

          // If the distance for the city is not present in the map
          if (cityDistance.get(r.getDestination()) == null) {
            // Add the distance and the previous road
            cityDistance.put(r.getDestination(), distance + cityDistance.get(nextCity));
            previousRoad.put(r.getDestination(), r);

            // else if the distance is less than the current distance
          } else if (cityDistance.get(r.getDestination()) > distance + cityDistance.get(nextCity)) {
            // Update the distance and the previous road
            cityDistance.put(r.getDestination(), distance + cityDistance.get(nextCity));
            previousRoad.put(r.getDestination(), r);
          }

          // add a city to the queue
          toVisit.add(r.getDestination());
        }
      }

      // Add the city to the visited set
      visited.add(nextCity);
      // Remove the road from the set to visit
      toVisit.remove(nextCity);

      // Find the closest city in the Set to visit
      shortestDistance = Integer.MAX_VALUE;
      for (City c : toVisit) {
        // If the distance is less than the current shortest distance
        double distance = cityDistance.get(c);
        if (distance < shortestDistance) {
          // Update the shortest distance, road and next city
          shortestDistance = distance;
          nextCity = c;
        }
      }
    } while (!toVisit.isEmpty() && !visited.contains(c2));

    // If the city c2 is not visited
    if (!visited.contains(c2)) {
      System.out.println("Aucun chemin existant");
      throw new NoSuchElementException();
    }

    // Unstack the queue to find the way
    ArrayDeque<Road> way = new ArrayDeque<>();
    Road lastRoad = previousRoad.get(c2);
    double km = cityDistance.get(c2);
    do {
      // Add the road to the way
      way.addFirst(lastRoad);
      // Get the previous road
      lastRoad = previousRoad.get(lastRoad.getSource());
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

  }

}
