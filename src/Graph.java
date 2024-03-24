import domaine.City;
import domaine.Road;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

public class Graph {

  // Map to store the roads from a city
  private final Map<City, Set<Road>> outputRoads;
  // Map to store the cities by their name
  private final Map<String, City> correspondanceNomVille;

  /**
   * Constructor of the Graph. It initializes the maps and calls the method to create the graph from
   * the files.
   *
   * @param cities the file containing the cities
   * @param roads  the file containing the roads
   */
  public Graph(File cities, File roads) {
    correspondanceNomVille = new HashMap<>();
    outputRoads = new HashMap<>();
    createGraphByTxt(cities, roads);
  }

  /**
   * Create the graph from the cities and roads files.
   *
   * @param cities the file containing the cities
   * @param roads  the file containing the roads
   */
  public void createGraphByTxt(File cities, File roads) {

    // Map to store the cities by their id
    HashMap<Integer, City> citiesTab = new HashMap<>();

    // Insert all cities
    try (Scanner scanner = new Scanner(cities)) {
      do {
        String[] elems = scanner.nextLine().split(",");
        int id = Integer.parseInt(elems[0]);
        String cityName = elems[1];
        double latitude = Double.parseDouble(elems[2]);
        double longitude = Double.parseDouble(elems[3]);
        City c = new City(id, cityName, latitude, longitude);
        addCity(c);
        citiesTab.put(id, c);
      } while (scanner.hasNextLine());
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    // Insert all roads
    try (Scanner scanner = new Scanner(roads)) {
      do {
        String[] elems = scanner.nextLine().split(",");
        int idSrc = Integer.parseInt(elems[0]);
        int idDest = Integer.parseInt(elems[1]);
        Road r = new Road(citiesTab.get(idSrc), citiesTab.get(idDest));
        addRoad(r);
        r = new Road(citiesTab.get(idDest), citiesTab.get(idSrc));
        addRoad(r);
      } while (scanner.hasNextLine());
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Add a city to the graph.
   *
   * @param a the city to add
   */
  public void addCity(City a) {
    correspondanceNomVille.put(a.getName(), a);
    outputRoads.put(a, new HashSet<>());
  }

  /**
   * Add a road to the graph.
   *
   * @param r the road to add
   */
  protected void addRoad(Road r) {
    outputRoads.get(r.getSource()).add(r);
  }

  /**
   * Get all roads from a city.
   *
   * @param c the city
   * @return the roads from the city
   */
  public Set<Road> roadsFromCity(City c) {
    return outputRoads.get(c);
  }

  /**
   * Calculate the way between two cities minimizing the number of roads. The algorithm used is BFS.
   * The way is displayed with the number of roads and the total distance.
   *
   * @param city1 the source city
   * @param city2 the destination city
   * @throws NoSuchElementException if there is no way between the two cities
   */
  public void calculerItineraireMinimisantNombreRoutes(String city1, String city2) {

    // Get the cities from the graph
    City c1 = correspondanceNomVille.get(city1);
    City c2 = correspondanceNomVille.get(city2);

    // Queue to store the cities to visit with the BFS algorithm
    ArrayDeque<City> queue = new ArrayDeque<>();

    // Map to store the previous road for each city
    Map<City, Road> previousRoad = new HashMap<>();

    // Start with the source city
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
          // Add the city and its previous road to the map
          previousRoad.put(r.getDestination(), r);
        }
      }

      // If the queue is not empty get the next city to visit
      if (!queue.isEmpty()) {
        nextCity = queue.removeFirst();
      }

    } while (!queue.isEmpty() && !nextCity.equals(c2));

    // If the city c2 is not visited
    if (!nextCity.equals(c2)) {
      throw new NoSuchElementException(
          "Aucun chemin existant entre " + c1.getName() + " et " + c2.getName());
    }

    // Go through the map backwards to find the way and the total distance
    ArrayDeque<Road> way = new ArrayDeque<>();
    Road lastRoad = previousRoad.get(c2);
    double km = 0.0;
    // While the road source is not the source city, add the road to the way and add the road distance to the total distance
    do {
      way.addFirst(lastRoad);
      km += lastRoad.getDistance();
      lastRoad = previousRoad.get(lastRoad.getSource());
    } while (!way.getFirst().getSource().equals(c1));

    // Get the number of roads
    int size = way.size();
    // Display the way and the total distance
    printDistanceAndWay(c1, c2, size, km, way);

  }

  /**
   * Calculate the way between two cities minimizing the distance. The algorithm used is Dijkstra.
   * The way is displayed with the number of roads and the total distance.
   *
   * @param city1 the source city
   * @param city2 the destination city
   * @throws NoSuchElementException if there is no way between the two cities
   */
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

    // Variable to store the shortest distance
    double shortestDistance;

    // Start with the source city
    City nextCity = c1;
    cityDistance.put(c1, 0.0);
    previousRoad.put(c1, null);

    // While the queue is not empty and the city destination is not visited
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
      throw new NoSuchElementException(
          "Aucun chemin existant entre " + c1.getName() + " et " + c2.getName());
    }

    // Go through the map backwards to find the way
    ArrayDeque<Road> way = new ArrayDeque<>();
    Road lastRoad = previousRoad.get(c2);
    // While the road source is not the source city, add the road to the way
    do {
      way.addFirst(lastRoad);
      lastRoad = previousRoad.get(lastRoad.getSource());
    } while (!way.getFirst().getSource().equals(c1));

    // Get the total distance and the number of roads
    double km = cityDistance.get(c2);
    int size = way.size();
    // Display the way and the total distance
    printDistanceAndWay(c1, c2, size, km, way);

  }

  /**
   * Display the way and the total distance between two cities
   *
   * @param c1   the source city
   * @param c2   the destination city
   * @param size the number of roads
   * @param km   the total distance
   * @param way  the way between the two cities
   */
  private static void printDistanceAndWay(City c1, City c2, int size, double km,
      ArrayDeque<Road> way) {

    // Display Menu
    System.out.println(
        "\nTrajet de " + c1.getName() + " à " + c2.getName() + ": " + size + " routes et " + km
            + " kms");

    // Display the way
    for (int i = 0; i < size; i++) {
      Road currentRoad = way.removeFirst();
      System.out.println(
          currentRoad.getSource().getName() + " -> " + currentRoad.getDestination().getName() + " ("
              + String.format("%.2f", currentRoad.getDistance()) + " km)");
    }
  }

}
