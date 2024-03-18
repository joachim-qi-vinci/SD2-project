import java.io.File;

public class Main {

  public static void main(String[] args) {
    File cities = new File("cities.txt");
    File roads = new File("roads.txt");
    Graph BFS = new BFS(cities, roads);
    BFS.calculerItineraireMinimisantNombreRoutes("Berlin", "Domokos");
    System.out.println("--------------------------");
    //graph.calculerItineraireMinimisantKm("Berlin", "Madrid");
  }

}
