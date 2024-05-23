package com.lyra.common.domain

import java.util.concurrent.ConcurrentHashMap

/**
 * Implements memoization functionality that allows caching the results of function or supplier calls.
 * Memoization is a technique that allows to cache the results of function calls for each input.
 * @author Yuniel
 */
object Memoizers {
    /**
     * Creates a memoized supplier that caches the result of the given supplier.
     *
     * @param Result The type of the result of the supplier.
     * @param supplier The supplier to be memoized.
     * @return A memoized supplier that caches the result of the given supplier.
     */
    fun <Result> of(supplier: () -> Result): () -> Result = { of { _: Any? -> supplier() }(null) }

    /**
     * Constructs a memoized function that caches the results of the given function for each input.
     *
     * @param function the function to be memoized
     * @param Input the input type of the function
     * @param Result the result type of the function
     * @return a memoized function that caches and returns the results of the given function
     */
    fun <Input, Result> of(function: (Input) -> Result): (Input) -> Result = MemoizedFunction(function)

    /**
     * A class representing a memoized function.
     *
     * @param Input The type of the input to the function.
     * @param Result The type of the result returned by the function.
     * @property function The function to be memoized.
     * @property results The map storing memoized inputs and results.
     */
    private class MemoizedFunction<Input, Result>(private val function: (Input) -> Result) : (Input) -> Result {
        private val results = ConcurrentHashMap<MemoizedInput<Input>, MemoizedResult<Result>>()

        /**
         * Invokes the method with the given input and returns the result.
         *
         * @param input The input parameter of type [Input] to be passed to the method.
         *
         * @return The result of type [Result] returned by the method.
         */
        override fun invoke(
            input: Input
        ): Result = results.computeIfAbsent(MemoizedInput(input), this::toMemoizedResult).result

        /**
         * Converts a MemoizedInput object into a MemoizedResult object.
         *
         * @param input The MemoizedInput object to be converted.
         * @return A MemoizedResult object.
         */
        private fun toMemoizedResult(
            input: MemoizedInput<Input>
        ): MemoizedResult<Result> = MemoizedResult(function(input.input))

        /**
         * Represents a memoized input object.
         *
         * @param Input the type of the input object.
         * @property input the input object to be memoized.
         */
        private data class MemoizedInput<Input>(val input: Input)

        /**
         * Represents a memoized result object.
         *
         * @param Result the type of the result object.
         * @property result the result object to be memoized.
         */
        private data class MemoizedResult<Result>(val result: Result)
    }
}
