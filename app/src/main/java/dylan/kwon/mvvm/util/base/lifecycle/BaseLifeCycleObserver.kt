package dylan.kwon.mvvm.util.base.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * Created by seokchan.kwon on 2018. 1. 25..
 * 생명주기에서 공통적으로 작업해야 하는 부분을 이 클래스를 상속받은 후 메소드를 오버라이드 해서 정의.
 * 예) 어떤 Thread를 onStart()에서 start(), onStop()에서 .interrupt() 하는데, 여러 컴포넌트에서 동시에 사용할 경우.
 */
open class BaseLifeCycleObserver : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun onCreate() {
        // empty method.
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onStart() {
        // empty method.
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResume() {
        // empty method.
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause() {
        // empty method.
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onStop() {
        // empty method.
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroy() {
        // empty method.
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    open fun onAny() {
        // empty method.
    }

}
