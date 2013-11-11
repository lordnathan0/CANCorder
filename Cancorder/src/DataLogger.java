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
     * Method to log data onto a csv file.
     * 
     * @param b
     *            The Bike Object
     */
    public void dataLog(Bike b) {

        int i = 0;
        String fileName = "file" + i + ".csv";
        File f = new File(this.directory + fileName);

        while (f.exists()) {
            i++;
            fileName = "file" + i + ".csv";
            f = new File(this.directory + fileName);
        }
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            FileWriter writer = new FileWriter(f);

            writer.append("Time");
            writer.append(',');

            /* Get MAP and write headers */
            LinkedList<Double> q = new LinkedList<>();
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
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                    .format(Calendar.getInstance().getTime());
            writer.append(timeStamp);
            writer.append(',');

            /* Write appropriate values */
            while (q.size() > 0) {
                writer.append(String.valueOf(q.poll()));
                writer.append(',');
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
