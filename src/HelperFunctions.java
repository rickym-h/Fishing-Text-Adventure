

public class HelperFunctions {
    static void say(String sentence){
        // Same as a System.out.println() - but it adds each character incrementally instead so it is not instant.
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

    static void pause(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
