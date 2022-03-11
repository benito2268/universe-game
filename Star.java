import java.awt.Color;

public class Star{
    private final double radius; 
    private final Color color;
    private final int procGen;
    protected final Color[] colors = {new Color(252, 173, 3), //orange
                                      new Color(252, 61, 3), //red
                                      new Color(252, 227, 115), //yellow
                                      new Color(252, 255, 255), //white
                                      new Color(177, 218, 230)}; //blue

    public Star(double radius, int procGen) {
        this.procGen = procGen;
        this.radius = radius;
        this.color = colors[randInt(0, 5)];
    }

    public double getRadius(){
        return this.radius;
    }

    private int randInt(int min, int max) {
        return Rand.Lehmer_32(procGen) % (max - min) + min;
    }

    public Color getColor() {
        return this.color;
    }
}
