public class Point3 extends Vec3 {
    Point3(double x, double y, double z) {
        super(x,y,z);
    }
    Point3(Point3 p) {
        super(p.x,p.y,p.z);
    }
    Point3(Vec3 v) {
        super(v);
    }

    public boolean isEqualTo(Point3 p) {
        return this.x == p.x && this.y == p.y && this.z == p.z;
    }

    public void show() {
        System.out.println("(" + x + ", " + y + ", " + z + ")");
    }
}
