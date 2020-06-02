package yaqub.tesapp001.model;

import android.graphics.Color;
import android.media.Image;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import ae.java.awt.image.BufferedImage;
import ae.javax.imageio.ImageIO;


/**
 * Created by Yaqub on 2/13/2018.
 */

public class General {

    public static String pDatetime(String sFormat) {
        DateFormat idateFormat = new SimpleDateFormat(sFormat); //"yyyy-MM-dd HH:mm:ss"
        idateFormat.setLenient(false);
        String iNow = idateFormat.format(new Date());
        return iNow;
    }

    public static String[] mColors = {
            "#E0FFFF", // LIGHTCYAN
            "#FFA07A", // LIGHTSALMON
            "#7FFFD4", // AQUAMARINE
            "#FFC0CB", // PINK
            "#00FFFF", // CYAN
            "#FFF8DC", // CORNSILK
            "#FFE4C4", // BISQUE
            "#FFFF00", // YELLOW
            "#FFDAB9", // PEACHPUFF
            "#98FB98", // PALEGREEN
            "#C0C0C0", // SILVER
            "#FFE4E1", // MISTYROSE
            "#E6E6FA", // LAVENDER
            "#F0E68C"  // KHAKI
    };

    public static int getColorRandom() {
        String color;

        // Randomly select a fact
        Random randomGenerator = new Random(); // Construct a new Random number generator
        int randomNumber = randomGenerator.nextInt(mColors.length);

        color = mColors[randomNumber];
        int colorAsInt = Color.parseColor(color);

        return colorAsInt;
    }

}
