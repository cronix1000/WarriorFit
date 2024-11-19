package com.warriorfit.warriorfit

import io.appwrite.Client
import io.appwrite.ID
import io.appwrite.models.Collection
import io.appwrite.models.Database
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
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.VBox
import javafx.stage.Stage
import javafx.scene.control.TextField
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*

class SignUpController {

    val client = Client()
        .setEndpoint("https://cloud.appwrite.io/v1")
        .setProject("673272a1002734925f33")
        .setKey("standard_6968dc3490c3429654fa0cc9c066b9cd8ed4b1c8269509c77c9efe5bd8a54733bcce1c24768e9040b7289b59810e9af3fe9effe7dc8069abf9c4c50e4a590f92e0c234c9f04c0779ff82fb08d0e1f00b54d77a5921a0437f22377afbe16c50e500346d10dc338beba3bd4e001d27a6910c97add504367ab26bb18c262754248f");

    private val account = Account(client)
    private val databases = Databases(client)
    private var excersieDB : Database? = null
    private var usersCollection : Collection? = null

    suspend fun initDB(){
        excersieDB = databases.get("6732731c0015a0af0d1e")
        usersCollection = databases.getCollection("6732731c0015a0af0d1e", "6736dacb0021a324feed")

    }

    fun createAccount(
        email: String,
        password: String,
        username: String,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Create Appwrite account
                val user = account.create(
                    userId = ID.unique(),
                    email = email,
                    password = password,
                    name = username
                )

                // Create user profile in database
                createUserProfile(
                    userId = user.id,
                    email = email,
                    username = username
                )

                Platform.runLater {
                    val alert = Alert(AlertType.INFORMATION)
                    alert.contentText = "Account Creation Successful"
                    alert.show()
                   // onSuccess()
                }
            } catch (e: Exception) {
                Platform.runLater {
                    val alert = Alert(AlertType.ERROR)
                    alert.contentText = "Account Creation Failed"
                    alert.show()
                    println(e.message)
                }

            }
        }
    }

    // Helper function to create user profile in database
    private suspend fun createUserProfile(
        userId: String,
        email: String,
        username: String
    ) {
        // Create stats document
        val statsId = ID.unique().substring(0, 14)
        val statsData = mapOf(
            "stats_id" to statsId,
            "strength" to 0,
            "endurance" to 0,
            "flexibility" to 0,
            "speed" to 0,
            "balance" to 0
        )

        databases.createDocument(
            databaseId = "6732731c0015a0af0d1e",
            collectionId = "673a59ce0037e409146e", // Replace with actual collection ID
            documentId = statsId,
            data = statsData
        )

        // Create muscle groups document
        val muscleGroupsId = ID.unique().substring(0, 14)
        val muscleGroupsData = mapOf(
            "muscle_groups_id" to muscleGroupsId,
            "chest" to 0,
            "back" to 0,
            "legs" to 0,
            "shoulders" to 0,
            "core" to 0
        )

        databases.createDocument(
            databaseId = "6732731c0015a0af0d1e",
            collectionId = "673a59c600068e20490a", // Replace with actual collection ID
            documentId = muscleGroupsId,
            data = muscleGroupsData
        )

        // Create initial workout document (optional, you might not want to create this immediately)
        val workoutId = ID.unique().substring(0, 14)
        val workoutData = mapOf(
            "workouts_id" to workoutId,
            "date" to Date(),
            "type" to "initial",
            "duration" to 0,
            "exercises" to listOf<String>(),
            "muscleGroups" to muscleGroupsId,  // Reference to muscle groups
            "totalXPEarned" to "0",
            "notes" to ""
        )

        databases.createDocument(
            databaseId = "6732731c0015a0af0d1e",
            collectionId = "6736dc8f0019010e89b3", // Replace with actual collection ID
            documentId = workoutId,
            data = workoutData
        )

        // Create main user profile document with relationships
        val userData = mapOf(
            "user_id" to userId,
            "level" to 1,
            "currentXP" to 0,
            "streak" to 0,
            "workouts_id" to listOf(workoutId),  // Initialize with the first workout
            "stats_id" to statsId,
            "muscle_groups_id" to muscleGroupsId,
        )

        databases.createDocument(
            databaseId = "6732731c0015a0af0d1e",
            collectionId = "6736dacb0021a324feed",
            documentId = userId,
            data = userData
        )

        AppState.setUserId(userId)

        // Load exercises once at login
        AppState.loadExercises()
    }


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

        createAccount(emailField.text,passwordField.text, usernameField.text)

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