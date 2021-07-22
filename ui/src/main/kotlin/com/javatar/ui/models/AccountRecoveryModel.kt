package com.javatar.ui.models

import javafx.beans.property.SimpleStringProperty
import tornadofx.ViewModel

class AccountRecoveryModel : ViewModel() {

    val email = bind { SimpleStringProperty(this, "email", "") }

    val message = bind { SimpleStringProperty(this, "msg", "") }

}