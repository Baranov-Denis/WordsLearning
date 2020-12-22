import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class MyButton extends JButton {

    private Color hoverBackgroundColor = MyColors.BUTTON_HOVER;
    private Color pressedBackgroundColor = MyColors.BUTTON_PRESSED;


    public MyButton() {
        this(null);
        this.setPreferredSize(new Dimension(320, 41));
    }


    public MyButton(String text) {

        super(text);

            super.setContentAreaFilled(false);
            this.setPreferredSize(new Dimension(320, 41));
            this.setBorder(new BevelBorder(5));
            this.setBackground(MyColors.BUTTON_COLOR);
            this.setForeground(MyColors.FONT);
            this.setCursor(new Cursor(Cursor.HAND_CURSOR));

    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(pressedBackgroundColor);
        } else if (getModel().isRollover()) {
            g.setColor(hoverBackgroundColor);
        } else {
            g.setColor(getBackground());
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    @Override
    public void setContentAreaFilled(boolean b) {
    }

    public Color getHoverBackgroundColor() {
        return hoverBackgroundColor;
    }

    public void setHoverBackgroundColor(Color hoverBackgroundColor) {
        this.hoverBackgroundColor = hoverBackgroundColor;
    }

    public Color getPressedBackgroundColor() {
        return pressedBackgroundColor;
    }

    public void setPressedBackgroundColor(Color pressedBackgroundColor) {
        this.pressedBackgroundColor = pressedBackgroundColor;
    }
}
