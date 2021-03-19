package com.javatar.graphics;

public class Rasterizer2D {
    public int[] graphicsPixels;
    public int graphicsPixelsWidth;
    public int graphicsPixelsHeight;
    public int drawingAreaTop;
    public int drawingAreaBottom;
    public int draw_region_x;
    public int drawingAreaRight;

    public void setRasterBuffer(int[] var0, int var1, int var2) {
        graphicsPixels = var0;
        graphicsPixelsWidth = var1;
        graphicsPixelsHeight = var2;
        setDrawRegion(0, 0, var1, var2);
    }

    public void setDrawRegion(int var0, int var1, int var2, int var3) {
        if (var0 < 0) {
            var0 = 0;
        }

        if (var1 < 0) {
            var1 = 0;
        }

        if (var2 > graphicsPixelsWidth) {
            var2 = graphicsPixelsWidth;
        }

        if (var3 > graphicsPixelsHeight) {
            var3 = graphicsPixelsHeight;
        }

        draw_region_x = var0;
        drawingAreaTop = var1;
        drawingAreaRight = var2;
        drawingAreaBottom = var3;
    }

    public void reset() {
        int var0 = 0;

        int var1;
        for (var1 = graphicsPixelsWidth * graphicsPixelsHeight - 7; var0 < var1; graphicsPixels[var0++] = 0) {
            graphicsPixels[var0++] = 0;
            graphicsPixels[var0++] = 0;
            graphicsPixels[var0++] = 0;
            graphicsPixels[var0++] = 0;
            graphicsPixels[var0++] = 0;
            graphicsPixels[var0++] = 0;
            graphicsPixels[var0++] = 0;
        }

        for (var1 += 7; var0 < var1; graphicsPixels[var0++] = 0) {
            ;
        }

    }

}
