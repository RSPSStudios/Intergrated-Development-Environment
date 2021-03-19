package com.javatar.textures;

import com.javatar.definition.DefinitionProvider;
import com.javatar.osrs.definitions.impl.SpriteDefinition;
import com.javatar.osrs.definitions.impl.SpriteGroupDefinition;
import com.javatar.osrs.definitions.impl.TextureDefinition;

public class RSTextureProvider {
    private final DefinitionProvider<SpriteGroupDefinition> spriteProvider;
    TextureDefinition[] textures;
    int maxSize;
    int size;
    double brightness;
    int width;

    public RSTextureProvider(DefinitionProvider<TextureDefinition> textureProvider, DefinitionProvider<SpriteGroupDefinition> spriteProvider) {
        this.spriteProvider = spriteProvider;
        this.size = 0;
        this.brightness = 1.0D;
        this.width = 128;
        this.maxSize = 20;
        this.size = this.maxSize;
        this.brightness = 0.8D;
        this.width = 128;

        int max = -1;
        for (TextureDefinition textureDefinition : textureProvider.values()) {
            if (textureDefinition.getId() > max) {
                max = textureDefinition.getId();
            }
        }

        textures = new TextureDefinition[max + 1];
        for (TextureDefinition textureDefinition : textureProvider.values()) {
            textures[textureDefinition.getId()] = textureDefinition;
        }
    }

    public int[] load(int var1) {

        if (var1 >= this.textures.length) {
            return null;
        }

        TextureDefinition var2 = this.textures[var1];
        if (var2 != null) {
            if (var2.pixels != null) {
                return var2.pixels;
            }

            boolean var3 = method2680(var2, this.brightness, this.width, spriteProvider);
            return var2.pixels;
        }

        return null;
    }

    public boolean method2680(TextureDefinition def, double var1, int var3, DefinitionProvider<SpriteGroupDefinition> spriteProvider) {
        int var5 = var3 * var3;
        def.pixels = new int[var5];

        for (int var6 = 0; var6 < def.getFileIds().length; ++var6) {
            SpriteDefinition var7 = spriteProvider.getDefinition(def.getFileIds()[var6]).getSprites()[0];
            var7.normalize();
            byte[] var8 = var7.pixelIdx;
            int[] var9 = var7.palette;
            int var10 = def.field1786[var6];

            int var11;
            int var12;
            int var13;
            int var14;
            if ((var10 & -16777216) == 50331648) {
                var11 = var10 & 16711935;
                var12 = var10 >> 8 & 255;

                for (var13 = 0; var13 < var9.length; ++var13) {
                    var14 = var9[var13];
                    if (var14 >> 8 == (var14 & 65535)) {
                        var14 &= 255;
                        var9[var13] = var11 * var14 >> 8 & 16711935 | var12 * var14 & 65280;
                    }
                }
            }

            for (var11 = 0; var11 < var9.length; ++var11) {
                var9[var11] = TextureDefinition.adjustRGB(var9[var11], var1);
            }

            if (var6 == 0) {
                var11 = 0;
            } else {
                var11 = def.field1780[var6 - 1];
            }

            if (var11 == 0) {
                if (var3 == var7.getMaxWidth()) {
                    for (var12 = 0; var12 < var5; ++var12) {
                        def.pixels[var12] = var9[var8[var12] & 255];
                    }
                } else if (var7.getMaxWidth() == 64 && var3 == 128) {
                    var12 = 0;

                    for (var13 = 0; var13 < var3; ++var13) {
                        for (var14 = 0; var14 < var3; ++var14) {
                            def.pixels[var12++] = var9[var8[(var13 >> 1 << 6) + (var14 >> 1)] & 255];
                        }
                    }
                } else {
                    if (var7.getMaxWidth() != 128 || var3 != 64) {
                        throw new RuntimeException();
                    }

                    var12 = 0;

                    for (var13 = 0; var13 < var3; ++var13) {
                        for (var14 = 0; var14 < var3; ++var14) {
                            def.pixels[var12++] = var9[var8[(var14 << 1) + (var13 << 1 << 7)] & 255];
                        }
                    }
                }
            }
        }

        return true;
    }


    public int getAverageTextureRGB(int var1) {
        if (var1 >= this.textures.length) return 0;
        return this.textures[var1] != null ? this.textures[var1].field1777 : 0;
    }


    public boolean vmethod3057(int var1) {
        return this.textures[var1].field1778;
    }


    public boolean vmethod3066(int var1) {
        return this.width == 64;
    }
}
