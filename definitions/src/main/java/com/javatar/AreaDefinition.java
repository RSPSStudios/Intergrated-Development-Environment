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
package com.javatar;

import com.javatar.definition.Definition;

import java.util.Arrays;
import java.util.Objects;

public class AreaDefinition implements Definition {
    public int id;
    public int[] field3292;
    public int spriteId = -1;
    public int field3294 = -1;
    public String name;
    public int field3296;
    public int worldmapCategoryId = -1;
    public String[] field3298 = new String[5];
    public int[] field3300;
    public String field3308;
    public byte[] field3309;
    public int field3310;

    public int opcode29;
    public int opcode30;

    @Override
    public int getDefinitionId() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AreaDefinition that = (AreaDefinition) o;
        return spriteId == that.spriteId &&
                field3294 == that.field3294 &&
                field3296 == that.field3296 &&
                worldmapCategoryId == that.worldmapCategoryId &&
                field3310 == that.field3310 &&
                opcode29 == that.opcode29 &&
                opcode30 == that.opcode30 &&
                Arrays.equals(field3292, that.field3292) &&
                Objects.equals(name, that.name) &&
                Arrays.equals(field3298, that.field3298) &&
                Arrays.equals(field3300, that.field3300) &&
                Objects.equals(field3308, that.field3308) &&
                Arrays.equals(field3309, that.field3309);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, spriteId, field3294, name, field3296, worldmapCategoryId, field3308, field3310, opcode29, opcode30);
        result = 31 * result + Arrays.hashCode(field3292);
        result = 31 * result + Arrays.hashCode(field3298);
        result = 31 * result + Arrays.hashCode(field3300);
        result = 31 * result + Arrays.hashCode(field3309);
        return result;
    }
}
