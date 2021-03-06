import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import de.entropia.can.CanSocket;
import de.entropia.can.CanSocket.CanFrame;
import de.entropia.can.CanSocket.CanId;
import de.entropia.can.CanSocket.CanInterface;
import de.entropia.can.CanSocket.Mode;

public final class Main extends TimerTask{

	DataLogger l;
	Bike b;
	
	@Override
	public void run(){
		try {
			l.dataLog(b, l.writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Main(DataLogger d, Bike b){
		this.l = d;
		this.b = b;
	}
	
	
	static String CAN_INTERFACE = "can0";
	
	public static void main(String args[]) throws IOException, InterruptedException{
		DBFParser d = new DBFParser("largetest.dbc");
		Bike b = new Bike(d);
		Translator t = new Translator(d);
		DataLogger log = new DataLogger();
		System.out.println("Intialized classes.");
		log.createFile(b);
		
		CanFrame f;
		System.out.println("Declare frame");
		CanSocket socket = new CanSocket(Mode.RAW);
		System.out.println("create socket");
		CanInterface canif = new CanInterface(socket, CAN_INTERFACE);
		System.out.println("create interface");
		socket.bind(CanSocket.CAN_ALL_INTERFACES);
		System.out.println("Bind interface");
		
		
		Timer time = new Timer();
		Main m = new Main(log,b);
		time.schedule(m,0,500);
		
		System.out.println("Scheduled task");
		
		
		while(true){
			f = socket.recv();
			byte[] frameData = f.getData();
			
			if(!f.getCanId().equals(null)){
				System.out.println("Translating message");
				t.translate(b, f);
			}
		}
	}
}