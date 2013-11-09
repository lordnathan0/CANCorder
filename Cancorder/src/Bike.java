import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Bike Class.
 * 
 * @author Ishmeet
 * 
 */

//Assuming DBFParser is in the same code. 
public final class Bike {

    /*
     * Bike data is stored in a Map for easy access. Right now I'm using the OSU
     * Components but it will probably be changed to a different Map not
     * affiliated with OSU CSE Department.
     */
    /**
     * Map to hold all the bikeData.
     */
    private Map<String, Double> bikeData = new HashMap<String, Double>();

    /**
     * Public constructor to instantiate the Bike object.
     * 
     * @param parsedObject
     *            The object obtained from DBFParser to construct the Bike
     *            Object
     */
    public Bike(DBFParser parsedObject) {
        /*
         * Create ArrayList based on DBF signal type
         */
        ArrayList<Signal<String, Integer, Integer, Integer>> a = parsedObject
                .getSignals();

        //Update the Map with data from arrayList
        double value = 0.0;
        for (int i = 0; i < a.size(); i++) {
            String signalName = a.get(i).signalID;
            this.bikeData.put(signalName, value);
        }

    }

    /**
     * Method to get the value of a particular parameter.
     * 
     * Use it by calling Bike object.getSignal(String you're looking for)
     * 
     * Method takes in a String that you are looking for and returns its current
     * value. If needed.
     * 
     * @param s
     *            The Name of the signal
     * @return Value of the signal
     * 
     */
    public double getSignal(String s) {
        double value = this.bikeData.get(s);
        return value;
    }

    /**
     * Method to update or replace the value of a particular parameter.
     * 
     * Use it by calling Bike object.putSignal(String you want to update, value
     * to update with)
     * 
     * @param s
     *            The signal name
     * @param v
     *            The signal value
     */
    public void putSignal(String s, double v) {
        this.bikeData.put(s, v);
    }

    /**
     * Method to obtain bikeData outside of class since it is private.
     * 
     * @return the BikeData
     */
    public Map<String, Double> getData() {
        return this.bikeData;
    }

}
