package hello;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by diegoalves on 24/12/15.
 */
public class GreetingControllerTest {

    @BeforeMethod
    public void setUp() throws Exception {

    }

    @Test
    public void testGreeting() throws Exception {
        assertTrue(true);
    }
}