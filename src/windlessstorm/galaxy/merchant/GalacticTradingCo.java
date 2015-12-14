package windlessstorm.galaxy.merchant;

import java.util.ArrayList;

public class GalacticTradingCo {

	public static void main(String[] args) {
		
		System.out.println(" *** GALACTIC TRADING COORPORATION OF SOL LIMITED *** \n\n  Welcome Traveller! \n  => Please provide trade data and querry below : ( and blank like to finish!) : \n");
		
		TradeData tradeData = new TradeData();
		
		ArrayList<String> output=tradeData.read();
		
		for(int i=0;i<output.size();i++)
		{
			System.out.println(output.get(i));
		}
		
		
	}

}
