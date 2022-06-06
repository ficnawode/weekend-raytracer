public class Vec3{
    double x;
    double y;
    double z;

    Vec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    Vec3(Vec3 v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public boolean isEqualTo(Vec3 v) {
        return this.x == v.x && this.y == v.y && this.z == v.z;
    }

    double lengthSquared() {
        return x*x + y*y + z*z;
    }

    double length() {
        return Math.sqrt( lengthSquared());
    }

    Vec3 addTo(Vec3 v) { return new Vec3(x + v.x, y + v.y, z + v.z); }

    Vec3 subtractFrom(Vec3 v) {
        return new Vec3(x - v.x, y - v.y, z - v.z);
    }

    Vec3 multBy(double t) {
        return new Vec3(t*x, t*y, t*z);
    }

    double dot(Vec3 v) { return (x * v.x) + (y * v.y) + (z * v.z); }

    Vec3 cross(Vec3 v) {
        return new Vec3(y * v.z - z * v.y, v.x * z - v.z - x, x * v.y - y * v.x);
    }

    Vec3 unitVector() {
        return this.multBy(1/this.length());
    }

    Vec3 random(double a, double b) {
        double len = Math.abs(a-b);
        double r1 = Math.random() * len + a; // move from [0,1] to [a,b]
        double r2 = Math.random() * len + a;
        double r3 = Math.random() * len + a;
        return new Vec3(r1, r2, r3);
    }

    Vec3 randomInUnitSphere() {
        Vec3 p = new Vec3(this);
        while (true) {
            p = p.random(-1,1);
            if (p.lengthSquared() >= 1) continue;
            return p;
        }
    }

    Vec3 randomUnitVector() {
        return this.randomInUnitSphere().unitVector();
    }

    boolean nearZero() {
        final double s = 1e-8;
        return (Math.abs(x) < s) && (Math.abs(y) < s) && (Math.abs(z) < s);
    }

    public void show() {
        System.out.println("[" + x + ", " + y + ", " + z + "]");
    }
}
