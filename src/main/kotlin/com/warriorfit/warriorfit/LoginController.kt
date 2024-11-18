package com.warriorfit.warriorfit

import io.appwrite.Client
import io.appwrite.services.Account
import io.appwrite.services.Databases
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import javafx.stage.Stage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException


class LoginController {


    val client = Client()
    .setEndpoint("https://cloud.appwrite.io/v1")
    .setProject("673272a1002734925f33")
    .setKey("standard_6968dc3490c3429654fa0cc9c066b9cd8ed4b1c8269509c77c9efe5bd8a54733bcce1c24768e9040b7289b59810e9af3fe9effe7dc8069abf9c4c50e4a590f92e0c234c9f04c0779ff82fb08d0e1f00b54d77a5921a0437f22377afbe16c50e500346d10dc338beba3bd4e001d27a6910c97add504367ab26bb18c262754248f");

    private val account = Account(client)
    private val databases = Databases(client)

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val session = account.createEmailPasswordSession(
                    email = email,
                    password = password
                )
                val userId = session.userId

                AppState.setUserId(userId)

                Platform.runLater {
                    val alert = Alert(AlertType.INFORMATION)
                    alert.contentText = "Login Successful"
                    alert.show()
                    onSuccess()
                }
            } catch (e: Exception) {
                Platform.runLater {
                    val alert = Alert(AlertType.ERROR)
                    alert.contentText = "Login Failed"
                    alert.show()
                    onError(e)
                }
            }
        }
    }

    @FXML
    public lateinit var submitButton: Button
    public lateinit var background: AnchorPane

    //username and password text field
    public lateinit var emailTextField: TextField //used to check if username is correct
    public lateinit var passwordTextField: TextField //used to check if password is correct

    public lateinit var loginText: Label

    //backbutton
    public lateinit var backButton: Button
    @FXML
    //initialize function
    public fun initialize() {

        loginText.setStyle("-fx-text-fill: white;")

        //make submit button background white
        submitButton.setStyle("-fx-background-color: white;")

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

    // on submit button click
    public fun onSubmitButtonClick() {
            login(
                email = emailTextField.text.trim(),
                password = passwordTextField.text.trim(),
                onSuccess = { proceedFunction() },  // Lambda for success callback
                onError = { e -> println("Failed: ${e.message}") }  // Lambda for error callback
            )
    }

    fun proceedFunction(){
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