package dylan.kwon.mvvm.util.base.model

import java.util.*

interface Dto : Model {

    override var id: Long?

    var createdAt: Date?

    var updatedAt: Date?

}
