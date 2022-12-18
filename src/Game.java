import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

enum Location {
    FISHING_VILLAGE,
    TRADING_VILLAGE,
    DOCKS
}

enum GameState {
    PLAYING,
    SAVE_QUIT,
    WON
}

public class Game implements Serializable {
    private Location location;
    private int money;
    private ArrayList<Fish> fishInventory;
    private boolean escaped = false;
    private final HashMap<Location,Integer> travelFees = new HashMap<Location,Integer>() {{
        put(Location.FISHING_VILLAGE, 5);
        put(Location.TRADING_VILLAGE, 10);
        put(Location.DOCKS, 20);
    }};
    private final int escapeCost = 40;

    Game() {
        // Constructor for new game instance
        location = Location.FISHING_VILLAGE;
        money = 0;
        fishInventory = new ArrayList<>();

        HelperFunctions.say("You wake up... Drowsy and wet - at the edge of some kind of fishing village?");
        HelperFunctions.pause();
        HelperFunctions.say("Seems like a ratty run down place... They dont even seem to sell fresh water...");
        HelperFunctions.pause();
        HelperFunctions.say("You're a thousand leagues from home! Better make some coin to get back!");
        HelperFunctions.pause();
        HelperFunctions.say("You've got to be careful though, in 10 days you're going to run out of fresh water...");
    }

    public GameState playGameLoop() {
        // Main loop of a 'round'
        // Gives the user options of what they can do, and will update the game state accordingly
        // Returns true normally
        // Returns false if the player wants to save and quit.
        // todo maybe keep track of days? Each time you travel it is a new day?
        System.out.println();
        System.out.println("------------------------------------------------------------");
        HelperFunctions.say("You have " + this.money + " coins. You have " + this.fishInventory.size() + " fish. You are currently in the " + this.location);

        // Get a list of all the 'things' the user can do.
        ArrayList<String> possibleActions = new ArrayList<>();

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
        possibleActions.add("Save and Quit (WARNING - will overwrite any previous saves)");

        // Print out possible actions to the user
        for (int i = 0; i < possibleActions.size(); i++) {
            HelperFunctions.say(i + 1 + ": " + possibleActions.get(i));
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
                if (hasWon()) {
                    return GameState.WON;
                }
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
                return GameState.SAVE_QUIT;
            default:
        }

        return GameState.PLAYING;

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
            System.out.println("  " + viewIndex + ": " + f.getName() + " - (Value: " + f.getValue() + " coins)");
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
                attemptEscape();
            default:
        }
    }

    private void attemptEscape() {
        if (money - escapeCost < 0) {
            HelperFunctions.say("Not enough money to escape...");
        }
        money -= escapeCost;
        escaped = true;
        HelperFunctions.say("You have enough money to escape!");
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
//        Fish fish = new Fish(10);
//        this.fishInventory.add(fish);
//        System.out.println("You have gained a fish worth " + fish.getValue() + " coins!");

        // minigame will come up with a fish name, and get the player to type out the name exactly to get the fish
        String name = Fish.getRandomFishName();
        HelperFunctions.say("You are fishing...");
        HelperFunctions.say("It seems to be very quiet...");
        HelperFunctions.say("You found a fish! - Quick! Type out the name before it escapes!!");
        HelperFunctions.say("It's a '" + name + "'!");
        Scanner myScanner = new Scanner(System.in);
        System.out.print("Type name: ");
        String input = myScanner.nextLine();
        if (input.equals(name)) {
            HelperFunctions.say("Nice! You caught a " + name + "!!");
            Fish fish = new Fish(10, name);
            this.fishInventory.add(fish);
            HelperFunctions.say("It is worth " + fish.getValue() + " coins!");
        } else {
            HelperFunctions.say("Unlucky... The " + name + " got away! Make sure you type the name correctly next time!");
        }
    }

    private void sellAllFish() {
        // Sells all fish in inventory. Should only be possible when in the trading village.
        int value = 0;
        for (Fish f : fishInventory) {
            value += f.getValue();
        }
        HelperFunctions.say("You have gained " + value + " coins!");
        money += value;
        fishInventory = new ArrayList<>();
    }

    public boolean hasWon() {
        return this.escaped;
    }
}
