import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to my Fishing Game!");

        // todo Query if the user wants to start a new game, or load from a save.

        // Start game with either a fresh player with no money or fish, or load game with a set number of fish and money
        Game gameInstance = new Game();


        while (!gameInstance.hasWon()) {
            // If the player does not want to keep playing, process saving the user data and then quit.
            GameState status = gameInstance.playGameLoop();
            if (status == GameState.SAVE_QUIT) {
                try {
                    FileOutputStream fileOut = new FileOutputStream("saveFile.txt");
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(gameInstance);
                    out.close();
                    fileOut.close();
                    System.out.println("Successfully saved game!");
                } catch (IOException e) {
                    System.out.println("ERROR - Could not save game...");
                    e.printStackTrace();
                    System.out.println("ERROR - Could not save game...");
                }
                return;
            }
            if (status == GameState.WON) {
                // Congratulate the user and quit.
                System.out.println("Congratulations on escaping and winning!");
                break;
            }
        }

        // Game has completed
        System.out.println("Goodbye!!");
    }
}