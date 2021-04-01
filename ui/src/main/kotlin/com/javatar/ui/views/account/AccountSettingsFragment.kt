package com.javatar.ui.views.account

import com.javatar.api.http.Client
import com.javatar.api.http.Credentials
import com.javatar.api.http.CredentialsResponse
import com.javatar.api.http.CredentialsUpdateRequest
import com.javatar.api.ui.models.AccountModel
import com.javatar.api.ui.models.AccountSettingsModel
import javafx.beans.property.SimpleStringProperty
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

class AccountSettingsFragment : Fragment("Account Settings") {

    val accountSettings: AccountSettingsModel by inject()
    val accountModel: AccountModel by di()
    val client: Client by di()

    val msg = SimpleStringProperty(this, "msg")

    override val root = form {
        prefWidth = 400.0
        prefHeight = 300.0
        style = "-fx-base: #3f474f;"
        fieldset("Account Settings") {
            field("Login Email:") {
                label(accountSettings.email)
            }
            field("Primary Email:") {
                label(accountSettings.primaryEmail)
            }
            field("Subscribed:") {
                label("Yes")
            }
        }
        fieldset("Change Password") {
            field("Current Password") {
                passwordfield(accountSettings.currentPassword).required()
            }
            field("New Password") {
                passwordfield(accountSettings.newPassword).required()
            }
            field("Confirm Password") {
                passwordfield(accountSettings.confirmPassword).validator {
                    if (it.isNullOrBlank() || it != accountSettings.newPassword.get()) {
                        error("Passwords do not match!")
                    } else null
                }
            }
            field {
                button("Change Password") {
                    enableWhen(
                        accountSettings.valid(
                            accountSettings.currentPassword,
                            accountSettings.newPassword,
                            accountSettings.confirmPassword
                        )
                    )
                    action {
                        val creds = accountModel.activeCredentials.get()
                        val updateCreds = CredentialsUpdateRequest(
                            creds.email,
                            accountSettings.currentPassword.get(),
                            accountSettings.newPassword.get()
                        )
                        if (creds != null) {
                            client.post<CredentialsResponse>("user/passwd", updateCreds, creds)
                                .onEach {
                                    msg.set(it.msg)
                                    accountModel.loggedIn.set(false)
                                }.launchIn(CoroutineScope(Dispatchers.JavaFx))
                        }
                    }
                }
                button("Request Reset").action {
                    val creds = accountModel.activeCredentials.get()
                    client.post<CredentialsResponse>("user/resetpwd", creds, creds)
                        .onEach {
                            msg.set(it.msg)
                        }.launchIn(CoroutineScope(Dispatchers.JavaFx))
                }
            }
            label(msg)
        }

    }

}
