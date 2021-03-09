package com.javatar.saver;

import com.javatar.EnumDefinition;
import com.javatar.output.OutputStream;

public class EnumSaver {

    public byte[] serialize(EnumDefinition def) {
        OutputStream out = new OutputStream();
        if (def.getKeyType() != null) {
            out.writeByte(1);
            out.writeByte(def.getKeyType().getKeyChar());
        }
        if (def.getValType() != null) {
            out.writeByte(2);
            out.writeByte(def.getValType().getKeyChar());
        }
        if (def.getDefaultString() != null) {
            out.writeByte(3);
            out.writeString(def.getDefaultString());
        }
        if (def.getDefaultInt() != 0) {
            out.writeByte(4);
            out.writeInt(def.getDefaultInt());
        }
        if (def.getKeys() != null && def.getStringVals() != null) {
            out.writeByte(5);
            out.writeShort(def.getKeys().length);
            for (int i = 0; i < def.getKeys().length; i++) {
                out.writeInt(def.getKeys()[i]);
                out.writeString(def.getStringVals()[i]);
            }
        }
        if (def.getKeys() != null && def.getIntVals() != null) {
            out.writeByte(6);
            out.writeShort(def.getKeys().length);
            for (int i = 0; i < def.getKeys().length; i++) {
                out.writeInt(def.getKeys()[i]);
                out.writeInt(def.getIntVals()[i]);
            }
        }
        out.writeByte(0);
        return out.flip();
    }
}
