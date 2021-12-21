import java.util.LinkedHashMap;
import java.util.Map;

public class Location {

    /*
     * DONE
     * declare private final locationId, description, exits
     */

    private final int locationId;
    private final String description;
    private final LinkedHashMap<String, Integer> exits;


    public Location(int locationId, String description, Map<String, Integer> exits) {
        /*
         * DONE
         * set the locationId and the description
         */

        this.locationId = locationId;
        this.description = description;

        /* DONE
         * if exits are not null, set the exit
         * Note that exits should be of type LinkedHashMap to maintain the insertion order
         * otherwise, set the exits LinkedHashMap to (Q,0)
         */

        if (exits == null || exits.containsKey(null) && exits.containsValue(null)) {
            this.exits = new LinkedHashMap(exits);
            exits.put("Q", 0);
        } else {
            this.exits = new LinkedHashMap<String, Integer>(exits);
        }
    }

    protected void addExit(String direction, int location) {
        /* DONE
         * put the direction and the location in the exits LinkedHashMap
         */

        exits.put(direction, location);
    }

    public int getLocationId() {
        /* DONE
         * complete getter to return the location id
         */

        return locationId;
    }

    public String getDescription() {
        /* DONE
         * complete getter to return the description
         */

        return description;
    }

    public Map<String, Integer> getExits() {
        /* DONE
         * complete getter to return a copy of exits
         * (preventing modification of exits from outside the Location instance) -> use .clone() to prevent modification
         */

        return (LinkedHashMap<String, Integer>) exits.clone();
    }
}