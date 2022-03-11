import java.awt.Graphics;
import java.awt.Color;

public class SolarSystem extends GameObject{
    private boolean exists;
    private Star star;
    private int procGen = 0;
    protected Vector2n<Integer> inSector;
    protected boolean isMouseOver = false;
    
    public SolarSystem(int screenX, int screenY, int x, int y, Vector2n<Integer> inSector) {
        super(screenX, screenY, ID.STAR_SYSTEM);
        //solar systems do not move so,
        super.dx = 0.0f;
        super.dy = 0.0f;
        //set the current screen sector
        this.inSector = inSector;
        //set the seed based on location
        procGen = (x & 0xFFFF) << 16 | (y & 0xFFFF);
        exists = randInt(1, 20) == 1;

        //create the actual star
        this.star = new Star(randDouble(5.0, 20.0), procGen);
    }
    
    private int randInt(int min, int max) {
        return Rand.Lehmer_32(procGen) % (max - min) + min;
    }

    private double randDouble(double min, double max) {
        return ((double)Rand.Lehmer_32(procGen) / ((double)(0x7FFFFFFF)) * (max - min) + min);
    }

    @Override
    public void tick() {
        //at the moment, solar systems do nothing on tick   
    }

    @Override
    public void render(Graphics gfx) {
        //check if it should be highlighted
        if(isMouseOver) {
            gfx.setColor(Color.white);
            gfx.fillOval(super.x, super.y, (int)this.star.getRadius() + 3, (int)this.star.getRadius() + 3);

        }
        //render the circle representing the star
        if(exists) {
            gfx.setColor(this.star.getColor());
            gfx.fillOval(super.x, super.y, (int)this.star.getRadius(), (int)this.star.getRadius());
        }
    }
}
