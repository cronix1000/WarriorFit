package com.warriorfit.warriorfit

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import io.appwrite.Client
import io.appwrite.ID
import io.appwrite.services.Databases
import io.appwrite.models.Database
import io.appwrite.models.Collection
import kotlinx.coroutines.coroutineScope

//image view
import javafx.scene.image.Image
import javafx.scene.image.ImageView

//my account
//Test@Tester.ca
//Tester1234

val client = Client()
    .setEndpoint("https://cloud.appwrite.io/v1")
    .setProject("673272a1002734925f33")
.setKey("standard_6968dc3490c3429654fa0cc9c066b9cd8ed4b1c8269509c77c9efe5bd8a54733bcce1c24768e9040b7289b59810e9af3fe9effe7dc8069abf9c4c50e4a590f92e0c234c9f04c0779ff82fb08d0e1f00b54d77a5921a0437f22377afbe16c50e500346d10dc338beba3bd4e001d27a6910c97add504367ab26bb18c262754248f");

val databases = Databases(client)

var todoDatabase: Database? = null
var todoCollection: Collection? = null

suspend fun getTodos() {
    val todos = databases.listDocuments(todoDatabase?.id!!, todoCollection?.id!!)
    for (todo in todos.documents) {
        println(
            """
            Title: ${todo.data["title"]}
            Description: ${todo.data["description"]}
            Is Todo Complete: ${todo.data["isComplete"]}
            """.trimIndent()
        )
    }
}

class HelloApplication : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(HelloApplication::class.java.getResource("hello-view.fxml"))
        val scene = Scene(fxmlLoader.load(), 1280.0, 720.0)
        //image
        scene.stylesheets.add("styles/style.css")
        stage.title = "Warrior Fitness"
        stage.scene = scene
        stage.show()
    }
}

suspend fun main() = coroutineScope {
    Application.launch(HelloApplication::class.java)
    AppState.loadExercises()
    AppState.excersieDB = databases.get("6732731c0015a0af0d1e")
}