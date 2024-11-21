package com.warriorfit.warriorfit

//button
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.VBox
import javafx.stage.Stage
import java.io.IOException
import kotlin.system.exitProcess


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
        vbox.setStyle("-fx-background-color: red;")

        startText.setStyle("-fx-text-fill: white;")
        alreadyText.setStyle("-fx-text-fill: white;")
        //button smooth edges
        createButton.setStyle("-fx-background-radius: 28;")
        loginButton.setStyle("-fx-background-radius: 28;")
        exitButton.setStyle("-fx-background-radius: 28;")

        ImageView().fitWidth = 800.0
        ImageView().fitHeight = 800.0
        imageView.image = Image("" + javaClass.getResource("/warriorFitness.png"))
        ImageView().fitWidth = 800.0
        ImageView().fitHeight = 800.0
        imageView2.image = Image("" + javaClass.getResource("/warriorImage.png"))
    }
    public fun onCreateButtonClick() {
        try {
        createButton.text = "Create Button Clicked"

        val loader = FXMLLoader(javaClass.getResource("/com/warriorfit/warriorfit/create-account.fxml"))
        val root = loader.load<Parent>()

        val newScene = Scene(root, 1280.0, 720.0)

        val currentStage = createButton.scene.window as Stage

        currentStage.scene = newScene
        currentStage.title = "Warrior Fitness - Home"

        currentStage.minWidth = 1280.0
        currentStage.minHeight = 720.0

        currentStage.show()

    } catch (e: IOException) {
        e.printStackTrace()
    }
    }

    public fun onLoginButtonClick() {
        try {
            val loader = FXMLLoader(javaClass.getResource("/com/warriorfit/warriorfit/login.fxml"))
            val root = loader.load<Parent>()

            val newScene = Scene(root, 1280.0, 720.0)

            val currentStage = loginButton.scene.window as Stage

            currentStage.scene = newScene
            currentStage.title = "Warrior Fitness - Settings"

            currentStage.minWidth = 1280.0
            currentStage.minHeight = 720.0

            currentStage.show()
        }
     catch (e: IOException) {
        e.printStackTrace()
    }
    }

    public fun onExitButtonClick() {
        exitButton.text = "Exit Button Clicked"
        exitProcess(0)
    }
}