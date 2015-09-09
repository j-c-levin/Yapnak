package com.uq.yapnak;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;

import java.security.AlgorithmParameters;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by vahizan on 29/06/2015.
 */


public class SecureDetails {

    private String salt ;
    private static int pswrdIterate = 65536;
    private static int KEY_SIZE = 256;
    private byte [] mBytes;
    private byte [] userBytes;


    public String encrypt(String content)throws Exception{
        salt = generateSalt();
        byte[] bytes = salt.getBytes("UTF-8");

        //get key for content

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(

                content.toCharArray(),
                bytes,
                pswrdIterate,
                KEY_SIZE
        );


        SecretKey secretKey = keyFactory.generateSecret(spec);
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(),"AES");

        //ENCRYPT content

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec);
        AlgorithmParameters params = cipher.getParameters();
        mBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] encryptedText = cipher.doFinal(content.getBytes("UTF-8"));

        String encryptedPass = new Base64().encodeAsString(encryptedText);
        return encryptedPass;
    }

    public String decrypt(String content) throws Exception{

        byte [] saltB = salt.getBytes();
        byte [] encryptedText = new Base64().decodeBase64(content);



        //getPassKey
        SecretKeyFactory secretKeyFactory  = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec passSpec = new PBEKeySpec(

                content.toCharArray(),
                saltB,
                pswrdIterate,
                KEY_SIZE
        );

        SecretKey secretKey = secretKeyFactory.generateSecret(passSpec);
        SecretKeySpec secretPass = new SecretKeySpec(secretKey.getEncoded(),"AES");

        //Decryption of Pass

        Cipher passCipher = Cipher.getInstance("AES/CBS/PKCS5Padding");

        byte [] decryptedBytes = null;

        try{
            decryptedBytes = passCipher.doFinal(encryptedText);

        }catch(IllegalBlockSizeException block){
            block.printStackTrace();

        }catch(BadPaddingException pad){
            pad.printStackTrace();
        }

        String passDecryptText = new String(decryptedBytes);
        return passDecryptText;

    }

    public String [] encrypt(String user, String pass) throws Exception{

        salt = generateSalt();
        byte[] bytes = salt.getBytes("UTF-8");

        //get key for password

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(

                pass.toCharArray(),
                bytes,
                pswrdIterate,
                KEY_SIZE
        );


        SecretKey secretKey = keyFactory.generateSecret(spec);
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(),"AES");

        //ENCRYPT Password

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec);
        AlgorithmParameters params = cipher.getParameters();
        mBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] encryptedText = cipher.doFinal(pass.getBytes("UTF-8"));

        String encryptedPass = new Base64().encodeAsString(encryptedText);



        //USER

        //GET KEY FOR USERNAME

        SecretKeyFactory userKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec userKeySpec = new PBEKeySpec(

                user.toCharArray(),
                bytes,
                pswrdIterate,
                KEY_SIZE

        );

        SecretKey userKey = userKeyFactory.generateSecret(userKeySpec);
        SecretKeySpec secretUserKeySpec = new SecretKeySpec(userKey.getEncoded(),"AES");

        //ENCRYPT USER

        Cipher userCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE,secretUserKeySpec);
        AlgorithmParameters userAlgParams = cipher.getParameters();
        userBytes = userAlgParams.getParameterSpec(IvParameterSpec.class).getIV();
        byte[]encryptedUserBytes = cipher.doFinal(user.getBytes("UTF-8"));

        String encryptedUser = new Base64().encodeAsString(encryptedUserBytes);

        String [] encryptedUserPass = new String [2];
        encryptedUserPass[0] = encryptedUser;
        encryptedUserPass[1] = encryptedPass;

        return encryptedUserPass;


  }

    public String [] decrypt(String user,String pass) throws Exception{

        byte [] saltB = salt.getBytes();
        byte [] encryptedText = new Base64().decodeBase64(pass);

        //encrypted userText
        byte [] encryptedUserText= new Base64().decode(user);

        //getPassKey
        SecretKeyFactory secretKeyFactory  = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec passSpec = new PBEKeySpec(

                pass.toCharArray(),
                saltB,
                pswrdIterate,
                KEY_SIZE
        );

        SecretKey secretKey = secretKeyFactory.generateSecret(passSpec);
        SecretKeySpec secretPass = new SecretKeySpec(secretKey.getEncoded(),"AES");

        //get User Key

        SecretKeyFactory userSecret = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec userSpec = new PBEKeySpec(

                user.toCharArray(),
                saltB,
                pswrdIterate,
                KEY_SIZE
        );

        SecretKey userSecretKey = userSecret.generateSecret(userSpec);
        SecretKeySpec userKeyPec = new SecretKeySpec(userSecretKey.getEncoded(),"AES");

        //Decryption of Pass

        Cipher passCipher = Cipher.getInstance("AES/CBS/PKCS5Padding");

        byte [] decryptedBytes = null;

        try{
            decryptedBytes = passCipher.doFinal(encryptedText);

        }catch(IllegalBlockSizeException block){
            block.printStackTrace();

        }catch(BadPaddingException pad){
             pad.printStackTrace();
        }

        String passDecryptText = new String(decryptedBytes);

        //DECRYPTION OF USER

        Cipher userCipher = Cipher.getInstance("AES/CBS/PKCS5Padding");
        byte [] decryptedUser = null;

        try{

            decryptedUser = userCipher.doFinal(encryptedUserText);

        }catch(IllegalBlockSizeException block){

            block.printStackTrace();

        }catch(BadPaddingException pad){

            pad.printStackTrace();
        }

        String userDecryptedText = new String(decryptedUser);

        String []  userPass = new String[2];

        userPass[0] = passDecryptText;
        userPass[2] = userDecryptedText;


        return userPass;

    }

    private String generateSalt(){
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        String string = new String(bytes);

        return string;
    }

    
}
