package com.example.kaptain.data

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*

object BookStore {

    private var events = MutableSharedFlow<List<Order>>(replay = 5)
    private val scope = CoroutineScope(Job() + Dispatchers.Default)

    init {
        print("in init")
        events.onEach {
            handleOrders(it)
        }.launchIn(scope)
    }

    private fun handleOrders(orderList: List<Order>) {
        orderList.map { order ->
            scope.launch {
                delayOrder(order)
            }
        }
    }

    fun startMain() {
        runBlocking {
            val nrOfOrders = (1..5).random()
            getOrders(nrOfOrders)
            delay(10000)
        }
    }

    private suspend fun getOrders(nrOfOrders: Int) {
        val ordersBook = mutableListOf<Book>()
        val order = mutableListOf<Order>()
        (1..nrOfOrders).forEach { _ ->
            val randType = Type.values()[(0..5).random()]
            val randName = BookName.values()[(0..5).random()]
            ordersBook.add(Book(randName, randType))
        }
        order.add(Order(UUID.randomUUID(), ordersBook))
        println("orders $order")
        events.emit(order)
    }

    private suspend fun delayOrder(order: Order) {
        scope.launch {
            val awaitList = mutableListOf<Deferred<Unit>>()
            order.books.forEach { _ ->
                awaitList.add(async(Dispatchers.Default) {
                    val timePerBook = (1..6).random() * 1000
                    println("Start delay $timePerBook")
                    delay(timePerBook.toLong())
                    println("End delay $timePerBook")
                })
            }
            awaitList.awaitAll()
            delay(4000)
            println("endDelayForOrder")
        }
    }
}

fun main() {
    while (true) {
        runBlocking {
            BookStore.startMain()
        }
    }
}

data class Book(val bookName: BookName, val type: Type)
data class Order(val id: UUID, val books: List<Book>)

enum class Type {
    Adventure,
    Mystery,
    Fantasy,
    Historical,
    SF,
    Other
}

enum class BookName {
    HarryPotter,
    Hobbit,
    LordOfTheRings,
    TheDiaryOfAYoungGirl,
    PrideAndPrejudice,
    Other
}