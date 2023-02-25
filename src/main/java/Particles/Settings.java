package Particles;

import java.awt.*;
import java.util.Random;

public class Settings {

    public static double CURRENT_TIME = 0;
    public static double STEP_SIZE = .05;
    public static double scale = 1.55;
    public static final int SCREEN_WIDTH = 1200;
    public static final int SCREEN_HEIGHT = 800;
    public static final Dimension screen = new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT);


    public static final int NUM_PARTICLES = 400;
    public static final double PARTICLE_RADIUS = 10;
    public static final Color PARTICLE_COLOR = new Color(255, 255, 255, 194);
    public static final Random RANDOM_PROVIDER = new Random();
}
