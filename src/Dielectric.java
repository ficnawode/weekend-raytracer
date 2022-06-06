public class Dielectric extends Material{

    double schlickReflectionOffset;
    double refractionIndex; // Index of Refraction

    Dielectric(double RefractionIndex, double SchlickReflectionOffset) {
        refractionIndex = RefractionIndex;
        schlickReflectionOffset = SchlickReflectionOffset;
    }

    Vec3 refract(Vec3 uv, Vec3 n, double etaIOverEtaT) {
        double cos_theta = Math.min(uv.multBy(-1).dot(n), 1.0);
        Vec3 r_outPerpendicular =  uv.addTo(n.multBy(cos_theta)).multBy(etaIOverEtaT);
        Vec3 r_outParallel = n.multBy(-Math.sqrt(Math.abs(1.0 - r_outPerpendicular.lengthSquared())));
        return r_outPerpendicular.addTo(r_outParallel);
    }

    Vec3 reflect(Vec3 v, Vec3 u) { return v.subtractFrom(u.multBy(2*v.dot(u))); }

    double reflectance(double cosine, double ref_idx) {
        // Use Schlick's approximation for reflectance.
        double r0 = (1-ref_idx) / (1+ref_idx);
        r0 = r0*r0;
        return r0 + (1-r0)*Math.pow((1 - cosine),5) + schlickReflectionOffset;
    }

    public boolean scatter(Ray r_in, HitRecord rec, Pixel attenuation, Ray scattered) {
        attenuation.set(1.0, 1.0, 1.0);
        double refractionRatio = rec.frontFace ? (1.0/refractionIndex) : refractionIndex;

        Vec3 unitDirection = r_in.direction.unitVector();


        double cosTheta = Math.min(unitDirection.multBy(-1).dot(rec.normal), 1.0);
        double sinTheta = Math.sqrt(1.0 - cosTheta*cosTheta);

        boolean cannot_refract = refractionRatio * sinTheta > 1.0;
        Vec3 direction;

        if (cannot_refract || reflectance(cosTheta, refractionRatio) > Math.random())
            direction = reflect(unitDirection, rec.normal);
        else
            direction = refract(unitDirection, rec.normal, refractionRatio);

        scattered.set(rec.p, direction);
        return true;
    }
}
