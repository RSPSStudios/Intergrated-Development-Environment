package com.javatar.plugin.definition.editor.ui.editor.items.model

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.ItemViewModel

class PartialItemDefinition {
    val nameProperty = SimpleStringProperty(this, "name", "New Item")
    val costProperty = SimpleIntegerProperty(this, "cost", 1)
    val stackableProperty = SimpleBooleanProperty(this, "stackable", false)
    val tradeable = SimpleBooleanProperty(this, "tradeable", false)
    val members = SimpleBooleanProperty(this, "members", false)
    val genBankNoteProperty = SimpleBooleanProperty(this, "gen_bank_note", false)
    val genPlaceholderProperty = SimpleBooleanProperty(this, "gen_placeholder", false)
}

class ItemCreationModel : ItemViewModel<PartialItemDefinition>() {

    val name = bind(PartialItemDefinition::nameProperty, true)
    val cost = bind(PartialItemDefinition::costProperty, true)
    val stackable = bind(PartialItemDefinition::stackableProperty, true)
    val tradeable = bind(PartialItemDefinition::tradeable, true)
    val members = bind(PartialItemDefinition::members, true)

    val generateBankNote = bind(PartialItemDefinition::genBankNoteProperty, true)
    val generatePlaceholder = bind(PartialItemDefinition::genPlaceholderProperty, true)
}