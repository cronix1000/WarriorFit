package com.warriorfit.warriorfit

import io.appwrite.models.Collection
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.shape.ClosePath
import javafx.scene.shape.LineTo
import javafx.scene.shape.MoveTo
import javafx.scene.shape.Path
import javafx.scene.text.Text
import javafx.stage.Stage
import kotlinx.coroutines.runBlocking
import java.io.IOException
import kotlin.math.cos
import kotlin.math.sin

class StatisticsController {
    @FXML
    public lateinit var backButton: Button

    @FXML private lateinit var spiderChart: Pane
    @FXML private lateinit var avgSessionValue: Label
    @FXML private lateinit var trainingTimeValue: Label

    var statsCollection : Collection? = null
    var muscleGroupCollection : Collection? = null
    var usersCollection : Collection? = null

    @FXML private lateinit var background2: AnchorPane
    @FXML private lateinit var background: VBox

    @FXML private lateinit var currentStatsLabel: Label

    private var stats = listOf<Pair<String, Any>>(
        "Chest" to 0,
        "Shoulders" to 0,
        "Legs" to 0,
        "Back" to 0,
        "Triceps" to 0,
        "Biceps" to 0
    )

    @FXML
    fun initialize() {
        runBlocking {
            val (userStats, _) = AppState.getUserFitnessData()
            stats = userStats?.toList() ?: stats
            val personalStats = listOf<Pair<String, Any>>(
                ("Strength" to stats.find { it.first == "strength" }!!.second),
                ("Endurance" to stats.find { it.first == "endurance" }!!.second),
                ("Flexibility" to stats.find { it.first == "flexibility" }!!.second),
                ("Speed" to stats.find { it.first == "speed" }!!.second),
                ("Balance" to stats.find { it.first == "balance" }!!.second)
            )
            stats = personalStats
            drawSpiderChart()
        }

        drawSpiderChart()

        //background red
        spiderChart.setStyle("-fx-background-color: red;")
        //back button
        backButton.graphic = ImageView(Image("" + javaClass.getResource("/goBack.png")))
        backButton.text = ""
        (backButton.graphic as ImageView)?.fitWidth = 70.0
        (backButton.graphic as ImageView)?.fitHeight = 70.0

        //transparent background
        backButton.setStyle("-fx-background-color: transparent;")

        //set text to white
        currentStatsLabel.setStyle("-fx-text-fill: white;")

        //background red
        background2.setStyle("-fx-background-color: red;")
        //set background to red
        background.setStyle("-fx-background-color: red;")

    }

    /**
     * Draws a spider (radar) chart in the spiderChart container.
     *
     * This function does the following:
     * 1. Calculates the center and radius of the chart based on container dimensions
     * 2. Creates the background web/grid of the spider chart
     * 3. Draws a polygon representing the data points
     * 4. Adds axis labels around the chart
     * 5. Sets up resize event listeners to dynamically update the chart
     */
    private fun drawSpiderChart() {
        val centerX = spiderChart.width / 2
        val centerY = spiderChart.height / 2
        val radius = minOf(centerX, centerY) * 0.8

        val backgroundWeb = createWebShape(centerX, centerY, radius, Color.web("#333333"))

        val dataPolygon = createDataPolygon(centerX, centerY, radius)

        val labels = createAxisLabels(centerX, centerY, radius)

        spiderChart.children.addAll(backgroundWeb, dataPolygon)
        spiderChart.children.addAll(labels)

        // Handle resize events
        spiderChart.widthProperty().addListener { _, _, _ -> updateChart() }
        spiderChart.heightProperty().addListener { _, _, _ -> updateChart() }
    }

    /**
     * Creates the background web/grid shape for the spider chart.
     *
     * @param centerX X-coordinate of the chart's center
     * @param centerY Y-coordinate of the chart's center
     * @param radius Radius of the chart
     * @param color Color of the web lines
     * @return Path representing the web/grid lines
     */
    private fun createWebShape(centerX: Double, centerY: Double, radius: Double, color: Color): Path {
        val path = Path()
        path.stroke = color
        path.fill = Color.TRANSPARENT
        path.strokeWidth = 1.0

        for (i in 0..5) {
            val angle = 2 * Math.PI * i / 6 - Math.PI / 2
            val x = centerX + radius * cos(angle)
            val y = centerY + radius * sin(angle)

            if (i == 0) {
                path.elements.add(MoveTo(x, y))
            } else {
                path.elements.add(LineTo(x, y))
            }
        }
        path.elements.add(ClosePath())

        return path
    }

    /**
     * Creates a polygon representing the data points on the spider chart.
     *
     * @param centerX X-coordinate of the chart's center
     * @param centerY Y-coordinate of the chart's center
     * @param radius Radius of the chart
     * @return Path representing the data polygon
     */
    private fun createDataPolygon(centerX: Double, centerY: Double, radius: Double): Path {
        val path = Path()
        path.fill = Color.web("#007AFF", 0.3)
        path.stroke = Color.web("#007AFF")
        path.strokeWidth = 2.0

        stats.forEachIndexed { index, stat ->
            val angle = 2 * Math.PI * index / 6 - Math.PI / 2

            val value = stat.second as Double
            val x = centerX + radius * value * cos(angle)
            val y = centerY + radius * value * sin(angle)

            if (index == 0) {
                path.elements.add(MoveTo(x, y))
            } else {
                path.elements.add(LineTo(x, y))
            }
        }
        path.elements.add(ClosePath())

        return path
    }

    /**
     * Creates labels for each axis of the spider chart.
     *
     * @param centerX X-coordinate of the chart's center
     * @param centerY Y-coordinate of the chart's center
     * @param radius Radius of the chart
     * @return List of Text nodes representing axis labels
     */
    private fun createAxisLabels(centerX: Double, centerY: Double, radius: Double): List<Text> {
        return stats.mapIndexed { index, stat ->
            val angle = 2 * Math.PI * index / 6 - Math.PI / 2
            val labelRadius = radius + 20
            val x = centerX + labelRadius * cos(angle)
            val y = centerY + labelRadius * sin(angle)

            Text(x, y, stat.first).apply {
                fill = Color.WHITE
                style = "-fx-font-size: 12px; -fx-font-weight: bold;"
            }
        }
    }

    private fun updateChart() {
        spiderChart.children.clear()
        drawSpiderChart()
    }

    public fun onBackButtonClick() {
        try {
            backButton.text = "Back Button Clicked"
            //go to previous scene
            // Load the new FXML file temporary home file
            val loader = FXMLLoader(
                javaClass.getResource
                    ("/com/warriorfit/warriorfit/home.fxml")
            )
            val root = loader.load<Parent>()
            val stage = backButton.scene.window as Stage
            stage.scene = Scene(root, 1280.0, 720.0)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}