import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;
import java.util.Map;

public class BikeTest {

	@Test
	public void testGetSignal() {
		Bike b = new Bike();
		double value = b.getSignal("Ken");
		assertEquals(1.0,value,0.0);
	}

	@Test
	public void testPutSignalNewName() {
		Bike b = new Bike();
		double v = 8.0;
		String s = "Sean";
		b.putSignal(s, v);
		
		//Reference
		Map<String,Double> map = new HashMap<String, Double>();
        map.put("Ken", 1.0);
        map.put("James", 2.0);
        map.put("Kate", 3.0);
        map.put("Ish", 5.0);
        map.put("Nathan", 6.0);
        map.put("Sean", 8.0);
        
        //Check
        assertEquals(map,b.getData());
	}
	
	@Test
	public void testPutSignalExistingName() {
		Bike b = new Bike();
		double v = 8.0;
		String s = "Ish";
		b.putSignal(s, v);
		
		//Reference
		Map<String,Double> map = new HashMap<String, Double>();
        map.put("Ken", 1.0);
        map.put("James", 2.0);
        map.put("Kate", 3.0);
        map.put("Ish", 8.0);
        map.put("Nathan", 6.0);
        
        //Check
        assertEquals(map, b.getData());
	}
	
	@Test
	public void testPutSignalDuplicateValue() {
		Bike b = new Bike();
		double v = 3.0;
		String s = "Sean";
		b.putSignal(s, v);
		
		//Reference
		Map<String,Double> map = new HashMap<String, Double>();
        map.put("Ken", 1.0);
        map.put("James", 2.0);
        map.put("Kate", 3.0);
        map.put("Ish", 5.0);
        map.put("Nathan", 6.0);
        map.put("Sean", 3.0);
        
        //Check
        assertEquals(map,b.getData());
	}

	@Test
	public void testGetData() {
		Bike b = new Bike();
		Map<String,Double> map = b.getData();
		
		Map<String,Double> mapRef = new HashMap<String, Double>();
        mapRef.put("Ken", 1.0);
        mapRef.put("James", 2.0);
        mapRef.put("Kate", 3.0);
        mapRef.put("Ish", 5.0);
        mapRef.put("Nathan", 6.0);
        
        assertEquals(mapRef,map);

	}
	
	@Test
	public void testGetDataAfterAdd() {
		Bike b = new Bike();
		Map<String,Double> map = b.getData();
		b.putSignal("Sean",8.0);
		
		Map<String,Double> mapRef = new HashMap<String, Double>();
        mapRef.put("Ken", 1.0);
        mapRef.put("James", 2.0);
        mapRef.put("Kate", 3.0);
        mapRef.put("Ish", 5.0);
        mapRef.put("Nathan", 6.0);
        mapRef.put("Sean", 8.0);
        
        assertEquals(mapRef,map);

	}
	
	@Test
	public void testGetDataAfterReplace() {
		Bike b = new Bike();
		Map<String,Double> map = b.getData();
		b.putSignal("Ish",8.0);
		
		Map<String,Double> mapRef = new HashMap<String, Double>();
        mapRef.put("Ken", 1.0);
        mapRef.put("James", 2.0);
        mapRef.put("Kate", 3.0);
        mapRef.put("Ish", 8.0);
        mapRef.put("Nathan", 6.0);
        
        assertEquals(mapRef,map);

	}

}
