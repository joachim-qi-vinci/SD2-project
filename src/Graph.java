import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public abstract class Graph {

  protected Map<String, City> correspondanceNomVille;


  public Graph() {
    correspondanceNomVille = new HashMap<>();
  }

  public void construireTxt(File cities, File roads) {
    HashMap<Integer, City> citiesTab = new HashMap<>();

    // insert all cities
    try (Scanner scanner = new Scanner(cities)) {

      while (scanner.hasNextLine()) {

        String[] elems = scanner.nextLine().split(",");
        int id = Integer.parseInt(elems[0]);
        String cityName = elems[1];
        double latitude = Double.parseDouble(elems[2]);
        double longitude = Double.parseDouble(elems[3]);
        City c = new City(id, cityName, latitude, longitude);
        correspondanceNomVille.put(cityName, c);
        addCity(c);
        citiesTab.put(id, c);
      }
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    // insert all road
    try (Scanner scanner = new Scanner(roads)) {
      while (scanner.hasNextLine()) {
        String[] elems = scanner.nextLine().split(",");
        int idSrc = Integer.parseInt(elems[0]);
        int idDest = Integer.parseInt(elems[1]);
        Road r = new Road(citiesTab.get(idSrc), citiesTab.get(idDest));
        addRoad(r);
      }
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public City getAirport(String nom) {
    return correspondanceNomVille.get(nom);
  }

  protected abstract void addCity(City c);

  protected abstract void addRoad(Road r);

  public abstract Set<Road> roadsFromCity(City c);

  public abstract boolean areAdjoining(City c1, City c2);

  public abstract void calculerItineraireMinimisantKm(String city1, String city2);

  public abstract void calculerItineraireMinimisantNombreRoutes(String city1, String city2);
}
