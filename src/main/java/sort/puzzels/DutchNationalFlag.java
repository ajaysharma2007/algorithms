package sort.puzzels;

/**
 * Created by ajay on 10/20/16.
 */
public class DutchNationalFlag {
    private char[] pebbles = {'b', 'w', 'r', 'w', 'r', 'b'};

    public static void main(String[] args) {
        DutchNationalFlag dutchNationalFlag = new DutchNationalFlag();
        int red = 0;
        int blue = dutchNationalFlag.pebbles.length - 1;
        int i = 0;
        while (i <= blue) {
            char color = dutchNationalFlag.color(i);
            if (color == 'r') {
                dutchNationalFlag.swap(i, red++);
                i++;
            } else if (color == 'b') {
                dutchNationalFlag.swap(i, blue--);
            } else {
                i++;
            }
        }

        for (char c: dutchNationalFlag.pebbles) {
            System.out.print(c + "\t");
        }
    }

    private void swap(int i, int j) {
        char temp = pebbles[i];
        pebbles[i] = pebbles[j];
        pebbles[j] = temp;
    }

    private char color(int i) {
        return pebbles[i];
    }
}
