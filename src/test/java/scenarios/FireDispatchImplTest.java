package scenarios;

import api.City;
import api.CityNode;
import api.FireDispatch;
import api.exceptions.NoFirefighterFoundException;
import firefighters.FireDispatchImpl;
import impls.CityImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FireDispatchImplTest {

    @Test
    public void hireFirefighters() {
        City basicCity = new CityImpl(2, 2, new CityNode(0, 0));
        FireDispatch fireDispatch = new FireDispatchImpl(basicCity);
        assertEquals(0, fireDispatch.getFirefighters().size());
        fireDispatch.hireFirefighters(1);
        assertEquals(1, fireDispatch.getFirefighters().size());
        fireDispatch.hireFirefighters(2);
        assertEquals(3, fireDispatch.getFirefighters().size());
    }

    @Test
    public void dispatchFirefightersNoFirefighters() {
        City basicCity = new CityImpl(2, 2, new CityNode(0, 0));
        FireDispatch fireDispatch = new FireDispatchImpl(basicCity);

        Assertions.assertThrows(NoFirefighterFoundException.class, () -> {
            fireDispatch.dispatchFirefighters(new CityNode(1, 1));
        });
    }

}
