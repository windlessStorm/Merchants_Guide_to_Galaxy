package windlessstorm.galaxy.merchant;

public class GalaxySpeak{

	public static enum Type{
		
		ASSIGNMENT, 
		 
		CREDITS, 
		
		HOWMUCH, 
		 
		HOWMANY, 
		 
		NOMATCH
		 
	}
	
	
	public class ParseLine
	{
		private GalaxySpeak.Type type;
		private String pattern;
		public ParseLine(GalaxySpeak.Type type, String pattern)
		{
			this.type = type;
			this.pattern = pattern;
		}
		
		public String getPattern()
		{
			return this.pattern;
					
		}
		
		public GalaxySpeak.Type getType()
		{
			return this.type;
		}
	}
	
	
	
	public static String patternAssigned = "^([A-Za-z]+) is ([I|V|X|L|C|D|M])$";
	public static String patternCredits = "^([A-Za-z]+)([A-Za-z\\s]*) is ([0-9]+) ([c|C]redits)$";
	public static String patternHowMuch = "^how much is (([A-Za-z\\s])+)\\?$";
	public static String patternHowMany= "^how many [c|C]redits is (([A-Za-z\\s])+)\\?$";
	private ParseLine[] lineparser;

	
	

	public GalaxySpeak()
	{
		
		this.lineparser = new ParseLine[4];
		this.lineparser[0] = new ParseLine(GalaxySpeak.Type.ASSIGNMENT, patternAssigned);
		this.lineparser[1] = new ParseLine(GalaxySpeak.Type.CREDITS, patternCredits);
		this.lineparser[2] = new ParseLine(GalaxySpeak.Type.HOWMUCH, patternHowMuch);
		this.lineparser[3] = new ParseLine(GalaxySpeak.Type.HOWMANY, patternHowMany);
		
	}
		
		
	
	
	
	public GalaxySpeak.Type getLineType(String line)
	{
		line = line.trim();
		GalaxySpeak.Type result = Type.NOMATCH;
		
		boolean matched = false;
			
		for(int i =0;i<lineparser.length && !matched ;i++)
		{
			if( line.matches(lineparser[i].getPattern()) )
			{
				matched = true;
				result = lineparser[i].getType();
			}
			
		}
		
		return result;
		
	}
	
}
