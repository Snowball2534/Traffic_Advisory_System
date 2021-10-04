import java.io.*;
import java.util.Objects;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.InputMismatchException;
public class BackEnd {
	
	 /**
     * Data objects involve the number and the name of a city as the type to pass to T
     * The key in hashtable
     */
	protected class Data {
		public int number;
		public String name;
		
		public Data(int number, String name) {
			this.number = number;
			this.name = name;
		}
		
		public void printData() {
			System.out.println(this.number + ". " + this.name);
		}

		public int getNumber()
		{
			return this.number;
		}

		public String getName()
		{
			return this.name;
		}

		@Override
		public boolean equals(Object obj){
			if(this == obj) return true;
		        if(!(obj instanceof Data)) return false;
			Data d = (Data) obj;
			return (Objects.equals(getNumber(), d.getNumber())&&
					Objects.equals(getName(), d.getName()));
		}

		@Override
		public int hashCode(){
			return Objects.hash(getNumber(),getName());
		}
	}
	
	 /**
     * Information objects store all information of a city, include data object,
     * description and interests
     */
	protected class Information {
		public Data data;
		public String description;
		public String interests;
		
		public Information(int number, String name, String description, String interests) {
			this.data = new Data(number, name);
			this.description = description;
			this.interests = interests;
		}
		
		public Information(Data data, String description, String interests) {
			this.data = data;
			this.description = description;
			this.interests = interests;
		}
		
		public void printInfo() {
			data.printData();
			System.out.println("Description: " + this.description);
			System.out.println("Tourist Attraction: " + this.interests);
		}
	}
	
	protected ArrayList<Information> info = new ArrayList<>(); // holds the information of the city
	                                                           // index corresponds to the number of the city
	protected CS400Graph<Data> graphCarTime = new CS400Graph<>(), graphCarCost = new CS400Graph<>(); 
	protected CS400Graph<Data> graphTrainTime = new CS400Graph<>(), graphTrainCost = new CS400Graph<>();
	protected CS400Graph<Data> graphPlaneTime = new CS400Graph<>(), graphPlaneCost = new CS400Graph<>();
	
    /**
     * Constructor funtion: read data from external files
     * 
     * @param infoFileName the name of the file storing the information of the city
     * @param carTime the name of the file storing the time graph of the car
     * @param carCost the name of the file storing the cost graph of the car
     * @param trainTime the name of the file storing the time graph of the train
     * @param trainCost the name of the file storing the cost graph of the train 
     * @param planeTime the name of the file storing the time graph of the plane
     * @param planeCost the name of the file storing the cost graph of the plane
     */
	public BackEnd(String infoFileName, String carTime, String carCost, String trainTime, 
			String trainCost, String planeTime, String planeCost) {
			     this.infoDataInput(info, infoFileName);
			     this.graphDataInput(graphCarTime, carTime);
			     this.graphDataInput(graphCarCost, carCost);
			     this.graphDataInput(graphTrainTime, trainTime);
			     this.graphDataInput(graphTrainCost, trainCost);
			     this.graphDataInput(graphPlaneTime, planeTime);
			     this.graphDataInput(graphPlaneCost, planeCost);
	}
	
    /**
     * Read the information of the city from external files
     * 
     * @param info the ArrayList to store the information in the program
     * @param infoFileName the name of the file storing the information of the city
     * @return ture if the reading operation is successful, false if it is unsuccessful
     */
	protected boolean infoDataInput(ArrayList<Information> info, String infoFileName) {
		try{
			BufferedReader sc = new BufferedReader(new FileReader(infoFileName));
			int number;
			String s, name, description, interests;
			
			while((s = sc.readLine()) != null) {
				 number = Integer.parseInt(s); // for each city, the first line is the number
				 name = sc.readLine(); // the second line is the name
			     description = sc.readLine(); // the third line is the description
			     interests = sc.readLine(); // the fourth line is the attractions
			     Information city = new Information(number, name, description, interests);
			     if(number == 1)
				     info.add(city);
			     info.add(city);
			}
			sc.close();
			
			return true;
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
			return false;
		}
	}
	
    /**
     * Read the graph from external files
     * 
     * @param graph the CS400Graph to store the graph in the program
     * @param graphFileName the name of the file storing the graph
     * @return ture if the reading operation is successful, false if it is unsuccessful
     */
	protected boolean graphDataInput(CS400Graph<Data> graph, String graphFileName) {
		try{
			Scanner sc = new Scanner(new BufferedReader(new FileReader(graphFileName)));
		    
			Data source, target;
			int weight;
			while(sc.hasNext()) {
				 source = new Data(sc.nextInt(), sc.next());
				 target = new Data(sc.nextInt(), sc.next());
				 weight = sc.nextInt();
			     
				 graph.insertVertex(source);
				 graph.insertVertex(target);
				 graph.insertEdge(source, target, weight);
			}
			sc.close() ;
			
			return true;
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
			return false;
		}
	}
	
    /**
     * Print the information of the city
     */
	public void printInfo() {
		if(info.size() < 2)
			System.out.println("There is no city!");
		for(int i = 1; i < info.size(); i++)
			info.get(i).printInfo();
	}

     /**
      * Print the time graph for each transportation
      */	
	public void printGraphByTime()
	{
		System.out.println("*************************************************************************");
		System.out.println("-1 means unreachable!");
		System.out.println("*************************************************************************");
		System.out.println("The Time Graph of Car:");
		printGraphHelper(info, graphCarTime);
		System.out.println("The Time Graph of Train:");
		printGraphHelper(info, graphTrainTime);
		System.out.println("The Time Graph of Plane:");
		printGraphHelper(info, graphPlaneTime);
	}
	
      /**
       * Print the cost graph for each transportation
       */
	public void printGraphByCost()
	{
		System.out.println("*************************************************************************");
		System.out.println("-1 means unreachable!");
		System.out.println("*************************************************************************");
		System.out.println("The Cost Graph of Car:");
		printGraphHelper(info, graphCarCost);
		System.out.println("The Cost Graph of Train:");
		printGraphHelper(info, graphTrainCost);
		System.out.println("The Cost Graph of Plane:");
		printGraphHelper(info, graphPlaneCost);
	}

      /**
       * Print a CS400Graph and the complete information of the graph is stored in info
       *
       * @param info the arraylist storing the complete information of the graph
       * @param graph the graph to be printed
       */
	protected void printGraphHelper(ArrayList<Information> info, CS400Graph<Data> graph) {
		int size = info.size();
		int[][] adjMatrix = new int[size][size];
		for (int i = 1; i < size; i++)   // initialize the adjacency matrix
			for(int j = 1; j < size; j++)
			{
				if(i == j)
					adjMatrix[i][j] = 0;  // the distance of city to its own is 0
				else
					adjMatrix[i][j] = -1; // -1 represents unreachable
			}
		for(int i = 1; i < info.size(); i++)
		{
			Data key = info.get(i).data; // iterate each city stored in info
			if(graph.vertices.containsKey(key)) { // if the city is in the graph, iterate each edge to other cities
			Iterator<CS400Graph<BackEnd.Data>.Edge> it = graph.vertices.get(key).edgesLeaving.iterator();
			while(it.hasNext())
			{
				var e = it.next();
				adjMatrix[i][e.target.data.number] = e.weight;
			}}
		}
		System.out.print("    ");
		for (int i = 1; i < size; i++)
			System.out.format("%4d", i);
		System.out.println();
		for (int i = 1; i < size; i++) // print out the adjacency matrix
		{
			System.out.format("%4d", i);
				for(int j = 1; j < size; j++)
				{
					System.out.format("%4d", adjMatrix[i][j]);
				}
			System.out.println();
		}
		System.out.println("*************************************************************************");
	}

	// the transportation name for each way
	protected enum Transportation{
		CAR, TRAIN, PLANE
		}
	
      /**
        * ShortestPath objects store the shortest path for one transportation of time or cost. It implements
        * the interface of Comparable for the priority queue used in the following methods.
        */
	protected class ShortestPath implements Comparable<ShortestPath>{
		public Data start;
		public Data end;
		public Transportation trans;
		public int distance;
		public List<Data> dataSequence;
		
		public ShortestPath(CS400Graph<Data> graph, Transportation trans, Data start, Data end)
		{
			this.trans = trans;
			this.start = start;
			this.end = end;
			this.distance = graph.getPathCost(start, end);
			this.dataSequence = graph.shortestPath(start, end);
		}
		
        public int compareTo(ShortestPath other) {
            int cmp = this.distance - other.distance;
            if(cmp != 0) return cmp;
            return this.trans.compareTo(other.trans); // if the distance is the same, compare by the trans 
        }
	}
	
      /**
       *  Print the sorted shortest path by time for each transportation
       *
       *  @throws NoSuchElementException if the input of either start city or end city does not exist
       *  @return true if output successfully, false for other cases
       */ 
	public boolean pathByTime()
	{
	int number1, number2;
	String name1, name2;
	Scanner obj = new Scanner(System.in);
	System.out.println("*************************************************************************");
	try {
	System.out.println("-> Please input the starting city (number AND name): ");
	number1 = obj.nextInt();
	name1 = obj.next();
        System.out.println("-> Please input the ending city (number AND name): ");
	number2 = obj.nextInt();
	name2 = obj.next();
	} catch (InputMismatchException e) {
		System.out.println("Illegal Input!");
		return false;
	}
	Data start = new Data(number1, name1);
	Data end = new Data(number2, name2);
	if (!info.get(start.number).data.equals(start) || !info.get(end.number).data.equals(end)) 
		// throw an exception when there is no such city
		throw new NoSuchElementException("No vertex containing start or end can be found");
       	PriorityQueue<ShortestPath> queue = new PriorityQueue<>();
	        // add each shortest path into priorityqueue for sorting
		try {
		queue.add(new ShortestPath(graphCarTime, Transportation.CAR, start, end));
		}
		catch (NoSuchElementException e) {}
		try {
		queue.add(new ShortestPath(graphTrainTime, Transportation.TRAIN, start, end));
		}
		catch (NoSuchElementException e) {}
		try {
		queue.add(new ShortestPath(graphPlaneTime, Transportation.PLANE, start, end));
		}
		catch (NoSuchElementException e) {}
	// print out the results
        int i = 1;
        while(!queue.isEmpty()) {
        	ShortestPath p = queue.poll();
		System.out.println(i + ". " + p.trans + "  "+ p.distance + "min");
        	Iterator<Data> it_ = p.dataSequence.iterator();
    		System.out.print("  ");
        	while(it_.hasNext())
        	{
        		Data d = it_.next();
        		System.out.print("(" + d.number + " " + d.name + ")  ");
        	}
        	System.out.println();
        	i++;
        }
	return true;
	}

      /**
        * Print the sorted shortest path by cost for each transportation
	*
	* @throws NoSuchElementException if either start city or end city does not exist
	* @return true if output successfully, false for other cases
	*/
	public boolean pathByCost()
	{
	int number1, number2;
	String name1, name2;
	Scanner obj = new Scanner(System.in);
	System.out.println("*************************************************************************");
        try{
	       	System.out.println("-> Please input the starting city (number AND name): ");
		number1 = obj.nextInt();
		name1 = obj.next();
                System.out.println("-> Please input the ending city (number AND name): ");
		number2 = obj.nextInt();
		name2 = obj.next();
	} catch (InputMismatchException e){
		System.out.println("Illegal Input!");
		return false;
	}
	Data start = new Data(number1, name1);
	Data end = new Data(number2, name2);
        if (!info.get(start.number).data.equals(start)||!info.get(end.number).data.equals(end))
		// throw an exception when there is no such ciy
        	throw new NoSuchElementException("No vertex containing start or end can be found");
        PriorityQueue<ShortestPath> queue = new PriorityQueue<>();
	        // add each shortest path into priority queue for sorting
		try {
		queue.add(new ShortestPath(graphCarCost, Transportation.CAR, start, end));
		} catch (NoSuchElementException e) {}
		try {
		queue.add(new ShortestPath(graphTrainCost, Transportation.TRAIN, start, end));
		} catch (NoSuchElementException e) {}
		try {
		queue.add(new ShortestPath(graphPlaneCost, Transportation.PLANE, start, end));
		} catch (NoSuchElementException e) {}
	//print out the results
        int i = 1;
        while(!queue.isEmpty()) {
        	ShortestPath p = queue.poll();
        	System.out.println(i + ". " + p.trans + "  " + "$" + p.distance);
        	Iterator<Data> it_ = p.dataSequence.iterator();
    		System.out.print("  ");
        	while(it_.hasNext())
        	{
        		Data d = it_.next();
        		System.out.print("(" + d.number + " " + d.name + ")  ");
        	}
        	System.out.println();
        	i++;
        }

	return true;
	}
}
