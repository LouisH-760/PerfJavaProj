import static org.junit.Assert.*;

import java.io.IOException;


import org.junit.jupiter.api.Test;

/**
 * Test class
 * @author Arno Chaidron
 */
class SensorLogicTest {
	private SensorLogic sensorLogic;
	private Thread sensorThread;

	public void setUpSensorLogic() {
		try {
			sensorLogic = new SensorLogic("MyVendorId", "MyProductId", "MyLocation");
		} catch (IOException e) {
			System.out.println("Error during setUpSensorLogic");
			e.printStackTrace();
		}
		sensorThread = new Thread(sensorLogic);
		sensorThread.start();
	}
	
	public void terminate() {
		sensorLogic.close();
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testConstructor() {
		setUpSensorLogic();
		String expected = "ProductID: MyProductId;VendorID: MyVendorId;Location: MyLocation";
		String actual = sensorLogic.toString();
		terminate();
		assertEquals(expected, actual);
	}
	
	@Test
	void testSetProductID() {
		setUpSensorLogic();
		String expected = "ProductID: 42;VendorID: MyVendorId;Location: MyLocation";
		sensorLogic.setProductId("42");
		String actual = sensorLogic.toString();
		terminate();
		assertEquals(expected, actual);
	}
	
	@Test
	void testSetVendorID() {
		setUpSensorLogic();
		String expected = "ProductID: MyProductId;VendorID: 42;Location: MyLocation";
		sensorLogic.setVendorId("42");
		String actual = sensorLogic.toString();
		terminate();
		assertEquals(expected, actual);
	}
	
	@Test
	void testSetLocation() {
		setUpSensorLogic();
		String expected = "ProductID: MyProductId;VendorID: MyVendorId;Location: 42";
		sensorLogic.setLocation("42");
		String actual = sensorLogic.toString();
		terminate();
		assertEquals(expected, actual);
	}

}
