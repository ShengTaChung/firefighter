package firefighters;

import api.CityNode;
import api.Firefighter;

public class FirefighterImpl implements Firefighter {

  private CityNode currentLocation;
  private int distanceTraveled;

  public FirefighterImpl(CityNode currentLocation) {
    this.currentLocation = currentLocation;
  }

  @Override
  public CityNode getLocation() {
    return currentLocation;
  }

  @Override
  public void updateLocation(CityNode cityNode) {
    currentLocation = cityNode;
  }

  @Override
  public int distanceTraveled() {
    return distanceTraveled;
  }

  @Override
  public void addDistanceTraveled(int distance) {
    distanceTraveled = distanceTraveled +distance;
  }

}
