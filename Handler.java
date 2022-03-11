import java.awt.Graphics;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.awt.PointerInfo;
import java.awt.Point;
import java.awt.MouseInfo;

public class Handler {
    protected ArrayList<GameObject> objects = new ArrayList<GameObject>();
    private PointerInfo p;
    private Point mousePoint;
    private int mouseX;
    private int mouseY;

    public void tick() {
        for(GameObject g : objects) {
            g.tick();
        }
    }

    public void render(Graphics gfx) { 
        //get the current mouse info 
        p = MouseInfo.getPointerInfo();
        mousePoint = p.getLocation();
        this.mouseX = (int)mousePoint.getX();
        this.mouseY = (int)mousePoint.getY();

        for(GameObject g : objects) {
            SolarSystem s = (SolarSystem)g;
            System.out.println(s.inSector.x);
            if(inSector(mouseX, mouseY, s.inSector)) {
                s.isMouseOver = true;
            } else {
                s.isMouseOver = false;
            }
            g.render(gfx);
        }
    }

    public void loadObjects(Vector2n<Integer> uniCoords, Vector2n<Integer> numSectors){
        removeAll();
        Vector2n<Integer> thisSector = new Vector2n<Integer>();
        for(thisSector.x = 0; thisSector.x < numSectors.x; thisSector.x++) {
            for(thisSector.y = 0; thisSector.y < numSectors.y; thisSector.y++) {
                int seedX = uniCoords.x + thisSector.x;
                int seedY = uniCoords.y + thisSector.y;
                this.objects.add(new SolarSystem(thisSector.x * 16 + 8, thisSector.y * 16 + 8, seedX, seedY, thisSector));
            }
        }
    }

    public void addObject(GameObject toAdd) {
        objects.add(toAdd);
    }

    public GameObject removeObject(GameObject toRemove) throws NoSuchElementException {
        for(int i = 0; i < objects.size(); i++) {
            if(objects.get(i).equals(toRemove)){
                return objects.remove(i);
            }
        }
        throw new NoSuchElementException("could not remove non-existant element: " + toRemove);
    }

    public boolean inSector(int x, int y, Vector2n<Integer> sector) {
        if(x > sector.x * 16 && x < (sector.x * 16) + 16) {
            if(y > sector.y * 16 && y < (sector.y * 16) + 16) {
                return true;
            }
        }
        return false;
    }

    public void removeAll() {
        objects.clear();
    }
    
}
