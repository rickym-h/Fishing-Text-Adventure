import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to my Fishing Game!");

        // todo Query if the user wants to start a new game, or load from a save.
        System.out.println("How would you like to play the game?");
        System.out.println("1: Play new game!");
        System.out.println("2: Load game from last save.");

        // Get user input, and continue to ask until a valid response is recorded.
        String input;
        do {
            Scanner myScanner = new Scanner(System.in);
            System.out.print("Please enter your choice: ");
            input = myScanner.nextLine();
        } while ((Integer.parseInt(input) != 1) && (Integer.parseInt(input) != 2));

        Game gameInstance;
        if (Integer.parseInt(input) == 1) {
            // Start fresh file game
            System.out.println("Starting new game!");
            gameInstance = new Game();
        } else {
            System.out.println("Attempting to load last saved game...");
            try {
                FileInputStream fileIn = new FileInputStream("saveFile.txt");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                gameInstance = (Game) in.readObject();
                in.close();
                fileIn.close();
            } catch (Exception e) {
                System.out.println("ERROR - Could not load last save");
                e.printStackTrace();
                System.out.println("Loading new game instead!");
                gameInstance = new Game();
            }
        }

        // Starting main game loop
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