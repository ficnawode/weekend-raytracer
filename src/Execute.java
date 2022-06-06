public class Execute {
    public static void main (String[] args) {

        final double pi = 3.14159;
        //final double deg = pi/180;

        final double r = 1.0;
        final double alpha_0 = pi/4;
        //final double alpha_1 = 3*pi/4;
        //final double dAlpha = deg;

        String fileName = "demo" + ".ppm";

        Point3 lookFrom = new Point3(r*Math.cos(alpha_0), -0.2, r*Math.sin(alpha_0));
        Point3 lookAt = new Point3(0, -0.0, 0);
        Vec3 worldUp = new Vec3(0, 10000, 0);

        Image img = new Image(fileName, lookFrom, lookAt, worldUp);
        img.render();


        /*
        int counter = 0;
        for(double i = alpha_0; i< alpha_1; i+= dAlpha) {
            String fileName = "rendered" + counter + ".ppm";

            Point3 lookFrom = new Point3(r*Math.cos(i), -0.2, r*Math.sin(i));
            Point3 lookAt = new Point3(0, -0.0, 0);
            Vec3 worldUp = new Vec3(0, 10000, 0);

            Image img = new Image(fileName, lookFrom, lookAt, worldUp);
            img.render();

            counter++;
        }
        */
    }
}
