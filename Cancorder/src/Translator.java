import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Map;

import de.entropia.can.CanSocket.CanFrame;
import de.entropia.can.CanSocket.CanId;

public class Translator {
	
	DBFParser dbfMap;
	
	public Translator(DBFParser dbfMap) {
		this.dbfMap = dbfMap;
	}
	
	public final void translate(Bike bike, CanFrame frame) {
		
		byte[] frameData = frame.getData();
		CanId frameID = frame.getCanId();
		Integer messageID = frameID.getCanId_SFF();
		
		Map<String, Double> bikeData = bike.getData();

		if(dbfMap.dbfMap.containsKey(messageID)) {
			ArrayList<Signal<String, Integer, Integer, Integer, String>> dbfSignals = dbfMap.dbfMap.get(messageID);
			for (Signal<String, Integer, Integer, Integer, String> signal : dbfSignals) {
				
				int startBit = signal.startBit;
				//Create a ByteBuffer that holds 8 bytes (entire message)
				ByteBuffer buffer = ByteBuffer.allocate(8);
				//Get data order and change ByteBuffer to BIG_ENDIAN OR LITTLE_ENDIAN ** BIG_ENDIAN BY DEFAULT
				if(signal.byteOrder.equals(0)) {
					buffer.order(ByteOrder.BIG_ENDIAN);
					startBit = 64 - (8*(signal.startBit/8)) - ((8-((signal.startBit+1)%8))%8) - signal.length;
				}
				else {
					buffer.order(ByteOrder.LITTLE_ENDIAN);
				}
				buffer.put(frameData);
				buffer.position(0);
				long bitmask = 0;
				long power = (startBit);
				for(int i = 0; i < signal.length.intValue(); i ++) {
					bitmask = (bitmask | (1L << power));
					power ++;
				}
				
				long bitData = 0;
				long adjustment = 0;
				bitData = buffer.getLong();

				byte[] recompiledData = new byte[buffer.limit()];
				bitData = bitData & bitmask;
				bitData = bitData >>> startBit;
				
				long negativeThreshold = 2 << (signal.length-2);
				if(signal.dataType.equals("-") && (negativeThreshold <= bitData)) {
					for(int i = signal.length; i < 64; i++) {
						adjustment = (adjustment | (1L << i));
					}
					bitData = bitData | adjustment;
				}
				
				if(signal.byteOrder.equals(0)){
					for(int i = 7, j = 0; i >= 0; i--, j++) {
						recompiledData[i] = (byte)(bitData >> j*8);
					}
				}
				else {
					for(int i = 0, j = 0; i <= 7; i++, j++) {
						recompiledData[i] = (byte)(bitData >> j*8);
					}
				}
				
				
				buffer.clear();
				buffer = buffer.put(recompiledData);
				buffer.position(0);
				if(signal.dataType.equals("float")) {
					double data = buffer.getFloat();
					data += buffer.getFloat();
					bikeData.put(signal.signalID, data);
				}
				else if(signal.dataType.equals("-")) {
					long data = buffer.getLong();
					bikeData.put(signal.signalID, (double)data);
				}
				else if(signal.dataType.equals("+")) {
					long data = buffer.getLong();
					data = data << 64 - signal.length;
					data = data >>> 64-signal.length; // Not needed??
					bikeData.put(signal.signalID, (double)data);
				}
				else{
					buffer.order(ByteOrder.BIG_ENDIAN);
					double data = buffer.getDouble();
					bikeData.put(signal.signalID, data);
				}
			}
		}
	}	
}