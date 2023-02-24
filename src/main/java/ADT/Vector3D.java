package ADT;

import static Particles.Graphics.Renderer3D.UNIT_SIZE;
import static java.lang.Math.sqrt;

public record Vector3D(double x, double y, double z) implements Vector {

    public static Vector3D cross(Vector a, Vector b) {
        return new Vector3D(
                (a.y() * b.z()) - (a.z() * b.y()),
                (a.z() * b.x()) - (a.x() * b.z()),
                (a.x() * b.y()) - (a.y() * b.x())
        );
    }

    @Override
    public Vector multiply(Vector v) {
        return new Vector3D(x*v.x(), y*v.y(), z*v.z());
    }

    @Override
    public Vector multiply(double v) {
        return new Vector3D(x*v, y*v, z*v);
    }

    @Override
    public Vector add(Vector v) {
        return new Vector3D(x+v.x(), y+v.y(), z+v.z());
    }

    @Override
    public Vector sub(Vector v) {
        return new Vector3D(x-v.x(), y-v.y(), z-v.z());
    }

    @Override
    public double magnitude(){
        return sqrt((x()*x()) + (y()*y()) + (z()*z()));
    }
}
