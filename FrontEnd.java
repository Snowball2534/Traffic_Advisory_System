import java.util.Scanner;
import java.util.NoSuchElementException;

public class FrontEnd {
	public static void main(String[] args) {
		BackEnd ds = new BackEnd	("infoFile.txt", "carTime.txt", "carCost.txt", "trainTime.txt", 
				"trainCost.txt", "planeTime.txt", "planeCost.txt");// the parameters are the names of external files to read
		int command; 
		Scanner obj = new Scanner(System.in);
		// output the introduction of the system
		System.out.println("*************************************************************************");
		System.out.println("****  Welcome to Traffic Advisory System! ****");
		while(true) {
			System.out.println("*************************************************************************");
			System.out.println("** 1. Introduction to Travelling City       **");
			System.out.println("** 2. Display the Time Graph                **");
			System.out.println("** 3. Display the Cost Graph                **");
			System.out.println("** 4. Generate the Shortest Path by Time    **");
			System.out.println("** 5. Generate the Shortest Path by Cost    **");
			System.out.println("** 6. Quit the System                       **");
			System.out.println("*************************************************************************");
			System.out.println("-> Please Input the Command: ");
			command = obj.nextInt();
			switch (command) {
			case 1: ds.printInfo();
				break;
			case 2: ds.printGraphByTime();
				break;
			case 3: ds.printGraphByCost();
				break;
			case 4: 
				// handle the exception throwed by the function called for no existing city
				try {
				ds.pathByTime();
				}
				catch (NoSuchElementException e)
				{
					System.out.println(e.getMessage());
				}
				finally
				{
					break;
				}
			case 5: 
				// handle the exception throwed by the function called for no existing city
				try {
				ds.pathByCost();
				}
				catch (NoSuchElementException e)
				{
					System.out.println(e.getMessage());
				}
				finally
				{
					break;
				}
			case 6: 
				return;
				// illegal command
			default:
				System.out.println("Input Error!");
			}
		}
		}
	}
