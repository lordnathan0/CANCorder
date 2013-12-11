import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Map;

/**
 * http://derekmolloy.ie/automatically-setting-the-beaglebone-black-time-using-ntp/
 */

/**
 * Data Logging Class.
 * 
 * @author Ishmeet
 * 
 */
public final class DataLogger {

    /**
     * Declare string as current working directory.
     */
    private String directory;
    FileWriter writer;

    /**
     * Default Constructor.
     */
    public DataLogger() {
        this.directory = System.getProperty("user.dir");
    }

    /**
     * Constructor to initialize directory. Constructor gets a string object
     * with new directory name.
     * 
     * @param s
     *            The string representation of the new directory.
     */
    public DataLogger(String s) {
        this.directory = s;
    }
    
    /**
     * 
     * @param b
     * @return
     * @throws IOException 
     */
    public void createFile(Bike b) throws IOException{
    	int i = 0;
        String fileName = "/file" + i + ".csv";
        File f = new File(this.directory + fileName);

        while (f.exists()) {
            i++;
            fileName = "/file" + i + ".csv";
            f = new File(this.directory + fileName);
        }
        f.createNewFile();
        this.writer = new FileWriter(f);

        this.writer.append("Time");
        this.writer.append(',');

        /* Get MAP and write headers */

        Map<String, Double> map = b.getData();

        for (Map.Entry<String, Double> entry : map.entrySet()) {
            this.writer.append(entry.getKey());
            this.writer.append(',');
        }
        this.writer.append('\n');
        this.writer.flush();
        
    }

    /**
     * Method to log data onto a csv file.
     * 
     * @param b
     *            The Bike Object
     * @throws IOException 
     */
    public void dataLog(Bike b, FileWriter writer) throws IOException {
        /**
         * WRITE THE TIME HERE, TEMP is 12PM.
         */
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(Calendar.getInstance().getTime());
        writer.append(timeStamp);
        writer.append(',');

        /* Write appropriate values */
        Map<String, Double> map = b.getData();
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            writer.append(String.valueOf(entry.getValue()));
            writer.append(',');
        }
        writer.append('\n');
        writer.flush();
    }
}
