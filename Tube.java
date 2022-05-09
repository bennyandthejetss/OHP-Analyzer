//instance of a tube
import java.util.ArrayList;

public class Tube 
{
	private boolean success;
	private int tubePosition;
	private double totalTubeLength; 
	protected double calcFillRatio;
	private String fileName;
	protected ArrayList<Double> xPosition = new ArrayList<Double>();
	private ArrayList<Double> yPosition = new ArrayList<Double>();
	protected ArrayList<Double> liquidLengths = new ArrayList<Double>();
	protected ArrayList<Double> liquidPercents = new ArrayList<Double>();


	public Tube(int x, boolean y)
	{
		tubePosition = x;
		success = y;
	}//end constructor
	
	public void addLiquidLength(double x)
	{
		liquidLengths.add(x);
	}//end addLiquidLength
	
	public void addLiquidPercent(double y)
	{
		liquidPercents.add(y);
	}//end addLiquidPercent
	
	public void addXPosition(double x)
	{
		xPosition.add(x);
	}//end addXPosition
	
	public void addYPosition(double y)
	{
		yPosition.add(y);
	}//end addYPosition
	
	public void setPosition(int x)
	{ tubePosition = x; }
	
	public int getPosition()
	{ return tubePosition; }
	
	public void setFileName(String x)
	{ fileName = x; }
	
	public String getFileName()
	{ return fileName; }
	
	public boolean getSuccess()
	{ return success; }
	
	public double getTotalTubeLength()
	{
		if(tubePosition == 1)
			totalTubeLength = 1179;
		else if(tubePosition == 2)
			totalTubeLength = 1180;
		else if(tubePosition == 3)
			totalTubeLength = 1180;
		else if(tubePosition == 4)
			totalTubeLength = 1179;
		else if(tubePosition == 5)
			totalTubeLength = 1178;
		return totalTubeLength;
	}
	
	public String toString()
	{ return "Tube at position " + tubePosition; }
	
}//end Tube Class
