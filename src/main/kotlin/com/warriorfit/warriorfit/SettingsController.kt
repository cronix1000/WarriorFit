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
import java.io.IOException
import kotlin.system.exitProcess


class SettingsController {
    @FXML
    public lateinit var backButton: Button
    public lateinit var exitButton: Button
    public lateinit var logoutButton: Button

    public lateinit var welcomeText: Label
    public lateinit var background: AnchorPane

    @FXML

    public fun initialize() {
        //background red
        background.setStyle("-fx-background-color: red;")

        backButton.setStyle("-fx-background-radius: 25;")
        exitButton.setStyle("-fx-background-radius: 25;")
        logoutButton.setStyle("-fx-background-radius: 25;")

        //set text to white
        welcomeText.setStyle("-fx-text-fill: white;")

        //backbutton set image to back arrow
        backButton.graphic = ImageView(Image("C:\\Users\\mcpla\\OneDrive\\Desktop\\Year 4\\Programming Languages\\WarriorFit\\src\\main\\resources\\goBack.png"))
        //remove backbuttontext
        backButton.text = ""
        (backButton.graphic as ImageView)?.fitWidth = 60.0
        (backButton.graphic as ImageView)?.fitHeight = 60.0
    }
    public fun onBackButtonClick() {
        try {
            backButton.text = "Back Button Clicked"
            //go to previous scene
            // Load the new FXML file temporary home file
            val loader = FXMLLoader(javaClass.getResource
            ("/com/warriorfit/warriorfit/home.fxml"))
            val root = loader.load<Parent>()

            // Create a new scene with a specified width and height
            val newScene = Scene(root, 1280.0, 720.0)

            // Get the current stage
            val currentStage = backButton.scene.window as Stage

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
    public fun onExitButtonClick() {
        exitButton.text = "Exit Button Clicked"
        //close app
        exitProcess(0)
    }
    public fun onLogoutButtonClick() {
        logoutButton.text = "Logout Button Clicked"
    }
}