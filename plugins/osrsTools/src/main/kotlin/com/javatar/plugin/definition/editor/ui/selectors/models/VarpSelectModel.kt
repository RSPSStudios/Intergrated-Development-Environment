package com.javatar.plugin.definition.editor.ui.selectors.models

import com.javatar.osrs.definitions.impl.VarbitDefinition
import com.javatar.osrs.definitions.impl.VarpDefinition
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import tornadofx.ViewModel

class VarpSelectModel : ViewModel() {

    val varps = bind { SimpleListProperty<VarpDefinition>(this, "varps", FXCollections.observableArrayList()) }
    val varbits = bind { SimpleListProperty<VarbitDefinition>(this, "varbits", FXCollections.observableArrayList()) }

    val selected = bind { SimpleObjectProperty<VarpDefinition>(this, "selected") }

    val selectedVarbit = bind { SimpleObjectProperty<VarbitDefinition>(this, "selected_varbit") }


}