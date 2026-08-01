package com.cs360.weighttracker.viewmodels.states;

class RegisterScreenState {

    private String username, password, confirmPassword, usernameError, passwordError, confirmPasswordError;


    /**
     * Create a login state instance.
     */
    public RegisterScreenState() {
        this.username = "";
        this.password = "";
        this.usernameError = "";
        this.passwordError = "";
        this.confirmPassword = "";
        this.confirmPasswordError = "";
    }


    /// /////////////////////////
    /// GETTERS

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUsernameError() {
        return usernameError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getConfirmPasswordError() {
        return confirmPasswordError;
    }


    /// /////////////////////////
    /// SETTERS
    ///

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsernameError(String usernameError) {
        this.usernameError = usernameError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public void setConfirmPasswordError(String confirmPasswordError) {
        this.confirmPasswordError = confirmPasswordError;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}