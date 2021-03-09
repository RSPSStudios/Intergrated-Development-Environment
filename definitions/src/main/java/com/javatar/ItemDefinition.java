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
package com.javatar;

import com.javatar.definition.Definition;

import java.util.Map;


public class ItemDefinition implements Definition {
    public final int id;

    public String name = "null";

    public int resizeX = 128;
    public int resizeY = 128;
    public int resizeZ = 128;

    public int xan2d = 0;
    public int yan2d = 0;
    public int zan2d = 0;

    public int cost = 1;
    public boolean isTradeable;
    public int stackable = 0;
    public int inventoryModel;
    public boolean members = false;

    public short[] colorFind;
    public short[] colorReplace;
    public short[] textureFind;
    public short[] textureReplace;

    public int zoom2d = 2000;
    public int xOffset2d = 0;
    public int yOffset2d = 0;

    public int ambient;
    public int contrast;

    public int[] countCo;
    public int[] countObj;

    public String[] options = new String[]
            {
                    null, null, "Take", null, null
            };

    public String[] interfaceOptions = new String[]
            {
                    null, null, null, null, "Drop"
            };

    public int maleModel0 = -1;
    public int maleModel1 = -1;
    public int maleModel2 = -1;
    public int maleOffset;
    public int maleHeadModel = -1;
    public int maleHeadModel2 = -1;

    public int femaleModel0 = -1;
    public int femaleModel1 = -1;
    public int femaleModel2 = -1;
    public int femaleOffset;
    public int femaleHeadModel = -1;
    public int femaleHeadModel2 = -1;

    public int notedID = -1;
    public int notedTemplate = -1;

    public int team;

    public int shiftClickDropIndex = -2;

    public int boughtId = -1;
    public int boughtTemplateId = -1;

    public int placeholderId = -1;
    public int placeholderTemplateId = -1;

    public Map<Integer, Object> params = null;

    public ItemDefinition(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResizeX() {
        return resizeX;
    }

    public void setResizeX(int resizeX) {
        this.resizeX = resizeX;
    }

    public int getResizeY() {
        return resizeY;
    }

    public void setResizeY(int resizeY) {
        this.resizeY = resizeY;
    }

    public int getResizeZ() {
        return resizeZ;
    }

    public void setResizeZ(int resizeZ) {
        this.resizeZ = resizeZ;
    }

    public int getXan2d() {
        return xan2d;
    }

    public void setXan2d(int xan2d) {
        this.xan2d = xan2d;
    }

    public int getYan2d() {
        return yan2d;
    }

    public void setYan2d(int yan2d) {
        this.yan2d = yan2d;
    }

    public int getZan2d() {
        return zan2d;
    }

    public void setZan2d(int zan2d) {
        this.zan2d = zan2d;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public boolean isTradeable() {
        return isTradeable;
    }

    public void setTradeable(boolean tradeable) {
        isTradeable = tradeable;
    }

    public int getStackable() {
        return stackable;
    }

    public void setStackable(int stackable) {
        this.stackable = stackable;
    }

    public int getInventoryModel() {
        return inventoryModel;
    }

    public void setInventoryModel(int inventoryModel) {
        this.inventoryModel = inventoryModel;
    }

    public boolean isMembers() {
        return members;
    }

    public void setMembers(boolean members) {
        this.members = members;
    }

    public short[] getColorFind() {
        return colorFind;
    }

    public void setColorFind(short[] colorFind) {
        this.colorFind = colorFind;
    }

    public short[] getColorReplace() {
        return colorReplace;
    }

    public void setColorReplace(short[] colorReplace) {
        this.colorReplace = colorReplace;
    }

    public short[] getTextureFind() {
        return textureFind;
    }

    public void setTextureFind(short[] textureFind) {
        this.textureFind = textureFind;
    }

    public short[] getTextureReplace() {
        return textureReplace;
    }

    public void setTextureReplace(short[] textureReplace) {
        this.textureReplace = textureReplace;
    }

    public int getZoom2d() {
        return zoom2d;
    }

    public void setZoom2d(int zoom2d) {
        this.zoom2d = zoom2d;
    }

    public int getxOffset2d() {
        return xOffset2d;
    }

    public void setxOffset2d(int xOffset2d) {
        this.xOffset2d = xOffset2d;
    }

    public int getyOffset2d() {
        return yOffset2d;
    }

    public void setyOffset2d(int yOffset2d) {
        this.yOffset2d = yOffset2d;
    }

    public int getAmbient() {
        return ambient;
    }

    public void setAmbient(int ambient) {
        this.ambient = ambient;
    }

    public int getContrast() {
        return contrast;
    }

    public void setContrast(int contrast) {
        this.contrast = contrast;
    }

    public int[] getCountCo() {
        return countCo;
    }

    public void setCountCo(int[] countCo) {
        this.countCo = countCo;
    }

    public int[] getCountObj() {
        return countObj;
    }

    public void setCountObj(int[] countObj) {
        this.countObj = countObj;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public String[] getInterfaceOptions() {
        return interfaceOptions;
    }

    public void setInterfaceOptions(String[] interfaceOptions) {
        this.interfaceOptions = interfaceOptions;
    }

    public int getMaleModel0() {
        return maleModel0;
    }

    public void setMaleModel0(int maleModel0) {
        this.maleModel0 = maleModel0;
    }

    public int getMaleModel1() {
        return maleModel1;
    }

    public void setMaleModel1(int maleModel1) {
        this.maleModel1 = maleModel1;
    }

    public int getMaleModel2() {
        return maleModel2;
    }

    public void setMaleModel2(int maleModel2) {
        this.maleModel2 = maleModel2;
    }

    public int getMaleOffset() {
        return maleOffset;
    }

    public void setMaleOffset(int maleOffset) {
        this.maleOffset = maleOffset;
    }

    public int getMaleHeadModel() {
        return maleHeadModel;
    }

    public void setMaleHeadModel(int maleHeadModel) {
        this.maleHeadModel = maleHeadModel;
    }

    public int getMaleHeadModel2() {
        return maleHeadModel2;
    }

    public void setMaleHeadModel2(int maleHeadModel2) {
        this.maleHeadModel2 = maleHeadModel2;
    }

    public int getFemaleModel0() {
        return femaleModel0;
    }

    public void setFemaleModel0(int femaleModel0) {
        this.femaleModel0 = femaleModel0;
    }

    public int getFemaleModel1() {
        return femaleModel1;
    }

    public void setFemaleModel1(int femaleModel1) {
        this.femaleModel1 = femaleModel1;
    }

    public int getFemaleModel2() {
        return femaleModel2;
    }

    public void setFemaleModel2(int femaleModel2) {
        this.femaleModel2 = femaleModel2;
    }

    public int getFemaleOffset() {
        return femaleOffset;
    }

    public void setFemaleOffset(int femaleOffset) {
        this.femaleOffset = femaleOffset;
    }

    public int getFemaleHeadModel() {
        return femaleHeadModel;
    }

    public void setFemaleHeadModel(int femaleHeadModel) {
        this.femaleHeadModel = femaleHeadModel;
    }

    public int getFemaleHeadModel2() {
        return femaleHeadModel2;
    }

    public void setFemaleHeadModel2(int femaleHeadModel2) {
        this.femaleHeadModel2 = femaleHeadModel2;
    }

    public int getNotedID() {
        return notedID;
    }

    public void setNotedID(int notedID) {
        this.notedID = notedID;
    }

    public int getNotedTemplate() {
        return notedTemplate;
    }

    public void setNotedTemplate(int notedTemplate) {
        this.notedTemplate = notedTemplate;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public int getShiftClickDropIndex() {
        return shiftClickDropIndex;
    }

    public void setShiftClickDropIndex(int shiftClickDropIndex) {
        this.shiftClickDropIndex = shiftClickDropIndex;
    }

    public int getBoughtId() {
        return boughtId;
    }

    public void setBoughtId(int boughtId) {
        this.boughtId = boughtId;
    }

    public int getBoughtTemplateId() {
        return boughtTemplateId;
    }

    public void setBoughtTemplateId(int boughtTemplateId) {
        this.boughtTemplateId = boughtTemplateId;
    }

    public int getPlaceholderId() {
        return placeholderId;
    }

    public void setPlaceholderId(int placeholderId) {
        this.placeholderId = placeholderId;
    }

    public int getPlaceholderTemplateId() {
        return placeholderTemplateId;
    }

    public void setPlaceholderTemplateId(int placeholderTemplateId) {
        this.placeholderTemplateId = placeholderTemplateId;
    }

    public Map<Integer, Object> getParams() {
        return params;
    }

    public void setParams(Map<Integer, Object> params) {
        this.params = params;
    }

    public void updateNote(ItemDefinition notedItem, ItemDefinition unnotedItem) {
        this.inventoryModel = notedItem.inventoryModel;
        this.zoom2d = notedItem.zoom2d;
        this.xan2d = notedItem.xan2d;
        this.yan2d = notedItem.yan2d;
        this.zan2d = notedItem.zan2d;
        this.xOffset2d = notedItem.xOffset2d;
        this.yOffset2d = notedItem.yOffset2d;
        this.colorFind = notedItem.colorFind;
        this.colorReplace = notedItem.colorReplace;
        this.textureFind = notedItem.textureFind;
        this.textureReplace = notedItem.textureReplace;
        this.name = unnotedItem.name;
        this.members = unnotedItem.members;
        this.cost = unnotedItem.cost;
        this.stackable = 1;
    }

    @Override
    public int getDefinitionId() {
        return this.id;
    }


}
