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

public class MapDefinition implements Definition {
    public static final int X = 64;
    public static final int Y = 64;
    public static final int Z = 4;
    private int regionX;
    private int regionY;
    private Tile[][][] tiles = new Tile[Z][X][Y];

    public int getRegionX() {
        return regionX;
    }

    public void setRegionX(int regionX) {
        this.regionX = regionX;
    }

    public int getRegionY() {
        return regionY;
    }

    public void setRegionY(int regionY) {
        this.regionY = regionY;
    }

    public Tile[][][] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[][][] tiles) {
        this.tiles = tiles;
    }

    @Override
    public int getDefinitionId() {
        return regionX << 8 | regionY;
    }

    public static class Tile {
        public Integer height;
        public int attrOpcode;
        public byte settings;
        public byte overlayId;
        public byte overlayPath;
        public byte overlayRotation;
        public byte underlayId;
    }
}
