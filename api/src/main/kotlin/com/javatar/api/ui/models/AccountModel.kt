package com.javatar.api.ui.models

import com.javatar.api.http.Credentials
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
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

    val activeCredentials = bind {
        SimpleObjectProperty<Credentials>(
            this,
            "active_credentials",
            Credentials(config.string("email_address") ?: "", config.string("password") ?: "")
        )
    }

    override fun onCommit() {
        super.onCommit()
        with(config) {
            set("email_address", email.get())
            set("password", password.get())
            activeCredentials.set(Credentials(email.get(), password.get()))
            save()
        }
    }
}
