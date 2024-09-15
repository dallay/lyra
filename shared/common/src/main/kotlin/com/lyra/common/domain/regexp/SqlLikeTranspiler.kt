package com.lyra.common.domain.regexp

/**
 * Object responsible for transpiling SQL LIKE patterns to regular expressions.
 *
 * This object uses a tokenizer to parse SQL LIKE patterns and convert them into
 * equivalent regular expressions.
 *
 * @created 10/1/24
 */
object SqlLikeTranspiler {
    // Tokenizer for parsing SQL LIKE patterns
    private val TOKENIZER = Tokenizer()
        .add("^\\[([^]]*)]") { EscapeToken(it) } // Token for escaped characters
        .add("^(%)") { WildcardToken(it) } // Token for wildcard '%'
        .add("^(_)") { WildcharToken(it) } // Token for wildcard '_'
        .add("^([^\\[\\]%_]+)") { StringToken(it) } // Token for regular strings

    /**
     * Converts a SQL LIKE pattern to a regular expression.
     *
     * This function takes a SQL LIKE pattern and converts it into a regular expression
     * by tokenizing the pattern and converting each token to its regex equivalent.
     *
     * @param pattern The SQL LIKE pattern to convert.
     * @return The equivalent regular expression.
     */
    fun toRegEx(pattern: String): String {
        val sb = StringBuilder().append("^")
        val tokens = TOKENIZER.tokenize(pattern)
        for (token in tokens) {
            sb.append(token.convert())
        }
        return sb.append("$").toString()
    }
}
