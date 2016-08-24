package redux

/*
 * Copyright (C) 2016 Michael Pardo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * A reducer accepts an accumulation and a value and returns a new accumulation. They are used to reduce a collection of
 * values down to a single value.
 *
 * @see <a href="http://redux.js.org/docs/basics/Reducers.html">http://redux.js.org/docs/basics/Reducers.html</a>
 */
interface Reducer<S : Any> {

    /**
     * A pure function which returns a new state given the previous state and an action.
     *
     * Things you should never do inside a reducer:
     * * Mutate its arguments;
     * * Perform side effects like API calls and routing transitions;
     * * Call non-pure functions, e.g. Date.now() or Math.random().
     *
     * Given the same arguments, it should calculate the next state and return it. No surprises. No side effects. No API
     * calls. No mutations. Just a calculation.
     *
     * @see <a href="http://redux.js.org/docs/basics/Reducers.html">http://redux.js.org/docs/basics/Reducers.html</a>
     *
     * @param[state] The previous state
     * @param[action] The dispatched action
     * @return The new state
     */
    fun reduce(state: S, action: Any): S

    private class CombinedReducer<S : Any>(vararg val reducers: Reducer<S>) : Reducer<S> {

        override fun reduce(state: S, action: Any): S {
            return reducers.fold(state) { state, reducer -> reducer.reduce(state, action) }
        }

    }

    companion object {

        /**
         * Combines multiple reducers into a single reducer.
         *
         * @see <a href="http://redux.js.org/docs/api/combineReducers.html">http://redux.js.org/docs/api/combineReducers.html</a>
         *
         * @param[reducers] A list of reducers
         * @return The combined reducer
         */
        fun <S : Any> combine(vararg reducers: Reducer<S>): Reducer<S> {
            return CombinedReducer(*reducers)
        }

        /**
         * Creates a new [Reducer] instance using the provided function as the [reduce()] implementation.
         *
         * @param[f] A higher-order function equivalent to the [reduce()] function
         * @return A new reducer instance
         */
        operator fun <S : Any> invoke(f: (S, Any) -> S) = object : Reducer<S> {
            override fun reduce(state: S, action: Any) = f(state, action)
        }

    }

}
