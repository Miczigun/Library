package pl.polsl.library.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
/**
 * The {@code KeyGeneratorUtility} class provides a static method to generate RSA key pairs.
 */
public class KeyGeneratorUtility {

    /**
     * Generates a new RSA KeyPair with a key size of 2048 bits.
     *
     * @return A new KeyPair containing the public and private keys generated.
     * @throws IllegalStateException If any error occurs during the key pair generation process.
     */
    public static KeyPair generateRsaKey(){

        KeyPair keyPair;

        try{
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch(Exception e){
            throw new IllegalStateException();
        }

        return keyPair;
    }

}
