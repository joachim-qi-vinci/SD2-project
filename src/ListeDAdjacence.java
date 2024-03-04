import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;

public class ListeDAdjacence extends Graph {

  private Map<City, Set<Road>> outputRoads;

  public ListeDAdjacence(){
    super();
    outputRoads = new HashMap<City, Set<Road>>();

  }

  @Override
  protected void ajouterSommet(City c){
    correspondanceIdCity.put(c.getId(), c);
    outputRoads.put(c, new HashSet<>());
  }

  @Override
  protected void ajouterRoute(Road r){
    outputRoads.get(r.getSource()).add(r);
  }

  @Override
  public Set<Road> arcsSortants(City c){
    return outputRoads.get(c);
  }

  @Override
  public boolean sontAdjacents(City c1, City c2){
    for(Road r1 : outputRoads.get(c1)){
      if(r1.getDestination().equals(c2)){
        return true;
      }
    }
    for(Road r2 : outputRoads.get(c2)){
      if(r2.getDestination().equals(c1)){
        return true;
      }
    }
    return false;
  }

}

