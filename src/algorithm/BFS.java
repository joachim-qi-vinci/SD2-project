package algorithm;

import domaine.City;
import domaine.Road;
import graph.arcSortants;
import java.io.File;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class BFS extends arcSortants {

  public BFS(File cities, File roads) {
    super(cities, roads);
  }

  public void calculerItineraireMinimisantNombreRoutes(String city1, String city2) {

    // Get the cities from the graph
    City c1 = correspondanceNomVille.get(city1);
    City c2 = correspondanceNomVille.get(city2);

    // Queue to store the cities to visit with the algorithm.BFS
    ArrayDeque<City> queue = new ArrayDeque<>();
    // Map to store the previous road for each city
    Map<City, Road> previousRoad = new HashMap<>();

    City nextCity = c1;
    previousRoad.put(c1, null);

    // While the queue is not empty and the next city is not the destination
    do {
      // For each road from the city
      for (Road r : roadsFromCity(nextCity)) {
        // If the city is not present in the map
        if (!previousRoad.containsKey(r.getDestination())) {
          // add a city to the queue
          queue.add(r.getDestination());
          // Add the city and its road to the map
          previousRoad.put(r.getDestination(), r);
        }
      }

      if (!queue.isEmpty()) {
        nextCity = queue.removeFirst();
      }

    } while (!queue.isEmpty() && !nextCity.equals(c2));

    if (!nextCity.equals(c2)) {
      System.out.println("Aucun chemin existant");
      throw new NoSuchElementException();
    }

    // Unstack the queue to find the way
    ArrayDeque<Road> way = new ArrayDeque<>();
    Road lastRoad = previousRoad.get(c2);
    double km = 0.0;
    do {
      // Add the road to the way
      way.addFirst(lastRoad);
      // Add the distance to the total distance
      km += lastRoad.getDistance();
      // Get the previous road
      lastRoad = previousRoad.get(lastRoad.getSource());
    } while (!way.getFirst().getSource().equals(c1));

    // Display Menu
    System.out.println(
        "\nTrajet de " + c1.getName() + " à " + c2.getName() + ": " + way.size() + " routes "
            + " et " + km + " kms\n");

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
