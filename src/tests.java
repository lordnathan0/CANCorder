import java.util.ArrayList;


public class tests {

	public tests() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		DBFParser parser = new DBFParser("test.dbf");
		testGetSignals(parser);
		Bike bike = new Bike(parser);
		
		Translator translator = new Translator(parser);
	}
	
	
	public static void testGetSignals(DBFParser parser) {
		ArrayList<Signal<String, Integer, Integer>> signals = parser.getSignals();
		for(Signal<String, Integer, Integer> signal : signals) {
			System.out.println(signal.signalID + ", start: " + signal.startBit + ", length: " + signal.length);
		}
	}
	
	public static void testTranslator() {
		
	}
	
	

}
