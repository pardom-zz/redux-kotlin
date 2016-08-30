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
 * A dispatcher is an interface that accepts an action or an async action; it then may or may not dispatch one or more
 * actions to the store.
 *
 * @see <a href="http://redux.js.org/docs/Glossary.html#dispatching-function">http://redux.js.org/docs/Glossary.html#dispatching-function</a>
 */
interface Dispatcher {

    /**
     * Dispatches an action. This is the only way to trigger a state change.
     *
     * ## Example
     * ```
     * sealed class Action {
     *     class AddTodo(val todo: String) : Action()
     * }
     *
     * val store = Store.create(todos, listOf("Use Redux"))
     *
     * store.dispatch(AddTodo("Read the docs"))
     * store.dispatch(AddTodo("Read about the middleware"))
     * ```
     *
     * @see <a href="http://redux.js.org/docs/api/Store.html#dispatch">http://redux.js.org/docs/api/Store.html#dispatch</a>
     *
     * @param[action] A plain object describing the change that makes sense for your application
     * @return The dispatched action
     */
    fun dispatch(action: Any): Any

}
