package com.javatar.ui.views.account

import com.javatar.api.ui.models.AccountSettingsModel
import tornadofx.*

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 22 2021
 */

class AccountSettingsFragment : Fragment("Account Settings") {

    val accountSettings: AccountSettingsModel by inject()

    override val root = form {
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
            button("Change Password") {
                enableWhen(
                    accountSettings.valid(
                        accountSettings.currentPassword,
                        accountSettings.newPassword,
                        accountSettings.confirmPassword
                    )
                )
                action {
                    TODO("Sent password change post request")
                }
            }
        }

    }

}
