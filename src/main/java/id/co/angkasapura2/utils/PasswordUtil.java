package id.co.angkasapura2.utils;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class PasswordUtil {

    public static String generatePassword(){
        String collections = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        int N = 7;
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<N; i++){
            Random rand = new Random();
            int indexRandom = rand.nextInt(collections.length());
            sb.append(collections.charAt(indexRandom));
        }
        return sb.toString();
    }

    public static String hash(String plainText){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte[] diggest = md.digest();
            return DatatypeConverter.printHexBinary(diggest).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static boolean compare(String plainText, String hashed){
        return hashed.equals(hash(plainText));
    }
}
