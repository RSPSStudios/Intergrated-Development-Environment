/*
 * Copyright (c) 2020, Hexagon <hexagon@fking.work>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.javatar.osrs.definitions.impl;

import com.javatar.osrs.definitions.Definition;

public class HitSplatDefinition implements Definition {

    private int id;
    private String stringFormat = "";
    private int varbitID = -1;
    private int leftSprite = -1;
    private int leftSprite2 = -1;
    private int rightSpriteId = -1;
    private int fontType = -1;
    private int backgroundSprite = -1;
    private int varpID = -1;
    private int useDamage = -1;
    private int textColor = 0xFFFFFF;
    private int displayCycles = 70;
    private int[] multihitsplats;
    private int scrollToOffsetX = 0;
    private int fadeStartCycle = -1;
    private int scrollToOffsetY = 0;
    private int textOffsetY = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStringFormat() {
        return stringFormat;
    }

    public void setStringFormat(String stringFormat) {
        this.stringFormat = stringFormat;
    }

    public int getVarbitID() {
        return varbitID;
    }

    public void setVarbitID(int varbitID) {
        this.varbitID = varbitID;
    }

    public int getLeftSprite() {
        return leftSprite;
    }

    public void setLeftSprite(int leftSprite) {
        this.leftSprite = leftSprite;
    }

    public int getLeftSprite2() {
        return leftSprite2;
    }

    public void setLeftSprite2(int leftSprite2) {
        this.leftSprite2 = leftSprite2;
    }

    public int getRightSpriteId() {
        return rightSpriteId;
    }

    public void setRightSpriteId(int rightSpriteId) {
        this.rightSpriteId = rightSpriteId;
    }

    public int getFontType() {
        return fontType;
    }

    public void setFontType(int fontType) {
        this.fontType = fontType;
    }

    public int getBackgroundSprite() {
        return backgroundSprite;
    }

    public void setBackgroundSprite(int backgroundSprite) {
        this.backgroundSprite = backgroundSprite;
    }

    public int getVarpID() {
        return varpID;
    }

    public void setVarpID(int varpID) {
        this.varpID = varpID;
    }

    public int getUseDamage() {
        return useDamage;
    }

    public void setUseDamage(int useDamage) {
        this.useDamage = useDamage;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getDisplayCycles() {
        return displayCycles;
    }

    public void setDisplayCycles(int displayCycles) {
        this.displayCycles = displayCycles;
    }

    public int[] getMultihitsplats() {
        return multihitsplats;
    }

    public void setMultihitsplats(int[] multihitsplats) {
        this.multihitsplats = multihitsplats;
    }

    public int getScrollToOffsetX() {
        return scrollToOffsetX;
    }

    public void setScrollToOffsetX(int scrollToOffsetX) {
        this.scrollToOffsetX = scrollToOffsetX;
    }

    public int getFadeStartCycle() {
        return fadeStartCycle;
    }

    public void setFadeStartCycle(int fadeStartCycle) {
        this.fadeStartCycle = fadeStartCycle;
    }

    public int getScrollToOffsetY() {
        return scrollToOffsetY;
    }

    public void setScrollToOffsetY(int scrollToOffsetY) {
        this.scrollToOffsetY = scrollToOffsetY;
    }

    public int getTextOffsetY() {
        return textOffsetY;
    }

    public void setTextOffsetY(int textOffsetY) {
        this.textOffsetY = textOffsetY;
    }

    @Override
    public int getDefinitionId() {
        return this.id;
    }
}
