package Particles.System.Solver;

import Particles.System.Gravity;
import ADT.Vector;
import Particles.System.Particle;
import org.tinspin.index.kdtree.KDIterator;
import org.tinspin.index.kdtree.KDTree;

import java.util.Iterator;
import java.util.List;

import static Particles.Settings.CURRENT_TIME;
import static Particles.Settings.STEP_SIZE;

@FunctionalInterface
public interface Solver {
    default State[] solve(ODE f, State y0, double[] ts) {
        State[] states = new State[ts.length];
        states[0] = y0;
        for (int i = 1; i < ts.length - 1; i++) {
            states[i] = this.step(f, ts[i], states[i - 1], ts[i] - ts[i - 1]);
        }
        return states;
    }
    default State[] solve(ODE f, State y0, double tf, double h) {
        State[] states = new State[(int) (tf / h) + 2];
        double t = 0;
        states[0] = y0;
        int i = 1;
        while (t < tf) {
            states[i] = this.step(f, t, states[i - 1], h);
            t += h;
            i++;
        }
        states[states.length - 1] = this.step(f, tf - t, states[states.length - 1], tf - t);
        return states;
    }

    State step(ODE f, double t, State y, double dt);

    default void step(List<Particle> particles) {
        particles.parallelStream().forEach(p -> {
            Vector vel = (Vector) this.step(Gravity.evaluateList(p, particles),CURRENT_TIME,p.getVelocity(),STEP_SIZE);
            Vector pos = (Vector) this.step((t,y) -> vel,CURRENT_TIME,p.getPosition(),STEP_SIZE);
            p.setPosition(pos);
            p.setVelocity(vel);
        });
        CURRENT_TIME += STEP_SIZE;
    }

//    default void step(List<Particle> particles) {
//        KDTree<Particle> tree = KDTree.create(3);
//        particles.parallelStream().forEach(p -> {
//            Vector vp = p.getPosition();
//            tree.insert(new double[]{vp.x(),vp.y(),vp.z()}, p);
//        });
//
//        particles.parallelStream().forEach(p -> {
//            Vector v1 = p.getPosition().normalize().multiply(500);
//            Vector v2 = p.getPosition().normalize().multiply(-500);
//            Vector vel = (Vector) this.step(Gravity.evaluateIterator(p, tree.query(new double[]{v1.x(),v1.y(),v1.z()}, new double[]{v2.x(),v2.y(),v2.z()})),CURRENT_TIME,p.getVelocity(),STEP_SIZE);
//            Vector pos = (Vector) this.step((t,y) -> vel,CURRENT_TIME,p.getPosition(),STEP_SIZE);
//            p.setPosition(pos);
//            p.setVelocity(vel);
//        });
//        CURRENT_TIME += STEP_SIZE;
//    }
}
