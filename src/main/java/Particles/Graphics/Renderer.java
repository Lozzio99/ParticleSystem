package Particles.Graphics;

import javax.swing.*;
import java.util.List;

public interface Renderer {
    JFrame frame = new JFrame();
    Renderer3D.Scene scene = new Renderer3D.Scene();
    void init();
    void mouseUpdate();
    void render(List<?> objects);
    void mouseReset();
}
