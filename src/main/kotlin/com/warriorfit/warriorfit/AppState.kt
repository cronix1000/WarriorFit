package com.warriorfit.warriorfit

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.appwrite.Client
import io.appwrite.models.Collection
import io.appwrite.models.Database
import io.appwrite.models.Document
import io.appwrite.services.Account
import io.appwrite.services.Databases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext



object AppState {
    private const val DATABASE_ID = "6732731c0015a0af0d1e"
    private const val STATS_COLLECTION_ID = "673a59ce0037e409146e"
    private const val USERS_COLLECTION_ID = "6736dacb0021a324feed"


    val client = Client()
        .setEndpoint("https://cloud.appwrite.io/v1")
        .setProject("673272a1002734925f33")
        .setKey("standard_6968dc3490c3429654fa0cc9c066b9cd8ed4b1c8269509c77c9efe5bd8a54733bcce1c24768e9040b7289b59810e9af3fe9effe7dc8069abf9c4c50e4a590f92e0c234c9f04c0779ff82fb08d0e1f00b54d77a5921a0437f22377afbe16c50e500346d10dc338beba3bd4e001d27a6910c97add504367ab26bb18c262754248f");
    private val account = Account(com.warriorfit.warriorfit.client)
    private val databases = Databases(com.warriorfit.warriorfit.client)
    public var excersieDB: Database? = null

    public var usersCollection: Collection? = null

    private  var userName: String = ""
    private var userId: String = ""

    private var exercises: List<Exercise> = listOf()
    private val gson = Gson()

    private var isExercisesLoaded = false

    fun setUserName(name: String){
        userName = name
    }

    fun getUserName(): String{
        return userName
    }

    fun setUserId(id: String) {
        userId = id
    }

    fun getUserId(): String {
        return userId
    }

    fun loadExercises() {
        if (!isExercisesLoaded) {
            try {
                val jsonString = javaClass.getResourceAsStream("/exercises.json")?.bufferedReader()?.readText()
                    ?: throw Exception("Could not find exercises.json")

                exercises = try {
                    val listType = object : TypeToken<List<Exercise>>() {}.type
                    gson.fromJson(jsonString, listType)
                } catch (e: Exception) {
                    listOf(gson.fromJson(jsonString, Exercise::class.java))
                }

                isExercisesLoaded = true
            } catch (e: Exception) {
                println("Error loading exercises: ${e.message}")
                exercises = listOf()
            }
        }
    }

    fun getExercises(): List<Exercise> {
        if (!isExercisesLoaded) {
            loadExercises()
        }
        return exercises
    }

    fun clearUserSession() {
        userId = ""
    }

        suspend fun getCurrentUserData(): Document<Map<String, Any>>? {
            return withContext(Dispatchers.IO) {
                try {
                    databases.getDocument(
                        databaseId = DATABASE_ID,
                        collectionId = USERS_COLLECTION_ID,
                        documentId = userId
                    )
                } catch (e: Exception) {
                    println("Error fetching user data: ${e.message}")
                    null
                }
            }
        }

        suspend fun getUserFitnessData(): Triple<Map<String, Any>?,Map<String, Any>?, Map<String, Any>?> {
            return withContext(Dispatchers.IO) {
                try {
                    val userData = getCurrentUserData() ?: return@withContext Triple(null, null, null)

                    // Extract nested IDs correctly
                    val statsId = (userData.data["stats_id"] as? Map<String, Any>)
                    val muscleGroupsId = (userData.data["muscle_groups_id"] as? Map<String, Any>)
                    val workoutsId = (userData.data["workouts_id"] as? List<Map<String, Any>>)?.firstOrNull()




                    Triple(statsId, muscleGroupsId, workoutsId)
                } catch (e: Exception) {
                    println("Error fetching fitness data: ${e.message}")
                    Triple(null, null, null)
                }
            }
        }

        suspend fun updateUserStats(
            strength: Double,
            endurance: Double,
            flexibility: Double,
            speed: Double,
            balance: Double
        ) {
            withContext(Dispatchers.IO) {
                try {
                    val userData = getCurrentUserData() ?: throw Exception("User data not found")
                    val stats = userData.data["stats_id"] as? Map<String, Any>
                    val statsId = stats?.get("stats_id") as? String
                        ?: throw Exception("Stats ID not found")
                    try {
                        val response = databases.updateDocument(
                            databaseId = DATABASE_ID,
                            collectionId = STATS_COLLECTION_ID,
                            documentId = statsId,
                            data = mapOf(
                                "strength" to strength,
                                "endurance" to endurance,
                                "flexibility" to flexibility,
                                "speed" to speed,
                                "balance" to balance
                            )
                        )
                        println("Update successful: $response")
                    } catch (e: Exception) {
                        println("Update failed: ${e.message}")
                        e.printStackTrace()
                    }
                } catch (e: Exception) {
                    println("Error updating user stats: ${e.message}")
                }
            }
        }
    }