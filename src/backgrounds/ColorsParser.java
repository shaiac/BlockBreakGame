package backgrounds;

import java.awt.Color;

/**
 * ColorsParser class.
 * @author Shai acoca.
 */
public class ColorsParser {

    /**
     * this method returns a color.
     * @param s a string
     * @return a color
     */
    public java.awt.Color returnKnownColor(String s) {
        String color = s.replaceAll("[()]", "");
        if (color.contains("color")) {
            color = color.substring(5);
        }
        if (color.equals("black") || color.equals("BLACK")) {
            return Color.black;
        } else if (color.equals("blue") || color.equals("BLUE")) {
            return Color.blue;
        } else if (color.equals("cyan") || color.equals("CYAN")) {
            return Color.cyan;
        } else if (color.equals("gray") || color.equals("GRAY")) {
            return Color.gray;
        } else if (color.equals("lightGray") || color.equals("LIGHT_GRAY")) {
            return Color.lightGray;
        } else if (color.equals("green") || color.equals("GREEN")) {
            return Color.green;
        } else if (color.equals("orange") || color.equals("ORANGE")) {
            return Color.orange;
        } else if (color.equals("pink") || color.equals("PINK")) {
            return Color.pink;
        } else if (color.equals("red") || color.equals("RED")) {
            return Color.red;
        } else if (color.equals("white") || color.equals("WHITE")) {
            return Color.white;
        } else if (color.equals("yellow") || color.equals("YELLOW")) {
            return Color.yellow;
        }
        // if no color was submitted
        return null;
    }

    /**
     * this method returns a RGB color.
     * @param s a string
     * @return a RGB color
     */
    public java.awt.Color returnRGBColor(String s) {
        // "cleaning" the expression
        s = s.replaceAll("[A-Za-z()]", "");
        // splitting it
        String[] rgb = s.split(",");
        int r = Integer.parseInt(rgb[0]);
        int g = Integer.parseInt(rgb[1]);
        int b = Integer.parseInt(rgb[2]);
        // return a new color
        return new Color(r, g, b);
    }

    /**
     * this method gets a string and returns a color.
     * @param s The string.
     * @return a color.
     */
    public java.awt.Color colorFromString(String s) {
        if (s.contains("RGB")) {
            return returnRGBColor(s);
        }
        return returnKnownColor(s);
    }
}
