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
 * Provides a third-party extension point between dispatching an action, and the moment it reaches the reducer.
 *
 * @see <a href="http://redux.js.org/docs/advanced/Middleware.html">http://redux.js.org/docs/advanced/Middleware.html</a>
 */
interface Middleware<S : Any> {

	/**
	 * Apply middleware behavior to the dispatched action.
	 *
	 * ## Example
	 *
	 * ```kotlin
	 * Middleware { store: Store&lt;S&gt;, action: Any, next: Dispatcher ->
	 *     println("Previous state: $store")
	 *     val result = next.dispatch(action)
	 *     println("New state: $store")
	 *     result
	 * }
	 * ```
	 *
	 * @param[store] The previous state
	 * @param[action] A plain object describing the change that makes sense for your application
	 * @param[next] The next dispatcher to call
	 * @return The dispatched action
	 */
	fun dispatch(store: Store<S>, action: Any, next: Dispatcher): Any

	private class Enhancer<S : Any>(val middlewares: Array<out Middleware<S>>) : Store.Enhancer<S> {

		override fun enhance(next: Store.Creator<S>): Store.Creator<S> = Creator(next, middlewares)

	}

	private class Creator<S : Any>(
			val creator: Store.Creator<S>,
			val middlewares: Array<out Middleware<S>>) : Store.Creator<S> {

		override fun create(reducer: Reducer<S>, initialState: S): Store<S> {

			return Delegate(creator.create(reducer, initialState), middlewares)

		}

	}

	private class Delegate<S : Any>(
			store: Store<S>,
			middlewares: Array<out Middleware<S>>) : Store<S> by store {

		val rootDispatcher = middlewares.foldRight(store as Dispatcher) { middleware, next ->
			Wrapper(middleware, store, next)
		}

		override fun dispatch(action: Any): Any {
			return rootDispatcher.dispatch(action)
		}

		class Wrapper<S : Any>(
				val middleware: Middleware<S>,
				val store: Store<S>,
				val next: Dispatcher) : Dispatcher {

			override fun dispatch(action: Any): Any {
				return middleware.dispatch(store, action, next)
			}

		}

	}

	companion object {

		/**
		 * Creates a Store [Enhancer] by applying one or more [Middleware].
		 *
		 * @see <a href="http://redux.js.org/docs/api/applyMiddleware.html">http://redux.js.org/docs/api/applyMiddleware.html</a>
		 *
		 * @param[middlewares] A list of middleware, applied in the order listed
		 * @return The middleware store enhancer
		 */
		fun <S : Any> apply(vararg middlewares: Middleware<S>): Store.Enhancer<S> {
			return Enhancer(middlewares)
		}

		/**
		 * Creates a new [Middleware] instance using the provided function as the [dispatch()] implementation.
		 *
		 * @param[f] A higher-order function equivalent to the [dispatch()] function
		 * @return A new middleware instance
		 */
		operator fun <S : Any> invoke(f: (Store<S>, Any, Dispatcher) -> Any) = object : Middleware<S> {
			override fun dispatch(store: Store<S>, action: Any, next: Dispatcher) = f(store, action, next)
		}

	}

}
