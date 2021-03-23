package com.javatar.ui.models

import javafx.beans.property.SimpleStringProperty
import tornadofx.ViewModel

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 22 2021
 */

class TitleModel : ViewModel() {

    val title = bind { SimpleStringProperty(this, "title", "RuneScape Private Server Studios") }
    val accountEmailOrName = bind { SimpleStringProperty(this, "account_name", "Not Logged in.") }

}
