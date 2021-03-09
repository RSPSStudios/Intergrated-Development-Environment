package com.javatar.saver;

import com.javatar.ParamDefinition;
import com.javatar.definition.SerializableDefinition;
import com.javatar.output.OutputStream;


public class ParamSaver implements SerializableDefinition<ParamDefinition> {
    @Override
    public byte[] serialize(ParamDefinition def) {
        OutputStream out = new OutputStream();

        if (def.getType() != null) {
            out.writeByte(1);
            out.writeByte(def.getType().getKeyChar());
        }
        if (def.getDefaultInt() != 0) {
            out.writeByte(2);
            out.writeInt(def.getDefaultInt());
        }
        if (!def.isMembers()) {
            out.writeByte(4);
        }
        if (def.getDefaultString() != null) {
            out.writeByte(5);
            out.writeString(def.getDefaultString());
        }

        out.writeByte(0);
        return out.flip();
    }
}
