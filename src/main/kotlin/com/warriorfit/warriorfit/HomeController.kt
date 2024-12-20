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
import kotlinx.coroutines.runBlocking
import java.io.IOException

class HomeController {
    @FXML
    lateinit var settingsButton: Button

    lateinit var startButton: Button

    lateinit var welcomeText: Label
    lateinit var background: AnchorPane
    lateinit var levelLabel: Label

    //imageview warriorLogo
    lateinit var warriorLogo: ImageView

    //statistics button
    lateinit var statisticsButton: Button

    @FXML
//initialize function
    fun initialize() {
        // background red
        background.style = "-fx-background-color: red;"
        //white text
        welcomeText.style = "-fx-text-fill: white;"
        levelLabel.style = "-fx-text-fill: white;"
        settingsButton.style = "-fx-background-radius: 25;"

        startButton.style = "-fx-background-color: white;"
        startButton.style = "-fx-background-radius: 28;"
        startButton.style = "-fx-text-fill: red;"

        statisticsButton.style = "-fx-background-color: white;"
        statisticsButton.style = "-fx-background-radius: 28;"
        statisticsButton.style = "-fx-text-fill: red;"

        settingsButton.text = ""

        settingsButton.graphic = ImageView(Image("" + javaClass.getResource("/settings.png")))
        (settingsButton.graphic as ImageView).fitWidth = 70.0
        (settingsButton.graphic as ImageView).fitHeight = 70.0

        settingsButton.graphic.style = "-fx-fill: white;"

        settingsButton.style = "-fx-background-color: transparent;"

        ImageView().fitWidth = 500.0
        ImageView().fitHeight = 500.0
        warriorLogo.image =
            Image("" + javaClass.getResource("/warriorImage.png"))

        var name = ""
        var level = ""
        runBlocking {
            val user = AppState.getCurrentUserData()
            level = user!!.data["level"].toString()
            name = AppState.getUserName()

            levelLabel.text = "Current Level: $level"

            //if level is 1 then Welcome Beginner
            //level is 0
            welcomeText.text = when (level) {
                "0", "1" -> "Welcome Beginner"
                "2" -> "Welcome Intermediate"
                "3" -> "Welcome Advanced"
                "4" -> "Welcome Expert"
                "5" -> "Welcome Master"
                else -> "Welcome Beginner" // Default case
            }
        }
    }

    fun onStatisticsButtonClick() {
        try {
            statisticsButton.text = "Statistics Button Clicked"
            // Load the new FXML file temporary home file
            val loader = FXMLLoader(javaClass.getResource
            ("/com/warriorfit/warriorfit/statistics.fxml"))
            val root = loader.load<Parent>()

            val newScene = Scene(root, 1280.0, 720.0)

            val currentStage = statisticsButton.scene.window as Stage

            currentStage.scene = newScene
            currentStage.title = "Warrior Fitness - Statistics"

            currentStage.minWidth = 1280.0
            currentStage.minHeight = 720.0

            currentStage.show()

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun onSettingsButtonClick() {
        try {
            settingsButton.text = "Settings Button Clicked"
            val loader = FXMLLoader(javaClass.getResource("/com/warriorfit/warriorfit/settings.fxml"))
            val root = loader.load<Parent>()

            val newScene = Scene(root, 1280.0, 720.0)

            val currentStage = settingsButton.scene.window as Stage

            currentStage.scene = newScene
            currentStage.title = "Warrior Fitness - Settings"

            currentStage.minWidth = 1280.0
            currentStage.minHeight = 720.0

            currentStage.show()

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun onStartWorkoutButtonClick() {
        try {
            startButton.text = "Start Workout Button Clicked"
            val loader = FXMLLoader(javaClass.getResource("/com/warriorfit/warriorfit/start-workout-view.fxml"))
            val root = loader.load<Parent>()

            val newScene = Scene(root, 1280.0, 720.0)

            val currentStage = startButton.scene.window as Stage

            currentStage.scene = newScene
            currentStage.title = "Warrior Fitness - Start Workout"

            currentStage.minWidth = 1280.0
            currentStage.minHeight = 720.0

            currentStage.show()

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}