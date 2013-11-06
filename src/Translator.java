import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Map;

public class Translator {
	
	DBFParser dbfMap;
	
	public Translator(DBFParser dbfMap) {
		this.dbfMap = dbfMap;
	}
	
	public final void translate(Bike bike, CanFrame frame) {
		ByteBuffer buffer;
		byte[] frameData = frame.getData();
		CanID frameID = frame.getCanID();
		Integer messageID = frameID.getCanId_SFF();
		ArrayList<Signal<String, Integer, Integer>> dbfSignals = dbfMap.dbfMap.get(messageID);
		for (Signal<String, Integer, Integer> signal : dbfSignals) {
			Map<String, Double> bikeData = bike.getData();
			int bitmask = 0;
			buffer = buffer.get(frameData, signal.startBit/8, signal.length/8);
			int power = buffer.limit() * 8 - (signal.startBit - signal.length);
			for(int i = 0; i < signal.length; i ++) {
				bitmask = (bitmask | (1 << power));
				power ++;
			}
			int bitData = buffer.getInt();
			byte[] recompiledData = new byte[buffer.limit()];
			for(int i = 0; i < buffer.limit(); i++) {
				recompiledData[i] = (byte)(bitData >> i*8 & bitmask);
			}
			buffer = buffer.put(recompiledData);
			double data = buffer.getFloat();
			bikeData.put(signal.signalID, data);	
		}
	}	
}