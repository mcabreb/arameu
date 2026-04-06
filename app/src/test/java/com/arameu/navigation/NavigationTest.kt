package com.arameu.navigation

import org.junit.Assert.assertEquals
import org.junit.Test

class NavigationTest {

    @Test
    fun `Routes WELCOME is welcome`() {
        assertEquals("welcome", Routes.WELCOME)
    }

    @Test
    fun `Routes COURSE is course`() {
        assertEquals("course", Routes.COURSE)
    }

    @Test
    fun `Routes LESSON template has lessonId param`() {
        assertEquals("lesson/{lessonId}", Routes.LESSON)
    }

    @Test
    fun `Routes lesson helper builds correct path`() {
        assertEquals("lesson/42", Routes.lesson(42))
    }

    @Test
    fun `Routes lesson helper handles lesson 1`() {
        assertEquals("lesson/1", Routes.lesson(1))
    }
}
