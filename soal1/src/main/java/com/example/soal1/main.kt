package com.example.soal1

import java.util.Scanner

fun main() {
    val manager = restaurantManager()
    manager.addMenu("Ayam Geprek","Ayam digeprek dengan sambal", 25000.0)
    manager.addMenu("Indomie", "Mie instant", 10000.0)
    manager.addMenu("Es Teh", "Teh manis atau tawar dengan es", 5000.0)

    val scanner = Scanner(System.`in`)
    var running = true

    while (running){
        println("Welcome to Restaurant Ciamik")
        println("=== Ordering system ===")
        println("[1] Make Order")
        println("[2] View Order")
        println("[3] View Menu")
        println("[4] Add Menu")
        println("[5] Edit Menu")
        println("[6] Delete Menu")
        println("[7] Exit")
        print("Input : ")

        when(scanner.nextLine()?.trim()){
            "1" -> {
                print("Customer's name : ")
                val name = scanner.nextLine()?.trim().orEmpty()
                if (name.isBlank()){
                    print("Error. Name is empty, Try again")
                    continue
                }
                val menu = manager.getMenu()
                if (menu.isEmpty()){
                    print("Menu is empty, please add items first")
                    continue
                }
                val cart = mutableMapOf<Int, Int>()
                while (true){
                    println("=== Menu ===")
                    menu.forEach { println("[${it.id}] ${it.name}  -  Rp.${it.price}")}
                    print("Enter menu ID to add to cart, or type Done if you are finished : ")
                    val input = scanner.nextLine()?.trim()
                    if (input.equals("done", ignoreCase = true)) break

                    val id = input?.toIntOrNull()
                    if (id == null || menu.none { it.id == id }) {
                        println("Invalid ID Menu, please try again")
                        continue
                    }

                    print("Enter quantity : ")
                    val quantity = scanner.nextLine()?.toIntOrNull()
                    if (quantity == null || quantity <= 0){
                        println("Invalid item quantity, please try again")
                        continue
                    }

                    cart[id] = (cart[id]?: 0) + quantity
                    println("Added item to cart")
                }
                val order = manager.createOrder(name,cart)
                if (order != null){
                    println("=== ${order.name}'s Order ===")
                    order.items.forEachIndexed { index, item ->
                        println("${index + 1}. ${item.menuITEM.name} ${item.quantity}  -  Rp.${item.menuITEM.price * item.quantity}")
                    }
                    println("Total Rp.${order.totalPrice}")
                } else {
                    println("Order invalid, your cart is empty")
                }
            }
            "2" -> {
                val orders = manager.getOrder()
                if (orders.isEmpty()){
                    println("No order has been made")
                } else {
                    orders.forEach { currOrder ->
                        println("Customer : ${currOrder.name} | Total : Rp.${currOrder.totalPrice}")
                        currOrder.items.forEach { println("- ${it.menuITEM.name} ${it.quantity}x") }
                    }
                }
            }
            "3" -> {
                println("=== Restaurant Menu ===")
                val menu = manager.getMenu()
                if (menu.isEmpty()){
                    println("Menu is empty")
                }
                menu.forEach { println("[${it.id}] ${it.name} - Rp.${it.price}\n Description : ${it.description}") }
            }
            "4" -> {
                print("Enter menu name : ")
                val menuName = scanner.nextLine()?.trim().orEmpty()
                print("Enter description : ")
                val description = scanner.nextLine()?.trim().orEmpty()
                print("Enter price : ")
                val price = scanner.nextLine()?.toDoubleOrNull() ?: -1.0
                if (manager.addMenu(menuName, description, price)){
                    println("New menu has been added successfully")
                } else {
                    println("Error, invalid input")
                }
            }
            "5" -> {
                print("Enter menu ID to edit : ")
                val menuID = scanner.nextLine()?.toIntOrNull() ?: -1
                print("Enter new menu name : ")
                val newMenuName = scanner.nextLine()?.trim().orEmpty()
                print("Enter new description : ")
                val newDescription = scanner.nextLine()?.trim().orEmpty()
                print("Enter new price : ")
                val newPrice = scanner.nextLine()?.toDoubleOrNull() ?: -1.0
                if (manager.editMenuItem(menuID, newMenuName, newDescription, newPrice )){
                    println("Menu has been edited successfully")
                } else {
                    println("Error, ID not found or invalid input")
                }
            }
            "6" -> {
                print("Enter menu ID to delete : ")
                val menuID = scanner.nextLine()?.toIntOrNull() ?: -1
                if (manager.deleteMenu(menuID)){
                    println("Menu has been deleted successfully")
                } else {
                    println("Error, ID not found")
                }
            }
            "7" -> {
                println("Exiting Restaurant Ciamik, Thankyou for visiting")
                running = false
            }
            else -> println("Invalid input, please choose between 1-7")
        }
    }

}