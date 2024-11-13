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

val client = Client()
    .setEndpoint("https://cloud.appwrite.io/v1")
    .setProject("673272a1002734925f33")
.setKey("standard_6968dc3490c3429654fa0cc9c066b9cd8ed4b1c8269509c77c9efe5bd8a54733bcce1c24768e9040b7289b59810e9af3fe9effe7dc8069abf9c4c50e4a590f92e0c234c9f04c0779ff82fb08d0e1f00b54d77a5921a0437f22377afbe16c50e500346d10dc338beba3bd4e001d27a6910c97add504367ab26bb18c262754248f");

val databases = Databases(client)

var todoDatabase: Database? = null
var todoCollection: Collection? = null

suspend fun prepareDatabase() {
    todoDatabase = databases.get("6732731c0015a0af0d1e")
    todoCollection = databases.getCollection("6732731c0015a0af0d1e", "67342fdd002592c10942")
}

//suspend fun seedDatabase() {
//    val testTodo1 = mapOf(
//        "title" to "Buy apples",
//        "description" to "At least 2KGs",
//        "isComplete" to true
//    )
//
//    val testTodo2 = mapOf(
//        "title" to "Wash the apples",
//        "isComplete" to true
//    )
//
//    val testTodo3 = mapOf(
//        "title" to "Cut the apples",
//        "description" to "Don't forget to pack them in a box",
//        "isComplete" to false
//    )
//
//    databases.createDocument(
//        databaseId = todoDatabase?.id!!,
//        collectionId = todoCollection?.id!!,
//        documentId = ID.unique(),
//        data = testTodo1
//    )
//
//    databases.createDocument(
//        databaseId = todoDatabase?.id!!,
//        collectionId = todoCollection?.id!!,
//        documentId = ID.unique(),
//        data = testTodo2
//    )
//
//    databases.createDocument(
//        databaseId = todoDatabase?.id!!,
//        collectionId = todoCollection?.id!!,
//        documentId = ID.unique(),
//        data = testTodo3
//    )
//}

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
        stage.title = "Hello!"
        stage.scene = scene
        stage.show()
    }
}

suspend fun main() = coroutineScope {
    Application.launch(HelloApplication::class.java)
        prepareDatabase()
        //seedDatabase()
        getTodos()

}