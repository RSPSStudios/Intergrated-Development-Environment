package com.javatar.plugin.definition.editor.ui.editor.animations.model

import com.displee.cache.CacheLibrary
import com.javatar.osrs.definitions.impl.SequenceDefinition
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import tornadofx.ViewModel
import tornadofx.onChange

class AnimationEditorModel : ViewModel() {

    val animations = bind { SimpleListProperty<SequenceDefinition>(this, "animations", FXCollections.observableArrayList()) }

    val selected = bind { SimpleObjectProperty<SequenceDefinition>(this, "selected") }

    val cache = SimpleObjectProperty<CacheLibrary>(this, "cache")

    val forcedPriority = bind { SimpleIntegerProperty(this, "forced_priority", 5) }
    val priority = bind { SimpleIntegerProperty(this, "priority", -1) }
    val replayMode = bind { SimpleIntegerProperty(this, "replay_mode", 2) }

    val leftHandItem = bind { SimpleIntegerProperty(this, "left_hand_item", -1) }
    val rightHandItem = bind { SimpleIntegerProperty(this, "right_hand_item", -1) }

    init {
        selected.onChange {
            if(it != null) {
                forcedPriority.set(it.forcedPriority)
                priority.set(it.priority)
                replayMode.set(it.replyMode)
                leftHandItem.set(it.leftHandItem)
                rightHandItem.set(it.rightHandItem)
            }
        }
    }

    fun commitAnimation() {
        val anim = selected.get()
        if(anim != null) {
            anim.forcedPriority = forcedPriority.get()
            anim.priority = priority.get()
            anim.replyMode = replayMode.get()
            anim.leftHandItem = leftHandItem.get()
            anim.rightHandItem = rightHandItem.get()
        }
    }

}