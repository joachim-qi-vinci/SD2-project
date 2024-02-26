import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class Graph {

  protected Map<String, City> correspondanceIdCity;


  public Graph(File _cities, File _roads) {
    correspondanceIdCity = new HashMap<>();
    Scanner scanner = null;
    try {
      scanner = new Scanner(_cities);

      while(scanner.hasNextLine()){
        JO
      }

      scanner.close();
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }




  }

  protected abstract void ajouterSommet(City a);

  protected abstract void ajouterArc(Road f);

  public abstract Set<Road> arcsSortants(City a);

  public abstract boolean sontAdjacents(City a1, City a2);

  public void calculerItineraireMinimisantNombreRoutes(String source, String dest) {


  }

  public void calculerItineraireMinimisantKm(String source, String dest) {
  }

  // pile dans la methode

}