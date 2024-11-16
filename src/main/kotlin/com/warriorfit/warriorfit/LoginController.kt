package com.warriorfit.warriorfit

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.VBox
import javafx.scene.control.TextField
import javafx.stage.Stage
import java.io.IOException

class LoginController {
    @FXML
    public lateinit var submitButton: Button
    public lateinit var background: AnchorPane

    //username and password text field
    public lateinit var usernameTextField: TextField //used to check if username is correct
    public lateinit var passwordTextField: TextField //used to check if password is correct

    public lateinit var usernameText: Label
    public lateinit var passwordText: Label
    public lateinit var loginText: Label

    //backbutton
    public lateinit var backButton: Button
    @FXML
    //initialize function
    public fun initialize() {

        //set the text to white
        usernameText.setStyle("-fx-text-fill: white;")
        passwordText.setStyle("-fx-text-fill: white;")
        loginText.setStyle("-fx-text-fill: white;")
        //make background red
        background.setStyle("-fx-background-color: red;")

        //make submit button background white
        submitButton.setStyle("-fx-background-color: white;")
        //button smooth edges
        submitButton.setStyle("-fx-background-radius: 28;")

        //backbutton set image to back arrow
        backButton.graphic = ImageView(Image("" + javaClass.getResource("/goBack.png")))
        //remove backbuttontext
        backButton.text = ""
        (backButton.graphic as ImageView)?.fitWidth = 70.0
        (backButton.graphic as ImageView)?.fitHeight = 70.0
        //change graphic colour to white
        backButton.graphic.style = "-fx-fill: white;"
        //remove button background
        backButton.setStyle("-fx-background-color: transparent;")

    }

    //on submit button click
    public fun onSubmitButtonClick() {
        try {
            val loader = FXMLLoader(javaClass.getResource("/com/warriorfit/warriorfit/home.fxml"))
            val root = loader.load<Parent>()

            // Create a new scene with a specified width and height
            val newScene = Scene(root, 1280.0, 720.0) // Set width to 800 and height to 600

            // Get the current stage
            val currentStage = submitButton.scene.window as Stage

            // Set the new scene on the current stage
            currentStage.scene = newScene
            currentStage.title = "Warrior Fitness - Home"

            // Optionally, you can also set a minimum or fixed size on the stage
            currentStage.minWidth = 1280.0
            currentStage.minHeight = 720.0

            // Display the stage
            currentStage.show()

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    //back button
    public fun onBackButtonClick() {
        try {
            val loader = FXMLLoader(javaClass.getResource("/com/warriorfit/warriorfit/hello-view.fxml"))
            val root = loader.load<Parent>()

            // Create a new scene with a specified width and height
            val newScene = Scene(root, 1280.0, 720.0) // Set width to 800 and height to 600

            // Get the current stage
            val currentStage = backButton.scene.window as Stage

            // Set the new scene on the current stage
            currentStage.scene = newScene
            currentStage.title = "Warrior Fitness"

            // Optionally, you can also set a minimum or fixed size on the stage
            currentStage.minWidth = 1280.0
            currentStage.minHeight = 720.0

            // Display the stage
            currentStage.show()

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}