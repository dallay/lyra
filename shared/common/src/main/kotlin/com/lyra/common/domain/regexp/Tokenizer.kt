package com.lyra.common.domain.regexp

import java.text.ParseException
import java.util.regex.Pattern

/**
 * Tokenizer class for parsing strings based on specified patterns.
 *
 * This class allows adding multiple regex patterns and their corresponding token creators.
 * It then uses these patterns to tokenize an input string into a collection of tokens.
 *
 * @created 10/1/24
 */
class Tokenizer {
    // List of patterns and their corresponding token creators
    private val patterns = mutableListOf<Pair<Pattern, (String) -> Token>>()

    /**
     * Adds a new pattern and its corresponding token creator to the tokenizer.
     *
     * @param regex The regex pattern to match.
     * @param creator The function to create a token from the matched string.
     * @return The current instance of the Tokenizer for method chaining.
     */
    fun add(regex: String, creator: (String) -> Token): Tokenizer {
        patterns.add(Pattern.compile(regex) to creator)
        return this
    }

    /**
     * Tokenizes the input string based on the added patterns.
     *
     * @param clause The input string to tokenize.
     * @return A collection of tokens parsed from the input string.
     * @throws ParseException If an unexpected sequence is found in the input string.
     */
    fun tokenize(clause: String): Collection<Token> {
        val tokens = mutableListOf<Token>()
        var copy = String(clause.toCharArray())
        var position = 0
        while (copy != "") {
            var found = false
            for ((pattern, fn) in patterns) {
                val m = pattern.matcher(copy)
                if (m.find()) {
                    found = true
                    val token = m.group(1)
                    tokens.add(fn(token))
                    copy = m.replaceFirst("")
                    position += token.length
                    break
                }
            }
            if (!found) {
                throw ParseException("Unexpected sequence found in input string.", ++position)
            }
        }
        return tokens
    }
}
