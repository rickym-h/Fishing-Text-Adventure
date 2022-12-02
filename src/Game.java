import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

enum Location {
    FISHING_VILLAGE,
    TRADING_VILLAGE,
    DOCKS
}

public class Game {
    private Location location;
    private int money;
    private ArrayList<Fish> fishInventory;
    private boolean escaped = false;
    private HashMap<Location,Integer> travelFees = new HashMap<Location,Integer>() {{
        put(Location.FISHING_VILLAGE, 5);
        put(Location.TRADING_VILLAGE, 10);
        put(Location.DOCKS, 20);
    }};

    Game() {
        // Constructor for new game instance
        location = Location.FISHING_VILLAGE;
        money = 0;
        fishInventory = new ArrayList<Fish>();

        // todo give the user some detail about the world as they start the game
    }

    Game(int money, ArrayList<Fish> fishInventory, Location location) {
        // Constructor for game with saved game data
        // todo set the data
        // todo remind the player about where they are in the story
    }

    public boolean playGameLoop() {
        // Main loop of a 'round'
        // Gives the user options of what they can do, and will update the game state accordingly
        // Returns true normally
        // Returns false if the player wants to save and quit.
        System.out.println("PLAYING GAME LOOP");
        System.out.println("You have " + this.money + " coins. You have " + this.fishInventory.size() + " fish.");

        // Get a list of all the 'things' the user can do.
        ArrayList<String> possibleActions = new ArrayList<String>();

        // 1: Interactions (Fishing or Trading or Escaping)
        switch (this.location) {
            case FISHING_VILLAGE:
                possibleActions.add("Go Fishing!");
                break;
            case TRADING_VILLAGE:
                possibleActions.add("Sell all fish in inventory.");
                break;
            case DOCKS:
                possibleActions.add("ESCAPE!");
                break;
            default:
        }
        // 2: View Inventory
        possibleActions.add("View Inventory");
        // 3: Travel
        // 4: Travel
        for (Location loc : Location.values()) {
            if (loc == this.location) {
                continue;
            }
            switch (loc) {
                case FISHING_VILLAGE:
                    possibleActions.add("Travel to fishing village.");
                    break;
                case TRADING_VILLAGE:
                    possibleActions.add("Travel to trading village.");
                    break;
                case DOCKS:
                    possibleActions.add("Travel to docks.");
                default:
            }
        }
        // 5: Save+Quit
        possibleActions.add("Save and Quit");

        // Print out possible actions to the user
        for (int i = 0; i < possibleActions.size(); i++) {
            System.out.println(i + 1 + ": " + possibleActions.get(i));
        }

        // Get user input, and continue to ask until a valid response is recorded.
        String input;
        do {
            Scanner myScanner = new Scanner(System.in);
            System.out.print("Please enter your choice: ");
            input = myScanner.nextLine();
        } while (!isActionInputValid(input));


        return true;

    }

    private static boolean isActionInputValid(String input) {
        try {
            int processedInput = Integer.parseInt(input);
            if ((processedInput <= 0) || (processedInput > 5)) {
                System.out.println("Invalid input. Please enter an integer between 1 - 5 and try again!");
                return false;
            }
            return true;
        } catch (Exception e) {
            // Input not valid
            System.out.println("User input not recognised... Please enter an integer between 1 - 5 and try again!");
            return false;
        }
    }

    private void fishingInteraction() {
        // Function which will perform the fishing interaction, and get a fish for the user.
        Fish fish = new Fish(10);
        this.fishInventory.add(fish);
        // todo make it harder and value based on some minigame?
    }


    public boolean hasWon() {
        return this.escaped;
    }
}
