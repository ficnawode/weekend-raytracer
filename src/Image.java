import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;




public class Image {

    //Set Global image parameters

    final double aspectRatio = 16.0/9.0;
    final int width = 400;
    final int height = (int)(width/aspectRatio);
    final int samplesPerPixel = 100;
    final int maxDepth = 60;

    final double verticalFov = 3.14159/2;   //camera FOV
    Point3 lookFrom;
    Point3 lookAt;
    Vec3 worldUp;

    String fileName;
    FileWriter outWriter;



    Image(String FileName, Point3 LookFrom, Point3 LookAt, Vec3 WorldUp) {
        fileName = FileName;
        lookAt = LookAt;
        lookFrom = LookFrom;
        worldUp = WorldUp;

        try {
            outWriter = new FileWriter(fileName);
        }
        catch (IOException e) {
            System.out.println("An error occurred while opening the file.");
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void render() {
        try {
            outWriter.write("P3\n" + width +' '+ height + "\n255\n");
        }
        catch (IOException e) {
            System.out.println("An error occurred while writing the file header.");
            e.printStackTrace();
            System.exit(0);
        }

        double u;
        double v;

        HittableList world = new HittableList();


        Material material_ground = new Lambertian(new Pixel(0.7, 0.7, 0.7));
        Material material_lambertian = new Lambertian(new Pixel(0.2, 0.9, 0.2));
        Material material_center = new Dielectric(1.5, 0.2);
        Material material_left   = new Metal(new Pixel(0.8, 0.8, 0.8), 0);
        Material material_right  = new Metal(new Pixel(0.9, 0.0, 0.0), 0.1);

        world.add(new Sphere(new Point3( 0.0, -1000.5, -1.0), 1000.0, material_ground));


        world.add(new Sphere(new Point3( 0.0,    0.0, 0.0),   0.5, material_lambertian));
        //world.add(new Sphere(new Point3(-1.0,    0.0, 0.0),   0.5, material_left));
        //world.add(new Sphere(new Point3( 1.0,    0.0, 0.0),   0.5, material_right));

        /*
        Material small_red      = new Metal(new Pixel(0.9, 0.2, 0.3), 0.9);
        Material small_green    = new Metal(new Pixel(0.2, 0.9, 0.3), 0.9);
        Material small_blue     = new Metal(new Pixel(0.3, 0.2, 0.9), 0.9);
        Material small_glass    = new Dielectric(1.5, 0.1);
        Material small_glass2   = new Dielectric(1.5, 0.2);
        Material small_mirror   = new Metal(new Pixel(0.8, 0.6, 0.2), 0.9);

        Vector<Material> materials = new Vector<Material>();
        materials.add(small_red      );
        materials.add(small_green    );
        materials.add(small_blue     );
        materials.add(small_glass    );
        materials.add(small_glass2   );
        materials.add(small_mirror   );

        for (double i = -1 ; i < 1; i += 0.2 + 0.2* Math.abs(Math.sin(1234*i))) {
            for (double j = -1; j < -0.2; j += 0.2 + 0.2 * Math.abs(Math.sin(1234 * j)))
                world.add(new Sphere(new Point3(i, -0.4, j), 0.1, materials.get(1000 * (int) (1000 * (Math.abs(Math.sin(i + j)))) % 6)));
            for (double j = 1; j > 0.2; j -= 0.2 + 0.2 * Math.abs(Math.sin(1234 * j)))
                world.add(new Sphere(new Point3(i, -0.4, j), 0.1, materials.get(1000 * (int) (1000 * (Math.abs(Math.sin(i + j)))) % 6)));
        }
        */



        Camera cam = new Camera(lookFrom, lookAt, worldUp, aspectRatio, verticalFov);

        for(int j = height; j>0; j--) {
            System.out.println("Scanlines remaining: " + j);

            for(int i = 0; i<width; i++) {
                Pixel pixel = new Pixel(0,0,0);

                for(int s = 0; s<samplesPerPixel; s++) {
                    u = (i + Math.random()) / (double)(width - 1);
                    v = (j + Math.random()) / (double)(height - 1);

                    Ray ray = cam.getRay(u, v);
                    pixel = ray.color(world, maxDepth).addTo(pixel);
                }
                pixel.write(outWriter, samplesPerPixel);
            }
        }
        try {
            outWriter.close();
        } catch (IOException e ) {
            System.exit(0);
        }
    }
}
