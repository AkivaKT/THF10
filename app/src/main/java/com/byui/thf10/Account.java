package com.byui.thf10;

public class Account extends JsonConvertible {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String hashCode;
    private String hash;
    private String salt;

    Account(String startPassword) {
        password = startPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String newPassword) {
        password = newPassword;
    }

    public String getPassword(){return password;}

    public String getSalt() {
        return salt;
    }

    public void setSalt(String newSalt) {
        salt = newSalt;
    }

    public String getHash() {
        return hash;
    }

    public void encrypt()throws Exception {
        Encryption.hashUserPassword(this);
    }
    public void setHash(String hash){this.hash = hash; }

    public boolean checkPassword (String password)throws Exception{
        setPassword(password);
        if (Encryption.verifyPassword(this)){
            return true;}
        else{return false;}
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }
}