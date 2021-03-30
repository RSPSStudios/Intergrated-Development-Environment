package com.javatar.ui.views.account

import com.javatar.api.ui.models.AccountModel
import javafx.geometry.Pos
import tornadofx.*

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 21 2021
 */

class LoginDialog : Fragment("Account") {

    val accountModel: AccountModel by di()

    override val root = vbox {
        style = "-fx-base: #3f474f;"
        alignment = Pos.CENTER
        spacing = 10.0
        tabpane {
            tab<AccountLoginFragment> {
                closeableWhen(false.toProperty())
            }
            tab<RegistrationFragment> {
                closeableWhen(false.toProperty())
                disableWhen(accountModel.loggedIn)
            }
        }
    }

}
