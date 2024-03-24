package domaine.utils;

public class Util {

  /**
   * Calculate the distance between two points in latitude and longitude.
   *
   * @param lat1 Latitude of the first point
   * @param lon1 Longitude of the first point
   * @param lat2 Latitude of the second point
   * @param lon2 Longitude of the second point
   * @return Distance in kilometers
   */
  public static double distance(double lat1, double lon1, double lat2, double lon2) {
    double earthRadius = 6371; // Radius of the Earth in kilometers

    double latDistance = Math.toRadians(lat2 - lat1);
    double lonDistance = Math.toRadians(lon2 - lon1);

    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
        + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
        * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    double distance = earthRadius * c;

    return distance;
  }
}
