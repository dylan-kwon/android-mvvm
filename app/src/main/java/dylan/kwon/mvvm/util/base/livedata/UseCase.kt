package dylan.kwon.mvvm.util.base.livedata

import io.reactivex.disposables.Disposable

abstract class UseCase<Request, Response> {

    abstract fun request(
        request: Request,
        onResponse: (Response) -> Unit
    ): Disposable

}