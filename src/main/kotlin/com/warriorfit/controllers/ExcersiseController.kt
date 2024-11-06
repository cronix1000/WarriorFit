package com.warriorfit.controllers

import io.ktor.http.*
import io.ktor.server.routing.*
import kotlin.text.get

// Controllers/ExerciseController.kt
fun Route.exerciseController() {
    route("/api/exercises") {
        get {
            //call.respond(exerciseService.getAllExercises())
        }

//        get("/{id}") {
//            val id = call.parameters["id"]?.toIntOrNull()
//            if (id != null) {
//                val exercise = exerciseService.getExerciseById(id)
//                if (exercise != null) {
//                    call.respond(exercise)
//                } else {
//                    call.respond(HttpStatusCode.NotFound)
//                }
//            } else {
//                call.respond(HttpStatusCode.BadRequest)
//            }
       // }

        // Add other CRUD operations...
    }
}