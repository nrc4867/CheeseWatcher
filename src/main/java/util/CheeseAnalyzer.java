package util;

import javafx.scene.canvas.GraphicsContext;

import java.awt.image.BufferedImage;

/**
 * Nothing fancy, just check a few pixels on the cheese to test if its there
 * Fancy stuff is too slow if your doing it live
 *
 * @author Nicholas Chieppa
 */
public class CheeseAnalyzer {

    private final static float maxdistance = 50.0f;
    private final static int minMatch = 2;


    public static boolean scanNormalCheese(BufferedImage image) {
        int pixels[][][] = new int[][][]{
                {{image.getRGB(8,140)}, {253,255,143}},
                {{image.getRGB(15,146)}, {253,242,100}},
                {{image.getRGB(19,140)}, {237,186,35}}
        };

        return matchPixels(pixels);
    }

    public static boolean scanRadCheese(BufferedImage image) {
        int pixels[][][] = new int[][][]{
                {{image.getRGB(8,140)}, {214,248,246}},
                {{image.getRGB(15,146)}, {240,231,253}},
                {{image.getRGB(19,140)}, {78,227,243}}
        };

        return matchPixels(pixels);
    }

    private static boolean matchPixels(int[][][] pixels) {
        int match = 0;
        for (int i = 0; i < pixels.length; i++) {
            if (scanpixel(pixels[i][0][0], pixels[i][1][0], pixels[i][1][1], pixels[i][1][2]))
                match++;
        }
        return match >= minMatch;
    }

    private static boolean scanpixel(int pixel, int r, int g, int b) {
        int pr = (pixel >> 16) & 0xff;
        int pg = (pixel >> 8) & 0xff;
        int pb  = (pixel) & 0xff;

        return colorDistance(pr,pg,pb,r,g,b) <= maxdistance;
    }

    private static float colorDistance(int pr, int pg, int pb, int r, int g, int b) {
        final int diffR = r - pr;
        final int diffG = g - pg;
        final int diffB = b - pb;
        return (float) Math.sqrt(diffB*diffB + diffR*diffR + diffG * diffG);
    }


}