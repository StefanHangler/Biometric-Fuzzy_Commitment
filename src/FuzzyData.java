import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FuzzyData {

    public String[][] createBitstringPairsFromFile(File file) throws FileNotFoundException {
        String[][] bitstringPairs = new String[5][2];

        Scanner scanner = new Scanner(file);

        for(int i = 0; i < 5; i++) {
            bitstringPairs[i][0] = scanner.next();
            bitstringPairs[i][1] = randomFlip(bitstringPairs[i][0]);
        }

        return bitstringPairs;
    }

    public String[][] createBitstringPairs (int number){
        String[][] bitstringPairs = new String[number][2];

        for(int i = 0; i < number; i++) {
            bitstringPairs[i][0] = randomString(256);
            bitstringPairs[i][1] = randomFlip(bitstringPairs[i][0]);
        }

        return bitstringPairs;
    }

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

    public String randomFlip(String bitstring){
        String randFlip = bitstring;

        int count = 0;

        for(int i = 0; i < randFlip.length(); i++){
            int rand = (int) (Math.random()*(25)) + 1;
            //System.out.println("Rand: " + rand);
            if(rand == 1){
                if(randFlip.charAt(i) == '1'){
                    randFlip = randFlip.substring(0,i) + '0' + randFlip.substring(i+1);
                    //randFlip.replace(randFlip.charAt(i), '0');
                    count += 1;
                } else {
                    randFlip = randFlip.substring(0,i) + '1' + randFlip.substring(i+1);
                   // randFlip.replace(randFlip.charAt(i), '1');
                    count += 1;
                }
            }
        }

        //System.out.println("#flips: " + count);
        //System.out.println("Bitstring: " + randFlip);
        return randFlip;
    }
}
