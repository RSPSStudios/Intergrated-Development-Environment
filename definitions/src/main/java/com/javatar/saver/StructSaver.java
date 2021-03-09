package com.javatar.saver;

import com.javatar.StructDefinition;
import com.javatar.definition.SerializableDefinition;
import com.javatar.output.OutputStream;

import java.util.Map;


public class StructSaver implements SerializableDefinition<StructDefinition> {
    @Override
    public byte[] serialize(StructDefinition def) {
        OutputStream out = new OutputStream();

        if (def.params != null) {
            out.writeByte(249);
            out.writeByte(def.params.size());
            for (Map.Entry<Integer, Object> entry : def.params.entrySet()) {
                out.writeByte(entry.getValue() instanceof String ? 1 : 0);
                out.write24BitInt(entry.getKey());
                if (entry.getValue() instanceof String) {
                    out.writeString((String) entry.getValue());
                } else {
                    out.writeInt((Integer) entry.getValue());
                }
            }
        }

        out.writeByte(0);
        return out.flip();
    }
}
