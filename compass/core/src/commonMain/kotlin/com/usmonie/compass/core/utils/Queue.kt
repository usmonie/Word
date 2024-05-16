package com.usmonie.compass.core.utils

interface Queue<T : Any> : Collection<T> {

    fun peek(): T

    fun peekOrNull(): T?
}

interface MutableQueue<T : Any> : Queue<T> {

    fun add(item: T)

    fun add(items: Collection<T>)

    fun remove(): T

    fun removeOrNull(): T?

    fun poll(): T? = removeOrNull()

    fun pop(): T? = poll()
}

class Stack<T : Any>(
    private val items: MutableList<T> = mutableListOf()
) : MutableQueue<T>, List<T> by items {

    override val size: Int
        get() = items.size

    override fun add(item: T) {
        items.add(item)
    }

    override fun add(items: Collection<T>) {
        this.items.addAll(items)
    }

    override fun peek(): T = items.last()

    override fun peekOrNull(): T? = items.lastOrNull()

    override fun remove(): T = items.removeLast()

    override fun removeOrNull(): T? = items.removeLastOrNull()

    override fun poll(): T? = if (size > 1) super.poll() else null

    fun removeUntil(predicate: (T) -> Boolean): Boolean {
        var success = false
        val shouldPop = {
            items.lastOrNull()
                ?.let(predicate)
                ?.also { success = it }
                ?.not()
                ?: false
        }

        while (size > 0 && shouldPop()) {
            items.removeLast()
        }

        return success
    }
}

fun <T : Any> List<T>.toStack() = Stack(this.toMutableList())
