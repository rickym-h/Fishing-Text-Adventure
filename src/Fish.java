import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Fish implements Serializable {
    private final int value;
    private final String name;

    // Generates a random name from a list of colours and a list of types of fish
    public static String getRandomFishName() {
        String[] possibleFish = {
                "Trout",
                "Cod",
                "Salmon",
                "Tuna",
                "Swordfish",
                "Carp",
                "Mackerel",
                "Haddock",
                "Bass",
                "Sardine",
                "Pike"
        };
        String[] possibleColours = {
                "Red",
                "Blue",
                "Green",
                "Orange",
                "Purple",
                "Yellow",
                "Amber",
                "Gold",
                "Silver",
                "Pink",
                "Turquoise"
        };

        return possibleColours[new Random().nextInt(possibleColours.length)] + " " + possibleFish[new Random().nextInt(possibleFish.length)];
    }

    Fish(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
