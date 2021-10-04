import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.PriorityQueue;
import java.util.NoSuchElementException; 

@DisplayName("Test BackEnd")
public class TestFinalProject{
       // store the information of tested city from external file	
       protected class DataInfo {
		public int number;
		public String name;
		public String description;
		public String interests;
		
		public DataInfo(int number, String name, String description, String interests) {
			this.number = number;
			this.name = name;
			this.description = description;
			this.interests = interests;
		}
	}
	
	protected BackEnd _instance = null;
        
	// create BackEnd before each test
	@BeforeEach
	public void createInstance() {
		_instance = new BackEnd("infoFile.txt", "carTime.txt", "carCost.txt", "trainTime.txt",
				"trainCost.txt", "planeTime.txt", "planeCost.txt");
	}

	@DisplayName("Test if reading correctly from external infoFile")
	@Test
	public void testFuntion_1() {
		DataInfo d1 = new DataInfo(2, "Los_Angeles", "Los Angeles is a sprawling Southern California city "
				+ "and the center of the nation's film and television industry. "
				+ "Near its iconic Hollywood sign, studios such as Paramount Pictures, "
				+ "Universal and Warner Brothers offer behind-the-scenes tours.", 
				"The Getty, Universal Studios Hollywood, Santa Monica Pier");
		DataInfo d2 = new DataInfo(6, "Seattle", "Seattle, a city on Puget Sound in the Pacific Northwest, "
				+ "is surrounded by water, mountains and evergreen forests, "
				+ "and contains thousands of acres of parkland.",
				"Space Needle, Museum of Pop Culture, Chihuly Garden and Glass");
		DataInfo d3 = new DataInfo(10, "San_Jose", "San Jose is a large city surrounded by "
				+ "rolling hills in Silicon Valley, a major technology hub in California's Bay Area.",
				"Winchester Mystery House, California's Great America, The Tech Interactive");
		// test if the information stored in the program is the same as the external file
		assertEquals(d1.number, _instance.info.get(d1.number).data.number);
		assertEquals(d1.name, _instance.info.get(d1.number).data.name);
		assertEquals(d1.description, _instance.info.get(d1.number).description);
		assertEquals(d1.interests, _instance.info.get(d1.number).interests);
		assertEquals(d2.number, _instance.info.get(d2.number).data.number);
		assertEquals(d2.name, _instance.info.get(d2.number).data.name);
		assertEquals(d2.description, _instance.info.get(d2.number).description);
		assertEquals(d2.interests, _instance.info.get(d2.number).interests);
		assertEquals(d3.number, _instance.info.get(d3.number).data.number);
		assertEquals(d3.name, _instance.info.get(d3.number).data.name);
		assertEquals(d3.description, _instance.info.get(d3.number).description);
		assertEquals(d3.interests, _instance.info.get(d3.number).interests);
	}
	
	@DisplayName("Test if constructing the graph correctly")
	@Test
	public void testFunction_2() {
		// test the data structure of graph
		assertFalse(_instance.graphTrainCost.isEmpty());
		assertEquals(10, _instance.graphTrainCost.getVertexCount());
		assertEquals(26, _instance.graphTrainCost.getEdgeCount());
		BackEnd.Data d1 = _instance.new Data(3, "Chicago");
		BackEnd.Data d2 = _instance.new Data(4, "Houston");
		BackEnd.Data d3 = _instance.new Data(9, "Phoenix");
		BackEnd.Data d4 = _instance.new Data(8, "Dallas");
		BackEnd.Data d5 = _instance.new Data(1, "New_York");
		BackEnd.Data d6 = _instance.new Data(6, "Seattle");
		BackEnd.Data d7 = _instance.new Data(5, "Madison");
		BackEnd.Data d8 = _instance.new Data(15, "Ann_Arbor");
		// test the non-existing edge or node
        	assertFalse(_instance.graphTrainCost.containsVertex(d7));
		assertFalse(_instance.graphTrainCost.containsVertex(d8));
		assertFalse(_instance.graphTrainCost.containsEdge(d1, d4));
		assertFalse(_instance.graphTrainCost.containsEdge(d4, d2));
		assertTrue(_instance.graphTrainCost.containsEdge(d1, d2));
		assertTrue(_instance.graphTrainCost.containsEdge(d1, d3));
		assertTrue(_instance.graphTrainCost.containsEdge(d4, d5));
		assertTrue(_instance.graphTrainCost.containsEdge(d4, d6));
	}
	
	@DisplayName("Test if correctly computing the shortest path")
	@Test
	public void testFunction_3() {
		// test the class ShortestPath construction
		BackEnd.Data d1 = _instance.new Data(3, "Chicago");
		BackEnd.Data d2 = _instance.new Data(5, "San_Diego");
		BackEnd.Data d3 = _instance.new Data(8, "Dallas");
		BackEnd.Data d4 = _instance.new Data(7, "Boston");
		BackEnd.ShortestPath p1 = _instance.new ShortestPath(_instance.graphPlaneTime, BackEnd.Transportation.PLANE, d1, d2);
		BackEnd.ShortestPath p2 = _instance.new ShortestPath(_instance.graphCarCost, BackEnd.Transportation.CAR, d3, d4);
		assertEquals(220, p1.distance);
		assertEquals(3, p1.dataSequence.get(0).number);
		assertEquals("Chicago", p1.dataSequence.get(0).name);
		assertEquals(5, p1.dataSequence.get(1).number);
		assertEquals("San_Diego", p1.dataSequence.get(1).name);
		assertEquals(204, p2.distance);
		assertEquals(8, p2.dataSequence.get(0).number);
		assertEquals("Dallas", p2.dataSequence.get(0).name);
		assertEquals(9, p2.dataSequence.get(1).number);
		assertEquals("Phoenix", p2.dataSequence.get(1).name);
		assertEquals(7, p2.dataSequence.get(2).number);
		assertEquals("Boston", p2.dataSequence.get(2).name);
	}
	
	@DisplayName("Test if correctly handling the input of no existing city")
	@Test
	public void testFunction_4() {
		Exception e = assertThrows(NoSuchElementException.class,() -> _instance.pathByTime());
		assertEquals("No vertex containing start or end can be found", e.getMessage());
	}
	
	@DisplayName("Test if correctly sorting the paths by using the class ShortestPath")
	@Test
	public void testFunction_5() {
		// test the Comparable method in ShortestPath 
		int distance1, distance2;
		BackEnd.Data d1 = _instance.new Data(3, "Chicago");
		BackEnd.Data d2 = _instance.new Data(4, "Houston");
		PriorityQueue<BackEnd.ShortestPath> queue = new PriorityQueue<>();
		try {
		queue.add(_instance.new ShortestPath(_instance.graphCarCost, BackEnd.Transportation.CAR, d1, d2));
		} catch (NoSuchElementException e){}
		try {
		queue.add(_instance.new ShortestPath(_instance.graphTrainCost, BackEnd.Transportation.TRAIN, d1, d2));
		} catch (NoSuchElementException e){}
		try {
		queue.add(_instance.new ShortestPath(_instance.graphCarTime, BackEnd.Transportation.CAR, d1, d2));
		} catch (NoSuchElementException e){}
		try {
		queue.add(_instance.new ShortestPath(_instance.graphPlaneTime, BackEnd.Transportation.PLANE, d1, d2));
		} catch (NoSuchElementException e) {}
		distance1 = -1;
		while(!queue.isEmpty()) {
			distance2 = queue.poll().distance;
			// the previous node in the priority queue should be large than the later node
			assertTrue(distance1 < distance2);
			distance1 = distance2;
        }
	}
}
