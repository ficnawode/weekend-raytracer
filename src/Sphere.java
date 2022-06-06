public class Sphere extends Hittable {
    Point3 center;
    double radius;
    Material material;

    Sphere(Point3 cen, double r, Material mat) {
        center = new Point3(cen);
        radius = r;
        material = mat;
    }
    Sphere(Sphere S) {
        center = new Point3(S.center);
        radius = S.radius;
        material = S.material;
    }

    public boolean hit(Ray r, double t_min, double t_max, HitRecord rec) {
        Vec3 oc = r.origin.subtractFrom(center);
        double a = r.direction.lengthSquared();
        double half_b = oc.dot( r.direction);
        double c = oc.lengthSquared() - radius*radius;

        double discriminant = half_b*half_b - a*c;
        if (discriminant < 0)
            return false;
        double sqrtd = Math.sqrt(discriminant);

        // Find the nearest root that lies in the acceptable range.
        double root = (-half_b - sqrtd) / a;
        if (root < t_min || t_max < root) {
            root = (-half_b + sqrtd) / a;
            if (root < t_min || t_max < root)
                return false;
        }

        rec.t = root;
        rec.p = r.at(rec.t);
        rec.normal = (rec.p.subtractFrom(center)).multBy(1/radius);

        Vec3 outwardNormal = rec.p.subtractFrom(center).multBy(1/radius);
        rec.setFaceNormal(r, outwardNormal);

        rec.material = material;

        return true;
    }
}
