package redux.counter

import redux.Reducer
import redux.Store
import redux.counter.Action.DECREMENT
import redux.counter.Action.INCREMENT
import kotlin.browser.document

enum class Action {
    INCREMENT, DECREMENT
}

fun main(args: Array<String>) {
    val reducer = Reducer { state: Int, action: Any ->
        when (action) {
            INCREMENT -> state + 1
            DECREMENT -> state - 1
            else -> state
        }
    }

    val store = Store.create(reducer, 0)
    val valueEl = document.getElementById("value")

    val render = {
        valueEl?.innerHTML = "${store.getState()}"
    }

    render()
    store.subscribe(render)

    document.getElementById("increment")
            ?.addEventListener("click", {
                store.dispatch(INCREMENT)
            })

    document.getElementById("decrement")
            ?.addEventListener("click", {
                store.dispatch(DECREMENT)
            })

    document.getElementById("incrementIfOdd")
            ?.addEventListener("click", {
                if (store.getState() % 2 != 0) {
                    store.dispatch(INCREMENT)
                }
            })

    document.getElementById("incrementAsync")
            ?.addEventListener("click", {
                kotlin.browser.window.setTimeout({
                    store.dispatch(INCREMENT)
                }, 1000)
            })
}
