public class Ray {
    Point3 origin;
    Vec3 direction;

    Ray(Point3 o, Vec3 d) {
        origin = new Point3(o);
        direction = new Vec3(d);
    }

    void set(Point3 o, Vec3 d){
        origin = new Point3(o);
        direction = new Vec3(d);
    }

    private double hitSphere(Point3 center, double radius, Ray r) {
        Vec3 oc = r.origin.subtractFrom(center);
        double a = r.direction.lengthSquared();
        double half_b = oc.dot(r.direction);
        double c = oc.lengthSquared() - radius*radius;
        double delta = half_b*half_b - a*c;
        if (delta < 0) {
            return -1.0;
        } else {
            return (-half_b - Math.sqrt(delta) ) / a;
        }
    }

    public Pixel color(Hittable world, int depth) {
        HitRecord rec = new HitRecord(new Point3(0,0,0), new Vec3(0,0,0), 0, new Material());

        if (depth <= 0)
            return new Pixel(0,0,0);

        if(world.hit(this,0.001,Double.POSITIVE_INFINITY, rec)) {
            Ray scattered = new Ray(new Point3(0,0,0),new Vec3(0,0,0));
            Pixel attenuation = new Pixel(0,0,0);
            if (rec.material.scatter(this, rec, attenuation, scattered))
                return attenuation.multBy(scattered.color( world, depth-1));
            return new Pixel(0,0,0);
        }
        //if it doesnt find a sphere, print background
        Vec3 unitDirection = direction.unitVector();
        double t = 0.5*(unitDirection.y + 1.0);
        return new Pixel(1-t,1-t,1-t).addTo(new Pixel(0.5,0.7,1.0).multBy(t));
        //(1.0-t)*color(1.0, 1.0, 1.0) + t*color(0.5, 0.7, 1.0);
    }
    public static Vec3 randomInUnitSphere() {
        Vec3 p = new Vec3(0,0,0);
        while (true) {
            p = p.random(-1,1);
            if (p.lengthSquared() >= 1) continue;
            return p;
        }
    }
    Point3 at(double t) {
        return new Point3(origin.x + t*direction.x, origin.y + t*direction.y, origin.z + t*direction.z);
    }
}
