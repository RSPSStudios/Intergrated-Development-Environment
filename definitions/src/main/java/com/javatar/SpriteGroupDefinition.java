package com.javatar;

import com.javatar.definition.Definition;

import java.util.Iterator;

public class SpriteGroupDefinition implements Definition, Iterable<SpriteDefinition> {

    private int groupId;
    private int width;
    private int height;
    private SpriteDefinition[] sprites;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public SpriteDefinition[] getSprites() {
        return sprites;
    }

    public void setSprites(SpriteDefinition[] sprites) {
        this.sprites = sprites;
    }

    @Override
    public int getDefinitionId() {
        return this.groupId;
    }

    @Override
    public Iterator<SpriteDefinition> iterator() {
        return new Iterator<>() {
            private int index;

            @Override
            public boolean hasNext() {
                return index < sprites.length;
            }

            @Override
            public SpriteDefinition next() {
                return sprites[index++];
            }
        };
    }
}
