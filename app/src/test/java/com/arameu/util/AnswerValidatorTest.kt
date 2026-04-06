package com.arameu.util

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AnswerValidatorTest {

    @Test
    fun `exact match returns true`() {
        assertTrue(AnswerValidator.validate("malka", "malka", null))
    }

    @Test
    fun `case insensitive match returns true`() {
        assertTrue(AnswerValidator.validate("Malka", "malka", null))
    }

    @Test
    fun `variant match returns true`() {
        assertTrue(AnswerValidator.validate("malkā", "malka", listOf("malkā", "malko")))
    }

    @Test
    fun `whitespace trimmed before validation`() {
        assertTrue(AnswerValidator.validate("  malka  ", "malka", null))
    }

    @Test
    fun `empty input returns false`() {
        assertFalse(AnswerValidator.validate("", "malka", null))
    }

    @Test
    fun `whitespace only returns false`() {
        assertFalse(AnswerValidator.validate("   ", "malka", null))
    }

    @Test
    fun `wrong answer returns false`() {
        assertFalse(AnswerValidator.validate("beth", "malka", listOf("malkā")))
    }

    @Test
    fun `variant is case insensitive`() {
        assertTrue(AnswerValidator.validate("ALEPH", "aleph", listOf("Aleph")))
    }

    @Test
    fun `null variants only checks correct answer`() {
        assertTrue(AnswerValidator.validate("aleph", "aleph", null))
        assertFalse(AnswerValidator.validate("Alef", "aleph", null))
    }

    @Test
    fun `empty variants list returns false for non-exact`() {
        assertFalse(AnswerValidator.validate("alef", "aleph", emptyList()))
    }
}
