package com.byui.thf10;

import android.util.Log;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import static android.content.ContentValues.TAG;

final class Encryption {
    // The SecureRandom() class is a special subclass of Random() in
    // order to make our "random" salt values less predictable.
    // For more information, see:
    // http://stackoverflow.com/questions/11051205/difference-between-java-util-random-and-java-security-securerandom
    private static final Random RANDOM = new SecureRandom();

    // The use of "static final" here is the equivalent of "const" in C++
    // These constant values are used by the hash algorithm.
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    // By using a private constructor, we prevent instances of this class from being created
    private Encryption() {

    }

    // This function generates a random, 16-byte
    // salt value. You might be wondering if a longer salt
    // would result in a more secure hash:
    // http://stackoverflow.com/questions/184112/what-is-the-optimal-length-for-user-password-salt
    private static byte[] getNextSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return salt;
    }

    // This function uses the PBKDF2 algorithm for generating the hash. If you're
    // interested in why this particular function was chosen, see:
    // http://security.stackexchange.com/questions/4781/do-any-security-experts-recommend-bcrypt-for-password-storage/6415#6415
    private static byte[] getHash(char[] password, byte[] salt) throws NoSuchAlgorithmException {

        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);

        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBEwithHmacSHA512AndAES_256");
        byte[] hash = new byte[0];
        try {
            hash = skf.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            Log.e(TAG,"problem with key");
        }

        return hash;
    }

    /**
     * This function takes the password from the {@link Account} class and hashes it.
     * As a side-effect, the original password value is removed for security purposes.
     * @param user The user whose password needs to be hashed.
     * @exception Exception If there is a problem with the chosen hash function.
     */
    public static void hashUserPassword(Account user) throws Exception {


        // Get the next random salt value to use for this password
        byte[] salt = getNextSalt();
        char[] password = user.getPassword().toCharArray();

        // Once we've generated the hash, clear the old password
        // from memory for security purposes
        byte[] hash = getHash(password, salt);
        Arrays.fill(password, Character.MIN_VALUE);
        user.setPassword("");

        if(hash != null) {

            // By Base64-encoding the raw bytes, we can store them as strings.
            // This allows us to save the values to a file or database if needed.
            // For more information on Base64 encoding, see:
            // http://stackoverflow.com/questions/201479/what-is-base-64-encoding-used-for
            // https://en.wikipedia.org/wiki/Base64
            String hashString = Base64.getEncoder().encodeToString(hash);
            String saltString = Base64.getEncoder().encodeToString(salt);

            user.setHash(hashString);
            user.setSalt(saltString);
        }
        else
        {
            user.setHash(null);
            user.setSalt(null);
        }
    }

    /**
     * This function uses the password and salt in the {@link Account} to generate a hash,
     * then compares that hash to the original hash value.
     * @param user The user whose password needs to be hashed.
     * @return Whether or not the password values match.
     * @exception Exception If there is a problem with the chosen hash function.
     */
    public static Boolean verifyPassword(Account user) throws Exception {

        // Have to get the raw data values to use on our hash function
        char[] password = user.getPassword().toCharArray();
        byte[] salt = Base64.getDecoder().decode(user.getSalt());

        // Generate the new hash, and retrieve the user's hash
        byte[] expectedHash = getHash(password, salt);
        byte[] userHash = Base64.getDecoder().decode(user.getHash());

        // If the new hash came out as null, or the lengths don't match,
        // we know that the original password is different
        if(expectedHash == null || expectedHash.length != userHash.length)
            return false;

        // Check each byte of the two hashes and as soon as we find one
        // that is different, we know that the passwords don't match
        for(int i = 0; i < expectedHash.length; i++) {
            if(expectedHash[i] != userHash[i])
                return false;
        }

        // If we got this far, it means the password hashes match, so we
        // can assume the passwords do as well.
        return true;
    }
}

