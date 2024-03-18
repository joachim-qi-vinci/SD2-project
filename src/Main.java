import java.io.File;

public class Main {

  public static void main(String[] args) {
    File cities = new File("cities.txt");
    File roads = new File("roads.txt");
    BFS bfs = new BFS(cities, roads);
    Dijkstra dijkstra = new Dijkstra(cities, roads);
    System.out.println("------------- BFS -------------");
    bfs.calculerItineraireMinimisantNombreRoutes("Berlin", "Madrid");
    System.out.println("------------- Dijkstra -------------");
    dijkstra.calculerItineraireMinimisantKm("Berlin", "Madrid");
  }

}
