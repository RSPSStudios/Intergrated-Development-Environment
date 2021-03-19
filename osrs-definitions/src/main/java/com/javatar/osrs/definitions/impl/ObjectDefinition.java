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

public class ObjectDefinition implements Definition {
    private int id;
    private short[] retextureToFind;
    private int decorDisplacement = 16;
    private boolean isHollow = false;
    private String name = "null";
    private int[] objectModels;
	private int[] objectTypes;
	private short[] recolorToFind;
	private int mapAreaId = -1;
	private short[] textureToReplace;
	private int sizeX = 1;
	private int sizeY = 1;
	private int anInt2083 = 0;
	private int[] anIntArray2084;
	private int offsetX = 0;
	private boolean mergeNormals = false;
	private int wallOrDoor = -1;
	private int animationID = -1;
	private int varbitID = -1;
	private int ambient = 0;
	private int contrast = 0;
	private String[] actions = new String[5];
	private int interactType = 2;
	private int mapSceneID = -1;
	private int blockingMask = 0;
	private short[] recolorToReplace;
	private boolean shadow = true;
	private int modelSizeX = 128;
	private int modelSizeHeight = 128;
	private int modelSizeY = 128;
	private int objectID;
	private int offsetHeight = 0;
	private int offsetY = 0;
	private boolean obstructsGround = false;
	private int contouredGround = -1;
	private int supportsItems = -1;
	private int[] configChangeDest;
	private boolean isRotated = false;
	private int varpID = -1;
	private int ambientSoundId = -1;
	private boolean aBool2111 = false;
	private int anInt2112 = 0;
	private int anInt2113 = 0;
	private boolean blocksProjectile = true;
	private Map<Integer, Object> params = null;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public short[] getRetextureToFind() {
		return retextureToFind;
	}

	public void setRetextureToFind(short[] retextureToFind) {
		this.retextureToFind = retextureToFind;
	}

	public int getDecorDisplacement() {
		return decorDisplacement;
	}

	public void setDecorDisplacement(int decorDisplacement) {
		this.decorDisplacement = decorDisplacement;
	}

	public boolean isHollow() {
		return isHollow;
	}

	public void setHollow(boolean hollow) {
		isHollow = hollow;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int[] getObjectModels() {
		return objectModels;
	}

	public void setObjectModels(int[] objectModels) {
		this.objectModels = objectModels;
	}

	public int[] getObjectTypes() {
		return objectTypes;
	}

	public void setObjectTypes(int[] objectTypes) {
		this.objectTypes = objectTypes;
	}

	public short[] getRecolorToFind() {
		return recolorToFind;
	}

	public void setRecolorToFind(short[] recolorToFind) {
		this.recolorToFind = recolorToFind;
	}

	public int getMapAreaId() {
		return mapAreaId;
	}

	public void setMapAreaId(int mapAreaId) {
		this.mapAreaId = mapAreaId;
	}

	public short[] getTextureToReplace() {
		return textureToReplace;
	}

	public void setTextureToReplace(short[] textureToReplace) {
		this.textureToReplace = textureToReplace;
	}

	public int getSizeX() {
		return sizeX;
	}

	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}

	public int getAnInt2083() {
		return anInt2083;
	}

	public void setAnInt2083(int anInt2083) {
		this.anInt2083 = anInt2083;
	}

	public int[] getAnIntArray2084() {
		return anIntArray2084;
	}

	public void setAnIntArray2084(int[] anIntArray2084) {
		this.anIntArray2084 = anIntArray2084;
	}

	public int getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}

	public boolean isMergeNormals() {
		return mergeNormals;
	}

	public void setMergeNormals(boolean mergeNormals) {
		this.mergeNormals = mergeNormals;
	}

	public int getWallOrDoor() {
		return wallOrDoor;
	}

	public void setWallOrDoor(int wallOrDoor) {
		this.wallOrDoor = wallOrDoor;
	}

	public int getAnimationID() {
		return animationID;
	}

	public void setAnimationID(int animationID) {
		this.animationID = animationID;
	}

	public int getVarbitID() {
		return varbitID;
	}

	public void setVarbitID(int varbitID) {
		this.varbitID = varbitID;
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

	public String[] getActions() {
		return actions;
	}

	public void setActions(String[] actions) {
		this.actions = actions;
	}

	public int getInteractType() {
		return interactType;
	}

	public void setInteractType(int interactType) {
		this.interactType = interactType;
	}

	public int getMapSceneID() {
		return mapSceneID;
	}

	public void setMapSceneID(int mapSceneID) {
		this.mapSceneID = mapSceneID;
	}

	public int getBlockingMask() {
		return blockingMask;
	}

	public void setBlockingMask(int blockingMask) {
		this.blockingMask = blockingMask;
	}

	public short[] getRecolorToReplace() {
		return recolorToReplace;
	}

	public void setRecolorToReplace(short[] recolorToReplace) {
		this.recolorToReplace = recolorToReplace;
	}

	public boolean isShadow() {
		return shadow;
	}

	public void setShadow(boolean shadow) {
		this.shadow = shadow;
	}

	public int getModelSizeX() {
		return modelSizeX;
	}

	public void setModelSizeX(int modelSizeX) {
		this.modelSizeX = modelSizeX;
	}

	public int getModelSizeHeight() {
		return modelSizeHeight;
	}

	public void setModelSizeHeight(int modelSizeHeight) {
		this.modelSizeHeight = modelSizeHeight;
	}

	public int getModelSizeY() {
		return modelSizeY;
	}

	public void setModelSizeY(int modelSizeY) {
		this.modelSizeY = modelSizeY;
	}

	public int getObjectID() {
		return objectID;
	}

	public void setObjectID(int objectID) {
		this.objectID = objectID;
	}

	public int getOffsetHeight() {
		return offsetHeight;
	}

	public void setOffsetHeight(int offsetHeight) {
		this.offsetHeight = offsetHeight;
	}

	public int getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}

	public boolean isObstructsGround() {
		return obstructsGround;
	}

	public void setObstructsGround(boolean obstructsGround) {
		this.obstructsGround = obstructsGround;
	}

	public int getContouredGround() {
		return contouredGround;
	}

	public void setContouredGround(int contouredGround) {
		this.contouredGround = contouredGround;
	}

	public int getSupportsItems() {
		return supportsItems;
	}

	public void setSupportsItems(int supportsItems) {
		this.supportsItems = supportsItems;
	}

	public int[] getConfigChangeDest() {
		return configChangeDest;
	}

	public void setConfigChangeDest(int[] configChangeDest) {
		this.configChangeDest = configChangeDest;
	}

	public boolean isRotated() {
		return isRotated;
	}

	public void setRotated(boolean rotated) {
		isRotated = rotated;
	}

	public int getVarpID() {
		return varpID;
	}

	public void setVarpID(int varpID) {
		this.varpID = varpID;
	}

	public int getAmbientSoundId() {
		return ambientSoundId;
	}

	public void setAmbientSoundId(int ambientSoundId) {
		this.ambientSoundId = ambientSoundId;
	}

	public boolean isABool2111() {
		return aBool2111;
	}

	public void setABool2111(boolean b) {
		this.aBool2111 = b;
	}

	public int getAnInt2112() {
		return anInt2112;
	}

	public void setAnInt2112(int anInt2112) {
		this.anInt2112 = anInt2112;
	}

	public int getAnInt2113() {
		return anInt2113;
	}

	public void setAnInt2113(int anInt2113) {
		this.anInt2113 = anInt2113;
	}

	public boolean isBlocksProjectile() {
		return blocksProjectile;
	}

	public void setBlocksProjectile(boolean blocksProjectile) {
		this.blocksProjectile = blocksProjectile;
	}

	public Map<Integer, Object> getParams() {
		return params;
	}

	public void setParams(Map<Integer, Object> params) {
		this.params = params;
	}

	@Override
	public int getDefinitionId() {
		return this.id;
	}
}
