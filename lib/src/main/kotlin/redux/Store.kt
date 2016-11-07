package redux

import redux.api.Reducer
import redux.api.Store
import redux.api.Store.Creator
import redux.api.Store.Enhancer
import redux.api.Store.Subscriber
import redux.api.Store.Subscription

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
 * When a store is created, an "INIT" action is dispatched so that every reducer returns their initial state.
 * This effectively populates the initial state tree.
 */
object INIT {}

/**
 * Creates a Redux store that holds the complete state tree of your component. There should only be a single
 * store per component.
 *
 * @see <a href="http://redux.js.org/docs/api/createStore.html">http://redux.js.org/docs/api/createStore.html</a>
 *
 * @param[reducer] The [Reducer] which returns the next state tree, given the current state tree and an action to handle
 * @param[initialState] The initial state
 * @param[enhancer] The store [Enhancer]
 * @return An object that holds the complete state of your component.
 */
fun <S : Any> createStore(
        reducer: Reducer<S>,
        initialState: S,
        enhancer: Enhancer<S> = Enhancer { it }): Store<S> {

    val creator = Creator<S> { reducer, initialState ->
        object : Store<S> {
            private var reducer = reducer
            private var state = initialState
            private var subscribers = mutableListOf<Subscriber>()
            private var isDispatching = false

            override fun dispatch(action: Any): Any {
                if (isDispatching) {
                    throw IllegalAccessError("Reducers may not dispatch actions.")
                }
                try {
                    isDispatching = true
                    state = this.reducer.reduce(state, action)
                }
                finally {
                    isDispatching = false
                }
                subscribers.forEach { it.onStateChanged() }
                return action
            }

            override fun getState(): S {
                return state
            }

            override fun subscribe(subscriber: Subscriber): Subscription {
                subscribers.add(subscriber)
                return Subscription {
                    subscribers.remove(subscriber)
                }
            }

            override fun replaceReducer(reducer: Reducer<S>) {
                this.reducer = reducer
                dispatch(INIT)
            }
        }
    }
    val store = enhancer.enhance(creator).create(reducer, initialState)
    store.dispatch(INIT)
    return store
}
