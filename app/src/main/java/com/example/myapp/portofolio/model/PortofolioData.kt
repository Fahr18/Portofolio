package com.example.myapp.portofolio.model

import androidx.compose.ui.graphics.Color

data class PortofolioData(
    val label: String,
    val percentage: Float,
    val data: List<TransactionData>,
    val color: Color
)