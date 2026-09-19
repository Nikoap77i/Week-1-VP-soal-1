package com.example.soal1

class restaurantManager {
    private val menu = mutableListOf<menuItem>()
    private val orders = mutableListOf<order>()
    private var nextMenuID = 1

    fun addMenu(name: String, description: String, price: Double): Boolean{
        if (name.isBlank() || price < 0) return false
        menu.add(menuItem(nextMenuID++, name, description, price))
        return true
    }
    fun editMenuItem(id: Int, newName: String, newDescription: String, newPrice: Double): Boolean{
        val item = menu.find { it.id == id } ?: return false
        if (newName.isBlank() || newPrice < 0) return false
        item.name = newName
        item.description = newDescription
        item.price = newPrice
        return true
    }
    fun deleteMenu(id: Int): Boolean{
        return menu.removeIf { it.id == id }
    }
    fun getMenu(): List<menuItem> = menu.toList()
    fun createOrder(customerName: String, orderedItems: Map<Int, Int>): order? {
        if (customerName.isBlank() || orderedItems.isEmpty()) return null
        val orderItem = mutableListOf<itemOrdered>()
        var total = 0.0

        for ((menuId, quantities) in orderedItems) {
            if (quantities <= 0) continue
            val menuItem = menu.find { it.id == menuId } ?: continue
            orderItem.add(itemOrdered(menuItem, quantities))
            total += menuItem.price * quantities
        }

        if (orderItem.isEmpty()) return null

        val newOrder = order(customerName, orderItem, total)
        orders.add(newOrder)
        return newOrder
    }
    fun getOrder(): List<order> = orders.toList()
}