package com.javatar.saver;

import com.javatar.UnderlayDefinition;
import com.javatar.definition.SerializableDefinition;
import com.javatar.output.OutputStream;


public class UnderlaySaver implements SerializableDefinition<UnderlayDefinition> {
    @Override
    public byte[] serialize(UnderlayDefinition def) {
        OutputStream out = new OutputStream();

        if (def.getColor() != 0) {
            out.writeByte(1);
            out.write24BitInt(def.getColor());
        }

        out.writeByte(0);
        return out.flip();
    }
}
