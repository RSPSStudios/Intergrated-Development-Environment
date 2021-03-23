package com.javatar.ui.views.account

import com.javatar.ui.models.AccountModel
import javafx.beans.binding.Bindings
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import tornadofx.*

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 22 2021
 */

class AccountLoginFragment : Fragment("Account Login") {

    val accountModel: AccountModel by inject()

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
                        TODO("Logout action")
                    } else {
                        TODO("Login action")
                    }
                }
            }

        }
    }

}
