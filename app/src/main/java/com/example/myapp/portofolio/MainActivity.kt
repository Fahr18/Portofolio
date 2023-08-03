package com.example.myapp.portofolio

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapp.portofolio.model.*
import com.example.myapp.portofolio.ui.DonutChart
import com.example.myapp.portofolio.ui.HistoryTransactionDetail
import com.example.myapp.portofolio.ui.LabelSelection
import com.example.myapp.portofolio.ui.theme.PortofolioTheme
import com.example.myapp.portofolio.util.*


@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PortofolioTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    MyApp()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalComposeUiApi
@Composable
fun MyApp() {
    val context = LocalContext.current
    val json = remember { readJsonFromAssets(context, "userPortofolio.json") }
    val monthPortofolio = remember { parseMonthPortofolio(json.getJSONObject("monthPorto")) }
    var scale by remember { mutableStateOf(1f) }
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Portofolio Keuangan") }
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { navController.navigate("labelSelection") }
                    ) {
                        Text("Riwayat Transaksi")
                    }
                }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTransformGestures { _, pan, gestureZoom, _ ->
                                scale *= gestureZoom
                            }
                        }
                ) {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        item {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = "Data Portofolio Bulan Ini")
                                DonutChart(monthPortofolio, scale) { newScale ->
                                    scale = newScale
                                }
                            }
                        }
                    }
                }
            }
        }
        composable("labelSelection") {
            LabelSelection(monthPortofolio.data, navController)
        }
        composable(
            "historyTransactionDetail/{selectedLabel}",
            arguments = listOf(navArgument("selectedLabel") { type = NavType.StringType })
        ) { backStackEntry ->
            val selectedLabel = backStackEntry.arguments?.getString("selectedLabel") ?: ""
            HistoryTransactionDetail(selectedLabel, monthPortofolio, navController)
        }
    }
}
