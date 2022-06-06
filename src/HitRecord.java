public class HitRecord {
    Point3 p;
    Vec3 normal;
    Material material;
    double t;
    boolean frontFace;

    HitRecord(Point3 p_init, Vec3 normal_init, double t_init, Material mat) {
        p = new Point3(p_init);
        normal = new Vec3(normal_init);
        t = t_init;
        material = mat;
    }
    HitRecord(HitRecord h) {
        p = new Point3(h.p);
        normal = new Vec3(h.normal);
        t = h.t;
        material = h.material;
    }

    public void setFaceNormal(Ray r, Vec3 outwardNormal) {
        frontFace = r.direction.dot(outwardNormal) < 0;
        if(frontFace)
            normal = outwardNormal;
        else
            normal = outwardNormal.multBy(-1);
    }
}
