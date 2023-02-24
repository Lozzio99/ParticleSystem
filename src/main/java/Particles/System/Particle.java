package Particles.System;

import ADT.Vector;
import ADT.Vector2D;
import ADT.Vector3D;

import java.awt.*;
import java.awt.geom.Ellipse2D;

import static Particles.Graphics.MouseInput.mouseSensitivity;
import static Particles.Graphics.Renderer3D.*;
import static ADT.VectorConverter.*;
import static Particles.Settings.*;
import static java.lang.Math.*;

public class Particle {
    private Vector position, velocity;
    private final int id;
    private final double mass;
    private static final double INITIAL_POSITION_RADIUS = UNIT_SIZE * 0.4;

    public Particle(int id) {
        this.id = id;
        this.position = this.randomInitialPosition();
//        this.velocity = Vector.zero3D();
        this.velocity = this.randomInitialVelocity();
        this.mass = RANDOM_PROVIDER.nextDouble(10,15);
    }

    protected Vector randomInitialPosition(){
        double x = RANDOM_PROVIDER.nextGaussian(0, .2);
        double y = RANDOM_PROVIDER.nextGaussian(0, .1);
        double z = RANDOM_PROVIDER.nextGaussian(0, .2);
        if (x == 0 && y == 0 && z == 0) return randomInitialPosition();
        double sq = 1 / (sqrt(pow(x,2)  + pow(y,2) + pow(z,2)));
        double rad = RANDOM_PROVIDER.nextDouble( INITIAL_POSITION_RADIUS * 0.2, INITIAL_POSITION_RADIUS * 0.25);
        double r = sq * rad;
        return new Vector3D(x*r, y*r, z*r);
    }

    protected Vector randomInitialVelocity() {
        Vector v = this.getPosition().multiply(-1);
        double norm = sqrt((v.x() * v.x()) + (v.y() * v.y()));
        double d = norm * 1e-2;
        return new Vector3D(getPosition().x() + (v.y() * d),  getPosition().y() - (v.x() * d), 0);
    }

    protected Particle() {
        this.id = -1;
        this.position = Vector.zero3D();
        this.velocity = Vector.zero3D();
        this.mass = 5e7;
    }

    public int getId() {
        return id;
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
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
        g.fill(planetShape(getPosition()));
    }

    protected Ellipse2D.Double planetShape(Vector position) {
        Vector2D p = convertPosition(position);
        double r = PARTICLE_RADIUS * (mass * 0.1) * scale;
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
        public Massive() {
            super();
        }

        @Override
        protected Ellipse2D.Double planetShape(Vector position) {
            Vector2D p = convertPosition(position);
            double r = PARTICLE_RADIUS * 10 * scale;
            return new Ellipse2D.Double(p.x() - r, p.y() - r, r * 2, r * 2);
        }

        public void render(Graphics2D g) {
            g.setColor(Color.RED);
            g.fill(planetShape(getPosition()));
            g.setColor(PARTICLE_COLOR);
        }
    }

}
