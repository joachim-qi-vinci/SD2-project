import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;


public class Graph {

  // Map id - City
  protected Map<String, City> correspondanceIdCity;
  // Map source - dest
  protected Map<City, Set<Road>> sourceDestRoads;


  public Graph(File _cities, File _roads) {
    correspondanceIdCity = new HashMap<>();
    sourceDestRoads = new HashMap<>();
    Scanner scanner = null;
    try {
      scanner = new Scanner(_cities);

      while (scanner.hasNextLine()) {
        String[] arrOfString = scanner.nextLine().split(",");
        // convert text id to int
        int id = Integer.parseInt(arrOfString[0]);
        // taking city name
        String cityName = arrOfString[1];
        // convert String longitude to double
        double longitude = Double.parseDouble(arrOfString[2]);
        // convert String latitude to double
        double latitude = Double.parseDouble(arrOfString[3]);
        City city = new City(id, cityName, longitude, latitude);
        Set<Road> set = new HashSet<>();
        sourceDestRoads.put(city, set);
        ajouterSommet(city);
      }
      scanner.close();
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    try {
      scanner = new Scanner(_roads);
      while (scanner.hasNextLine()) {
        String[] arrOfString = scanner.nextLine().split(",");
        String sourceCityId = arrOfString[0];
        City source = correspondanceIdCity.get(sourceCityId);
        String destinationCityId = arrOfString[1];
        City dest = correspondanceIdCity.get(destinationCityId);
        double dist = Util.distance(source.getLatitude(), source.getLongitude(), dest.getLatitude(), dest.getLongitude());
        Road road = new Road(source, dest, dist);
        ajouterRoute(road);
      }
    } catch ( FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  protected void ajouterSommet(City c) {
    correspondanceIdCity.put(String.valueOf(c.getId()), c);
  }

  protected void ajouterRoute(Road r) {
    Set<Road> srcSet = sourceDestRoads.get(r.getSource());
    Set<Road> destSet = sourceDestRoads.get(r.getDestination());
    srcSet.add(r);
    destSet.add(r);
  }

  public Set<Road> arcsSortants(City a) {
    return null;
  }

  public boolean sontAdjacents(City a1, City a2) {
    return false;
  }

  public void calculerItineraireMinimisantNombreRoutes(String source, String dest) {
    return;
  }

  public void calculerItineraireMinimisantKm(String source, String dest) {
    return;
  }

  // pile dans la methode

}