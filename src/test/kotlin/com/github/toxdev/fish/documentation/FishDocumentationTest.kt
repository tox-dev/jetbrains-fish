package com.github.toxdev.fish.documentation

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class FishDocumentationTest {
    @ParameterizedTest
    @ValueSource(
        strings = [
            "if", "else", "for", "while", "switch", "case", "begin", "end",
            "function", "return", "break", "continue", "echo", "set", "read",
            "test", "string", "cd", "source", "eval", "exec", "exit", "and",
            "or", "not", "builtin", "command", "status", "printf", "math", "in", "time",
        ],
    )
    fun `command docs exist for keyword`(command: String) {
        val doc = FishDocumentation.getCommandDoc(command)
        assertNotNull(doc, "Documentation should exist for command: $command")
        assertTrue(doc!!.contains("<b>$command</b>"), "Documentation should contain command name: $command")
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "status", "argv", "pipestatus", "CMD_DURATION", "fish_pid", "last_pid",
            "PATH", "CDPATH", "fish_user_paths", "fish_function_path", "fish_complete_path",
            "fish_greeting", "fish_history", "fish_trace", "version", "hostname", "history",
            "status_generation", "fish_kill_signal", "fish_killring", "_",
        ],
    )
    fun `variable docs exist`(variable: String) {
        val doc = FishDocumentation.getVariableDoc(variable)
        assertNotNull(doc, "Documentation should exist for variable: $variable")
    }

    @Test
    fun `getVariableDoc handles dollar prefix`() {
        val doc = FishDocumentation.getVariableDoc("\$status")
        assertNotNull(doc)
        assertTrue(doc!!.contains("exit status"))
    }

    @Test
    fun `getVariableDoc handles array index suffix`() {
        val doc = FishDocumentation.getVariableDoc("\$argv[1]")
        assertNotNull(doc)
        assertTrue(doc!!.contains("arguments"))
    }

    @ParameterizedTest
    @ValueSource(strings = ["|", ">", ">>", "<", "&&", "||", "&", "2>", "&>"])
    fun `operator docs exist`(operator: String) {
        val doc = FishDocumentation.getOperatorDoc(operator)
        assertNotNull(doc, "Documentation should exist for operator: $operator")
    }

    @Test
    fun `unknown command returns null`() {
        assertNull(FishDocumentation.getCommandDoc("unknown_command_xyz"))
    }

    @Test
    fun `unknown variable returns null`() {
        assertNull(FishDocumentation.getVariableDoc("unknown_var_xyz"))
    }

    @Test
    fun `unknown operator returns null`() {
        assertNull(FishDocumentation.getOperatorDoc("%%%"))
    }

    @Test
    fun `command doc contains synopsis section`() {
        val doc = FishDocumentation.getCommandDoc("if")
        assertNotNull(doc)
        assertTrue(doc!!.contains("Synopsis"))
    }

    @Test
    fun `command doc contains description section`() {
        val doc = FishDocumentation.getCommandDoc("echo")
        assertNotNull(doc)
        assertTrue(doc!!.contains("Description"))
    }

    @Test
    fun `variable doc contains description section`() {
        val doc = FishDocumentation.getVariableDoc("status")
        assertNotNull(doc)
        assertTrue(doc!!.contains("Description"))
    }

    @Test
    fun `operator doc contains description section`() {
        val doc = FishDocumentation.getOperatorDoc("|")
        assertNotNull(doc)
        assertTrue(doc!!.contains("Description"))
    }
}
