package Particles.Graphics;
import ADT.Vector2D;
import ADT.Vector3D;
import Particles.System.Particle;

import javax.swing.*;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import static Particles.Graphics.MouseInput.mouseSensitivity;
import static ADT.VectorConverter.*;
import static Particles.Settings.*;
import static java.lang.Math.max;

public class Renderer3D implements Renderer {
    protected static int initialX;
    protected static int initialY;
    protected static int x;
    protected static int y;
    public static int dx;
    public static int dy;
    public static int totalDX;
    public static int totalDY;
    public MouseInput mouse;
    public final static int UNIT_SIZE = max(SCREEN_WIDTH,SCREEN_HEIGHT) * 4;
    protected static Vector3D left = new Vector3D(-UNIT_SIZE, 0, 0),
            right = new Vector3D(UNIT_SIZE, 0, 0),
            top = new Vector3D(0, UNIT_SIZE, 0),
            bottom = new Vector3D(0, -UNIT_SIZE, 0),
            front = new Vector3D(0, 0, UNIT_SIZE),
            rear = new Vector3D(0, 0, -UNIT_SIZE);


    static {
        scene.rotateOnAxisX(10);
        scene.rotateOnAxisY(10);
        scene.rotateOnAxisZ(0.5);
        zoomOut(10);
    }

    public void mouseUpdate() {
        x = mouse.getX();
        y = mouse.getY();
        if (mouse.getButton() == MouseInput.ClickType.LeftClick) {
            dx = x - initialX;
            totalDX += dx;
            scene.rotateOnAxisY( dx / mouseSensitivity);
        } else if (mouse.getButton() == MouseInput.ClickType.RightClick) {
            dy = y - initialY;
            totalDY += dy;
            scene.rotateOnAxisX(dy / mouseSensitivity);
        } else if (mouse.isScrollingUp()) {
            zoomIn();
        } else if (mouse.isScrollingDown()) {
            zoomOut();
        }
    }


    @Override
    public void render(List<?> objects) {
        scene.update(objects);
        scene.repaint();
    }

    @Override
    public void init() {
        frame.setSize(screen);
        scene.setPreferredSize(screen);
        frame.add(scene);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setFocusable(true);
        frame.setVisible(true);
        this.addMouseControl();
    }

    public void addMouseControl() {
        this.mouse = new MouseInput();
        frame.addMouseMotionListener(mouse);
        frame.addMouseListener(mouse);
        frame.addMouseWheelListener(mouse);
    }

    public void mouseReset() {
        initialX = x;
        initialY = y;
        mouse.resetScroll();
    }

    private record Painter(List<?> list,Graphics2D g) implements Runnable {

        @Override
        public void run() {
            list.forEach(o -> {
                if (o instanceof Particle p) {
                    p.render(g);
                }
            });
        }
    }

    public static class Scene extends JPanel {


        private List<?> objects = new ArrayList<>();

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;
            g2.setColor(new Color(0, 0, 0, 165));
            g2.fill3DRect(0,0,SCREEN_WIDTH,SCREEN_HEIGHT,false);
            this.renderAxes(g2);
            g2.setColor(PARTICLE_COLOR);
            this.objects.forEach(o -> {
                if (o instanceof Particle p) {
                    p.render(g2);
                }
            });

        }

        public static <T> Stream<List<T>> chunked(Stream<T> stream, int chunkSize) {
            AtomicInteger index = new AtomicInteger(0);

            return stream.collect(Collectors.groupingBy(x -> index.getAndIncrement() / chunkSize))
                    .entrySet().stream()
                    .sorted(Map.Entry.comparingByKey()).map(Map.Entry::getValue);
        }

        private void renderAxes(Graphics2D g) {
            g.setColor(Color.WHITE);
            Vector2D left = convertVector(Renderer3D.left);
            Vector2D right = convertVector(Renderer3D.right);
            Vector2D top = convertVector(Renderer3D.top);
            Vector2D bottom = convertVector(Renderer3D.bottom);
            Vector2D rear = convertVector(Renderer3D.rear);
            Vector2D front = convertVector(Renderer3D.front);
            g.draw(new Line2D.Double(left.x(),left.y(),right.x(),right.y()));
            g.draw(new Line2D.Double(top.x(),top.y(),bottom.x(),bottom.y()));
            g.draw(new Line2D.Double(rear.x(),rear.y(),front.x(),front.y()));
        }


        public void rotateOnAxisY(double theta) {
            top = rotateAxisY(top, false, theta);
            bottom = rotateAxisY(bottom, false, theta);
            left = rotateAxisY(left, false, theta);
            right = rotateAxisY(right, false, theta);
            rear = rotateAxisY(rear, false, theta);
            front = rotateAxisY(front, false, theta);
        }

        public void rotateOnAxisX(double theta) {
            top = rotateAxisX(top, false, theta);
            bottom = rotateAxisX(bottom, false, theta);
            left = rotateAxisX(left, false, theta);
            right = rotateAxisX(right, false, theta);
            rear = rotateAxisX(rear, false, theta);
            front = rotateAxisX(front, false, theta);
        }

        public void rotateOnAxisZ(double theta) {
            top = rotateAxisZ(top, false, theta);
            bottom = rotateAxisZ(bottom, false, theta);
            left = rotateAxisZ(left, false, theta);
            right = rotateAxisZ(right, false, theta);
            rear = rotateAxisZ(rear, false, theta);
            front = rotateAxisZ(front, false, theta);
        }

        public void update(List<?> objects) {
            this.objects = objects;
        }
    }
}
