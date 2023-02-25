package Particles.System;

import ADT.*;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Iterator;
import java.util.Queue;

import static Particles.Graphics.MouseInput.mouseSensitivity;
import static Particles.Graphics.Renderer3D.*;
import static ADT.VectorConverter.*;
import static Particles.Settings.*;
import static java.lang.Math.*;

public class Particle {
    private Vector position, velocity;
    protected double mass;
    private static final double INITIAL_POSITION_RADIUS = UNIT_SIZE * 0.4;
    protected final DoublyLinkedList trajectory = new DoublyLinkedList(50);

    public Particle() {
        this.setPosition(this.randomInitialPosition());
        this.velocity = this.randomInitialVelocity().multiply(.1);
        this.mass = RANDOM_PROVIDER.nextDouble(10,30);
    }

    public Particle(Vector position, Vector velocity, double mass) {
        this.setPosition(position);
        this.velocity = velocity;
        this.mass = mass;
    }

    protected Vector randomInitialPosition(){
        double x = RANDOM_PROVIDER.nextGaussian(0, .1);
        double y = RANDOM_PROVIDER.nextGaussian(0, .3);
        double z = RANDOM_PROVIDER.nextGaussian(0, .3);
        if (x == 0 && y == 0 && z == 0) return randomInitialPosition();
        double sq = 1 / (sqrt(pow(x,2)  + pow(y,2) + pow(z,2)));
        double rad = RANDOM_PROVIDER.nextDouble( INITIAL_POSITION_RADIUS * 0.4, INITIAL_POSITION_RADIUS * 0.5);
        double r = sq * rad;
        return new Vector3D(x*r, y*r, z*r);
    }

    protected Vector randomInitialVelocity() {
        Vector v = this.getPosition().multiply(-.1);
        double norm = sqrt((v.x() * v.x()) + (v.y() * v.y()));
        double d = norm * 1e-2;
        return new Vector3D(getPosition().x() + (v.y() * d),  0, getPosition().y() - (v.x() * d));
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
        this.trajectory.addNode(position);
    }

    public Vector getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }


    public double getMass() {
        return mass;
    }

    public void render(Graphics2D g) {
        renderTrajectory(g);
        g.setColor(PARTICLE_COLOR);
        g.fill(planetShape(getPosition()));
    }

    private void renderTrajectory(Graphics2D g) {
        Iterator<Node> iterator = this.trajectory.iterator();
        Vector2D a = convertPosition(this.trajectory.get().getItem());
        int i = 1;
        while(iterator.hasNext()) {
            Vector2D b = convertPosition(iterator.next().getItem());
            float alpha = min(1, (float) (1. / (this.trajectory.capacity-1) * i++));
            g.setColor(new Color(1, 1, 1, alpha));
            g.draw(new Line2D.Double(a.x(), a.y(), b.x(), b.y()));
            a = b;
        }
    }

    protected Ellipse2D.Double planetShape(Vector position) {
        Vector2D p = convertPosition(position);
        double r = PARTICLE_RADIUS * (mass * 0.05) * scale;
        return new Ellipse2D.Double(p.x() - r, p.y() - r, r * 2, r * 2);
    }

    private static Vector2D convertPosition(Vector position) {
        Vector2D p = null;
        if (position instanceof Vector3D v3) {
            v3 = rotateAxisX(v3,false,totalDY / mouseSensitivity);
            v3 = rotateAxisY(v3,false,totalDX / mouseSensitivity);
            p = convertVector(v3);
        }
        else if (position instanceof Vector2D v2) p = convertVector(v2);
        assert(p != null);
        return p;
    }


    public static class Massive extends Particle {
        public Massive(Vector pos, Vector vel) {
            super(pos, vel, 5e3);
        }

        @Override
        protected Ellipse2D.Double planetShape(Vector position) {
            Vector2D p = convertPosition(position);
            double r = PARTICLE_RADIUS * 10 * scale;
            return new Ellipse2D.Double(p.x() - r, p.y() - r, r * 2, r * 2);
        }

        public void render(Graphics2D g) {
            this.renderTrajectory(g);
            g.setColor(Color.RED);
            g.fill(planetShape(getPosition()));
            g.setColor(PARTICLE_COLOR);
        }

        private void renderTrajectory(Graphics2D g) {
            g.setStroke(new BasicStroke(0.3f));
            Iterator<Node> iterator = this.trajectory.iterator();
            Vector2D a = convertPosition(this.trajectory.get().getItem());
            int i = 1;
            while(iterator.hasNext()) {
                Vector2D b = convertPosition(iterator.next().getItem());
                float alpha = min(1, (float) (1. / (this.trajectory.capacity-1) * i++));
                g.setColor(new Color(1, 0, 0, alpha));
                g.draw(new Line2D.Double(a.x(), a.y(), b.x(), b.y()));
                a = b;
            }
        }
    }

}
