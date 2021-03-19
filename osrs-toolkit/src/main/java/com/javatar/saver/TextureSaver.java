package com.javatar.saver;

import com.javatar.definition.SerializableDefinition;
import com.javatar.osrs.definitions.impl.TextureDefinition;
import com.javatar.output.OutputStream;


public class TextureSaver implements SerializableDefinition<TextureDefinition> {
    @Override
    public byte[] serialize(TextureDefinition def) {
        OutputStream out = new OutputStream();
        out.writeShort(def.field1777);
        out.writeByte(def.field1778 ? 1 : 0);
        out.writeByte(def.getFileIds().length);
        for (int id : def.getFileIds()) {
            out.writeShort(id);
        }
        if (def.getFileIds().length > 1) {
            for (int value : def.field1780) {
                out.writeByte(value);
            }
        }
        if (def.getFileIds().length > 1) {
            for (int value : def.field1781) {
                out.writeByte(value);
            }
        }
        for (int value : def.field1786) {
            out.writeInt(value);
        }
        out.writeByte(def.field1783);
        out.writeByte(def.field1782);
        return out.flip();
    }
}
