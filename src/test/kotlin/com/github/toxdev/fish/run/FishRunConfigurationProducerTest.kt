package com.github.toxdev.fish.run

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class FishRunConfigurationProducerTest {
    private val producer = FishRunConfigurationProducer()

    @Test
    fun `producer is instantiable`() {
        assertNotNull(producer)
    }

    @Test
    fun `getConfigurationFactory returns factory`() {
        assertNotNull(producer.configurationFactory)
    }
}
