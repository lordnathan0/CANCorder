import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Map;

import de.entropia.can.CanSocket.*;
import de.entropia.can.CanSocket.CanFrame;
import de.entropia.can.CanSocket.CanId;
import de.entropia.can.CanSocket.CanInterface;
import de.entropia.can.CanSocket.Mode;

public class Translator {
	
	DBFParser dbfMap;
	
	public Translator(DBFParser dbfMap) {
		this.dbfMap = dbfMap;
	}
	
	public final void translate(Bike bike, CanFrame frame) {
		
		byte[] frameData = frame.getData();
		CanId frameID = frame.getCanId();
		Integer messageID = frameID.getCanId_SFF();
		
		// Time to reverse messageID because frameID returns it backwards

		if(dbfMap.dbfMap.containsKey(messageID)) {
			ArrayList<Signal<String, Integer, Integer, Integer, String>> dbfSignals = dbfMap.dbfMap.get(messageID);
			for (Signal<String, Integer, Integer, Integer, String> signal : dbfSignals) {
				//Create a smaller array from message array containing only the signal data needed
				byte[] signalArray = new byte[signal.length/8];
				System.arraycopy(frameData, signal.startBit/8, signalArray, 0, signal.length/8);
				//Create a ByteBuffer that holds n bytes where n = |signal array|
				ByteBuffer buffer = ByteBuffer.allocate(8);
				//Get data order and change ByteBuffer to BIG_ENDIAN OR LITTLE_ENDIAN
				if(signal.byteOrder.equals(0)) {
					buffer.order(ByteOrder.BIG_ENDIAN);
				}
				else {
					buffer.order(ByteOrder.LITTLE_ENDIAN);
				}
				buffer.put(frameData);
				buffer.position(0);
				//Get most recent bike data. MAY BE MORE EFFICIENT YET CORRECT TO THROW OUTSIDE THE LOOP
				Map<String, Double> bikeData = bike.getData();
				long bitmask = 0;
				System.out.println("Offset: " + (signal.startBit/8) + "; Modified: " + (signal.startBit/8-1));
				System.out.println("Length: " + (signal.length));
				//buffer = buffer.get(frameData, signal.startBit.intValue()/8-1, signal.length.intValue()/8);
				//buffer = buffer.
				System.out.println("Buffer limit = " + buffer.limit());
				System.out.println(signal.startBit);
				System.out.println(signal.length);
				int power = buffer.limit() * 8 - (signal.length + signal.startBit);
				System.out.println("Starting power: " + power);
				for(int i = 0; i < signal.length.intValue(); i ++) {
					bitmask = (bitmask | (1 << power));
					power ++;
				}
				long bitData = 0;
				bitData += buffer.getLong(); // Grab all 8 bits and convert into a long

				byte[] recompiledData = new byte[buffer.limit()];
				bitData = bitData & bitmask;
				for(int i = 7, j = 0; i >= 0; i--, j++) {
					recompiledData[i] = (byte)(bitData >> j*8);
				}
				buffer.clear();
				buffer = buffer.put(recompiledData);
				buffer.position(0);
				if(signal.dataType.equals("float")) {
					double data = buffer.getFloat();
					data += buffer.getFloat();
					bikeData.put(signal.signalID, data);
				}
				else if(signal.dataType.equals("double")) {
					double data = buffer.getDouble();
					bikeData.put(signal.signalID, data);
				}
				else {
					long data = buffer.getLong();
					bikeData.put(signal.signalID, (double)data);
				}
			}

		}
	}	
}