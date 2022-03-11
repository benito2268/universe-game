/**
 * represents vector with a size of two that may contain any type of number
 * @author ben staehle
 */
public class Vector2n<Type extends Number> {
    public Type x;
    public Type y;

    public Vector2n() {
    }

    public Vector2n(Type x, Type y) {
        this.x = x;
        this.y = y;
    }
}
