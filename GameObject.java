import java.awt.Graphics;

public abstract class GameObject {
    protected int x;
    protected int y;
    protected float dx;
    protected float dy;
    protected ID id;

    public GameObject(int x, int y, ID id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public abstract void tick();
    public abstract void render(Graphics gfx);

    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public float getDx(){
        return this.dx;
    }
    
    public float getDy(){
        return this.dy;
    }

    public ID getID(){
        return this.id;
    }

    public void setDx(float dx){
        this.dx = dx;
    }

    public void setDy(float dy){
        this.dy = dy;
    }
}
