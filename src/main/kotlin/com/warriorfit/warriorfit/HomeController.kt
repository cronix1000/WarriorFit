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

class HomeController {
    @FXML
    public lateinit var settingsButton: Button
    public lateinit var welcomeText: Label
    public lateinit var background: AnchorPane
    public lateinit var currentStatsText: Label
    // start workout button
    public lateinit var startWorkoutButton: Button

    //imageview warriorLogo
    public lateinit var warriorLogo: ImageView

    @FXML
//initialize function
    public fun initialize() {
        // background red
        background.setStyle("-fx-background-color: red;")
        //white text
        welcomeText.setStyle("-fx-text-fill: white;")
        currentStatsText.setStyle("-fx-text-fill: white;")
        settingsButton.setStyle("-fx-background-radius: 25;")

        //put a settings cogwheel image on button
        settingsButton.graphic = ImageView(Image("" + javaClass.getResource("/settings.png")))
        //remove settingstext
        settingsButton.text = ""
        //graphic size and location 50x50
        (settingsButton.graphic as ImageView)?.fitWidth = 90.0
        (settingsButton.graphic as ImageView)?.fitHeight = 90.0
        //change graphic colour to white
        settingsButton.graphic.style = "-fx-fill: white;"
        //remove button background
        settingsButton.setStyle("-fx-background-color: transparent;")

        //start workout button
        startWorkoutButton.setStyle("-fx-background-radius: 28;")

        //image size
        ImageView().fitWidth = 500.0
        ImageView().fitHeight = 500.0
        warriorLogo.image = Image("" + javaClass.getResource("/warriorImage.png"))

        //workout button transparent, change text to blue
        startWorkoutButton.setStyle("-fx-background-color: transparent;")
        startWorkoutButton.setStyle("-fx-text-fill: red;")

    }

    public fun onStartWorkoutButtonClick() {
        try {
            startWorkoutButton.text = "Start Workout Button Clicked"
            // Load the new FXML file temporary home file
            val loader = FXMLLoader(
                javaClass.getResource
                    ("/com/warriorfit/warriorfit/start-workout-view.fxml")
            )
            val root = loader.load<Parent>()
            val newScene = Scene(root, 1280.0, 720.0) // Set width to 800 and height to 600
            val currentStage = startWorkoutButton.scene.window as Stage
            currentStage.scene = newScene
            currentStage.title = "Warrior Fitness - Start Workout"
            currentStage.show()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    public fun onSettingsButtonClick() {
        try {
            settingsButton.text = "Settings Button Clicked"
            // Load the new FXML file temporary home file
            val loader = FXMLLoader(javaClass.getResource("/com/warriorfit/warriorfit/settings.fxml"))
            val root = loader.load<Parent>()

            // Create a new scene with a specified width and height
            val newScene = Scene(root, 1280.0, 720.0) // Set width to 800 and height to 600

            // Get the current stage
            val currentStage = settingsButton.scene.window as Stage

            // Set the new scene on the current stage
            currentStage.scene = newScene
            currentStage.title = "Warrior Fitness - Settings"

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