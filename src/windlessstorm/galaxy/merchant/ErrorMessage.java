package windlessstorm.galaxy.merchant;

public class ErrorMessage {

		
	public ErrorMessage(){
		}
		
	public void printMessage(ErrorCodes error)
	{
		String message = getMessage(error);
		if(message != null)
			System.out.println(message);
	}
	
	public String getMessage(ErrorCodes error)
	{
		String message = null;
		
		switch(error)
		{
			case NO_INPUT : 
					message = "No input was specified ! Program exited";
					break;
			
			case INVALID : 
					message = "Input format is wrong ! input discarded";
					break;
				
			case INVALID_ROMAN_CHARACTER : 
					message = "Illegal character specified in roman number ! input discarded";
					break;
				
			case INVALID_ROMAN_STRING : 
					message =  "wrong Roman number, voilated roman number format";
					break;
			
			case INVALID_LINE : 
					message =  "Exception caused during processing due to incorrect line type supplied";
					break;
				
			case NO_IDEA : 
					message = "I have no idea what you are talking about";
					break;
				
			default : 
					break;
		}
		
		return message;
	}
	
}

