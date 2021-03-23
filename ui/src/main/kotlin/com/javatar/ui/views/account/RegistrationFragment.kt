package com.javatar.ui.views.account

import com.javatar.ui.models.AccountModel
import javafx.geometry.Orientation
import javafx.geometry.Pos
import tornadofx.*

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 22 2021
 */

class RegistrationFragment : Fragment("Account Registration") {

    val accountModel: AccountModel by inject()

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
            button("Register Account") {
                enableWhen(
                    accountModel.valid(
                        accountModel.registrationEmail,
                        accountModel.registrationPassword,
                        accountModel.confirmPassword
                    )
                )
            }.action {
                TODO("Send register post request to auth server.")
            }
        }

    }

}
