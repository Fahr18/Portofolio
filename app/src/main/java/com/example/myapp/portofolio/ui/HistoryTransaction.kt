package com.example.myapp.portofolio.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.myapp.portofolio.model.MonthPortofolio
import com.example.myapp.portofolio.model.TransactionData
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HistoryTransaction(monthPortofolio: MonthPortofolio, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Riwayat Transaksi") }
            )
        }
    ) {
        LazyColumn {
            items(monthPortofolio.data) { portofolioData ->
                Button(
                    onClick = {
                        navController.navigate("historyTransactionDetail/${portofolioData.label}")
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(portofolioData.label)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HistoryTransactionDetail(selectedLabel: String, monthPortofolio: MonthPortofolio, navController: NavController) {
    val selectedData = remember {
        monthPortofolio.data.firstOrNull { it.label == selectedLabel }
    }

    val transactionList = remember {
        selectedData?.data.orEmpty()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(selectedLabel) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* "" */ }) {
                        Icon(Icons.Default.Home, contentDescription = "Home")
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn {
                items(transactionList) { transaction ->
                    TransactionItem(transaction)
                }
            }
        }
    }
}

@Composable
fun TransactionItem(transaction: TransactionData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Tanggal: ${transaction.trx_date}")
            Text(text = "Nominal: Rp ${transaction.nominal}")
        }
    }
}
