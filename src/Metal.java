public class Metal extends Material {

    Pixel albedo;
    double fuzz;

    Metal(Pixel Albedo, double Fuzz) {
        albedo = Albedo;
        fuzz = Fuzz;
    }

    Vec3 reflect(Vec3 v, Vec3 u) { return v.subtractFrom(u.multBy(2*v.dot(u))); }

    public boolean scatter(Ray r_in, HitRecord rec, Pixel attenuation, Ray scattered) {
        Vec3 reflected = this.reflect(r_in.direction.unitVector(), rec.normal);
        scattered.set(rec.p, reflected.addTo(rec.p.randomInUnitSphere().multBy(fuzz)));
        //scattered = ray(rec.p, reflected + fuzz*random_in_unit_sphere());

        attenuation.set(albedo);
        return (scattered.direction.dot( rec.normal) > 0);
    }

}
