package ADT;

import Particles.System.Solver.Rate;
import Particles.System.Solver.State;

import static Particles.Graphics.Renderer3D.UNIT_SIZE;
import static Particles.Settings.RANDOM_PROVIDER;

public interface Vector extends State, Rate {
    double x();
    double y();
    double z();
    Vector multiply(Vector v);
    Vector multiply(double v);
    Vector add(Vector v);
    default Vector normalize(){
        return this.multiply(1/magnitude());
    }



    static Vector2D zero2D() {
        return new Vector2D(0,0);
    }
    static Vector3D zero3D() { return new Vector3D(0,0,0); }
    static Vector2D random2D(double max) {
        return new Vector2D(RANDOM_PROVIDER.nextDouble(-max,max),RANDOM_PROVIDER.nextDouble(-max,max));
    }
    static Vector3D random3D(double max) {
        return new Vector3D(RANDOM_PROVIDER.nextDouble(-max,max),RANDOM_PROVIDER.nextDouble(-max,max),RANDOM_PROVIDER.nextDouble(-max,max));
    }
    static Vector3D random3D(double min, double max) {
        double x = RANDOM_PROVIDER.nextDouble(min,max);
        double y = RANDOM_PROVIDER.nextDouble(min,max);
        double z = RANDOM_PROVIDER.nextDouble(min,max);
        return new Vector3D (
                RANDOM_PROVIDER.nextDouble() < 0.5 ? x * -1 : x,
                RANDOM_PROVIDER.nextDouble() < 0.5 ? y * -1 : y,
                RANDOM_PROVIDER.nextDouble() < 0.5 ? z * -1 : z
        );
    }
    Vector sub(Vector v);
    double magnitude();
}
