package com.arameu

import org.junit.Assert.assertTrue
import org.junit.Test

class ProjectSetupTest {

    @Test
    fun `project compiles and test framework works`() {
        assertTrue("Test framework is operational", true)
    }

    @Test
    fun `package name is com arameu`() {
        val packageName = this::class.java.`package`?.name
        assertTrue(
            "Package should be com.arameu, was $packageName",
            packageName == "com.arameu"
        )
    }
}
