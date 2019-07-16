package com.byui.thf10;

import android.util.Log;

import static android.content.ContentValues.TAG;

public class Account extends JsonConvertible {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String hash;
    private String salt;
    private String date;


    // getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    String getUserName() {
        return userName;
    }

    String getPassword(){ return password;}

    String getSalt() { return salt; }

    String getHash() { return hash; }

    private String getDate() { return date; }

    // setters

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Describes the selected username
     * @param userName The Usernames of all the members of the company
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Password of user
     * @param newPassword will create the password for the user
     */

    public void setPassword(String newPassword) { password = newPassword; }

    /**
     * Hash and Salt are for the purposes of encryption
     * @param newSalt pairs with Hash
     */

    public void setSalt(String newSalt) { salt = newSalt; }

    public void setHash(String hash){this.hash = hash; }

    public void encrypt()throws Exception {
        Encryption.hashUserPassword(this);
    }

    public boolean checkPassword (String password){
        setPassword(password);
        try {
            return Encryption.verifyPassword(this);
        } catch (Exception e) {
            Log.e(TAG,"problem check password");
            return false;
        }
    }
}