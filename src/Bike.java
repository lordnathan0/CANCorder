import java.util.ArrayList;
import components.map.Map;
import components.map.Map1L;

/*import DBF Object*/

/**
 * Bike Class
 * 
 * @author Ishmeet
 * 
 */
public final class Bike {

    /*
     * Bike data is stored in a Map for easy access. Right now I'm using the OSU
     * Components but it will probably be changed to a different Map not
     * affiliated with OSU CSE Departmen.
     */

    protected Map<String, Double> bikeData = new Map1L<String, Double>();

    /**
     * Public constructor to instantiate the Bike object.
     */
    public Bike(DBF parsedObject) {
        /*
         * Create ArrayList based on DBF signal type
         */
        //Not entirely sure how to pass this in yet. Maybe it will depend on the
        //DBF parsedObject

        ArrayList<Signal> a = new ArrayList<Signal>();
        //Get signals from Parsed object into arraylist
        a = parsedObject.getSignals();

        //Update the Map with data from arrayList
        double value = 0.0;
        for (Signal s : a) {
            this.bikeData.add(s.signalID, value);
        }

    }

    /**
     * Method to get the value of a particular parameter.
     * 
     * Use it by calling Bike object.get(String you're looking for)
     * 
     * Method takes in a String that you are looking for and returns its current
     * value. If needed.
     */
    public double get(String s) {
        double value = 0.0;
        value = this.bikeData.value(s);
        return value;
    }

    /**
     * Method to update the value of a particular parameter.
     * 
     * Use it by calling Bike object.put(String you want to update, value to
     * update with)
     */
    public void put(String s, double v) {
        this.bikeData.replaceValue(s, v);
    }

}
