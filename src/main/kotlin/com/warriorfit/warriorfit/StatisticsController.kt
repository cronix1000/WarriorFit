package com.warriorfit.warriorfit

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.VBox
import javafx.stage.Stage
import java.io.IOException
import kotlin.system.exitProcess

class StatisticsController {
@FXML
public lateinit var backButton: Button
//stats label/ muscles label
public lateinit var statsLabel: Label
public lateinit var musclesLabel: Label
public lateinit var background: AnchorPane

//label for statistics
public lateinit var statisticsLabel: Label

//list view muscles/stats
public lateinit var statsListView: ListView<String>
public lateinit var musclesListView: ListView<String>

@FXML

//init
    public fun initialize() {
    //background red
    background.setStyle("-fx-background-color: red;")

    //make buttons background white
    backButton.setStyle("-fx-background-color: white;")

    backButton.setStyle("-fx-background-radius: 28;")

    //set text to white
    statsLabel.setStyle("-fx-text-fill: white;")
    musclesLabel.setStyle("-fx-text-fill: white;")
    statisticsLabel.setStyle("-fx-text-fill: white;")

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

    //list view
    //add items from db for stats
    statsListView.items.addAll("Strength:", "Endurance:", "Flexibility:", "Speed:", "Balance:")
    //text size 20
    statsListView.setStyle("-fx-font-size: 20;")
    //add items from db for muscles
    musclesListView.items.addAll("Chest:", "Back:", "Legs:", "Shoulders:", "Core:")
    //text size 20
    musclesListView.setStyle("-fx-font-size: 20;")

}
    //back button
    public fun onBackButtonClick() {
    try {
        backButton.text = "Back Button Clicked"
        //go to previous scene
        // Load the new FXML file temporary home file
        val loader = FXMLLoader(javaClass.getResource
            ("/com/warriorfit/warriorfit/home.fxml"))
        val root = loader.load<Parent>()
        val stage = backButton.scene.window as Stage
        stage.scene = Scene(root, 1280.0, 720.0)
    } catch (e: IOException) {
        e.printStackTrace()
    }
    }
}