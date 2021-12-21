import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Mapping {

    public static final int INITIAL_LOCATION = 95;
    /*
     * DONE
     * create a static LocationMap object
     */

    static LocationMap locationMap = new LocationMap();

    /*
     * DONE
     * create a vocabulary HashMap to store all directions a user can go
     */

    Map<String, String> vocabulary = new HashMap<>();

    /*
     * DONE
     * create a FileLogger object
     */

    FileLogger fileLogger = new FileLogger();

    /*
     * DONE
     * create a ConsoleLogger object
     */

    ConsoleLogger consoleLogger = new ConsoleLogger();


    public Mapping() {
        //vocabulary.put("QUIT", "Q"); //example
        /* DONE
         * complete the vocabulary HashMap <Key, Value> with all directions.
         * use the directions.txt file and crosscheck with the ExpectedInput and ExpectedOutput files to find the keys and the values
         */

        vocabulary.put("QUIT", "Q");
        vocabulary.put("NORTH", "N");
        vocabulary.put("SOUTH", "S");
        vocabulary.put("EAST", "E");
        vocabulary.put("WEST", "W");
        vocabulary.put("UP", "U");
        vocabulary.put("DOWN", "D");
        vocabulary.put("NORTHEAST", "NE");
        vocabulary.put("NORTHWEST", "NW");
        vocabulary.put("SOUTHEAST", "SE");
        vocabulary.put("SOUTHWEST", "SW");
    }

    public void mapping() {

        /* DONE
         * create a Scanner object
         */

        Scanner scan = new Scanner(System.in);

        /*
         * initialise a location variable with the INITIAL_LOCATION -> DONE
         */

        Location initialLocation = locationMap.get(INITIAL_LOCATION);

        while (true) {

            /* DONE
             * get the location and print its description to both console and file
             * use the FileLogger and ConsoleLogger objects
             */

            consoleLogger.log(initialLocation.getDescription());
            fileLogger.log(initialLocation.getDescription());

            /* DONE
             * verify if the location is an exit
             */

            if (initialLocation.getLocationId() == 0 || initialLocation.getDescription().equals("EXIT")) {
                break;
            }

            /* DONE
             * get a map of the exits for the location
             */

            Map<String, Integer> locationExits = initialLocation.getExits();

            /* DONE
             * print the available exits (to both console and file)
             * crosscheck with the ExpectedOutput files
             * Hint: you can use a StringBuilder to append the exits
             */

            StringBuilder sb = new StringBuilder();
            //locationExits.forEach((k, v) -> sb.append(k + ", "));
            sb.append("Available exits are ");
            locationExits.forEach((k, v) -> sb.append(k + ", ")); //lambda expression '->' for each

            /*sb.append("Available exits are ");
            for(Map.Entry<String, Integer> entry : locationExits.entrySet()) { //for each loop
                sb.append(", "); // after each key so fix this
            }
            consoleLogger.log(sb.toString);
            fileLogger.log(sb.toString);
            */

            /*
            consoleLogger.log("Available exits are " + sb);
            fileLogger.log("Available exits are " + sb);
             */

            fileLogger.log(sb.toString());
            consoleLogger.log(sb.toString());

            /* DONE
             * input a direction -> use scanner
             * ensure that the input is converted to uppercase -> use .toUpperCase()
             */

            String inputDirection = scan.nextLine().toUpperCase();

            /* DONE
             * are we dealing with a letter / word for the direction to go to?
             * available inputs are: a letter(the HashMap value), a word (the HashMap key), a string of words that contains the key
             * crosscheck with the ExpectedInput and ExpectedOutput files for examples of inputs
             * if the input contains multiple words, extract each word
             * find the direction to go to using the vocabulary mapping
             * if multiple viable directions are specified in the input, choose the last one -> STILL LEFT TO DO
             */

            String validInputDirection = "";

            //key is the word, e.g. 'NORTH' and value is the letter, e.g. 'N'
            //only need to check value for a single letter, check key for word or sentence

            //letter (check value only)
            if (inputDirection.length() <= 2) {//if less than or equal to 2 letters (e.g w and sw)
                if (vocabulary.containsValue(inputDirection)) { //if map contains value of inputted direction
                    validInputDirection = inputDirection;//then assign inputted direction as a valid direction
                }
            }

            //word (check key only)
            // if (inputDirection.length() > 2 && !inputDirection.contains(" "))
            if (inputDirection.length() > 1) { //if word because greater than 1 letter
                if (vocabulary.containsKey(inputDirection)) { //if map contains key of inputted direction
                    validInputDirection = vocabulary.get(inputDirection); //then get the inputted direction word from the vocabulary hashmap and assign as a valid direction
                }
            }

            /*if (inputDirection.length() > 1 && !inputDirection.contains(" ")) { //if not single letter and does not contain a space, i.e. a word
                if (vocabulary.containsValue(inputDirection)) {
                    validInputDirection = inputDirection; //then assign inputted direction as a valid direction
                } else if (vocabulary.containsKey(inputDirection)) { //if map contains key of inputted direction
                    validInputDirection = vocabulary.get(inputDirection); // then assign inputted direction as a valid direction
                }
            }*/

            //sentence (check key only)
            if (inputDirection.length() > 1 && inputDirection.contains(" ")) { //if not a single letter and contains a space, i.e. a sentence
                String[] extractedWord = inputDirection.split(" "); //an array to hold each word in the inputted direction, split by the spaces
                for (String word : extractedWord) { //for each loop to iterate through the array
                    if (vocabulary.containsKey(word)) { //if the vocabulary hash map contains the extracted word
                        validInputDirection = vocabulary.get(word); //then get the inputted direction word from the vocabulary hashmap and assign as a valid direction
                        //break;
                    }
                }
            }

            /* DONE
             * if user can go in that direction, then set the location to that direction
             * otherwise print an error message (to both console and file)
             * check the ExpectedOutput files
             */

            if (locationExits.containsKey(validInputDirection)) { //if location exits map contains a valid input direction key
                //then set location to direction -> Location must have 3 parameters: int locationId, String description and Map exits
                initialLocation = new Location(initialLocation.getExits().get(validInputDirection),
                        locationMap.get(locationExits.get(validInputDirection)).getDescription(),
                        locationMap.get(locationExits.get(validInputDirection)).getExits());
            }

            if (!locationExits.containsKey(validInputDirection)) { //if the location exits map does not contain a valid input direction
                fileLogger.log("You cannot go in that direction"); //then print error to console
                consoleLogger.log("You cannot go in that direction"); //and print to file
            }
        }
    }

    public static void main(String[] args) {
        /* DONE
         * run the program from here
         * create a Mapping object
         * start the game
         */

        Mapping mapping = new Mapping();
        mapping.mapping();
    }
}