import java.util.ArrayList;

/**
 * IMPORTANT: in this calculations the MSB is on the left side of an String
 *              that means --> the index 0 is the MSB and index lenght-1 the LSB
 */
public class ECCHamming {

    private final ArrayList<Integer> parityBitList= new ArrayList<>();

    public String addParity(String bits) {
        StringBuilder bitString = new StringBuilder(bits);
        StringBuilder bitStringParity = new StringBuilder();

        int parityPos = 0;
        int numberOfParityBits = 0;
        int bitstringIndex = bits.length()-1;
        int pos = 0;
        int actualXORvalue = 0;
        StringBuilder parityValues;
        int indexOfParityValue = 0; // actual index of parity Value in parityValues
        int parityIndex;
        int indexInString;

        // calculate the number of parity-bits
        while (bits.length() > (int) Math.pow(2, pos) - (pos + 1)) {
            numberOfParityBits++;
            pos++;
        }

        // generate new bitstring with default values of parity bits
        for (int i = 0; i < numberOfParityBits + bits.length(); i++) {
            if (i == ((int) Math.pow(2, parityPos) - 1)) {
                bitStringParity.insert(0,'0');  // default value = 0 of parity bits
                parityPos++;
            } else {
                bitStringParity.insert(0,bitString.charAt(bitstringIndex)); // append given bitstring
                bitstringIndex--;
            }
        }

        //System.out.println("number of parity bits: " + numberOfParityBits);
        //System.out.println("bitstring with parity bits: " + bitStringParity);

        // calculate values of parity bits
        for(int i = 0; i < bitStringParity.length(); i++){
            if(bitStringParity.charAt(bitStringParity.length()-i-1) == '1'){
                actualXORvalue = actualXORvalue ^ (i+1);
            }
        }

        this.parityBitList.add(actualXORvalue); //store parity bit values
        parityValues = new StringBuilder(getBinaryOfNumber(actualXORvalue,numberOfParityBits));
        parityPos = 0; //reset postions of parity bits to set the correct values in bitstring

        // change the default values of parity bits to the calculated values
        parityIndex = (int) Math.pow(2, parityPos) - 1;
        while(parityIndex < bitStringParity.length()) {
            indexInString = bitStringParity.length() - 1 - parityIndex;
            bitStringParity.setCharAt(indexInString,parityValues.charAt(numberOfParityBits-indexOfParityValue-1));
            indexOfParityValue++;
            parityPos++;
            parityIndex = (int) Math.pow(2, parityPos) - 1;
        }

        return bitStringParity.toString();
    }

    public String parityRemove(String inputBits, int indexOfParityList){
        StringBuilder bitStringParity = new StringBuilder(inputBits);

        int errorPos;
        int parityPos = 0;
        int actualXORvalue = 0;
        int posOfErrorBit;
        int parityIndex;
        int indexToDelete;

        // calculate parity bits of given input-bitstring
        for(int i = 0; i < bitStringParity.length(); i++){
            if (i != ((int) Math.pow(2, parityPos) - 1)) { // check if current bit index is no parity bit
                if(bitStringParity.charAt(bitStringParity.length()-i-1) == '1'){ // check if bit is '1'
                    actualXORvalue = actualXORvalue ^ (i+1);
                }
            }
            else
                parityPos++;
        }

        // XOR with the correct parity bit values to get the position of the wrong bit
        errorPos = this.parityBitList.get(indexOfParityList) ^ actualXORvalue;
        //System.out.println("Error-Position: " + errorPos);

        // error detected and get corrected
        if(errorPos != 0){
            posOfErrorBit = bitStringParity.length()-(errorPos); //errorPos is the position not the index!
            if(bitStringParity.charAt(posOfErrorBit) == '1')
                bitStringParity.setCharAt(posOfErrorBit,'0');
            else
                bitStringParity.setCharAt(posOfErrorBit,'1');
        }

        // delete parity bits
        parityPos = 0;
        parityIndex = (int) Math.pow(2, parityPos) - 1;
        while(parityIndex < bitStringParity.length()) {
            // -parityPos because every round a parity bit get deleted at the end
            //    -> index have to increase by the number of already deleted parity bits to
            indexToDelete = bitStringParity.length() - 1 - parityIndex + parityPos;
            bitStringParity.deleteCharAt(indexToDelete);

            parityPos++;
            parityIndex = (int) Math.pow(2, parityPos) - 1;
        }

        return bitStringParity.toString();
    }

    private String getBinaryOfNumber(int number, int length){
        StringBuilder binary = new StringBuilder();
        int actualNumb = 0;

        for(int i = length-1; i >= 0; i--){
            if(actualNumb + Math.pow(2,i) <= number){
                actualNumb += Math.pow(2,i);
                binary.insert(binary.length(),'1');
            } else
                binary.insert(binary.length(),'0');
        }
        return binary.toString();
    }

    public int getValueOfParityBitList(int i){
        return this.parityBitList.get(i);
    }
}
