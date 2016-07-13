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

interface Reducer<S : Any, in A : Any> {

	fun reduce(state: S, action: A): S

	private class CombinedReducer<S : Any, in A : Any>(vararg val reducers: Reducer<S, A>) : Reducer<S, A> {

		override fun reduce(state: S, action: A): S {
			return reducers.fold(state) { state, reducer -> reducer.reduce(state, action) }
		}

	}

	companion object {

		fun <S : Any, A : Any> combine(vararg reducers: Reducer<S, A>): Reducer<S, A> {
			return CombinedReducer(*reducers)
		}

	}

}
