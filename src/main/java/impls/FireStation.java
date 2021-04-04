package impls;

import api.CityNode;

public class FireStation extends BuildingImpl {

  public FireStation(CityNode location) {
    super(location, true);
  }

  public FireStation(CityNode location, boolean fireproof) {
    super(location, fireproof);
  }

}
