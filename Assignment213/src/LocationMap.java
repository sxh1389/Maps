import java.io.*;
import java.util.*;

//class that behaves like a map
public class LocationMap implements Map<Integer, Location> {

    private static final String LOCATIONS_FILE_NAME = "locations.txt";
    private static final String DIRECTIONS_FILE_NAME = "directions.txt";

    /*
     * DONE
     * create a static locations HashMap
     */
    static HashMap<Integer, Location> locations = new HashMap<Integer, Location>();

    static {
        /* DONE
         * create a FileLogger object
         */
        FileLogger fileLog = new FileLogger();

        /* DONE
         * create a ConsoleLogger object
         */

        ConsoleLogger consoleLog = new ConsoleLogger();

        /* DONE
         * Read from LOCATIONS_FILE_NAME so that a user can navigate from one location to another
         * use try-with-resources/catch block for the FileReader
         * extract the location and the description on each line -> location is int, description is String, these are separated by a , only
         * print all locations and descriptions to both console and file
         * check the ExpectedOutput files
         * put each location in the locations HashMap using temporary empty hashmaps for exits
         */

        // basically my logic is to split the string by the comma between the integer (location) and the string (description)
        // use an array list to add each location and description read
        // then assign these substrings to the variable 'location' and the variable 'description'

        //buffered reader used to read the text file
        //while next line in file exits, add line to array
        try (BufferedReader br = new BufferedReader(new FileReader(LOCATIONS_FILE_NAME))) {
            String line;
            ArrayList<String> locationsFile = new ArrayList<String>();
            while ((line = br.readLine()) != null) {
                //line.split(",");
                locationsFile.add(line);
            }

            //print message to file and console
            fileLog.log("Available locations:"); //print to file
            consoleLog.log("Available locations:"); //print to console

            //replace , with : from locations txt file as required in the output
            for (String locationId : locationsFile) {
                //locationId.replace(",", ": ");

                //String[] locationNum = locationId.split(",");
                //String locationNo = locationNum[0]
                //int locationNumber = Integer.parseInt()

                String locationNum = locationId.split(",")[0]; //split by comma and take element at index 0, i.e. before the comma
                int locationNumber = Integer.parseInt(locationNum); //convert from string to int and store in int variable called location number

                //int locationNumber = Integer.parseInt((locationId.substring(locationId.indexOf(",") - 1)));

                //int locationNumber = Integer.parseInt(locationNum.toString());

                //save description in a variable called location description -> substring after comma
                String locationDescription = locationId.substring(locationId.indexOf(",") + 1);

                fileLog.log(locationNumber + ": " + locationDescription); //print to file
                consoleLog.log(locationNumber + ": " + locationDescription); //print to console

                //put each location in the locations<Integer, Location> hashmap (using a temporary empty hashmap)
                //remember Location(int locationId, String description, Map<String, Integer> exits)
                locations.put(locationNumber, new Location(locationNumber, locationDescription, new LinkedHashMap<>()));
            }

        } catch (Exception e) {
            //System.out.println(e);
            e.printStackTrace();
        }

        /* DONE
         * Read from DIRECTIONS_FILE_NAME so that a user can move from A to B, i.e. current location to next location
         * use try-with-resources/catch block for the FileReader
         * extract the 3 elements  on each line: location, direction, destination
         * print all locations, directions and destinations to both console and file
         * check the ExpectedOutput files
         * add the exits for each location
         */

        //same as above
        try (BufferedReader br = new BufferedReader(new FileReader(DIRECTIONS_FILE_NAME))) {
            String line;
            ArrayList<String> directionsFile = new ArrayList<String>();
            while ((line = br.readLine()) != null) {
                //line.split(",");
                directionsFile.add(line);
            }

            fileLog.log("Available directions:"); //print to file
            consoleLog.log("Available directions:"); //print to console

            //create arraylists to hold the values from the directions file - outside for loop
            ArrayList<Integer> locationsArray = new ArrayList<Integer>();
            ArrayList<String> directionsArray = new ArrayList<String>();
            ArrayList<Integer> destinationsArray = new ArrayList<Integer>();

            for (String directionId : directionsFile) { //for each entry in the directions File array
                //directionId.replace(",", ": "); // replace each appearance of a comma with a colon followed by a space

                String locationNum = directionId.split(",")[0]; //split by comma and take element at index 0, i.e. before the comma
                int locationNumber = Integer.parseInt(locationNum); //convert from string to int and store in int variable called location number
                locationsArray.add(locationNumber); //add each location number to the locations array list

                //method 1 (for direction)
                //String direction = directionId.substring((directionId.indexOf(",")) , directionId.indexOf(",") + 1);

                //method 2 (for direction)
                //int from = directionId.indexOf(",") + ",".length;
                //int to = directionId.lastIndexOf(",");
                //String direction = directionId.substring(from, to - from);

                //method 3 (for direction)
                String[] directions = directionId.split(","); //split based on comma
                String direction = directions[1]; //take element at index 1 and store in string variable called direction
                directionsArray.add(direction); //add each direction to directions array list

                //split sentence by comma and take substring after the last appearing comma
                String destinationNum = directionId.substring(directionId.lastIndexOf(",") + 1);
                int destinationNumber = Integer.parseInt(destinationNum); //convert from string to int and store in int variable called destination number
                destinationsArray.add(destinationNumber); //add each destination number to the destinations array list

                fileLog.log(locationNumber + ": " + direction + ": " + destinationNumber); //print to file log
                consoleLog.log(locationNumber + ": " + direction + ": " + destinationNumber); //print to console log
            }

            for (HashMap.Entry<Integer, Location> hashMap : locations.entrySet()) {//for each entry in locations hashmap
                for (int i = 0; i < locationsArray.size(); i++) //iterate through the locations array and
                    if (hashMap.getKey().equals(locationsArray.get(i))) { //if the key equals the location
                        hashMap.getValue().addExit(directionsArray.get(i), destinationsArray.get(i)); //add the exits (string direction, int destination) for each location
                    }
            }

        } catch (Exception e) {
            //System.out.println(e);
            e.printStackTrace();
        }
    }

    /*
     * DONE
     * implement all methods for Map
     *
     * @return
     */
    @Override
    public int size() {
        //DONE
        return locations.size();
    }

    @Override
    public boolean isEmpty() {
        //DONE
        return locations.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        //DONE
        return locations.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        //DONE
        return locations.containsValue(value);
    }

    @Override
    public Location get(Object key) {
        //DONE
        return locations.get(key);
    }

    @Override
    public Location put(Integer key, Location value) {
        //DONE
        return locations.put(key, value);
    }

    @Override
    public Location remove(Object key) {
        //DONE
        return locations.remove(key);
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Location> m) {
        //DONE
        locations.putAll(m);
    }

    @Override
    public void clear() {
        //DONE
        locations.clear();
    }

    @Override
    public Set<Integer> keySet() {
        //DONE
        return locations.keySet();
    }

    @Override
    public Collection<Location> values() {
        //DONE
        return locations.values();
    }

    @Override
    public Set<Entry<Integer, Location>> entrySet() {
        //DONE
        return locations.entrySet();
    }
}
