import static org.junit.Assert.*;

import java.io.*;

import org.junit.*;

/**
 * Test class
 * @author Arno Chaidron
 *
 */
public class SensorGUITest {
    private final InputStream systemIn = System.in;

    private ByteArrayInputStream testIn;
    
    private SensorGUI gui;
    private static final double DELTA = 0.0001;
    private static final int  SLEEP_TIME = 50;
    
    // hijacks System.in for testing
    // (thanks StackOverFlow)
    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }
    
    private double processThread(String testString) {
    	double temperature;
    	
    	provideInput(testString);
    	
    	gui = new SensorGUI();
        Thread t = new Thread(gui);
        t.start();
        try {
			Thread.sleep(SLEEP_TIME);
		} catch (InterruptedException e) {
			System.out.println("no sleep during junit");
			e.printStackTrace();
		}
        temperature = gui.temp;
        gui.cont = false;
        return temperature;
    }
    
    private void processTest(double expected, String testString){
    	double temperature;

        temperature = processThread(testString);
        assertEquals(expected, temperature, DELTA);
    }

    @After
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
    }

	@Test
    public void testCase_expects5() {
    	final double testTemp = 5;
    	final String testString = "5";
    	processTest(testTemp, testString);
    }
	
	@Test
    public void testCase_withComma() {
    	final double expected = 0.1;
    	final String test = "0,1";
    	processTest(expected, test);
    }
	
	@Test
    public void testCase_InvalidPositive() {
    	final double expected = 0.0;
    	final String test = "50,01";
    	processTest(expected, test);
    }
	
	@Test
    public void testCase_InvalidNegative() {
    	final double expected = 0.0;
    	final String test = "-20,01";
    	processTest(expected, test);
    }
	
	@Test
    public void testCase_ValidMax() {
    	final double expected = 50.0;
    	final String test = "50,0";
    	processTest(expected, test);
    }
	
	@Test
    public void testCase_ValidMin() {
    	final double expected = -20.0;
    	final String test = "-20";
    	processTest(expected, test);
    }
	
	@Test
    public void testCase_Zero() {
    	final double expected = 0.0;
    	final String test = "0";
    	processTest(expected, test);
    }
	
	@Test
    public void testCase_ZeroWithComma() {
    	final double expected = 0.0;
    	final String test = "0,0";
    	processTest(expected, test);
    }
	
	@Test
    public void testCase_ValidWithSpaces() {
    	final double expected = 5.0;
    	final String test = "   5   ";
    	processTest(expected, test);
    }
	
	@Test
    public void testCase_PickLatest() {
    	final double expected = 5.0;
    	final String test = "4,3 5";
    	processTest(expected, test);
    }
	
	@Test
    public void testCase_PickLatest2() {
    	final double expected = 5.0;
    	final String test = "4,3\n 5";
    	processTest(expected, test);
    }
	
	@Test
    public void testCase_ValidThenChars() {
    	final double expected = 4.2;
    	final String test = "4,2 ValidThenChars";
    	processTest(expected, test);
    }
	
	@Test
    public void testCase_CharsThenValid() {
    	final double expected = 4.2;
    	final String test = "CharsThenValid 4,2";
    	processTest(expected, test);
    }
	
	@Test
    public void testCase_CharsThenValid2() {
    	final double expected = 4.2;
    	final String test = "CharsThenValid\n4,2";
    	processTest(expected, test);
    }
}