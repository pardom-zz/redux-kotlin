(function (Kotlin) {
  'use strict';
  var _ = Kotlin.defineRootPackage(null, /** @lends _ */ {
    redux: Kotlin.definePackage(null, /** @lends _.redux */ {
      Dispatcher: Kotlin.createTrait(null),
      Middleware: Kotlin.createTrait(null, null, /** @lends _.redux.Middleware */ {
        Enhancer: Kotlin.createClass(function () {
          return [_.redux.Store.Enhancer];
        }, function (middlewares) {
          this.middlewares = middlewares;
        }, /** @lends _.redux.Middleware.Enhancer.prototype */ {
          enhance_rg6n14$: function (next) {
            return new _.redux.Middleware.Creator(next, this.middlewares);
          }
        }),
        Creator: Kotlin.createClass(function () {
          return [_.redux.Store.Creator];
        }, function (creator, middlewares) {
          this.creator = creator;
          this.middlewares = middlewares;
        }, /** @lends _.redux.Middleware.Creator.prototype */ {
          create_wltvbw$: function (reducer, initialState, enhancer) {
            if (enhancer === void 0)
              enhancer = null;
            return new _.redux.Middleware.Delegate(this.creator.create_wltvbw$(reducer, initialState, enhancer), this.middlewares);
          }
        }),
        Delegate: Kotlin.createClass(function () {
          return [_.redux.Store];
        }, function (store, middlewares) {
          var tmp$0;
          this.$delegate_evpjd8$ = store;
          var index = Kotlin.modules['stdlib'].kotlin.collections.get_lastIndex_eg9ybj$(middlewares);
          var accumulator = store;
          while (index >= 0) {
            accumulator = new _.redux.Middleware.Delegate.Wrapper(middlewares[index--], store, accumulator);
          }
          this.rootDispatcher = accumulator;
        }, /** @lends _.redux.Middleware.Delegate.prototype */ {
          dispatch_za3rmp$: function (action) {
            return this.rootDispatcher.dispatch_za3rmp$(action);
          },
          getState: function () {
            return this.$delegate_evpjd8$.getState();
          },
          replaceReducer_ey93rf$: function (reducer) {
            return this.$delegate_evpjd8$.replaceReducer_ey93rf$(reducer);
          },
          subscribe_qshda6$: function (subscriber) {
            return this.$delegate_evpjd8$.subscribe_qshda6$(subscriber);
          },
          subscribe_37p9pt$: function (subscriber) {
            return this.$delegate_evpjd8$.subscribe_37p9pt$(subscriber);
          }
        }, /** @lends _.redux.Middleware.Delegate */ {
          Wrapper: Kotlin.createClass(function () {
            return [_.redux.Dispatcher];
          }, function (middleware, store, next) {
            this.middleware = middleware;
            this.store = store;
            this.next = next;
          }, /** @lends _.redux.Middleware.Delegate.Wrapper.prototype */ {
            dispatch_za3rmp$: function (action) {
              return this.middleware.dispatch_kwofg4$(this.store, action, this.next);
            }
          })
        }),
        Companion: Kotlin.createObject(null, null, /** @lends _.redux.Middleware.Companion.prototype */ {
          apply_oiu21q$: function (middlewares) {
            return new _.redux.Middleware.Enhancer(middlewares);
          },
          invoke_59ljli$: function (f) {
            return new _.redux.Middleware.Companion.invoke$f(f);
          }
        }, /** @lends _.redux.Middleware.Companion */ {
          invoke$f: Kotlin.createClass(function () {
            return [_.redux.Middleware];
          }, function (closure$f_0) {
            this.closure$f_0 = closure$f_0;
          }, /** @lends _.redux.Middleware.Companion.invoke$f.prototype */ {
            dispatch_kwofg4$: function (store, action, next) {
              return this.closure$f_0(store, action, next);
            }
          }, /** @lends _.redux.Middleware.Companion.invoke$f */ {
          })
        }),
        object_initializer$: function () {
          _.redux.Middleware.Companion;
        }
      }),
      Reducer: Kotlin.createTrait(null, null, /** @lends _.redux.Reducer */ {
        CombinedReducer: Kotlin.createClass(function () {
          return [_.redux.Reducer];
        }, function (reducers) {
          this.reducers = reducers;
        }, /** @lends _.redux.Reducer.CombinedReducer.prototype */ {
          reduce_wn2jw4$: function (state, action) {
            var tmp$0, tmp$1, tmp$2;
            var accumulator = state;
            tmp$0 = this.reducers, tmp$1 = tmp$0.length;
            for (var tmp$2 = 0; tmp$2 !== tmp$1; ++tmp$2) {
              var element = tmp$0[tmp$2];
              accumulator = element.reduce_wn2jw4$(accumulator, action);
            }
            return accumulator;
          }
        }, /** @lends _.redux.Reducer.CombinedReducer */ {
        }),
        Companion: Kotlin.createObject(null, null, /** @lends _.redux.Reducer.Companion.prototype */ {
          combine_q28sxo$: function (reducers) {
            return new _.redux.Reducer.CombinedReducer(reducers.slice());
          },
          invoke_67q5p2$: function (f) {
            return new _.redux.Reducer.Companion.invoke$f(f);
          }
        }, /** @lends _.redux.Reducer.Companion */ {
          invoke$f: Kotlin.createClass(function () {
            return [_.redux.Reducer];
          }, function (closure$f_0) {
            this.closure$f_0 = closure$f_0;
          }, /** @lends _.redux.Reducer.Companion.invoke$f.prototype */ {
            reduce_wn2jw4$: function (state, action) {
              return this.closure$f_0(state, action);
            }
          }, /** @lends _.redux.Reducer.Companion.invoke$f */ {
          })
        }),
        object_initializer$: function () {
          _.redux.Reducer.Companion;
        }
      }),
      Store: Kotlin.createTrait(function () {
        return [_.redux.Dispatcher];
      }, /** @lends _.redux.Store.prototype */ {
        subscribe_qshda6$: function (subscriber) {
          return this.subscribe_37p9pt$(_.redux.Store.Subscriber.Companion.invoke_qshda6$(subscriber));
        }
      }, /** @lends _.redux.Store */ {
        Creator: Kotlin.createTrait(null),
        Enhancer: Kotlin.createTrait(null),
        Subscriber: Kotlin.createTrait(null, null, /** @lends _.redux.Store.Subscriber */ {
          Companion: Kotlin.createObject(null, null, /** @lends _.redux.Store.Subscriber.Companion.prototype */ {
            invoke_qshda6$: function (f) {
              return new _.redux.Store.Subscriber.Companion.invoke$f(f);
            }
          }, /** @lends _.redux.Store.Subscriber.Companion */ {
            invoke$f: Kotlin.createClass(function () {
              return [_.redux.Store.Subscriber];
            }, function (closure$f_0) {
              this.closure$f_0 = closure$f_0;
            }, /** @lends _.redux.Store.Subscriber.Companion.invoke$f.prototype */ {
              onStateChanged: function () {
                this.closure$f_0();
              }
            }, /** @lends _.redux.Store.Subscriber.Companion.invoke$f */ {
            })
          }),
          object_initializer$: function () {
            _.redux.Store.Subscriber.Companion;
          }
        }),
        Subscription: Kotlin.createTrait(null),
        Impl: Kotlin.createClass(function () {
          return [_.redux.Store];
        }, function () {
          this.subscribers_v1nnl0$ = Kotlin.modules['stdlib'].kotlin.collections.mutableListOf_9mqe4v$([]);
          this.isDispatching_tg7ekp$ = false;
        }, /** @lends _.redux.Store.Impl.prototype */ {
          dispatch_za3rmp$: function (action) {
            if (this.isDispatching_tg7ekp$) {
            }
            try {
              this.isDispatching_tg7ekp$ = true;
              this.state_o4edue$ = this.reducer_nxftd1$.reduce_wn2jw4$(this.state_o4edue$, action);
            }
            finally {
              this.isDispatching_tg7ekp$ = false;
            }
            var tmp$0;
            tmp$0 = this.subscribers_v1nnl0$.iterator();
            while (tmp$0.hasNext()) {
              var element = tmp$0.next();
              element.onStateChanged();
            }
            return action;
          },
          getState: function () {
            return this.state_o4edue$;
          },
          subscribe_37p9pt$: function (subscriber) {
            this.subscribers_v1nnl0$.add_za3rmp$(subscriber);
            return new _.redux.Store.Impl.subscribe$f(this, subscriber);
          },
          replaceReducer_ey93rf$: function (reducer) {
            this.reducer_nxftd1$ = reducer;
          }
        }, /** @lends _.redux.Store.Impl */ {
          subscribe$f: Kotlin.createClass(function () {
            return [_.redux.Store.Subscription];
          }, function (this$Impl_0, closure$subscriber_0) {
            this.this$Impl_0 = this$Impl_0;
            this.closure$subscriber_0 = closure$subscriber_0;
          }, /** @lends _.redux.Store.Impl.subscribe$f.prototype */ {
            unsubscribe: function () {
              this.this$Impl_0.subscribers_v1nnl0$.remove_za3rmp$(this.closure$subscriber_0);
            }
          }, /** @lends _.redux.Store.Impl.subscribe$f */ {
          }),
          ImplCreator: Kotlin.createClass(function () {
            return [_.redux.Store.Creator];
          }, null, /** @lends _.redux.Store.Impl.ImplCreator.prototype */ {
            create_wltvbw$: function (reducer, initialState, enhancer) {
              if (enhancer === void 0)
                enhancer = null;
              return _.redux.Store.Impl_init_1(reducer, initialState);
            }
          })
        }),
        Impl_init_1: function (reducer, state, $this) {
          $this = $this || Object.create(_.redux.Store.Impl.prototype);
          _.redux.Store.Impl.call($this);
          $this.reducer_nxftd1$ = reducer;
          $this.state_o4edue$ = state;
          return $this;
        },
        Companion: Kotlin.createObject(null, null, /** @lends _.redux.Store.Companion.prototype */ {
          create_wltvbw$: function (reducer, initialState, enhancer) {
            var tmp$0;
            if (enhancer === void 0)
              enhancer = null;
            var creator = new _.redux.Store.Impl.ImplCreator();
            if (enhancer != null) {
              tmp$0 = enhancer.enhance_rg6n14$(creator).create_wltvbw$(reducer, initialState);
            }
             else {
              tmp$0 = creator.create_wltvbw$(reducer, initialState);
            }
            var store = tmp$0;
            store.dispatch_za3rmp$(_.redux.Store.Companion.INIT);
            return store;
          }
        }, /** @lends _.redux.Store.Companion */ {
          INIT: Kotlin.createObject(null, null)
        }),
        object_initializer$: function () {
          _.redux.Store.Companion;
        }
      })
    })
  });
  Kotlin.defineModule('redux-kotlin', _);
}(Kotlin));

//@ sourceMappingURL=redux-kotlin.js.map
