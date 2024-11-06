package com.warriorfit.plugins

import com.warriorfit.controllers.exerciseController
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
//        userController()
//        workoutController()
        exerciseController() // Add the new controller
    }
}
