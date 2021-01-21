package Viewer;

import java.awt.*;

public class MyColors {

    public MyColors(boolean dark){
        if(dark){
            FONT = darkFont;
            BUTTON_COLOR = darkButtons;
            BUTTON_HOVER = darkButtonsHover;
            BUTTON_PRESSED = darkButtonsPressed;
            BACKGROUND = darkBackground;
        }else{
            FONT = lightFont;
            BUTTON_COLOR = lightButtons;
            BUTTON_HOVER = lightButtonsHover;
            BUTTON_PRESSED = lightButtonsPressed;
            BACKGROUND = lightBackground;
        }
    }

    public static void changeTheme(boolean dark){
        if(dark){
            FONT = darkFont;
            BUTTON_COLOR = darkButtons;
            BUTTON_HOVER = darkButtonsHover;
            BUTTON_PRESSED = darkButtonsPressed;
            BACKGROUND = darkBackground;
        }else{
            FONT = lightFont;
            BUTTON_COLOR = lightButtons;
            BUTTON_HOVER = lightButtonsHover;
            BUTTON_PRESSED = lightButtonsPressed;
            BACKGROUND = lightBackground;
        }
    }



    private static final Color red = new Color(255, 100, 100);
    public static final Color MY_RED = red;

    private static final Color myGreen = new Color(100, 255, 100);
    public static final Color MY_GREEN = myGreen;

    public final static Color darkButtons = new Color(50, 50, 50);
    public final static Color lightButtons = new Color(250, 250, 250);

    private final static Color darkButtonsHover = new Color(70, 70, 70);
    private final static Color lightButtonsHover = new Color(230, 230, 230);

    public final static Color darkButtonsPressed = new Color(100, 100, 100);
    public final static Color lightButtonsPressed = new Color(200, 200, 200);


    public final static Color lightFont = new Color(10, 10, 10);
    public final static Color darkFont = new Color(250, 250, 250);

    private final static Color darkBackground = new Color(110, 110, 110);
    private final static Color lightBackground = new Color(170, 170, 170);


    public static Color BUTTON_COLOR;
    public static Color BUTTON_HOVER;
    public static Color BUTTON_PRESSED;
    public static Color FONT;
    public static Color BACKGROUND;

}
