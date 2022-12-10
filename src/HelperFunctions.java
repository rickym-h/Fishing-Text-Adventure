

public class HelperFunctions {
    // Prints a line to console - but it adds each character incrementally instead, so it is not displayed instantly
    static void say(String sentence){
        for (int i = 0; i < sentence.length(); i++) {
            System.out.print(sentence.charAt(i));
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println();
    }

    // Function overloading - in this case I just want a 'default' functionality with a set 300ms pause
    static void pause() {
        pause(300);
    }

    // Stops the flow of the program for 'time' milliseconds
    static void pause(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
