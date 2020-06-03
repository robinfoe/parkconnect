package org.park.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class SecurityUtil {

    private static SecurityUtil instance = null;

    private KeyStore store;

    private SecurityUtil(){/** ENSURE SINGLETON **/}
    public static SecurityUtil getInstance(){
        if(SecurityUtil.instance == null){
            SecurityUtil.instance = new SecurityUtil();
        }
        return SecurityUtil.instance;
    }


    public void loadKeyStore(String keystoreLocation, String keyStorePassword) throws Exception{

        store = KeyStore.getInstance("JCEKS");
        store.load(null, keyStorePassword.toCharArray());
        // KeyStore.PasswordProtection keyStorePP = new KeyStore.PasswordProtection(keyStorePassword.toCharArray());
        FileInputStream fIn = new FileInputStream(keystoreLocation);
        store.load(fIn, keyStorePassword.toCharArray());
    }

    public String getPasswordFromKeystore(String entry, String keyStorePassword) throws Exception{

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBE");
        KeyStore.PasswordProtection keyStorePP = new KeyStore.PasswordProtection(keyStorePassword.toCharArray());
        KeyStore.SecretKeyEntry ske = (KeyStore.SecretKeyEntry) this.store.getEntry(entry, keyStorePP);

        PBEKeySpec keySpec = (PBEKeySpec)factory.getKeySpec(
                                                    ske.getSecretKey(),
                                                    PBEKeySpec.class);

        char[] password = keySpec.getPassword();

        return new String(password);
    }

    public static void makeNewKeystoreEntry(Map<String,String> entries, String keyStoreLocation, String keyStorePassword)
            throws Exception {

        KeyStore ks = KeyStore.getInstance("JCEKS");
        ks.load(null, keyStorePassword.toCharArray());
        KeyStore.PasswordProtection keyStorePP = new KeyStore.PasswordProtection(keyStorePassword.toCharArray());
        
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBE");
        SecretKey generatedSecret = null;


        Set<String> keys = entries.keySet();
        for(String key: keys){
            String val = entries.get(key);
            generatedSecret = factory.generateSecret(new PBEKeySpec(val.toCharArray()));
            ks.setEntry(key, new KeyStore.SecretKeyEntry(generatedSecret), keyStorePP);
        }
        FileOutputStream fos = new java.io.FileOutputStream(keyStoreLocation);
        ks.store(fos, keyStorePassword.toCharArray());
    }

    public static void main(String... args){

        try{
            String location= "/Users/robin/workspace/workshop/parkconnect/parkconnector/cred/park.jks";
            String keyStorePassword = "geronimo"; 
            
            Map<String,String> entries = new HashMap<String,String>();
            entries.put("username", "mongoadmin");
            entries.put("password", "secret");

            SecurityUtil.getInstance().loadKeyStore(location, keyStorePassword);
            System.err.println(SecurityUtil.getInstance().getPasswordFromKeystore("username", keyStorePassword));
            System.err.println(SecurityUtil.getInstance().getPasswordFromKeystore("password", keyStorePassword));
            
        } catch(Exception e){
            e.printStackTrace();
        }

        //
    }

    
}