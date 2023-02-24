package Particles.System;

import ADT.Vector;
import Particles.System.Particle;
import Particles.System.Solver.ODE;
import Particles.System.Solver.Rate;
import org.tinspin.index.kdtree.KDIterator;
import org.tinspin.index.kdtree.Node;

import java.util.Iterator;
import java.util.List;

import static java.lang.Double.MIN_VALUE;
import static java.lang.Math.*;

public class Gravity {
    public static final double G = 9.8;

    // set the acceleration based on gravity
    public static ODE evaluateList(Particle p1, List<Particle> bodies) {
        return (t,y) -> {
            Vector acc = Vector.zero3D();
            for (Particle p2 : bodies) {
                acc = eval(p1, acc, p2);
            }
            return ((Rate) acc);
        };
    }

    public static ODE evaluateIterator(Particle p1, KDIterator<Particle> bodies) {
        return (t,y) -> {
            Vector acc = Vector.zero3D();
            while (bodies.hasNext()) {
                Node<Particle> p2 = bodies.next();
                acc = eval(p1, acc, p2.value());
            }
            return ((Rate) acc);
        };
    }

    private static Vector eval(Particle p1, Vector acc, Particle p2) {
        if (p1 == p2) return acc;
        Vector dist = p2.getPosition().sub(p1.getPosition());
        double M = G * p1.getMass() * p2.getMass();
        double dist3 = pow(dist.magnitude(),3);
        dist3 = max(MIN_VALUE, dist3);
        acc = acc.add(dist.multiply(M/dist3));
        return acc;
    }
}
