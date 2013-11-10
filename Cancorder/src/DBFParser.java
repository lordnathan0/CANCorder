import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class DBFParser {

	
	public Map<Integer,ArrayList<Signal<String, Integer, Integer, Integer, String>>> dbfMap; // ONLY TEMPORARY UNTIL YOU MAKE ALL THE GET METHODS
	private ArrayList<Signal<String, Integer, Integer, Integer, String>> message;
	private ArrayList<Signal<String, Integer, Integer, Integer, String>> allSignals;
	
	private void createNewParser() {
		dbfMap = new HashMap<Integer, ArrayList<Signal<String, Integer, Integer, Integer, String>>>(100);
		allSignals = new ArrayList<Signal<String, Integer, Integer, Integer, String>>();
	}
	
	public DBFParser(String file) {
		this.createNewParser();
		this.parseFile(file);
	}

	//GetData byte array
	private final void parseFile(String file){
		try {
			Scanner fileReader = new Scanner(new File(file));
			Integer key = 0;
			while(fileReader.hasNext()) {
				String currentLine = fileReader.nextLine();
				if(currentLine.startsWith("BO_")) {
					String[] strParts = currentLine.split(" ");
					key = Integer.parseInt(strParts[1]);
					message = new ArrayList<>();
				}
				if(currentLine.startsWith(" SG_")) {
					String[] strParts = currentLine.split("[ |@]");
					Integer bits = (Integer.parseInt(strParts[5])) + Integer.parseInt(strParts[4]); // May not need anymore
					System.out.println("BITS: " + bits.toString());
					Signal<String, Integer, Integer, Integer, String> signal = new Signal<String, Integer, Integer, Integer, String>(strParts[2], Integer.parseInt(strParts[4]), Integer.parseInt(strParts[5]), Integer.parseInt(strParts[6].substring(0, 1)), strParts[6].substring(1,2));
					allSignals.add(signal);
					message.add(signal);
				}
				if(currentLine.startsWith("")) {
					if(!(dbfMap.containsKey(key) && message == null)){
						dbfMap.put(key, message);	
					}
				}
				if(currentLine.startsWith("SIG_VALTYPE_")) {
					String[] strParts = currentLine.split(" ");
					ArrayList<Signal<String, Integer, Integer, Integer, String>> messageSignals = dbfMap.get(Integer.parseInt(strParts[1]));
					for(Signal<String, Integer, Integer, Integer, String> signal : messageSignals) {
						if(signal.signalID.equals(strParts[2])) {
							if(Integer.parseInt(strParts[4].substring(0, 1)) == 1){
								signal.dataType = "float";
							}
							if(Integer.parseInt(strParts[4].substring(0, 1)) == 2) {
								signal.dataType = "double";
							}
						}
					}
				}
			}
			
			fileReader.close();
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public final ArrayList<Signal<String, Integer, Integer, Integer, String>> getSignals() {
		return allSignals;
	}

	public static void main(String[] args){
	    DBFParser parser = new DBFParser("wavesculper200.dbc");
	}
}




