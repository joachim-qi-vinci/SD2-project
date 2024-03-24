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

  private final Map<City, Set<Road>> outputRoads;
  //
  private Map<String, City> correspondanceNomVille;

  public Graph(File cities, File roads) {
    correspondanceNomVille = new HashMap<>();
    outputRoads = new HashMap<>();
    construireTxt(cities, roads);
  }

  public void construireTxt(File cities, File roads) {
    HashMap<Integer, City> citiesTab = new HashMap<>();

    // insert all cities
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

    // insert all road
    try (Scanner scanner = new Scanner(roads)) {
      do {
        String[] elems = scanner.nextLine().split(",");
        int idSrc = Integer.parseInt(elems[0]);
        int idDest = Integer.parseInt(elems[1]);
        Road r = new Road(citiesTab.get(idSrc), citiesTab.get(idDest));
        Road rBack = new Road(citiesTab.get(idDest), citiesTab.get(idSrc));
        addRoad(r);
        addRoad(rBack);
      } while (scanner.hasNextLine());
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public City getAirport(String nom) {
    return correspondanceNomVille.get(nom);
  }

  public void addCity(City a) {
    correspondanceNomVille.put(a.getName(), a);
    outputRoads.put(a, new HashSet<>());
  }

  protected void addRoad(Road r) {
    outputRoads.get(r.getSource()).add(r);
  }

  public Set<Road> roadsFromCity(City c) {
    return outputRoads.get(c);
  }

  public boolean areAdjoining(City c1, City c2) {
    Set<Road> roads = outputRoads.get(c1);
    for (Road r : roads) {
      if (r.getDestination().equals(c2)) {
        return true;
      }
    }
    return false;
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
