package com.lyra.common.domain.regexp

/**
 *
 * @created 10/1/24
 */
object SqlLikeTranspiler {
    private val TOKENIZER = Tokenizer()
        .add("^\\[([^]]*)]") { EscapeToken(it) }
        .add("^(%)") { WildcardToken(it) }
        .add("^(_)") { WildcharToken(it) }
        .add("^([^\\[\\]%_]+)") { StringToken(it) }

    fun toRegEx(pattern: String): String {
        val sb = StringBuilder().append("^")
        val tokens = TOKENIZER.tokenize(pattern)
        for (token in tokens) {
            sb.append(token.convert())
        }
        return sb.append("$").toString()
    }
}
