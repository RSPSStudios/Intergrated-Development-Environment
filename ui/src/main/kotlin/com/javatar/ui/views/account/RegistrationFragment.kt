package com.javatar.ui.views.account

import com.javatar.api.http.Client
import com.javatar.api.http.CredentialsResponse
import com.javatar.ui.data.RegistrationInformation
import com.javatar.ui.models.AccountRegisterModel
import io.ktor.util.*
import javafx.beans.property.SimpleStringProperty
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

class RegistrationFragment : Fragment("Account Registration") {

    val accountModel: AccountRegisterModel by inject()
    val client: Client by di()

    val msg = SimpleStringProperty(this, "msg", "")

    @InternalAPI
    override val root = form {
        alignment = Pos.CENTER
        fieldset("Register an account") {
            field("Email", orientation = Orientation.HORIZONTAL) {
                textfield(accountModel.registrationEmail) {
                    promptText = "Enter email address"
                }.required()
            }
            field("Password", orientation = Orientation.HORIZONTAL) {
                passwordfield(accountModel.registrationPassword) {
                    promptText = "Enter password"
                }.required()
            }
            field("Confirm Password", orientation = Orientation.HORIZONTAL) {
                passwordfield(accountModel.confirmPassword) {
                    promptText = "Enter password again"
                }.validator {
                    if (it.isNullOrBlank() || it != accountModel.confirmPassword.get()) {
                        error("Passwords do not match!")
                    } else null
                }
            }
            hbox {
                spacing = 20.0
                button("Register Account") {
                    enableWhen(
                        accountModel.valid(
                            accountModel.registrationEmail,
                            accountModel.registrationPassword,
                            accountModel.confirmPassword
                        )
                    )
                }.action {
                    val reg = RegistrationInformation(
                        accountModel.registrationEmail.get(),
                        accountModel.registrationPassword.get()
                    )
                    client.post<CredentialsResponse>("register", reg)
                        .onEach { msg.set(it.msg) }
                        .launchIn(CoroutineScope(Dispatchers.JavaFx))

                }
                label(msg)
            }
        }

    }

}
