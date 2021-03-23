package com.javatar.ui.models

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.ViewModel

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 22 2021
 */

class AccountModel : ViewModel() {

    val email = bind { SimpleStringProperty(this, "email", config.string("email_address")) }
    val password = bind { SimpleStringProperty(this, "password", config.string("password")) }

    val loggedIn = bind { SimpleBooleanProperty(this, "logged_in", false) }

    val registrationEmail = bind { SimpleStringProperty(this, "register_email", "") }
    val registrationPassword = bind { SimpleStringProperty(this, "register_password", "") }
    val confirmPassword = bind { SimpleStringProperty(this, "confirm_password", "") }

    override fun onCommit() {
        super.onCommit()
        with(config) {
            set("email_address", email.get())
            set("password", password.get())
            save()
        }
    }
}
