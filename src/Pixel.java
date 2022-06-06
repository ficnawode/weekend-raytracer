import java.io.FileWriter;
import java.io.IOException;

public class Pixel {
    //r,g,b are values between 0 and 1
    double r;
    double g;
    double b;
    Pixel(double r, double g, double b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
    Pixel(Pixel p) {
        this.r = p.r;
        this.g = p.g;
        this.b = p.b;
    }

    void set(double r, double g, double b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    void set(Pixel p) {
        this.r = p.r;
        this.g = p.g;
        this.b = p.b;
    }
    public void show() {
        System.out.println("(" + r + ", " + g + ", " + b + ")");
    }

    Pixel multBy(double t) {
        return new Pixel(t*r, t*g, t*b);
    }
    Pixel multBy(Pixel p) {
        return new Pixel(p.r*r, p.g*g, p.b*b);
    }
    Pixel addTo(Pixel p) {
        return new Pixel(r + p.r, g + p.g, b + p.b);
    }

    private double clamp(double x, double min, double max) {
        if (x < min) return min;
        return Math.min(x, max);
    }

    public void write(FileWriter outWriter, int samplesPerPixel) {
        r = Math.sqrt(r/(double)samplesPerPixel);
        g = Math.sqrt(g/(double)samplesPerPixel);
        b = Math.sqrt(b/(double)samplesPerPixel);

        try {
            outWriter.write((int)(256 * clamp(r,0,0.999)) + " " + (int)(256 * clamp(g,0,0.999)) + " " + (int)(256 * clamp(b,0,0.999)) + '\n');
        }
        catch (IOException e) {
            System.out.println("An error occurred while writing the file body.");
            e.printStackTrace();
            System.exit(0);
        }
    }

}
