/*
 * Copyright (c) 2016-2017, Adam <Adam@sigterm.info>
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

import java.util.Map;

/**
 * Some fields are named incorrectly, thanks runelite ...
 */

public class ScriptDefinition implements Definition {
    private int id;
    private int[] instructions;
    private int[] intOperands;
    private String[] stringOperands;
    private int intStackCount;
    private int stringStackCount;
    private int localIntCount;
    private int localStringCount;
    private Map<Integer, Integer>[] switches;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int[] getInstructions() {
        return instructions;
    }

    public void setInstructions(int[] instructions) {
        this.instructions = instructions;
    }

    public int[] getIntOperands() {
        return intOperands;
    }

    public void setIntOperands(int[] intOperands) {
        this.intOperands = intOperands;
    }

    public String[] getStringOperands() {
        return stringOperands;
    }

    public void setStringOperands(String[] stringOperands) {
        this.stringOperands = stringOperands;
    }

    public int getIntStackCount() {
        return intStackCount;
    }

    public void setIntStackCount(int intStackCount) {
        this.intStackCount = intStackCount;
    }

    public int getStringStackCount() {
        return stringStackCount;
    }

    public void setStringStackCount(int stringStackCount) {
        this.stringStackCount = stringStackCount;
    }

    public int getLocalIntCount() {
        return localIntCount;
    }

    public void setLocalIntCount(int localIntCount) {
        this.localIntCount = localIntCount;
    }

    public int getLocalStringCount() {
        return localStringCount;
    }

    public void setLocalStringCount(int localStringCount) {
        this.localStringCount = localStringCount;
    }

    public Map<Integer, Integer>[] getSwitches() {
        return switches;
    }

    public void setSwitches(Map<Integer, Integer>[] switches) {
        this.switches = switches;
    }

    @Override
    public int getDefinitionId() {
        return this.id;
    }
}
