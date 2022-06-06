public class Lambertian extends Material{
    Pixel albedo;

    Lambertian(Pixel a){
        albedo = a;
    }

    public boolean scatter(Ray r_in, HitRecord rec, Pixel attenuation, Ray scattered) {
        Vec3 scatter_direction = rec.normal.addTo(rec.normal.randomUnitVector());

        if (scatter_direction.nearZero())
            scatter_direction = rec.normal;

        scattered.set(rec.p, scatter_direction);
        attenuation.set(albedo);

        return true;
    }

}
