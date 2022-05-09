//launch the DataAnalyzer and collect file names and exceptions
import java.util.ArrayList;
import java.util.Scanner;

public class OHPLauncher 
{
	private static ArrayList<String> fileNames = new ArrayList<String>();
	private static Scanner input = new Scanner(System.in);
	private static String fillRatio;
	
	public static void main(String[] args)
	{
		System.out.print("Fill Ratio: ");
		fillRatio = input.next();
		
		while(true)
		{
			initializer();
			DataAnalyzer OHPAnalyzer = new DataAnalyzer(fileNames);
			System.out.print("\nEnter 1 to continue, otherwise enter anything to exit: ");
			if(input.next().equals("1"))
				continue;
			else
				System.out.print("End");
				break;
		}
	}//end main
	
	public static void initializerOG()
	{
		
		System.out.println("Enter file names in the form of FR-trial#-pos# (enter 0 if tube failed)");
		for(int i = 0; i < 5; i++)
		{
			System.out.printf("Position %d Filename: ", i+1);
			fileNames.add(input.next());
		}
	}//end initializer
	
	public static void initializer()
	{
		fileNames.clear();
		System.out.print("\nTrial Number: ");
		String trial = input.next();

		System.out.println("Enter corresponding successful positions (enter 0 if tube failed)");
		for(int i = 0; i < 5; i++)
		{
			System.out.printf("Position %d: ", i+1);
			String position = input.next();
			if(position.equalsIgnoreCase("0"))
				fileNames.add("0");
			else if(position.equalsIgnoreCase(String.format("%d", i+1)))
				fileNames.add(String.format("%s-%s-%s", fillRatio, trial, position));
			else
			{
				System.out.println("Entered invalid number, re-enter position");
				i--;
				continue;
			}
		}
	}//end initializer
	
}//end OHPLauncher
