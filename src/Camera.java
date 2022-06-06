public class Camera {
    double viewportHeight;
    double viewportWidth;
    double focalLength;
    double verticalFov;

    Point3 origin;
    Vec3 horizontal;
    Vec3 vertical;
    Point3 lowerLeftCorner;

    Camera(Point3 lookFrom, Point3 lookAt, Vec3 worldUp, double aspectRatio, double vFov) {
        verticalFov = vFov;
        double h = Math.tan(vFov/2);
        viewportHeight = 2.0 * h;
        viewportWidth = aspectRatio * viewportHeight;

        focalLength = 1.0;

        Vec3 w = (lookFrom.subtractFrom(lookAt)).unitVector();
        Vec3 u = (worldUp.cross(w)).unitVector();
        Vec3 v = w.cross(u).unitVector();

        origin = lookFrom;
        horizontal = u.multBy(viewportWidth);
        vertical = v.multBy(viewportHeight);
        lowerLeftCorner = new Point3(origin.subtractFrom(horizontal.multBy(0.5)).subtractFrom(vertical.multBy(0.5)).subtractFrom(w));
    }

    public Ray getRay(double u, double v){
        return new Ray(origin, lowerLeftCorner.addTo(horizontal.multBy(u)).addTo(vertical.multBy(v)).subtractFrom(origin));
    }
}
