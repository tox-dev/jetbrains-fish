package com.github.toxdev.fish.run

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class FishRunConfigurationTypeTest {
    private val configurationType = FishRunConfigurationType()

    @Test
    fun `getDisplayName returns Fish Script`() {
        assertEquals("Fish Script", configurationType.displayName)
    }

    @Test
    fun `getConfigurationTypeDescription returns correct description`() {
        assertEquals("Run a Fish shell script", configurationType.configurationTypeDescription)
    }

    @Test
    fun `getId returns FishRunConfiguration`() {
        assertEquals("FishRunConfiguration", configurationType.id)
    }

    @Test
    fun `getIcon returns non-null icon`() {
        assertNotNull(configurationType.icon)
    }

    @Test
    fun `getConfigurationFactories returns one factory`() {
        val factories = configurationType.configurationFactories

        assertEquals(1, factories.size)
    }

    @Test
    fun `factory has correct id`() {
        val factory = configurationType.configurationFactories.first()

        assertEquals("FishConfigurationFactory", factory.id)
    }

    @Test
    fun `factory has correct name`() {
        val factory = configurationType.configurationFactories.first()

        assertEquals("Fish Script", factory.name)
    }
}
