import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Tester 
{
	private static ArrayList<String> myString = new ArrayList<String>();
	private static Tube myTube = new Tube(5,true);
		
	public static void main(String[] args)
	{
		myTube.setFileName("50-126-5");
		defineTube(myTube);
		/*
		myString.add("50-123-1");
		myString.add("50-123-2");
		myString.add("50-123-3");
		myString.add("50-123-4");
		myString.add("50-123-5");
		int FR = fillRatio(myString);
		System.out.print(FR);
		*/
	}//end main
	
	public static void defineTube(Tube x)
	{
		ArrayList<Double> xSegments = new ArrayList<Double>();
		ArrayList<Double> ySegments = new ArrayList<Double>();
		ArrayList<Double> xPositions = new ArrayList<Double>();

		ArrayList<Integer> segBounds = new ArrayList<Integer>();

		try//put entire data set into x and y segment arrays
		{
			File dataFile = new File(String.format("C:\\Users\\48041595\\Desktop\\OHP Text Files\\%s.txt", x.getFileName()));
			Scanner input = new Scanner(dataFile);
			input.nextLine(); //skip header line
			
			while(input.hasNext()) //put all x and y values into arrays
			{
				String line = input.nextLine();
				String[] delimitedLine = line.split("\\s+");
				xSegments.add(Double.parseDouble(delimitedLine[5]));
				ySegments.add(Double.parseDouble(delimitedLine[6]));
			}//end while loop
		}//end try block
		catch(Exception e)
		{	System.out.printf("\nError opening file for tube position %d", x.getPosition());	}
	
		for(int i = 0; i < ySegments.size()-1; i++) //find segment bounds
		{
			double diff = ySegments.get(i+1)-ySegments.get(i);
			if(diff > 20)
			{
				System.out.println(ySegments.get(i+1));
				segBounds.add(i+1);
			}
		}//end for to find segBounds
		System.out.print(segBounds.get(0)+" "+segBounds.get(1)+" "+segBounds.get(2)+" "+segBounds.get(3));
		
		
		System.out.println("\n 5th from: "+(segBounds.get(3)+1) +" to "+ (xSegments.size()-2));
		System.out.println("\n 4th from: "+(segBounds.get(2)+1)+" to "+ (segBounds.get(3)-2));
		System.out.println("\n 3th from: "+(segBounds.get(1)+1)+" to "+ (segBounds.get(2)-2));
		System.out.println("\n 2th from: "+(segBounds.get(0)+1)+" to "+ (segBounds.get(1)-2));
		System.out.println("\n 1th from: "+0+" to "+ (segBounds.get(0)-2));
		
		double length;
		double position;
		xPositions.add(0.0);
		for(int i = 0; i < segBounds.get(0)-2; i++) //extract 1st segment
		{
			length = xSegments.get(i)-xSegments.get(i+1);
			position = xPositions.get(i) + length;
			xPositions.add(position);
		}
		
		//find first position for 2nd segment
		length = xSegments.get(segBounds.get(0)+1)-xSegments.get(segBounds.get(0)+2);
		position = 250.0 + length;
		xPositions.add(position);
		
		for(int i = segBounds.get(0)+1+1; i < segBounds.get(1)-2; i++) //extract 2nd segment
		{
			length = xSegments.get(i)-xSegments.get(i+1);
			position = xPositions.get(i-3) + length;
			xPositions.add(position);
		}
		
		length = xSegments.get(segBounds.get(1)+1)-xSegments.get(segBounds.get(1)+2);
		position = 500.0 + length;
		xPositions.add(position);
		
		for(int i = segBounds.get(1)+1+1; i < segBounds.get(2)-2; i++) //extract 3rg segment
		{
			length = xSegments.get(i)-xSegments.get(i+1);
			position = xPositions.get(i-6) + length;
			xPositions.add(position);
		}
		
		length = xSegments.get(segBounds.get(2)+1)-xSegments.get(segBounds.get(2)+2);
		position = 750.0 + length;
		xPositions.add(position);
		
		for(int i = segBounds.get(2)+1+1; i < segBounds.get(3)-2; i++) //extract 4th segment
		{
			length = xSegments.get(i)-xSegments.get(i+1);
			position = xPositions.get(i-9) + length;
			xPositions.add(position);
		}
		
		length = xSegments.get(segBounds.get(3)+1)-xSegments.get(segBounds.get(3)+2);
		position = 1000.0 + length;
		xPositions.add(position);
		
		for(int i = segBounds.get(3)+1+1; i < xSegments.size()-2; i++) //extract 5th segment
		{
			length = xSegments.get(i)-xSegments.get(i+1);
			position = xPositions.get(i-12) + length;
			xPositions.add(position);
		}
		
		for(int i = 0; i < xPositions.size(); i++)
		{
			x.addXPosition(xPositions.get(i));
			System.out.println(xPositions.get(i));
			System.out.println(x.xPosition.get(i));
		}


		
		//check to make sure all the ending xPos match with known values 250, 500, etc
		
		//for(int i = 3; i > -1; i--)//split data into each segment
		/*
		int i = 3;
		{
			for(int y = 0; y < xSeg1.size()-(segBounds.get(i)-1) ; y++)
			{
				System.out.println("interval length: " + (xSeg1.size()-(segBounds.get(i)-1)));
				System.out.println("start index: " +segBounds.get(i)+y);
				
				xSeg5.add(xSeg1.get(segBounds.get(i)+y));
				
				System.out.println(xSeg5.get(segBounds.get(i)+y));
			}
		}//end for to split segments
		*/
	}//end defineTube
	
	
	public static void defineTubeOLD(Tube x)
	{
		ArrayList<Double> xSeg1 = new ArrayList<Double>();
		ArrayList<Double> ySeg1 = new ArrayList<Double>();
		ArrayList<Double> xSeg2 = new ArrayList<Double>();
		ArrayList<Double> ySeg2 = new ArrayList<Double>();
		ArrayList<Double> xSeg3 = new ArrayList<Double>();
		ArrayList<Double> ySeg3 = new ArrayList<Double>();
		ArrayList<Double> xSeg4 = new ArrayList<Double>();
		ArrayList<Double> ySeg4 = new ArrayList<Double>();
		ArrayList<Double> xSeg5 = new ArrayList<Double>();
		ArrayList<Double> ySeg5 = new ArrayList<Double>();
		ArrayList<Integer> segBounds = new ArrayList<Integer>();

		try//put entire data set into seg1 arrays
		{
			File dataFile = new File(String.format("C:\\Users\\48041595\\Desktop\\OHP Text Files\\%s.txt", x.getFileName())); //edit directory for different user
			Scanner input = new Scanner(dataFile);
			input.nextLine(); //skip header line
			
			while(input.hasNext()) //put all x and y values into arrays
			{
				String line = input.nextLine();
				String[] delimitedLine = line.split("\\s+");
				xSeg1.add(Double.parseDouble(delimitedLine[5]));
				ySeg1.add(Double.parseDouble(delimitedLine[6]));
			}//end while loop
		}//end try block
		catch(Exception e)
		{	System.out.printf("\nError opening file for tube position %d", x.getPosition());	}
	
		for(int i = 0; i < ySeg1.size()-1; i++) //find segment bounds
		{
			double diff = ySeg1.get(i+1)-ySeg1.get(i);
			if(diff > 20)
			{
				System.out.println(ySeg1.get(i+1));
				segBounds.add(i+2);
			}
		}//end for to find segBounds
		System.out.print(segBounds.get(3));
		
		
		
		//for(int i = 3; i > -1; i--)//split data into each segment
		int i = 3;
		{
			for(int y = 0; y < xSeg1.size()-(segBounds.get(i)-1) ; y++)
			{
				System.out.println("interval length: " + (xSeg1.size()-(segBounds.get(i)-1)));
				System.out.println("start index: " +segBounds.get(i)+y);
				
				xSeg5.add(xSeg1.get(segBounds.get(i)+y));
				
				System.out.println(xSeg5.get(segBounds.get(i)+y));
			}
		}//end for to split segments
		
	}//end defineTube

	public static int fillRatio(ArrayList<String> x)
	{
		String s = x.get(0);
		if(s.equals("0"))
		{
			s = x.get(2);
			if(s.equals("0"))
				s = x.get(4);
		}
		String[] parsed = s.split("-");
		return Integer.parseInt(parsed[0]);
	}
}
