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
import javafx.stage.Stage
import javafx.scene.control.TextField
import java.io.IOException

class SignUpController {
    @FXML
    public lateinit var submitButton: Button
    public lateinit var background: AnchorPane
    public lateinit var backButton: Button

    //username and password text field
    public lateinit var usernameField: TextField //used to check if username
    public lateinit var passwordField: TextField//used to check if password
    public lateinit var emailField: TextField //used to check if email

    public lateinit var usernameText: Label
    public lateinit var passwordText: Label
    public lateinit var emailText: Label
    public lateinit var createText: Label

    @FXML

    //initialize function
    public fun initialize() {

        //background red
        background.setStyle("-fx-background-color: red;")
        //set the text to white
        usernameText.setStyle("-fx-text-fill: white;")
        passwordText.setStyle("-fx-text-fill: white;")
        emailText.setStyle("-fx-text-fill: white;")
        createText.setStyle("-fx-text-fill: white;")
        //make background red
        background.setStyle("-fx-background-color: red;")

        //make submit button background white
        submitButton.setStyle("-fx-background-color: white;")
        //button smooth edges
        submitButton.setStyle("-fx-background-radius: 28;")

        //backbutton set image to back arrow
        backButton.graphic = ImageView(
            Image(
                "" + javaClass.getResource
                    ("/goBack.png")
            )
        )
        //remove backbuttontext
        backButton.text = ""
        (backButton.graphic as ImageView)?.fitWidth = 70.0
        (backButton.graphic as ImageView)?.fitHeight = 70.0
        //change graphic colour to white
        backButton.graphic.style = "-fx-fill: white;"
        //remove button background
        backButton.setStyle("-fx-background-color: transparent;")
    }

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

    //on submit button click
    public fun onSubmitButtonClick() { //takes user to login after
        try {
            val loader = FXMLLoader(javaClass.getResource("/com/warriorfit/warriorfit/login.fxml"))
            val root = loader.load<Parent>()

            // Create a new scene with a specified width and height
            val newScene = Scene(root, 1280.0, 720.0) // Set width to 800 and height to 600

            // Get the current stage
            val currentStage = submitButton.scene.window as Stage

            // Set the new scene on the current stage
            currentStage.scene = newScene
            currentStage.title = "Warrior Fitness - Login"

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