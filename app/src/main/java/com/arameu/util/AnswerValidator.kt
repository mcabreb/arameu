package com.arameu.util

object AnswerValidator {

    fun validate(
        userAnswer: String,
        correctAnswer: String,
        acceptedVariants: List<String>?,
    ): Boolean {
        val normalised = userAnswer.trim().lowercase()
        if (normalised.isEmpty()) return false
        if (normalised == correctAnswer.trim().lowercase()) return true
        return acceptedVariants?.any { it.trim().lowercase() == normalised } == true
    }
}
