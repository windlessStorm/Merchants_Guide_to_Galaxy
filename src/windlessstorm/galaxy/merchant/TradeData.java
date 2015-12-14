package windlessstorm.galaxy.merchant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;


public class TradeData {

	private Scanner scan;
	private GalaxySpeak galaxySpeak;
	private ErrorMessage errorMessage;
	
	private HashMap<String, String> assignments;
	
	
	private HashMap<String, String> computedData;
	
	
	private ArrayList<String> output;
	
	
	
	public TradeData()
	{
		this.scan = new Scanner(System.in);
		this.galaxySpeak = new GalaxySpeak();
		this.errorMessage = new ErrorMessage();
		this.assignments = new HashMap<String, String>();
		this.computedData = new  HashMap<String, String>();
		this.output = new ArrayList<String>();
	}
	
	
	
	

	public ArrayList<String> read()
	{
		String line;
		int count=0;
		ErrorCodes error = null;
		
		
		while(this.scan.hasNextLine() && (line = this.scan.nextLine()).length()>0 )
		{
			error = validate(line);
			
			switch(error)
			{
				case NO_IDEA :  
						this.output.add(this.errorMessage.getMessage(error));
						break;
				
				default :
						this.errorMessage.printMessage(error);
			}
			
			count++;
		}
		
		switch(count)
		{
			case 0: 
				error = ErrorCodes.NO_INPUT;
				this.errorMessage.printMessage(error);
				break;
					
			default : 
		}
		
		return this.output;
		
	}
	
	
	
	
	
	private ErrorCodes validate(String line)
	{
		
		ErrorCodes error = ErrorCodes.SUCCESS;
		
		GalaxySpeak.Type lineType = this.galaxySpeak.getLineType(line);
		
		switch(lineType)
		{
			case ASSIGNMENT : 		
					processAssignmentLine(line);
			        break;
							
			case CREDITS :			 
					processCreditsLine(line);
					break;
						    
			case HOWMUCH : 
					processHowMuchQuestion(line);
					break;
									 
			case HOWMANY : 
					processHowManyCreditsQuestion(line);
					break;
			
			default : 
					error = ErrorCodes.NO_IDEA; 
					break;
		}
				
		return error;
	}
	
	
	
	
	
	private void processAssignmentLine(String line)
	{
		
		String[] splited = line.trim().split("\\s+");
		
	
		try
		{
		
			assignments.put(splited[0], splited[2]);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			this.errorMessage.printMessage(ErrorCodes.INVALID_LINE);
			System.out.println(e.getMessage());
		}
	}
	
	
	
	
	
	
	
	private void processHowMuchQuestion(String line)
	{
		try
		{
			
			String formatted = line.split("\\sis\\s")[1].trim();
			
			
			formatted = formatted.replace("?","").trim();
			
			
			String keys[] = formatted.split("\\s+");
			
			
			String romanResult="";
			String completeResult = null;
			boolean errorOccured = false;
			
			for(String key : keys)
			{
				
				String romanValue = assignments.get(key);
				if(romanValue==null)
				{
					
					completeResult = this.errorMessage.getMessage(ErrorCodes.NO_IDEA);
					errorOccured = true;
					break;
				}
				romanResult += romanValue;
			}
			
			if(!errorOccured)
			{
				
				romanResult = RomanSystemUtil.romanToDecimal(romanResult);
				completeResult = formatted+" is "+romanResult;
			}
				
			output.add(completeResult);
			
		}
		catch(Exception e)
		{
			this.errorMessage.printMessage(ErrorCodes.INVALID_LINE);
			System.out.println(e.getMessage());
			
		}
	}
	
	

	private void processCreditsLine(String line)
	{
		try
		{
			
			String formatted = line.replaceAll("(is\\s+)|([c|C]redits\\s*)","").trim();
			
			
			String[] keys = formatted.split("\\s");
			
			
			
			String toBeComputed = keys[keys.length-2];
			float value = Float.parseFloat(keys[keys.length-1]);
			
		
			
			
			String roman="";
			
			for(int i=0;i<keys.length-2;i++)
			{
				roman += assignments.get(keys[i]);
			}
			
			int romanNumber = Integer.parseInt(RomanSystemUtil.romanToDecimal(roman));
			float credit = (float)(value/romanNumber);
			
					
			computedData.put(toBeComputed, credit+"");
		}
		catch(Exception e)
		{
			
			this.errorMessage.printMessage(ErrorCodes.INVALID_LINE);
			System.out.println(e.getMessage());
			
		}
	}
	
	
	
	
	
	private void processHowManyCreditsQuestion(String line) {
		
		try
		{
			
			String formatted = line.split("(\\sis\\s)")[1];
			
			formatted = formatted.replace("?","").trim();
			
			String[] keys = formatted.split("\\s");
			
			boolean found = false;
			String roman = "";
			String outputResult = null;
			Stack<Float> cvalues = new Stack<Float>();
			
			for(String key : keys)
			{
				found = false;
				
				String romanValue = assignments.get(key);
				if(romanValue!=null)
				{
					roman += romanValue;
					found = true;
				}
				
				String computedValue = computedData.get(key);
				if(!found && computedValue!=null)
				{
					cvalues.push(Float.parseFloat(computedValue));
					found = true;
				}
				
				if(!found)
				{
					outputResult = this.errorMessage.getMessage(ErrorCodes.NO_IDEA);
					break;
				}
			}
			
			if(found)
			{
				float res=1;
				for(int i =0;i<cvalues.size();i++)
				res *= cvalues.get(i);
					
				int finalres= (int) res;
				if(roman.length()>0)
				finalres = (int)(Integer.parseInt(RomanSystemUtil.romanToDecimal(roman))*res);
				outputResult = formatted +" is "+ finalres +" Credits";
			}
			
			this.output.add(outputResult);
			
		}
		catch(Exception e)
		{
			this.errorMessage.printMessage(ErrorCodes.INVALID_LINE);
			System.out.println(e.getMessage());
		}
		
	}
}
