import java.io.IOException;
import java.util.ArrayList;

import de.entropia.can.CanSocket;
import de.entropia.can.CanSocket.CanFrame;
import de.entropia.can.CanSocket.CanInterface;
import de.entropia.can.CanSocket.Mode;


public class tests {

	public tests() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		DBFParser parser = new DBFParser("test.dbf");
		testGetSignals(parser);
		Bike bike = new Bike(parser);
		Translator translator = new Translator(parser);
		
		String CAN_INTERFACE = "vcan0";
		CanFrame frame;
		try {
			CanSocket socket = new CanSocket(Mode.RAW); // Java wants me to try/catch this
			CanInterface canInterface = new CanInterface(socket, CAN_INTERFACE);
			socket.bind(CanSocket.CAN_ALL_INTERFACES);
			
			while(true) {
				frame = socket.recv();
				if(!frame.getCanId().equals(null)) {
					translator.translate(bike, frame);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	public static void testGetSignals(DBFParser parser) {
		ArrayList<Signal<String, Integer, Integer, Integer>> signals = parser.getSignals();
		for(Signal<String, Integer, Integer, Integer> signal : signals) {
			System.out.println(signal.signalID + ", start: " + signal.startBit + ", length: " + signal.length);
		}
	}
	
	public static void testTranslator(Bike bike, CanFrame frame) {
		
	}
	
	

}
