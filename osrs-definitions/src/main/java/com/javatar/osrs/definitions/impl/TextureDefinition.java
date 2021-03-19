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

public class TextureDefinition implements Definition {
    public int field1777;
    public boolean field1778;
    public int[] field1780;
    public int[] field1781;
    public int[] field1786;
    public int field1782;
    public int field1783;
    public transient int[] pixels;
    private int id;
    private int[] fileIds;

    public static int adjustRGB(int var0, double var1) {
        double var3 = (double) (var0 >> 16) / 256.0D;
        double var5 = (double) (var0 >> 8 & 255) / 256.0D;
        double var7 = (double) (var0 & 255) / 256.0D;
        var3 = Math.pow(var3, var1);
        var5 = Math.pow(var5, var1);
        var7 = Math.pow(var7, var1);
        int var9 = (int) (var3 * 256.0D);
        int var10 = (int) (var5 * 256.0D);
        int var11 = (int) (var7 * 256.0D);
        return var11 + (var10 << 8) + (var9 << 16);
    }

    public int getField1777() {
        return field1777;
    }

    public void setField1777(int field1777) {
        this.field1777 = field1777;
    }

    public boolean isField1778() {
        return field1778;
    }

    public void setField1778(boolean field1778) {
        this.field1778 = field1778;
    }

    public int[] getField1780() {
        return field1780;
    }

    public void setField1780(int[] field1780) {
        this.field1780 = field1780;
    }

    public int[] getField1781() {
        return field1781;
    }

    public void setField1781(int[] field1781) {
        this.field1781 = field1781;
    }

    public int[] getField1786() {
        return field1786;
    }

    public void setField1786(int[] field1786) {
        this.field1786 = field1786;
    }

    public int getField1782() {
        return field1782;
    }

    public void setField1782(int field1782) {
        this.field1782 = field1782;
    }

    public int getField1783() {
        return field1783;
    }

    public void setField1783(int field1783) {
        this.field1783 = field1783;
    }

    public int[] getPixels() {
        return pixels;
    }

    public void setPixels(int[] pixels) {
        this.pixels = pixels;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int[] getFileIds() {
        return fileIds;
    }

    public void setFileIds(int[] fileIds) {
        this.fileIds = fileIds;
    }

    @Override
    public int getDefinitionId() {
        return this.id;
    }
}
