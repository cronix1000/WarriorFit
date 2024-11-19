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
    private const val ENDPOINT = "YOUR_APPWRITE_ENDPOINT"
    private const val PROJECT_ID = "6732731c0015a0af0d1e"
    private const val DATABASE_ID = "6732731c0015a0af0d1e"
    private const val STATS_COLLECTION_ID = "673a59ce0037e409146e"
    private const val MUSCLE_GROUPS_COLLECTION_ID = "673a59c600068e20490a"
    private const val USERS_COLLECTION_ID = "6736dacb0021a324feed"
    private const val WORKOUTS_COLLECTION_ID = "6736dc8f0019010e89b3"


    val client = Client()
        .setEndpoint("https://cloud.appwrite.io/v1")
        .setProject("673272a1002734925f33")
        .setKey("standard_6968dc3490c3429654fa0cc9c066b9cd8ed4b1c8269509c77c9efe5bd8a54733bcce1c24768e9040b7289b59810e9af3fe9effe7dc8069abf9c4c50e4a590f92e0c234c9f04c0779ff82fb08d0e1f00b54d77a5921a0437f22377afbe16c50e500346d10dc338beba3bd4e001d27a6910c97add504367ab26bb18c262754248f");
    private val account = Account(com.warriorfit.warriorfit.client)
    private val databases = Databases(com.warriorfit.warriorfit.client)
    public var excersieDB: Database? = null

    public var usersCollection: Collection? = null


    // User state
    private var userId: String = ""

    // Exercise data
    private var exercises: List<Exercise> = listOf()
    private val gson = Gson()

    // Initialization flag
    private var isExercisesLoaded = false

    fun setUserId(id: String) {
        userId = id
    }

    fun getUserId(): String {
        return userId ?: throw IllegalStateException("User ID not set. Make sure user is logged in.")
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

        // Get current user data including relationships
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

        // Get user's stats and muscle groups in one call
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

        // Update user's stats
        suspend fun updateUserStats(
            strength: Int,
            endurance: Int,
            flexibility: Int,
            speed: Int,
            balance: Int
        ) {
            withContext(Dispatchers.IO) {
                try {
                    val userData = getCurrentUserData() ?: throw Exception("User data not found")
                    val statsId = userData.data["stats_id"] as? String ?: throw Exception("Stats ID not found")

                    databases.updateDocument(
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
                } catch (e: Exception) {
                    println("Error updating user stats: ${e.message}")
                }
            }
        }

        // Update user's muscle groups
        suspend fun updateUserMuscleGroups(
            chest: Int,
            back: Int,
            legs: Int,
            shoulders: Int,
            core: Int
        ) {
            withContext(Dispatchers.IO) {
                try {
                    val userData = getCurrentUserData() ?: throw Exception("User data not found")
                    val muscleGroupsId = userData.data["muscle_groups_id"] as? String
                        ?: throw Exception("Muscle groups ID not found")

                    databases.updateDocument(
                        databaseId = DATABASE_ID,
                        collectionId = MUSCLE_GROUPS_COLLECTION_ID,
                        documentId = muscleGroupsId,
                        data = mapOf(
                            "chest" to chest,
                            "back" to back,
                            "legs" to legs,
                            "shoulders" to shoulders,
                            "core" to core
                        )
                    )
                } catch (e: Exception) {
                    println("Error updating muscle groups: ${e.message}")
                }
            }
        }
    }