package com.javatar.ui.models

import com.javatar.api.fs.Clipboard
import javafx.beans.property.SimpleObjectProperty
import tornadofx.Commit
import tornadofx.ItemViewModel

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 13 2021
 */

class ClipboardModel : ItemViewModel<Clipboard>(Clipboard()) {

    val data = bind { SimpleObjectProperty<ByteArray>() }

    override fun onCommit(commits: List<Commit>) {
        commits.forEach {
            if (it.property == data && data.value != null) {
                item.data = data.value
            }
        }
    }
}
