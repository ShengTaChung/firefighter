package firefighters;


import api.City;
import api.CityNode;
import api.FireDispatch;
import api.Firefighter;
import api.exceptions.NoFireFoundException;
import api.exceptions.NoFirefighterFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;


import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FireDispatchImpl implements FireDispatch {
  private City city;
  private List<Firefighter> firefightersAtStation;
  private List<Firefighter> firefightersNotAtStation;


  public FireDispatchImpl(City city) {
    this.city = city;
    firefightersAtStation = new ArrayList<>();
    firefightersNotAtStation = new ArrayList<>();
  }

  @Override
  public void hireFirefighters(int numFirefighters) {
    for (int i = 0; i < numFirefighters; i++) {
      firefightersAtStation.add(new FirefighterImpl(city.getFireStation().getLocation()));
    }

  }

  @Override
  public List<Firefighter> getFirefighters() {
    List<Firefighter> allFirefighters = new ArrayList<>(firefightersAtStation);
    allFirefighters.addAll(firefightersNotAtStation);
    return allFirefighters;
  }

  @Override
  public void dispatchFirefighters(CityNode... burningBuildings) throws NoFirefighterFoundException {
    for (CityNode cityNode : burningBuildings) {
      if (CollectionUtils.isEmpty(getFirefighters())) {
        throw new NoFirefighterFoundException();
      }

      try {
        findClosestFirefighter(cityNode);
        city.getBuilding(cityNode).extinguishFire();
      } catch (NoFireFoundException ex) {
        log.error("The building is not on fire. xCoordinate: {}, yCoordinate: {}.",
                city.getBuilding(cityNode).getLocation().getX(),
                city.getBuilding(cityNode).getLocation().getY());
      } catch (Exception ex) {
        log.error("Error dispatching fire. xCoordinate: {}, yCoordinate: {}.",
                city.getBuilding(cityNode).getLocation().getX(),
                city.getBuilding(cityNode).getLocation().getY());
      }

    }
  }


  private void findClosestFirefighter(CityNode cityNode) {
    CityNode fireStation = city.getFireStation().getLocation();
    if (cityNode.equals(fireStation) && !CollectionUtils.isEmpty(firefightersAtStation)) {
      return;

    } else {
      int minSteps;
      Firefighter closestFirefighter;

      if (!CollectionUtils.isEmpty(firefightersAtStation)) {
        minSteps = cityNode.getX() + cityNode.getY();
        closestFirefighter = firefightersAtStation.get(0);
      } else {
        Firefighter firefighter = firefightersNotAtStation.get(0);
        minSteps = Math.abs(cityNode.getX() - firefighter.getLocation().getX())
                + Math.abs(cityNode.getY() - firefighter.getLocation().getY());
        closestFirefighter = firefightersNotAtStation.get(0);
      }

      for (Firefighter firefighter : firefightersNotAtStation) {
        int steps = Math.abs(cityNode.getX() - firefighter.getLocation().getX())
                + Math.abs(cityNode.getY() - firefighter.getLocation().getY());

        if (steps < minSteps) {
          minSteps = steps;
          closestFirefighter = firefighter;
        }

      }

      if (closestFirefighter.getLocation().equals(fireStation)) {
        firefightersAtStation.remove(0);
        firefightersNotAtStation.add(closestFirefighter);
      }

      if (cityNode.equals(fireStation)) {
        firefightersNotAtStation.remove(closestFirefighter);
        firefightersAtStation.add(closestFirefighter);
      }

      closestFirefighter.addDistanceTraveled(minSteps);
      closestFirefighter.updateLocation(cityNode);
    }
  }

}
