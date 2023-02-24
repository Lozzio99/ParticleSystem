
package Particles.Graphics;

import java.awt.event.*;

public class MouseInput extends MouseMotionAdapter implements MouseListener, MouseWheelListener {
    public static double mouseSensitivity = 8;

    // Variables
    private int mouseX = -1;
    private int mouseY = -1;
    private int mouseB = -1;
    private int scroll = 0;

    public int getX() {
        return this.mouseX;
    }
    public int getY() {
        return this.mouseY;
    }
    public boolean isScrollingUp() {
        return this.scroll == -1;
    }
    public boolean isScrollingDown() {
        return this.scroll == 1;
    }

    public void reset(){
        this.resetScroll();
        this.resetButton();
    }
    public void resetScroll() {
        this.scroll = 0;
    }
    public void resetButton() {
        this.mouseB = -1;
    }


    @Override public void mouseWheelMoved(MouseWheelEvent event) {
        scroll = event.getWheelRotation();
    }
    @Override public void mouseDragged(MouseEvent event) {
        this.mouseX = event.getX();
        this.mouseY = event.getY();
    }

    @Override public void mouseMoved(MouseEvent event) {
        this.mouseX = event.getX();
        this.mouseY = event.getY();
    }
    @Override public void mouseClicked(MouseEvent event) {
    }
    @Override public void mouseEntered(MouseEvent event) {
        // TODO Auto-generated method stub
    }
    @Override public void mouseExited(MouseEvent event) {
        // TODO Auto-generated method stub
    }


    @Override public void mousePressed(MouseEvent event) {
        this.mouseB = event.getButton();
    }

    @Override public void mouseReleased(MouseEvent event) {
        this.mouseB = -1;
    }


    public ClickType getButton() {
        return switch (this.mouseB) {
            case 1 -> ClickType.LeftClick;
            case 2 -> ClickType.ScrollClick;
            case 3 -> ClickType.RightClick;
            case 4 -> ClickType.BackPage;
            case 5 -> ClickType.ForwardPage;
            default -> ClickType.Unknown;
        };
    }
    public enum ClickType {
        Unknown,
        LeftClick,
        ScrollClick,
        RightClick,
        ForwardPage,
        BackPage
    }
}
