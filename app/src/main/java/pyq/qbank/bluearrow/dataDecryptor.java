package pyq.qbank.bluearrow;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class dataDecryptor {

    private static JsonArray decrypt(String encryptedData, String key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);



//        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
//        byte[] decryptedBytes = cipher.doFinal(decodedBytes);

//        JsonArray jsonArray = JsonParser.parseString(new String(decryptedBytes)).getAsJsonArray();


        return null;
    }}
