package com.javatar.saver;

import com.javatar.definition.SerializableDefinition;
import com.javatar.osrs.definitions.impl.AreaDefinition;
import com.javatar.output.OutputStream;


/**
 * Not writing opcodes that are ignore by runescape in this revision.
 */


public class AreaSaver implements SerializableDefinition<AreaDefinition> {
    @Override
    public byte[] serialize(AreaDefinition def) {
        OutputStream out = new OutputStream();
        if (def.spriteId != -1) {
            out.writeByte(1);
            out.writeBigSmart(def.spriteId);
        }
        if (def.field3294 != -1) {
            out.writeByte(2);
            out.writeBigSmart(def.field3294);
        }
        if (def.name != null) {
            out.writeByte(3);
            out.writeString(def.name);
        }
        if (def.field3296 != 0) {
            out.writeByte(4);
            out.write24BitInt(def.field3296);
        }
        if (def.field3310 != 0) {
            out.writeByte(6);
            out.writeByte(def.field3310);
        }
        for (int i = 0; i < def.field3298.length; i++) {
            if (def.field3298[i] != null) {
                out.writeByte(10 + i);
                out.writeString(def.field3298[i]);
            }
        }
        if (def.field3300 != null && def.field3300.length > 0) {
            out.writeByte(15);
            out.writeByte(def.field3309.length);
            for (int i = 0; i < def.field3300.length; i++) {
                out.writeShort(def.field3300[i]);
            }
            out.writeInt(0);
            out.writeByte(def.field3292.length);
            for (int i = 0; i < def.field3292.length; i++) {
                out.writeInt(def.field3292[i]);
            }
            for (int i = 0; i < def.field3309.length; i++) {
                out.writeByte(def.field3309[i]);
            }
        }
        if (def.field3308 != null) {
            out.writeByte(17);
            out.writeString(def.field3308);
        }
        if (def.worldmapCategoryId != -1) {
            out.writeByte(19);
            out.writeShort(def.worldmapCategoryId);
        }
        if (def.opcode29 != 0) {
            out.writeByte(29);
            out.writeByte(def.opcode29);
        }
        if (def.opcode30 != 0) {
            out.writeByte(30);
            out.writeByte(def.opcode30);
        }
        out.writeByte(0);
        return out.flip();
    }
}
