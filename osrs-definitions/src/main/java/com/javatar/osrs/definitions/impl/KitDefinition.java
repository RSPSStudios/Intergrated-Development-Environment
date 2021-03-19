/*
 * Copyright (c) 2017, Adam <Adam@sigterm.info>
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

public class KitDefinition implements Definition {
    private final int id;
    public short[] recolorToReplace;
    public short[] recolorToFind;
    public short[] retextureToFind;
    public short[] retextureToReplace;
    public int bodyPartId = -1;
    public int[] models;
    public int[] chatheadModels = new int[]
            {
                    -1, -1, -1, -1, -1
            };
    public boolean nonSelectable = false;

    public KitDefinition(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public short[] getRecolorToReplace() {
        return recolorToReplace;
    }

    public void setRecolorToReplace(short[] recolorToReplace) {
        this.recolorToReplace = recolorToReplace;
    }

    public short[] getRecolorToFind() {
        return recolorToFind;
    }

    public void setRecolorToFind(short[] recolorToFind) {
        this.recolorToFind = recolorToFind;
    }

    public short[] getRetextureToFind() {
        return retextureToFind;
    }

    public void setRetextureToFind(short[] retextureToFind) {
        this.retextureToFind = retextureToFind;
    }

    public short[] getRetextureToReplace() {
        return retextureToReplace;
    }

    public void setRetextureToReplace(short[] retextureToReplace) {
        this.retextureToReplace = retextureToReplace;
    }

    public int getBodyPartId() {
        return bodyPartId;
    }

    public void setBodyPartId(int bodyPartId) {
        this.bodyPartId = bodyPartId;
    }

    public int[] getModels() {
        return models;
    }

    public void setModels(int[] models) {
        this.models = models;
    }

    public int[] getChatheadModels() {
        return chatheadModels;
    }

    public void setChatheadModels(int[] chatheadModels) {
        this.chatheadModels = chatheadModels;
    }

    public boolean isNonSelectable() {
        return nonSelectable;
    }

    public void setNonSelectable(boolean nonSelectable) {
        this.nonSelectable = nonSelectable;
    }

    @Override
    public int getDefinitionId() {
        return this.id;
    }
}
