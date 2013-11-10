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

	
	public Map<Integer,ArrayList<Signal<String, Integer, Integer>>> dbfMap; // ONLY TEMPORARY UNTIL YOU MAKE ALL THE GET METHODS
	private ArrayList<Signal<String, Integer, Integer>> message;
	private ArrayList<Signal<String, Integer, Integer>> allSignals;
	
	private void createNewParser() {
		dbfMap = new HashMap<Integer, ArrayList<Signal<String, Integer, Integer>>>(100);
		allSignals = new ArrayList<Signal<String, Integer, Integer>>();
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
				if(currentLine.startsWith("[START_MSG]")) {
					String[] strParts = currentLine.split(",");
					key = Integer.parseInt(strParts[1]);
					message = new ArrayList<>(Integer.parseInt(strParts[4]));
				}
				if(currentLine.startsWith("[START_SIGNALS]")) {
					String[] strParts = currentLine.split(",");
					Integer bits = (Integer.parseInt(strParts[2])-1) * 8 + Integer.parseInt(strParts[3]);
					System.out.println("BITS: " + bits.toString());
					Signal<String, Integer, Integer> signal = new Signal<String, Integer, Integer>(strParts[0].substring(16), bits, Integer.parseInt(strParts[1]));
					allSignals.add(signal);
					//System.out.print(signal.signalID + " ");
					//System.out.print(signal.startBit + " ");
					//System.out.print(signal.length + " ");
					//System.out.println();
					message.add(signal);
				}
				if(currentLine.startsWith("[END_MSG]")) {
					dbfMap.put(key, message);	
				}

			}
			
			fileReader.close();
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public final ArrayList<Signal<String, Integer, Integer>> getSignals() {
		return allSignals;
	}

	public static void main(String[] args){
	    DBFParser parser = new DBFParser("test.dbf");
	}
}




