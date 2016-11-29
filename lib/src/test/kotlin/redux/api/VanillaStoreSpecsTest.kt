package redux.api

class VanillaStoreSpecsTest : redux.api.StoreTest() {

    override fun <S : Any> createStore(reducer: Reducer<S>, state: S): Store<S> {
       return redux.createStore(reducer, state, Store.Enhancer { it })
    }

}