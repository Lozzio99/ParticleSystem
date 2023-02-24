package ADT;

import java.awt.geom.Point2D;

import static Particles.Settings.*;

public class VectorConverter {
    protected static final double zf = 1.05;
    private static final Point2D.Double ORIGIN = new Point2D.Double(screen.width / 2., screen.height / 2.);
    public static void zoomIn() {
        zoomIn(zf);
    }
    public static void zoomOut() {
        zoomOut(zf);
    }

    public static void zoomIn(double f) {
        scale *= f;
    }
    public static void zoomOut(double f) {
        scale /= f;
    }

    public static Vector2D convertVector(final Vector2D point2D) {
        return new Vector2D(ORIGIN.x + (point2D.x() * (scale)), ORIGIN.y - (point2D.y() * scale));
    }


    public static void translate(final double dx, final double dy) {
        ORIGIN.x += dx;
        ORIGIN.y += dy;
    }

    public static Vector2D convertVector(Vector3D point3D) {
        double x3d = point3D.x() * scale;
        double y3d = point3D.y() * scale;
        double depth = point3D.z() * scale;
        double[] newVal = scale(x3d, y3d, depth);
        double x2d = (SCREEN_WIDTH / 2. + newVal[0]);
        double y2d = (SCREEN_HEIGHT / 2. - newVal[1]);
        return new Vector2D(x2d, y2d);
    }

    /**
     * Returns an array of double variables which represent a scale.
     *
     * @param x3d   the x value
     * @param y3d   the y value
     * @param depth the depth of the scene
     * @return an array containing the x,y point coordinates
     */
    private static double[] scale(double x3d, double y3d, double depth) {
        double dist = Math.sqrt(x3d * x3d + y3d * y3d);
        double theta = Math.atan2(y3d, x3d);
        double depth2 = 15 - depth;
        double localScale = Math.abs(1400 / (depth2 + 1400));
        dist *= localScale;
        double[] newVal = new double[2];
        newVal[0] = dist * Math.cos(theta);
        newVal[1] = dist * Math.sin(theta);
        return newVal;
    }

    /**
     * Rotates a point around the X axis.
     *
     * @param p       the p
     * @param CW      the cw
     * @param degrees the degrees
     */
    public static Vector3D rotateAxisX(final Vector3D p, boolean CW, double degrees) {
        if (degrees == 0) return p;
        double radius = Math.sqrt(p.y() * p.y() + p.z() * p.z());
        double theta = Math.atan2(p.z(), p.y());
        theta += 2 * Math.PI / 360 * degrees * (CW ? -1 : 1);
        return new Vector3D(p.x(),radius * Math.cos(theta),radius * Math.sin(theta));
    }

    /**
     * Rotates a point around the Y axis.
     *
     * @param p       the p
     * @param CW      the cw
     * @param degrees the degrees
     */
    public static Vector3D rotateAxisY(Vector3D p, boolean CW, double degrees) {
        if (degrees == 0) return p;
        double radius = Math.sqrt(p.z() * p.z() + p.x() * p.x());
        double theta = Math.atan2(p.z(), p.x());
        theta += 2 * Math.PI / 360 * degrees * (CW ? -1 : 1);
        return new Vector3D(radius * Math.cos(theta),p.y(),radius * Math.sin(theta));
    }

    public static Vector3D rotateAxisZ(Vector3D p, boolean CW, double degrees) {
        if (degrees == 0) return p;
        double radius = Math.sqrt(p.y() * p.y() + p.x() * p.x());
        double theta = Math.atan2(p.y(), p.x());
        theta += 2 * Math.PI / 360 * degrees * (CW ? -1 : 1);
        return new Vector3D(radius * Math.sin(theta), radius * Math.cos(theta),p.z());
    }

}
