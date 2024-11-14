package com.warriorfit.warriorfit

import javafx.fxml.FXML
import javafx.scene.control.Label
//button
import javafx.scene.control.Button
import javafx.scene.image.Image
import javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag
import kotlin.system.exitProcess
import javafx.scene.image.ImageView
import javafx.scene.layout.VBox

class HelloController {
    @FXML
    public lateinit var createButton: Button
    public lateinit var loginButton: Button
    public lateinit var exitButton: Button

    //image
    public lateinit var imageView: ImageView
    public lateinit var imageView2 : ImageView
    public lateinit var vbox: VBox

    //text
    public lateinit var startText: Label
    public lateinit var alreadyText: Label

    @FXML
    //import image
    public fun initialize() {
        //vbox background red
        vbox.setStyle("-fx-background-color: red;")
        //white text, bold, size 20, outline black text, font is neo sans
        startText.setStyle("-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold;")
        alreadyText.setStyle("-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold; ")
        //button smooth edges
        createButton.setStyle("-fx-background-radius: 25;")
        loginButton.setStyle("-fx-background-radius: 25;")
        exitButton.setStyle("-fx-background-radius: 25;")
        //image size
        ImageView().fitWidth = 800.0
        ImageView().fitHeight = 800.0
        imageView.image = Image("C:\\Users\\mcpla\\OneDrive\\Desktop\\Year 4\\Programming Languages\\WarriorFit\\src\\main\\resources\\warriorFitness.png")
        ImageView().fitWidth = 800.0
        ImageView().fitHeight = 800.0
        imageView2.image = Image("C:\\Users\\mcpla\\OneDrive\\Desktop\\Year 4\\Programming Languages\\WarriorFit\\src\\main\\resources\\warriorImage.png")
    }


    public fun onCreateButtonClick() {
        createButton.text = "Create Button Clicked"
    }

    public fun onLoginButtonClick() {
        loginButton.text = "Login Button Clicked"
    }

    public fun onExitButtonClick() {
        //close the application
        exitButton.text = "Exit Button Clicked"
        exitProcess(0)
    }
}