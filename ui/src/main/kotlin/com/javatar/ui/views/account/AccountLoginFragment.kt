package com.javatar.ui.views.account

import com.javatar.api.http.Client
import com.javatar.api.http.Credentials
import com.javatar.api.http.CredentialsResponse
import com.javatar.api.ui.models.AccountModel
import io.ktor.util.*
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.javafx.JavaFx
import tornadofx.*

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 22 2021
 */

class AccountLoginFragment : Fragment("Account Login") {

    val accountModel: AccountModel by di()
    val client: Client by di()

    val msg = SimpleStringProperty(this, "msg", "")

    @InternalAPI
    override val root = form {
        alignment = Pos.CENTER
        spacing = 10.0
        padding = Insets(10.0)

        fieldset("Login") {
            field("Email", orientation = Orientation.HORIZONTAL) {
                textfield(accountModel.email) {
                    promptText = "Enter email address"
                }.required()
                disableWhen(accountModel.loggedIn)
            }
            field("Password", orientation = Orientation.HORIZONTAL) {
                passwordfield(accountModel.password) {
                    promptText = "Enter password"
                }.required()
                disableWhen(accountModel.loggedIn)
            }

            hbox {
                alignment = Pos.CENTER
                spacing = 20.0
                button {
                    textProperty().bind(Bindings.createStringBinding({
                        if (accountModel.loggedIn.get()) {
                            "Logout"
                        } else "Login"
                    }, accountModel.loggedIn))
                    disableWhen(
                        accountModel.loggedIn.or(
                            accountModel.valid(accountModel.email, accountModel.password).not()
                        )
                    )
                    action {
                        if (accountModel.loggedIn.get()) {
                            client.get<Unit>("logout")
                                .launchIn(CoroutineScope(Dispatchers.IO))
                            accountModel.loggedIn.set(false)
                        } else {
                            client.post<CredentialsResponse>(
                                "login",
                                Credentials(accountModel.email.get(), accountModel.password.get())
                            ).onEach {
                                msg.set(it.msg)
                                accountModel.loggedIn.set(it.loggedIn)
                                accountModel.commit(accountModel.email, accountModel.password)
                            }.launchIn(CoroutineScope(Dispatchers.JavaFx))
                        }
                    }
                }
                label(msg)
            }

        }
    }

}
