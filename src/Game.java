import sun.security.util.ArrayUtil;

import java.security.InvalidParameterException;
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
        Location[] travelLocations = {getTravelLocation(0), getTravelLocation(1)};
        for (Location loc : travelLocations) {
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

        System.out.println();

        // Takes the user input, and perform the relevant action
        int choice = Integer.parseInt(input);
        switch (choice) {
            case 1:
                // Perform the interaction
                interactAction();
                break;
            case 2:
                // View Inventory
                viewInventory();
                break;
            case 3:
                // Travel to first location
                travelToLocation(getTravelLocation(0));
                break;
            case 4:
                // Travel to second location
                travelToLocation(getTravelLocation(1));
                break;
            case 5:
                // Save and Quit
                // todo run save and quit function
                break;
            default:
        }

        return true;

    }

    private void travelToLocation(Location l) {
        // todo charge fees for travel
        this.location = l;
    }

    private Location getTravelLocation(int index) {
        // Takes an index and finds the Location represented by that index with the current location taken out
        Location[] possibleLocations = Location.values();

        for (int i = 0; i <= index; i++) {
            if (possibleLocations[i] == this.location) {
                return possibleLocations[index+1];
            }
        }
        return possibleLocations[index];
    }

    private void viewInventory() {
        System.out.println("--------------------");
        System.out.println("Player Inventory: " + money + " coins. " + fishInventory.size() + " fish.");
        for (int i = 0; i < fishInventory.size(); i++) {
            int viewIndex = i+1;
            Fish f = fishInventory.get(i);
            System.out.println("  " + viewIndex + ": " + f.getName() + " - (Value = " + f.getValue() + ")");
        }
        System.out.println("--------------------");
    }

    private void interactAction() {
        // Attempts to interact, depending on the location
        switch (this.location) {
            case FISHING_VILLAGE:
                fishingInteraction();
                break;
            case TRADING_VILLAGE:
                sellAllFish();
                break;
            case DOCKS:
                System.out.println("ERROR - ESCAPE NOT IMPLEMENTED YET!!!");
            default:
        }
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
        System.out.println("You have gained a fish worth " + fish.getValue() + " coins!");
        // todo make it harder and value based on some minigame?
    }

    private void sellAllFish() {
        // Sells all fish in inventory. Should only be possible when in the trading village.
        int value = 0;
        for (Fish f : fishInventory) {
            value += f.getValue();
        }
        System.out.println("You have gained " + value + " coins!");
        money += value;
        fishInventory = new ArrayList<Fish>();
    }

    public boolean hasWon() {
        return this.escaped;
    }
}
