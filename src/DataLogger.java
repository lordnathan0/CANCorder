import java.io.FileWriter;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;

/**
 * Data Logging Class
 * 
 * @author Ishmeet
 * 
 */
public final class DataLogger {

    //Declare string as current working directory
    private String directory = System.getProperty("user.dir");

    /**
     * Constructor to intialize directory. Constructor gets a string object with
     * new directory name.
     */
    public DataLogger(String s) {
        this.directory = s;
    }

    /**
     * Method to log data onto a csv file
     */
    public void DataLog(Bike b) {
        /* Writing csv files using open csv */
        CSVWriter writer = new CSVWriter(new FileWriter(this.directory
                + "file.csv", ','));
        /*
         * Get Map and write headers.
         */
        writer.write("Time");
        ;

        Queue<Double> q = new Queue1L<Double>();
        Map<String, Double> temp = new Map1L<String, Double>();

        while (b.bikeData.size() > 0) {
            Pair<String, Double> p = b.bikeData.removeAny();
            //Write the header
            writer.write(p.key());
            //Add value to queue to keep order
            q.enqueue(p.value());
            //Add pair to temporary map
            temp.add(p.key(), p.value());
        }
        b.bikeData.transferFrom(temp);
        writer.write("\n");
        writer.write(/* Write time here. Still working on it */);

        while (q.length() > 0) {
            double value = q.dequeue();
            writer.write(value);
        }

        writer.close();

    }

}
