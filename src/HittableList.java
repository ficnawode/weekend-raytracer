import java.util.Vector;

public class HittableList extends Hittable {

    Vector<Hittable> objects;

    HittableList() {
        objects = new Vector<Hittable>();
    }
    HittableList(Hittable object) {
        objects = new Vector<Hittable>();
        objects.add(object);
    }

    void clear() { objects.clear(); }
    void add(Hittable object) { objects.add(object); }

    boolean hit (Ray r, double t_min, double t_max, HitRecord rec){
        HitRecord tempRec = new HitRecord(rec);
        boolean hitAnything = false;
        double closestSoFar = t_max;

        for (Hittable object : objects) {
            if(object.hit(r, t_min, closestSoFar, tempRec)) {
                closestSoFar = tempRec.t;
                rec.t = tempRec.t;
                rec.normal = tempRec.normal;
                rec.p = tempRec.p;
                rec.frontFace = tempRec.frontFace;
                rec.material = tempRec.material;
                hitAnything = true;
            }
        }
        return hitAnything;
    }
}
