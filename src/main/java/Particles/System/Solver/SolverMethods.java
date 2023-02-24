package Particles.System.Solver;

public enum SolverMethods {
    EULER(1, eulerMethod()),
    MID_POINT(2, midPointMethod()),
    RUNGE_KUTTA(4, rungeKuttaMethod());
    private final int order;
    private final Solver method;

    SolverMethods(int order, Solver method) {
        this.order = order;
        this.method = method;
    }

    public static Solver eulerMethod() {
        return (f, t, y, dt) -> y.addMul(dt, f.call(t, y));
    }

    public static Solver midPointMethod() {
        return (f, t, y, dt) -> {
            Rate k1, k2;
            k1 = f.call(t, y);
            k2 = f.call(t + (dt / 2), y.addMul(dt / 2, k1));
            return y.addMul(dt, k2);
        };
    }

    public static Solver rungeKuttaMethod() {
        return (f, t, y, dt) -> {
            Rate k1, k2, k3, k4;
            k1 = f.call(t, y);
            k2 = f.call(t + (dt / 2), y.addMul(dt / 2, k1));
            k3 = f.call(t + (dt / 2), y.addMul(dt / 2, k2));
            k4 = f.call(t + dt, y.addMul(dt, k3));
            return y.addMul(dt / 6, k1)
                    .addMul(dt / 3, k2)
                    .addMul(dt / 3, k3)
                    .addMul(dt / 6, k4);
        };
    }

    public Solver getMethod() {
        return method;
    }

    public int getOrder() {
        return order;
    }
}
