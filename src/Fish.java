import java.io.Serializable;

public class Fish implements Serializable {
    private int value = 10;

    Fish(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        // todo add custom names for each fish and value
        return "Fish";
    }
}
