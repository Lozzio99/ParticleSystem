package ADT;

import static java.lang.Math.sqrt;

public record Vector2D(@Override double x,@Override double y) implements Vector {

    public Vector add(Vector v){
        return new Vector2D(x+v.x(), y+v.y());
    }

    public Vector sub(Vector v){
        return new Vector2D(x-v.x(), y-v.y());
    }

    public Vector multiply(Vector v){
        return new Vector2D(x*v.x(), y*v.y());
    }

    @Override
    public double z() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Vector multiply(double v) {
        return new Vector2D(x*v, y*v);
    }


    @Override
    public double magnitude(){
        return sqrt((x()*x()) + (y()*y()));
    }

}
