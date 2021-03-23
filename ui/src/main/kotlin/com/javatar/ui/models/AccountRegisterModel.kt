package com.javatar.ui.models

import javafx.beans.property.SimpleStringProperty
import tornadofx.ViewModel

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 23 2021
 */

class AccountRegisterModel : ViewModel() {

    val registrationEmail = bind { SimpleStringProperty(this, "register_email", "") }
    val registrationPassword = bind { SimpleStringProperty(this, "register_password", "") }
    val confirmPassword = bind { SimpleStringProperty(this, "confirm_password", "") }

}
