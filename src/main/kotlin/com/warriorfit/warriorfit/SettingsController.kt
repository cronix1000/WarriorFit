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

        //make buttons background white
        backButton.setStyle("-fx-background-color: white;")
        exitButton.setStyle("-fx-background-color: white;")
        logoutButton.setStyle("-fx-background-color: white;")

        backButton.setStyle("-fx-background-radius: 28;")
        exitButton.setStyle("-fx-background-radius: 28;")
        logoutButton.setStyle("-fx-background-radius: 28;")

        //set text to white
        welcomeText.setStyle("-fx-text-fill: white;")

        //backbutton set image to back arrow
        backButton.graphic = ImageView(Image("" + javaClass.getResource("/goBack.png")))
        //remove backbuttontext
        backButton.text = ""
        (backButton.graphic as ImageView).fitWidth = 70.0
        (backButton.graphic as ImageView).fitHeight = 70.0
        //change graphic colour to white
        backButton.graphic.style = "-fx-fill: white;"
        //remove button background
        backButton.setStyle("-fx-background-color: transparent;")
    }
    public fun onBackButtonClick() {
        try {
            backButton.text = "Back Button Clicked"
            val loader = FXMLLoader(javaClass.getResource
            ("/com/warriorfit/warriorfit/home.fxml"))
            val root = loader.load<Parent>()

            val newScene = Scene(root, 1280.0, 720.0)

            val currentStage = backButton.scene.window as Stage

            currentStage.scene = newScene
            currentStage.title = "Warrior Fitness - Home"

            currentStage.minWidth = 1280.0
            currentStage.minHeight = 720.0

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
        try {
            val loader = FXMLLoader(javaClass.getResource
            ("/com/warriorfit/warriorfit/hello-view.fxml"))
            val root = loader.load<Parent>()

            val newScene = Scene(root, 1280.0, 720.0)

            val currentStage = backButton.scene.window as Stage

            currentStage.scene = newScene
            currentStage.title = "Warrior Fitness"

            currentStage.minWidth = 1280.0
            currentStage.minHeight = 720.0

            currentStage.show()

        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}