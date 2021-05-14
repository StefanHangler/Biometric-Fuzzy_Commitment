import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FuzzyData {

    /**
     * reads bit-strings from a file and creates a fuzzy variant for each of them
     * @param file
     * @param number number of bit-strings in the file
     * @return the read bit-strings with corresponding fuzzy variants
     * @throws FileNotFoundException
     */
    public String[][] createBitstringPairsFromFile(File file, int number) throws FileNotFoundException {
        String[][] bitstringPairs = new String[number][2];

        Scanner scanner = new Scanner(file);

        for(int i = 0; i < 5; i++) {
            bitstringPairs[i][0] = scanner.next();
            bitstringPairs[i][1] = randomFlip(bitstringPairs[i][0]);
        }

        return bitstringPairs;
    }

    /**
     *
     * @param number number of bit-strings
     * @param lengthOfString length of the bit-strings
     * @return bit-strings with corresponding fuzzy variants
     */
    public String[][] createBitstringPairs (int number, int lengthOfString){
        String[][] bitstringPairs = new String[number][2];

        for(int i = 0; i < number; i++) {
            bitstringPairs[i][0] = randomString(lengthOfString);
            bitstringPairs[i][1] = randomFlip(bitstringPairs[i][0]);
        }

        return bitstringPairs;
    }

    /**
     * creates a random bit-string for a given length
     * @param lengthOfString length of the bit-string
     * @return a random bit-string
     */
    public String randomString(int lengthOfString){
        StringBuilder bString = new StringBuilder();

        for(int i = 0; i < lengthOfString; i++){
           double rand = Math.random();
           if (rand <= 0.5)
               bString.append('0');
           else
               bString.append('1');
        }
        return bString.toString();
    }

    /**
     * randomly flips a bit-string with the probability of 1/25
     * @param bitstring
     * @return the flipped bit-string
     */
    public String randomFlip(String bitstring){
        String randFlip = bitstring;
        int chance = 25; // 1/chance --> probability

        for(int i = 0; i < randFlip.length(); i++){
            int rand = (int) (Math.random()*(chance)) + 1;
            if(rand == 1){
                if(randFlip.charAt(i) == '1'){
                    randFlip = randFlip.substring(0,i) + '0' + randFlip.substring(i+1);
                } else {
                    randFlip = randFlip.substring(0,i) + '1' + randFlip.substring(i+1);
                }
            }
        }

        return randFlip;
    }
}
