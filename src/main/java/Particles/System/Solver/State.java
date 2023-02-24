package Particles.System.Solver;

import ADT.Vector;

public interface State {
    default State addMul(double dt, Rate dx){
        return (State) ((Vector) this).add(((Vector) dx).multiply(dt));
    }

}
