import java.util.Scanner;

public class AlphabetCipher{

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    //returns a String with the same contents of undesiredLengthStr, but with desiredLengthStr's length (achieved by repition of undesiredLengthStr's contents)
    private static String adjustLength(String undesiredLengthStr, String desiredLengthStr){ 
        String adjustedString = "";               
        for(int c = 0; c < desiredLengthStr.length(); c++)
            adjustedString+= undesiredLengthStr.charAt(c % undesiredLengthStr.length());
        
        return adjustedString;
    }

    private static char findOutput(char decryptedChar, char key){
        return ALPHABET.charAt((key - 'a' + decryptedChar - 'a')% 26);
    }

    private static char findInput(char encryptedChar, char key){
        return ALPHABET.charAt((encryptedChar - 'a' + 'z' - key + 1) % 26);
    }

    public static String encrypt(String message, String key){
        String encryptedMessage = "";
        String lowerCaseMessage = message.toLowerCase();
        String lowerCaseKey = key.toLowerCase();
        String adjustedKey = adjustLength(lowerCaseKey,lowerCaseMessage);

        for(int c = 0; c < message.length(); c++){
            char currentMessageChar = lowerCaseMessage.charAt(c);
            char currentKeyChar = adjustedKey.charAt(c);
            if(currentMessageChar >= 'a' && currentMessageChar <= 'z')
                encryptedMessage+= findOutput(currentMessageChar, currentKeyChar);
            else
                encryptedMessage+= currentMessageChar;
        }
        return encryptedMessage;
    }

    public static String decrypt (String encryptedMessage, String key){
        String decryptedMessage = "";
        String lowerCaseEncryptedMessage = encryptedMessage.toLowerCase();
        String lowerCaseKey = key.toLowerCase();
        String adjustedKey = adjustLength(lowerCaseKey, encryptedMessage);

        for(int c = 0; c < encryptedMessage.length(); c++){
            char currentEncryptedChar = lowerCaseEncryptedMessage.charAt(c);
            char currentKeyChar = adjustedKey.charAt(c);
            if(currentEncryptedChar >= 'a' && currentEncryptedChar <= 'z')
                decryptedMessage+= findInput(currentEncryptedChar,currentKeyChar);
            else
                decryptedMessage+= currentEncryptedChar;
        }
        return decryptedMessage;
    }

    public static void main(String [] args){
        String message = "";
        String key = "";
        String encryptedMessage;
        String decryptedMessage;
        Scanner input = new Scanner(System.in);

        System.out.print("Enter key: ");
        key = input.next();
        input.nextLine();

        System.out.print("Enter Message: ");
        message = input.nextLine();

        encryptedMessage = AlphabetCipher.encrypt(message,key);
        decryptedMessage = AlphabetCipher.decrypt(encryptedMessage,key);

        System.out.println("Encrypted Message: " +encryptedMessage+ "\nDecrypted Message:" + decryptedMessage);

        input.close();
    }
}

/*






*/