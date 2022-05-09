//analzye inputed .txt into tables and graphs
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class DataAnalyzer 
{
	private ArrayList<String> fileNames = new ArrayList<String>();
	private int numOfPos;
	private int trialNumber;
	private int fillRatio;
	private ArrayList<Tube> tubes = new ArrayList<Tube>();
	
	public DataAnalyzer(ArrayList<String> y)
	{
		fillRatio = fillRatio(y);
		trialNumber = trialNumber(y);
		fileNames = y;
		addTube(y);
		
		//defining the tubes and skipping failed tubes
		for( int i = 0; i < 5; i++)
		{
			if(tubes.get(i).getSuccess()==false)
			{
				tubes.get(i).calcFillRatio = 0;
				continue;
			}
			else
			{
				defineTube(tubes.get(i));
				getLiquidLengths(tubes.get(i));
				getLiquidPercents(tubes.get(i));
				getFillRatio(tubes.get(i));
				numOfPos++;
			}
		}
		createDataFile(tubes);
	}//end constructor
	
	public int fillRatio(ArrayList<String> x)
	{
		String s = x.get(0);
		if(s.equals("0"))
		{
			s = x.get(1);
			if(s.equals("0"))
			{
				s = x.get(2);
				if(s.equals("0"))
				{
					s = x.get(3);
					if(s.equals("0"))
					{
						s = x.get(4);
					}
				}
			}
		}
		String[] parsed = s.split("-");
		return Integer.parseInt(parsed[0]);
	}//end fillRatio
	
	public int trialNumber(ArrayList<String> x)
	{
		String s = x.get(0);
		if(s.equals("0"))
		{
			s = x.get(1);
			if(s.equals("0"))
			{
				s = x.get(2);
				if(s.equals("0"))
				{
					s = x.get(3);
					if(s.equals("0"))
					{
						s = x.get(4);
					}
				}
			}
		}
		String[] parsed = s.split("-");
		return Integer.parseInt(parsed[1]);
	}//end trialNumber
	
	public void addTube(ArrayList<String> x)
	{
		for(int i = 0; i < 5; i++)
		{
			String s = x.get(i);
			if(s.equals("0"))
				tubes.add(new Tube(i+1, false));
			else
				tubes.add(new Tube(i+1, true));
				tubes.get(i).setFileName(fileNames.get(i));
		}//end for loop
	}//end addTube

	
	public void defineTube(Tube x)
	{
		ArrayList<Double> xSegments = new ArrayList<Double>();
		ArrayList<Double> ySegments = new ArrayList<Double>();
		ArrayList<Double> xPositions = new ArrayList<Double>();

		ArrayList<Integer> segBounds = new ArrayList<Integer>();

		try//put entire data set into x and y segment arrays
		{
			File dataFile = new File(String.format("C:\\Users\\48041595\\OneDrive - Southern Methodist University\\Desktop\\OHP Text Files\\%s.csv", x.getFileName())); //change directory for different users or use public 
			//File dataFile = new File(String.format("C:\\Users\\48041595\\Desktop\\OHP Text Files\\%s.txt", x.getFileName())); //change directory for different users or use public 
			Scanner input = new Scanner(dataFile);
			input.nextLine(); //skip header line
			
			while(input.hasNext()) //put all x and y values into arrays
			{
				String line = input.nextLine();
				String[] delimitedLine = line.split(",");
				//String[] delimitedLine = line.split("\\s+");
				xSegments.add(Double.parseDouble(delimitedLine[5]));
				ySegments.add(Double.parseDouble(delimitedLine[6]));
			}//end while loop
		}//end try block
		catch(Exception e)
		{	System.out.printf("\n!!Error opening file for tube position %d", x.getPosition());	}
	
//		System.out.println("\n**********************************");
//		System.out.printf("Tube Position %d \n",x.getPosition()); //position heading
//		System.out.println("**********************************");
	
		//System.out.println("Bound locator y value:");
		for(int i = 0; i < ySegments.size()-1; i++) //find segment bounds
		{
			double diff = ySegments.get(i+1)-ySegments.get(i);
			if(diff < -20) //make sure the "bound" points selected above the tube have y values that are smaller, otherwise need to change to diff > 20
			{
				//System.out.println(ySegments.get(i+1)); 
				segBounds.add(i+1);
			}
		}//end for to find segBounds
		
//		System.out.println("Bounds locations (txt index +1):");
//		System.out.println(segBounds.get(0)+" "+segBounds.get(1)+" "+segBounds.get(2)+" "+segBounds.get(3));
//		
//		System.out.println("Useful data sets (txt index +1):");
//		System.out.println(" 5th from: "+(segBounds.get(3)+1) +" to "+ (xSegments.size()-2));
//		System.out.println(" 4th from: "+(segBounds.get(2)+1)+" to "+ (segBounds.get(3)-2));
//		System.out.println(" 3th from: "+(segBounds.get(1)+1)+" to "+ (segBounds.get(2)-2));
//		System.out.println(" 2th from: "+(segBounds.get(0)+1)+" to "+ (segBounds.get(1)-2));
//		System.out.println(" 1th from: "+0+" to "+ (segBounds.get(0)-2));
		
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
//			System.out.println(xPositions.get(i));
//			System.out.println(x.xPosition.get(i));
		}

		//check to make sure all the ending xPos match with known values 250, 500, etc
	}//end defineTube
	
	
	public void getLiquidLengths(Tube x)
	{
		if(x.xPosition.size() % 2 != 0) //for normal cases ending in liquid slug
		{
			for(int i = 1; i < x.xPosition.size()/2+1 ;i++ )
			{
				int b = 2*i;
				int a = b-1;
				double length = x.xPosition.get(b)-x.xPosition.get(a);
				x.addLiquidLength(length);
			}
		}
		else if(x.xPosition.size() % 2 == 0) //for cases ending in vapor slug
		{
			for(int i = 1; i < x.xPosition.size()/2 ;i++ )
			{
				int b = 2*i;
				int a = b-1;
				double length = x.xPosition.get(b)-x.xPosition.get(a);
				x.addLiquidLength(length);
			}
		}
	
	}//end getLiquidLengths
	
	public void getLiquidPercents(Tube x)
	{
		if(x.xPosition.size() % 2 != 0) // cases ending in liquid slug
		{
			for(int i = 1; i < x.xPosition.size()/2+1 ;i++ )
			{
				int b = 2*i;
				double percent = x.xPosition.get(b)/x.getTotalTubeLength();
				x.addLiquidPercent(percent);
			}
		}
		else if(x.xPosition.size() % 2 == 0) // cases ending in vapor plug
		{
			for(int i = 1; i < x.xPosition.size()/2 ;i++ )
			{
				int b = 2*i;
				double percent = x.xPosition.get(b)/x.getTotalTubeLength();
				x.addLiquidPercent(percent);
			}
		}
	}//end getLiquidPercents
	
	public void getFillRatio(Tube x)
	{
		double sum = 0;
		for(int i = 0; i < x.liquidLengths.size(); i++)
		{
			sum += x.liquidLengths.get(i);
		}
		double fillRatio = sum/x.getTotalTubeLength()*100;
		x.calcFillRatio = fillRatio;
	}//end getFillRatio
	
	public void createDataFile(ArrayList<Tube> x)
	{
		try
		{
			PrintWriter pw = new PrintWriter(String.format("C:\\Users\\48041595\\OneDrive - Southern Methodist University\\Desktop\\OHP Text Files\\Data-%s-%s.csv", fillRatio, trialNumber)); //change directory for diff user
			pw.printf("%s-%s,%s", fillRatio, trialNumber,"Successful: " + numOfPos);
			pw.printf("\n1,2,3,4,5");
			pw.printf("\n%.3f,%.3f,%.3f,%.3f,%.3f",x.get(0).calcFillRatio,x.get(1).calcFillRatio,x.get(2).calcFillRatio,x.get(3).calcFillRatio,x.get(4).calcFillRatio);

			pw.printf("\n\nPosition,Lengths,Percents");
			for (int i = 0; i < 5; i++)
			{
				if(x.get(i).getSuccess() == true)
				{
					for(int b = 0; b < x.get(i).liquidLengths.size(); b++)
					{
							pw.printf("\n%d",i+1);
							pw.printf(",%.4f", x.get(i).liquidLengths.get(b)); //print lengths
							pw.printf(",%.5f", x.get(i).liquidPercents.get(b)); //print percents						
					}
				}
			}
			pw.close();
		}		
		catch (Exception e)
		{ System.out.print("\nError: Problem writing data file"); }
		
	}//end createDataFile
	
	public void createFullDataFile(ArrayList<Tube> x)
	{
		try
		{
			System.out.println("1"); ////////////////////////////////////////
			PrintWriter pw = new PrintWriter(String.format("C:\\Users\\48041595\\Desktop\\OHP Text Files\\%s-%s-FullData.csv",trialNumber, fillRatio)); //change directory for diff user
			pw.printf("%s-%s", fillRatio, trialNumber);
			System.out.println("2"); ////////////////////////////////////////
			for (int i = 0; i < 5; i++)
			{
				System.out.println("3"); ////////////////////////////////////////
				if(x.get(i).getSuccess() == true)
				{
					System.out.println("4"); ////////////////////////////////////////
					pw.printf("\n\nPosition %d,Lengths,Percents", i+1);
					System.out.println("5"); ////////////////////////////////////////
					for(int b = 0; b < x.get(i).xPosition.size(); b++)
					{
						System.out.println("6"); ////////////////////////////////////////
						pw.printf("\n%.4f", x.get(i).xPosition.get(b)); //print x positions
						if(b % 2 == 0 && b > 0)
						{
							System.out.println("7"); ////////////////////////////////////////
							pw.printf(",%.4f", x.get(i).liquidLengths.get(b/2-1)); //print lengths
							System.out.println("8"); ////////////////////////////////////////
							pw.printf(",%.4f", x.get(i).liquidPercents.get(b/2-1)); //print percents
							System.out.println("9"); ////////////////////////////////////////
						}						
					}

				}
			}
			pw.close();
		}		
		catch (Exception e)
		{ System.out.print("\nError: Problem writing full data file"); }
		
	}//end createDataFile
	

}//end DataAnalyzer
