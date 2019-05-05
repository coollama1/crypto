import javax.crypto.spec.IvParameterSpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Base64.Decoder;
import java.util.Scanner;

public class CryptoTesting{

    private static String password = "Secret Password!";
    private static String salt = "This is salt!"; 

    private final static String encryptionFunction(String input, String password, String salt, int encryptionMode){
        String output = "";
        try{
            byte [] iv = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

            IvParameterSpec newIvSpec = new IvParameterSpec(iv);
            SecretKeyFactory newKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec newKeySpec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tempSecretKey = newKeyFactory.generateSecret(newKeySpec);
            SecretKeySpec secretKey = new SecretKeySpec(tempSecretKey.getEncoded(),"AES");
            Cipher newCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            newCipher.init(encryptionMode,secretKey,newIvSpec);

            if(encryptionMode == Cipher.ENCRYPT_MODE)
                output = Base64.getEncoder().encodeToString(newCipher.doFinal(input.getBytes("UTF-8")));
            else if(encryptionMode == Cipher.DECRYPT_MODE)
                output = new String(newCipher.doFinal(Base64.getDecoder().decode(input)));
            
        }catch(Exception expt){
            System.out.println("Message was not encrypted properly");
            expt.printStackTrace();
        }

        return output;

    }

    public static String encrypt(String message, String password, String salt){
        return encryptionFunction(message, password, salt, Cipher.ENCRYPT_MODE);
    }

    public static String decrypt(String encryptedMessage, String password, String salt){
        return encryptionFunction(encryptedMessage, password, salt, Cipher.DECRYPT_MODE);
    }


    public static void main(String [] args){
        Scanner input = new Scanner(System.in);
        String inputString, encryptedString, decryptedString;
        while(true){
            System.out.print("Enter Message: ");

            inputString = input.nextLine();
            encryptedString = CryptoTesting.encrypt(inputString,password,salt);
            decryptedString = CryptoTesting.decrypt(encryptedString,password,salt);
            
            System.out.println("Encrypted Message: " + encryptedString + "\nDecrypted Message: " + decryptedString + "\n");
        }
        
    }
}