package Particles.System;

import Particles.Graphics.Renderer;
import Particles.Graphics.Renderer3D;
import Particles.System.Solver.Solver;
import Particles.System.Solver.SolverMethods;

public interface System {
    Solver solver = SolverMethods.rungeKuttaMethod();
    Renderer renderer();
    void start();

    void update();

    void init();
}
