package com.javatar.saver;

import com.javatar.definition.SerializableDefinition;
import com.javatar.osrs.definitions.impl.KitDefinition;
import com.javatar.output.OutputStream;


public class KitSaver implements SerializableDefinition<KitDefinition> {
    @Override
    public byte[] serialize(KitDefinition def) {
        OutputStream out = new OutputStream();

        if (def.getBodyPartId() != -1) {
            out.writeByte(1);
            out.writeByte(def.getBodyPartId());
        }
        if (def.getModels() != null) {
            out.writeByte(2);
            out.writeByte(def.getModels().length);
            for (int i = 0; i < def.getModels().length; i++) {
                out.writeShort(def.getModels()[i]);
            }
        }
        if (def.isNonSelectable()) {
            out.writeByte(3);
        }
        if (def.getRecolorToFind() != null && def.getRecolorToReplace() != null) {
            out.writeByte(40);
            out.writeByte(def.getRecolorToFind().length);
            for (int i = 0; i < def.getRecolorToFind().length; i++) {
                out.writeShort(def.getRecolorToFind()[i]);
                out.writeShort(def.getRecolorToReplace()[i]);
            }
        }
        if (def.getRetextureToFind() != null && def.getRetextureToReplace() != null) {
            out.writeByte(41);
            out.writeByte(def.getRetextureToFind().length);
            for (int i = 0; i < def.getRetextureToFind().length; i++) {
                out.writeShort(def.getRetextureToFind()[i]);
                out.writeShort(def.getRetextureToReplace()[i]);
            }
        }
        if (def.getChatheadModels() != null) {
            for (int i = 0; i < def.getChatheadModels().length; i++) {
                if (def.getChatheadModels()[i] != -1) {
                    out.writeByte(i + 60);
                    out.writeShort(def.getChatheadModels()[i]);
                }
            }
        }
        out.writeByte(0);
        return out.flip();
    }
}
