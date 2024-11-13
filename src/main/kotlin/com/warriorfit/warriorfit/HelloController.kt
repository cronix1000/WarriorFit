package com.warriorfit.warriorfit

import javafx.fxml.FXML
import javafx.scene.control.Label
//button
import javafx.scene.control.Button
import javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag

class HelloController {
    @FXML
    public lateinit var createButton: Button
    public lateinit var loginButton: Button

    @FXML
    public fun onCreateButtonClick() {
        createButton.text = "Create Button Clicked"
    }

    public fun onLoginButtonClick() {
        loginButton.text = "Login Button Clicked"
    }
}