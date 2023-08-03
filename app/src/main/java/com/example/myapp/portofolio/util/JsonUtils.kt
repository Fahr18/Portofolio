package com.example.myapp.portofolio.util

import android.content.Context
import androidx.compose.ui.graphics.Color
import com.example.myapp.portofolio.model.*
import org.json.JSONObject

fun readJsonFromAssets(context: Context, fileName: String): JSONObject {
    val json = context.assets.open(fileName).bufferedReader().use {
        it.readText()
    }
    return JSONObject(json)
}

fun parseMonthPortofolio(json: JSONObject): MonthPortofolio {
    val type = json.getString("type")
    val dataArray = json.getJSONArray("data")
    val dataList = mutableListOf<PortofolioData>()

    for (i in 0 until dataArray.length()) {
        val dataObj = dataArray.getJSONObject(i)
        val label = dataObj.getString("label")
        val percentage = dataObj.getString("percentage").toFloat()
        val transactionArray = dataObj.getJSONArray("data")
        val transactionList = mutableListOf<TransactionData>()

        for (j in 0 until transactionArray.length()) {
            val transactionObj = transactionArray.getJSONObject(j)
            val trxDate = transactionObj.getString("trx_date")
            val nominal = transactionObj.getInt("nominal")
            val transactionData = TransactionData(trxDate, nominal)
            transactionList.add(transactionData)
        }
        val color = when (label) {
            "Tarik Tunai" -> Color(android.graphics.Color.parseColor("#69D0F5"))
            "QRIS Payment" -> Color(android.graphics.Color.parseColor("#F57AF5"))
            "Topup Gopay" -> Color(android.graphics.Color.parseColor("#5DF562"))
            "Lainnya" -> Color(android.graphics.Color.parseColor("#E5FA7D"))
            else -> Color.White
        }

        val portofolioData = PortofolioData(label, percentage, transactionList, color)
        dataList.add(portofolioData)
    }

    return MonthPortofolio(type, dataList)
}
