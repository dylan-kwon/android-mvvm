package dylan.kwon.mvvm.util.base.model

import java.util.*

interface Entity : Model {

    companion object {
        const val AUTO_GENERATE_ID: Long = 0

        const val TRUE: Int = 1
        const val FALSE: Int = 0
    }

    override var id: Long?

    var createdAt: Date

    var updatedAt: Date

}
