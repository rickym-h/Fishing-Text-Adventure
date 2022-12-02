import java.util.ArrayList;

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
        System.out.println("Forcing escape...");
        this.escaped = true;
        return true;

    }

    public boolean hasWon() {
        return this.escaped;
    }
}
