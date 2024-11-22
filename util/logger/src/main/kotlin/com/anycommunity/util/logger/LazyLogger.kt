package com.anycommunity.util.logger

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

class LazyLogger : ReadOnlyProperty<Any, KLogger> {
    private var logger: KLogger? = null

    override fun getValue(thisRef: Any, property: KProperty<*>) =
        logger ?: createLogger(thisRef::class).also { logger = it }

    companion object {
        private fun createLogger(clazz: KClass<*>) = KotlinLogging.logger(
            clazz.java.enclosingClass?.name ?: clazz.java.name,
        )
    }
}
