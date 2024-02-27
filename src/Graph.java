import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class Graph {

  // Map id - City
  protected Map<String, City> correspondanceIdCity;


  public Graph(File _cities, File _roads) {
    correspondanceIdCity = new HashMap<>();
    Scanner scanner = null;
    try {
      scanner = new Scanner(_cities);

      while(scanner.hasNextLine()){
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
        correspondanceIdCity.put(arrOfString[0], city);
      }

      scanner.close();
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }




  }

  protected  void ajouterSommet(City a){
    return;
  }

  protected void ajouterArc(Road f){
    return;
  }

  public Set<Road> arcsSortants(City a){
    return null;
  }

  public boolean sontAdjacents(City a1, City a2){
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