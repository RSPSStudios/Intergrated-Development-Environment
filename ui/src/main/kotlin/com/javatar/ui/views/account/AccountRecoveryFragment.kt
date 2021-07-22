package com.javatar.ui.views.account

import com.javatar.api.http.Client
import com.javatar.api.http.Credentials
import com.javatar.api.http.CredentialsResponse
import com.javatar.ui.models.AccountRecoveryModel
import javafx.scene.Parent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.javafx.JavaFx
import tornadofx.*

class AccountRecoveryFragment : Fragment("Account Recovery") {

    val model: AccountRecoveryModel by inject()
    val client: Client by di()

    override val root: Parent = form {

        fieldset {
            field("Email") {
                textfield(model.email)
            }
            field {
                button("Request Password Reset") {
                    disableWhen(model.email.isEmpty)
                }.action {
                    val creds = Credentials(model.email.get(), "")
                    client.post<CredentialsResponse>("resetpwd", creds, creds)
                        .onEach {
                            model.message.set(it.msg)
                        }.launchIn(CoroutineScope(Dispatchers.JavaFx))
                }
            }
            field {
                label(model.message)
            }
        }
    }

}