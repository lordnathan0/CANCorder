import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.PriorityQueue;

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
    private String directory = System.getProperty("user.dir");

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
     * Method to log data onto a csv file.
     * 
     * @param b
     *            The Bike Object
     */
    public void dataLog(Bike b) {

        try {
            FileWriter writer = new FileWriter(this.directory + "\file.csv");

            writer.append("Time");
            writer.append(',');

            /* Get MAP and write headers */
            PriorityQueue<Double> q = new PriorityQueue<>();
            Map<String, Double> map = b.getData();

            for (Map.Entry<String, Double> entry : map.entrySet()) {
                writer.append(entry.getKey());
                q.add(entry.getValue());
                writer.append(',');
            }
            writer.append('\n');

            /**
             * WRITE THE TIME HERE, TEMP is 12PM.
             */
            writer.append("12PM");

            /* Write appropriate values */
            while (q.size() > 0) {
                double v = q.poll();
                String val = String.valueOf(v);
                writer.append(val);
                writer.append(',');
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
