package com.javatar.ui.models

import javafx.beans.property.SimpleStringProperty
import tornadofx.ViewModel

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 22 2021
 */

class AccountSettingsModel : ViewModel() {

    val email = bind { SimpleStringProperty(this, "email_address", "") }
    val subscribed = bind { SimpleStringProperty(this, "subscribed", "") }
    val currentPassword = bind { SimpleStringProperty(this, "current_password", "") }
    val newPassword = bind { SimpleStringProperty(this, "new_password", "") }
    val confirmPassword = bind { SimpleStringProperty(this, "confirm_password", "") }

}
