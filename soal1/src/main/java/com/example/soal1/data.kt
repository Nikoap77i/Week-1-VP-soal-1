package com.example.soal1

data class menuItem(
    var id: Int,
    var name: String,
    var description: String,
    var price: Double
)
data class itemOrdered(
    val menuITEM: menuItem,
    var quantity: Int
)
data class order(
    val name: String,
    val items: List<itemOrdered>,
    val totalPrice: Double
)