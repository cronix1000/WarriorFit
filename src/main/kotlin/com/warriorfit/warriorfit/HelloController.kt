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
        //white text, bold, size 20, outline black text, font is neo sans
        startText.setStyle("-fx-text-fill: white;")
        alreadyText.setStyle("-fx-text-fill: white;")
        //image size
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

        // Load the new FXML file temporary home file
        val loader = FXMLLoader(javaClass.getResource("/com/warriorfit/warriorfit/create-account.fxml"))
        val root = loader.load<Parent>()

        // Create a new scene with a specified width and height
        val newScene = Scene(root, 1280.0, 720.0) // Set width to 800 and height to 600

        // Get the current stage
        val currentStage = createButton.scene.window as Stage

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

    public fun onLoginButtonClick() {
        try {
            val loader = FXMLLoader(javaClass.getResource("/com/warriorfit/warriorfit/login.fxml"))
            val root = loader.load<Parent>()

            // Create a new scene with a specified width and height
            val newScene = Scene(root, 1280.0, 720.0) // Set width to 800 and height to 600

            // Get the current stage
            val currentStage = loginButton.scene.window as Stage

            // Set the new scene on the current stage
            currentStage.scene = newScene
            currentStage.title = "Warrior Fitness - Settings"

            // Optionally, you can also set a minimum or fixed size on the stage
            currentStage.minWidth = 1280.0
            currentStage.minHeight = 720.0

            // Display the stage
            currentStage.show()
        }
     catch (e: IOException) {
        e.printStackTrace()
    }
    }

    public fun onExitButtonClick() {
        //close the application
        exitButton.text = "Exit Button Clicked"
        exitProcess(0)
    }
}