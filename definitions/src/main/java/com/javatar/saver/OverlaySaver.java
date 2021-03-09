package com.javatar.saver;

import com.javatar.OverlayDefinition;
import com.javatar.definition.SerializableDefinition;
import com.javatar.output.OutputStream;


public class OverlaySaver implements SerializableDefinition<OverlayDefinition> {
    @Override
    public byte[] serialize(OverlayDefinition def) {
        OutputStream out = new OutputStream();

        if (def.getRgbColor() != 0) {
            out.writeByte(1);
            out.write24BitInt(def.getRgbColor());
        }
        if (def.getTexture() != -1) {
            out.writeByte(2);
            out.writeByte(def.getTexture());
        }
        if (!def.isHideUnderlay()) {
            out.writeByte(5);
        }
        if (def.getSecondaryRgbColor() != -1) {
            out.writeByte(7);
            out.write24BitInt(def.getSecondaryRgbColor());
        }

        out.writeByte(0);
        return out.flip();
    }
}
