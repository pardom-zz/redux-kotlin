package redux

import redux.api.Reducer

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
 * Combines multiple reducers into a single reducer.
 *
 * @see <a href="http://redux.js.org/docs/api/combineReducers.html">http://redux.js.org/docs/api/combineReducers.html</a>
 *
 * @param[reducers] A list of reducers
 * @return The combined reducer
 */
fun <S : Any> combineReducers(vararg reducers: Reducer<S>): Reducer<S> {
    return Reducer { state, action ->
        reducers.fold(state) { state, reducer -> reducer.reduce(state, action) }
    }
}

