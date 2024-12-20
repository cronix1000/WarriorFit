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

    public lateinit var emailTextField: TextField
    public lateinit var passwordTextField: TextField

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
        (backButton.graphic as ImageView).fitWidth = 70.0
        (backButton.graphic as ImageView).fitHeight = 70.0
        //change graphic colour to white
        backButton.graphic.style = "-fx-fill: white;"
        //remove button background
        backButton.setStyle("-fx-background-color: transparent;")

    }

    //on submit button click
    public fun onSubmitButtonClick() {
            login(
                email = emailTextField.text,
                password = passwordTextField.text,
                onSuccess = { proceedFunction() },  // Lambda for success callback
                onError = { e -> println("Failed: ${e.message}") }  // Lambda for error callback
            )

    }

    fun proceedFunction(){
        try {
            val loader = FXMLLoader(javaClass.getResource("/com/warriorfit/warriorfit/home.fxml"))
            val root = loader.load<Parent>()

            val newScene = Scene(root, 1280.0, 720.0)

            val currentStage = submitButton.scene.window as Stage

            currentStage.scene = newScene
            currentStage.title = "Warrior Fitness - Home"

            currentStage.minWidth = 1280.0
            currentStage.minHeight = 720.0

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