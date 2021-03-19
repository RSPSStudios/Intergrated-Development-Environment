package com.javatar.saver;

import com.javatar.definition.SerializableDefinition;
import com.javatar.osrs.definitions.impl.SpotAnimDefinition;
import com.javatar.output.OutputStream;


public class SpotAnimSaver implements SerializableDefinition<SpotAnimDefinition> {
    @Override
    public byte[] serialize(SpotAnimDefinition def) {
        OutputStream out = new OutputStream();

        if (def.modelId != 0) {
            out.writeByte(1);
            out.writeShort(def.modelId);
        }
        if (def.animationId != -1) {
            out.writeByte(2);
            out.writeShort(def.animationId);
        }
        if (def.resizeX != 128) {
            out.writeByte(4);
            out.writeShort(def.resizeX);
        }
        if (def.resizeY != 128) {
            out.writeByte(5);
            out.writeShort(def.resizeY);
        }
        if (def.rotaton != 0) {
            out.writeByte(6);
            out.writeShort(def.rotaton);
        }
        if (def.ambient != 0) {
            out.writeByte(7);
            out.writeByte(def.ambient);
        }
        if (def.contrast != 0) {
            out.writeByte(8);
            out.writeByte(def.contrast);
        }
        if (def.recolorToFind != null && def.recolorToReplace != null) {
            out.writeByte(40);
            out.writeByte(def.recolorToFind.length);
            for (int i = 0; i < def.recolorToFind.length; i++) {
                out.writeShort(def.recolorToFind[i]);
                out.writeShort(def.recolorToReplace[i]);
            }
        }
        if (def.textureToFind != null && def.textureToReplace != null) {
            out.writeByte(41);
            out.writeByte(def.textureToFind.length);
            for (int i = 0; i < def.textureToFind.length; i++) {
                out.writeShort(def.textureToFind[i]);
                out.writeShort(def.textureToReplace[i]);
            }
        }
        out.writeByte(0);
        return out.flip();
    }
}
