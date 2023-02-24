package Particles.System.Solver;
@FunctionalInterface
public interface ODE {
    Rate call(double t, State y);
}
