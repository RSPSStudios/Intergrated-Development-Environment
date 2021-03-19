package com.javatar.saver;

import com.javatar.definition.SerializableDefinition;
import com.javatar.osrs.definitions.impl.SpriteDefinition;
import com.javatar.osrs.definitions.impl.SpriteGroupDefinition;
import com.javatar.output.OutputStream;


public class SpriteSaver implements SerializableDefinition<SpriteGroupDefinition> {
    public byte[] save(SpriteGroupDefinition group) {
        int[] palette = new int[1];
        if (group.getSprites().length > 0) {
            palette = group.getSprites()[0].palette;
        }
        int length = 7 + (9 * group.getSprites().length) + (palette.length * 3);
        for (SpriteDefinition s : group) {
            length += s.getPixelIdx().length;
        }
        OutputStream out = new OutputStream(length);
        for (SpriteDefinition def : group) {
            out.writeByte(0x0);
            byte[] pixels = def.getPixelIdx();
            for (int pixel : pixels) {
                out.writeByte(pixel);
            }
        }
        for (int value : palette) {
            out.write24BitInt(value);
        }
        if (group.getSprites().length == 1) {
            out.writeShort(group.getSprites()[0].getWidth());
            out.writeShort(group.getSprites()[0].getHeight());
        } else {
            out.writeShort(group.getWidth());
            out.writeShort(group.getHeight());
        }
        out.writeByte(palette.length - 1);
        for (SpriteDefinition sprite : group) {
            out.writeShort(sprite.getOffsetX());
        }
        for (SpriteDefinition sprite : group) {
            out.writeShort(sprite.getOffsetY());
        }
        for (SpriteDefinition sprite : group) {
            out.writeShort(sprite.getWidth());
        }
        for (SpriteDefinition sprite : group) {
            out.writeShort(sprite.getHeight());
        }
        out.writeShort(group.getSprites().length);
        return out.flip();
    }

    @Override
    public byte[] serialize(SpriteGroupDefinition def) {
        return save(def);
    }
}
