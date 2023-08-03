package com.example.myapp.portofolio.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapp.portofolio.model.YearPortofolio

@Composable
fun LineChart(yearPortofolio: YearPortofolio) {
    // Implement Line Chart using Jetpack Compose here
    // You can use Canvas and draw the chart based on the data provided in yearPortofolio
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        // TODO: Implement the line chart drawing logic here
    }
}