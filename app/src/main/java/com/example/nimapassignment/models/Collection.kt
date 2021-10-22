package com.example.nimapassignment.models

import java.util.*

data class Collection (
    val title: String,
    val description: String,
    val collected: String,
    val goal: String,
    val startDate: Date,
    val endDate: Date,
    val imgUrl: String
)