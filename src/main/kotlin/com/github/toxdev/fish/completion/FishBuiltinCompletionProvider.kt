package com.github.toxdev.fish.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.util.ProcessingContext

class FishBuiltinCompletionProvider : CompletionProvider<CompletionParameters>() {
    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet,
    ) {
        BUILTINS.forEach { builtin ->
            result.addElement(
                LookupElementBuilder
                    .create(builtin)
                    .withTypeText("builtin"),
            )
        }
    }

    companion object {
        private val BUILTINS =
            setOf(
                "alias",
                "abbr",
                "argparse",
                "bg",
                "bind",
                "block",
                "cd",
                "commandline",
                "complete",
                "contains",
                "count",
                "dirh",
                "dirs",
                "disown",
                "echo",
                "emit",
                "eval",
                "exit",
                "fg",
                "fish",
                "fish_add_path",
                "fish_breakpoint_prompt",
                "fish_command_not_found",
                "fish_config",
                "fish_git_prompt",
                "fish_indent",
                "fish_is_root_user",
                "fish_key_reader",
                "fish_opt",
                "fish_prompt",
                "fish_right_prompt",
                "fish_status_to_signal",
                "fish_title",
                "fish_update_completions",
                "fish_vcs_prompt",
                "funced",
                "funcsave",
                "functions",
                "help",
                "history",
                "isatty",
                "jobs",
                "math",
                "nextd",
                "open",
                "path",
                "popd",
                "prevd",
                "printf",
                "prompt_pwd",
                "pushd",
                "pwd",
                "random",
                "read",
                "realpath",
                "source",
                "status",
                "string",
                "test",
                "trap",
                "type",
                "ulimit",
                "umask",
                "wait",
            )
    }
}
