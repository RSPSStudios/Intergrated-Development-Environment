package com.javatar.saver;

import com.javatar.definition.SerializableDefinition;
import com.javatar.osrs.definitions.impl.VarbitDefinition;
import com.javatar.output.OutputStream;


public class VarbitSaver implements SerializableDefinition<VarbitDefinition> {
    @Override
    public byte[] serialize(VarbitDefinition def) {
        OutputStream out = new OutputStream();

        if (def.getIndex() != 0) {
            out.writeByte(1);
            out.writeShort(def.getIndex());
            out.writeByte(def.getLeastSignificantBit());
            out.writeByte(def.getMostSignificantBit());
        }

        out.writeByte(0);
        return out.flip();
    }
}
