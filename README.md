[![Build Status](https://travis-ci.org/pardom/redux-kotlin.svg?branch=master)](https://travis-ci.org/pardom/redux-kotlin)
[![](https://jitpack.io/v/pardom/redux-kotlin.svg)](https://jitpack.io/#pardom/redux-kotlin)

# Redux Kotlin

Redux Kotlin is a predictable state container for Kotlin apps. It is a direct port of the original [Redux](https://github.com/reactjs/redux) library for JavaScript, which has excellent documentation at [http://redux.js.org/](http://redux.js.org/).

### The Gist

(Adapted from: https://github.com/reactjs/redux/#the-gist)

The whole state of your app is stored in an object tree inside a single *store*.  
The only way to change the state tree is to emit an *action*, an object describing what happened.  
To specify how the actions transform the state tree, you write pure *reducers*.

That's it!

```kotlin
/**
 * This is a reducer, a pure function with (state, action) -> state signature.
 * It describes how an action transforms the state into the next state.
 *
 * The shape of the state is up to you: it can be a primitive, an array, or even an object. 
 * The only important part is that you should not mutate the state object, but return a 
 * new object if the state changes.
 *
 * In this example, we use a `when` statement and strings, but you can use a helper that
 * follows a different convention if it makes sense for your project. In a real app, you
 * will likely also want to use action types rather than strings, e.g. sealed classes or
 * enum classes.
 */
val reducer = Reducer { state: Int, action: Any ->
	when (action) {
		"Inc" -> state + 1
		"Dec" -> state - 1
		else -> state
	}
}

// Create a Redux store holding the state of your app.
val store = Store.create(reducer, 0)

// You can use subscribe() to update the UI in response to state changes.
// Normally you'd use a view binding library (e.g. redux-rxjava-kotlin and RxBinding) rather
// than subscribe() directly.
store.subscribe {
	println("${store.getState()}")
}

// The only way to mutate the internal state is to dispatch an action.
// The actions can be serialized, logged or stored and later replayed.
store.dispatch("Inc")
// 1
store.dispatch("Inc")
// 2
store.dispatch("Dec")
// 1

```

### Download

```groovy
repositories {
	maven { url "https://jitpack.io" }
}
```

```groovy
// JVM/Android
compile 'com.github.pardom.redux-kotlin:lib:1.0.2'

// JavaScript
compile 'com.github.pardom.redux-kotlin:js:1.0.2'
```

Snapshots of the development version are available the by using [`-SNAPSHOT`](https://jitpack.io/#pardom/redux-kotlin/-SNAPSHOT) as the version.

### Samples

* [Counter](https://github.com/pardom/redux-kotlin/tree/master/samples/counter)
* [CleanNews](https://github.com/pardom/CleanNews)

### Middleware

* [redux-logger-kotlin](https://github.com/pardom/redux-logger-kotlin) — Log every Redux action and the next state
* [redux-observable-kotlin](https://github.com/pardom/redux-observable-kotlin) — RxJava middleware for action side effects using "Epics"
* [redux-optimist-kotlin](https://github.com/pardom/redux-optimist-kotlin) — Reducer enhancer to enable optimistic updates
* [redux-rxjava-kotlin](https://github.com/pardom/redux-rxjava-kotlin) — RxJava wrapper for store subscriptions
* [redux-standard-action-kotlin](https://github.com/pardom/redux-standard-action-kotlin) — Flux Standard Action implementation

### License

     Copyright (C) 2016 Michael Pardo
     
     Licensed under the Apache License, Version 2.0 (the "License");
     you may not use this file except in compliance with the License.
     You may obtain a copy of the License at
     
          http://www.apache.org/licenses/LICENSE-2.0
     
     Unless required by applicable law or agreed to in writing, software
     distributed under the License is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     See the License for the specific language governing permissions and
     limitations under the License.
 
