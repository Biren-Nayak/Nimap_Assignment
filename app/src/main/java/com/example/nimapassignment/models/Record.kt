package com.example.nimapassignment.models

data class Record(
    val Id: Int,
    val collectedValue: Int,
    val endDate: String,
    val mainImageURL: String,
    val shortDescription: String,
    val startDate: String,
    val title: String,
    val totalValue: Int
)