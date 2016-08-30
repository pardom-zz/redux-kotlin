(function (Kotlin) {
  'use strict';
  var _ = Kotlin.defineRootPackage(null, /** @lends _ */ {
    redux: Kotlin.definePackage(null, /** @lends _.redux */ {
      counter: Kotlin.definePackage(null, /** @lends _.redux.counter */ {
        Action: Kotlin.createEnumClass(function () {
          return [Kotlin.Enum];
        }, function $fun() {
          $fun.baseInitializer.call(this);
        }, function () {
          return {
            INCREMENT: function () {
              return new _.redux.counter.Action();
            },
            DECREMENT: function () {
              return new _.redux.counter.Action();
            }
          };
        }),
        main_kand9s$f: function (state, action) {
          if (Kotlin.equals(action, _.redux.counter.Action.INCREMENT))
            return state + 1;
          else if (Kotlin.equals(action, _.redux.counter.Action.DECREMENT))
            return state - 1;
          else
            return state;
        },
        main_kand9s$f_0: function (closure$store, closure$valueEl) {
          return function () {
            closure$valueEl != null ? (closure$valueEl.innerHTML = closure$store.getState().toString()) : null;
          };
        },
        main_kand9s$f_1: function (closure$store) {
          return function (it) {
            closure$store.dispatch_za3rmp$(_.redux.counter.Action.INCREMENT);
          };
        },
        main_kand9s$f_2: function (closure$store) {
          return function (it) {
            closure$store.dispatch_za3rmp$(_.redux.counter.Action.DECREMENT);
          };
        },
        main_kand9s$f_3: function (closure$store) {
          return function (it) {
            if (closure$store.getState() % 2 !== 0) {
              closure$store.dispatch_za3rmp$(_.redux.counter.Action.INCREMENT);
            }
          };
        },
        f: function (closure$store) {
          return function () {
            return closure$store.dispatch_za3rmp$(_.redux.counter.Action.INCREMENT);
          };
        },
        main_kand9s$f_4: function (closure$store) {
          return function (it) {
            window.setTimeout(_.redux.counter.f(closure$store), 1000);
          };
        },
        main_kand9s$: function (args) {
          var tmp$0, tmp$1, tmp$2, tmp$3;
          var reducer = Kotlin.modules['redux-kotlin'].redux.Reducer.Companion.invoke_67q5p2$(_.redux.counter.main_kand9s$f);
          var store = Kotlin.modules['redux-kotlin'].redux.Store.Companion.create_wltvbw$(reducer, 0);
          var valueEl = document.getElementById('value');
          var render = _.redux.counter.main_kand9s$f_0(store, valueEl);
          render();
          store.subscribe_qshda6$(render);
          (tmp$0 = document.getElementById('increment')) != null ? tmp$0.addEventListener('click', _.redux.counter.main_kand9s$f_1(store)) : null;
          (tmp$1 = document.getElementById('decrement')) != null ? tmp$1.addEventListener('click', _.redux.counter.main_kand9s$f_2(store)) : null;
          (tmp$2 = document.getElementById('incrementIfOdd')) != null ? tmp$2.addEventListener('click', _.redux.counter.main_kand9s$f_3(store)) : null;
          (tmp$3 = document.getElementById('incrementAsync')) != null ? tmp$3.addEventListener('click', _.redux.counter.main_kand9s$f_4(store)) : null;
        }
      })
    })
  });
  Kotlin.defineModule('app', _);
  _.redux.counter.main_kand9s$([]);
}(Kotlin));

//@ sourceMappingURL=app.js.map
