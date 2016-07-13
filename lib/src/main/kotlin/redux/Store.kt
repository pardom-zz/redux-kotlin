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

interface Store<S : Any, A : Any> : Stateful<S>, Dispatcher<A> {

	fun subscribe(subscriber: Subscriber): Subscription

	fun replaceReducer(reducer: Reducer<S, A>)

	interface Creator<S : Any, A : Any> {

		fun create(reducer: Reducer<S, A>, initialState: S, enhancer: Enhancer<S, A>? = null): Store<S, A>

	}

	interface Enhancer<S : Any, A : Any> {

		fun enhance(next: Creator<S, A>): Creator<S, A>

	}

	interface Subscriber {

		fun onStateChanged()

	}

	interface Subscription {

		fun unsubscribe()

	}

	private class Impl<S : Any, A : Any> : Store<S, A> {

		private var reducer: Reducer<S, A>
		private var state: S
		private var subscribers = mutableListOf<Subscriber>()

		private var isDispatching = false

		private constructor(reducer: Reducer<S, A>, state: S) {
			this.reducer = reducer
			this.state = state
		}

		override fun dispatch(action: A): A {
			if (isDispatching) {
				throw IllegalAccessError("Reducers may not dispatch actions.")
			}

			try {
				isDispatching = true
				state = reducer.reduce(state, action)
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

			return object : Subscription {

				override fun unsubscribe() {
					subscribers.remove(subscriber)
				}

			}
		}

		override fun replaceReducer(reducer: Reducer<S, A>) {
			this.reducer = reducer
		}

		class ImplCreator<S : Any, A : Any> : Creator<S, A> {

			override fun create(reducer: Reducer<S, A>, initialState: S, enhancer: Enhancer<S, A>?): Store<S, A> {
				return Impl(reducer, initialState)
			}

		}

	}

	companion object {

		fun <S : Any, A : Any> create(
				reducer: Reducer<S, A>,
				initialState: S,
				enhancer: Enhancer<S, A>? = null): Store<S, A> {

			val creator = Impl.ImplCreator<S, A>()
			val store = if (enhancer != null) {
				enhancer.enhance(creator).create(reducer, initialState)
			}
			else {
				creator.create(reducer, initialState)
			}

			//store.dispatch(null)

			return store
		}

	}

}