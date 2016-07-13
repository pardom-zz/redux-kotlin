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

interface Middleware<S : Any, A : Any> {

	fun dispatch(store: Store<S, A>, action: A, next: Dispatcher<A>): A

	private class Enhancer<S : Any, A : Any>(val middlewares: Array<out Middleware<S, A>>) : Store.Enhancer<S, A> {

		override fun enhance(next: Store.Creator<S, A>): Store.Creator<S, A> = Creator(next, middlewares)

	}

	private class Creator<S : Any, A : Any>(
			val creator: Store.Creator<S, A>,
			val middlewares: Array<out Middleware<S, A>>) : Store.Creator<S, A> {

		override fun create(
				reducer: Reducer<S, A>,
				initialState: S,
				enhancer: Store.Enhancer<S, A>?): Store<S, A> {

			return Delegate(creator.create(reducer, initialState, enhancer), middlewares)

		}

	}

	private class Delegate<S : Any, A : Any>(
			store: Store<S, A>,
			middlewares: Array<out Middleware<S, A>>) : Store<S, A> by store {

		val rootDispatcher = middlewares.foldRight(store as Dispatcher<A>) { middleware, next ->
			Wrapper(middleware, store, next)
		}

		override fun dispatch(action: A): A {
			return rootDispatcher.dispatch(action)
		}

		class Wrapper<S : Any, A : Any>(
				val middleware: Middleware<S, A>,
				val store: Store<S, A>,
				val next: Dispatcher<A>) : Dispatcher<A> {

			override fun dispatch(action: A): A {
				return middleware.dispatch(store, action, next)
			}

		}

	}

	companion object {

		fun <S : Any, A : Any> apply(vararg middlewares: Middleware<S, A>): Store.Enhancer<S, A> {
			return Enhancer(middlewares)
		}

	}

}
