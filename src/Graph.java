import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class Graph {

	protected Map<String, City> correspondanceNomVille ;

	public void Graph(){
		correspondanceNomVille= new HashMap<>();
	}

	public void constructFromTXT(File cities, File roads){

		HashMap<Integer, City> citiesTab = new HashMap<>();

		// insert all cities
		try (Scanner scanner = new Scanner(cities)) {

			while(scanner.hasNextLine()){
				String[] elems = scanner.next().split(",");
				int id = Integer.parseInt(elems[0]);
				String cityName = elems[1];
				double longitude = Double.parseDouble(elems[2]);
				double latitude = Double.parseDouble(elems[3]);
				City c = new City(id, cityName, longitude,latitude);
				correspondanceNomVille.put(cityName, c);
				ajouterSommet(c);
				citiesTab.put(id, c);
			}
    } catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

		// insert all road
		try (Scanner scanner = new Scanner(roads)) {
			while(scanner.hasNextLine()){
				String[] elems = scanner.next().split(",");
				int idSrc = Integer.parseInt(elems[0]);
				int idDest = Integer.parseInt(elems[1]);
				Road r = new Road(citiesTab.get(idSrc), citiesTab.get(idDest));
				ajouterArc(r);
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	public City getAirport(String nom) {
		return correspondanceNomVille.get(nom);
	}

	protected abstract void ajouterSommet(City c);

	protected abstract void ajouterArc(Road r);
	
	public abstract Set<Road> arcsSortants(Road r);

	public abstract boolean sontAdjacents(Road r1, Road r2);


	public abstract void calculerItineraireMinimisantKm(String city1, String city2);

	public abstract void calculerItineraireMinimisantNombreRoutes(String city1, String city2);
}
