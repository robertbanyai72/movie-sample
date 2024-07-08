package com.robert.banyai.sample.domain.logger

import java.util.logging.Level
import java.util.logging.Logger

fun logD(tag: String, message: String) {
    Logger.getLogger(tag).log(Level.FINE, message)
}

fun logE(tag: String, message: String, throwable: Throwable? = null) {
    val logger = Logger.getLogger(tag)
    if (throwable == null) {
        logger.log(Level.SEVERE, message)
    } else {
        logger.log(Level.SEVERE, message, throwable)
    }
}
