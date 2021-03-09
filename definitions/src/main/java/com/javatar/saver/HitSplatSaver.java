package com.javatar.saver;

import com.javatar.HitSplatDefinition;
import com.javatar.definition.SerializableDefinition;
import com.javatar.output.OutputStream;


public class HitSplatSaver implements SerializableDefinition<HitSplatDefinition> {
    @Override
    public byte[] serialize(HitSplatDefinition def) {
        OutputStream out = new OutputStream();

        if (def.getFontType() != -1) {
            out.writeByte(1);
            out.writeBigSmart(def.getFontType());
        }
        if (def.getTextColor() != 0xFFFFFF) {
            out.writeByte(2);
            out.write24BitInt(def.getTextColor());
        }
        if (def.getLeftSprite() != -1) {
            out.writeByte(3);
            out.writeBigSmart(def.getLeftSprite());
        }
        if (def.getLeftSprite2() != -1) {
            out.writeByte(4);
            out.writeBigSmart(def.getLeftSprite2());
        }
        if (def.getBackgroundSprite() != -1) {
            out.writeByte(5);
            out.writeBigSmart(def.getBackgroundSprite());
        }
        if (def.getRightSpriteId() != -1) {
            out.writeByte(6);
            out.writeBigSmart(def.getRightSpriteId());
        }
        if (def.getScrollToOffsetX() != 0) {
            out.writeByte(7);
            out.writeShort(def.getScrollToOffsetX());
        }
        if (def.getStringFormat() != null) {
            out.writeByte(8);
            out.writeString2(def.getStringFormat());
        }
        if (def.getDisplayCycles() != 70) {
            out.writeByte(9);
            out.writeShort(def.getDisplayCycles());
        }
        if (def.getScrollToOffsetY() != 0) {
            out.writeByte(10);
            out.writeShort(def.getScrollToOffsetY());
        }
        if (def.getFadeStartCycle() == 0) {
            out.writeByte(11);
        }
        if (def.getUseDamage() != -1) {
            out.writeByte(12);
            out.writeByte(def.getUseDamage());
        }
        if (def.getTextOffsetY() != 0) {
            out.writeByte(13);
            out.writeShort(def.getTextOffsetY());
        }
        if (def.getFadeStartCycle() > 0) {
            out.writeByte(14);
            out.writeShort(def.getFadeStartCycle());
        }
        if (def.getVarbitID() != -1) {
            int defaultId = def.getMultihitsplats()[def.getMultihitsplats().length - 1];
            out.writeByte(defaultId == -1 ? 17 : 18);
            out.writeShort(def.getVarbitID());
            out.writeShort(def.getVarpID());
            if (defaultId != -1) {
                out.writeShort(defaultId);
            }
            out.writeByte(def.getMultihitsplats().length - 2);
            for (int i = 0; i < def.getMultihitsplats().length - 1; i++) {
                out.writeShort(def.getMultihitsplats()[i]);
            }
        }
        out.writeByte(0);
        return out.flip();
    }
}
