import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class FuzzyCommitment {

    public String xorStrings(String inputData, String key){
        StringBuilder xorString = new StringBuilder();

        if(inputData.length() == key.length()){
            for(int i = 0; i < inputData.length(); i++) {
                xorString.append((inputData.charAt(i) ^ key.charAt(i)));
            }
        }
        else
            throw new RuntimeException("Given Strings haven't the same length -> but needed for XOR-operation \n" +
                    "input data: " + inputData + "\n  length = " + inputData.length() +
                    "key data: " + key+ "\n  length = " + key.length());

        return xorString.toString();
    }

    public String SHA256hashing(String bits) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(
                bits.getBytes(StandardCharsets.UTF_8));
        System.out.println(Arrays.toString(encodedhash));

        return new String(encodedhash);
    }
}
