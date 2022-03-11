
/**
 * Utilitity fuctions for use with the MTRandom class
 * These functions provide easy access to numbers generated
 * by the Mersenne Twister.
 */
public class MersenneTwister {
    private MTRandom rnd;

    public MersenneTwister(long seed) {
      rnd = new MTRandom(seed);
    }

  /**
   * @return rnd
   * 
   */
  public float get() // get random number from 0 to 1
  {
    return rnd.nextFloat();
  }

  /**
   * Gets a random number from 0(inclusive) to n(exclusive)
   * 
   * @param n
   *            The superior limit (exclusive)
   * @return A number from 0 to n-1
   */
  public int get(int n)
  {
    return (int) Math.floor(rnd.nextDouble() * n);
  }

  /**
   * @param min
   * @param max
   * @return value
   */
  public int get(int min, int max) // get random number from
  // min to max (not max-1 !)
  {
    return min + (int) Math.floor(rnd.nextDouble() * (max - min + 1));
  }

  /**
   * @param n
   * @return n
   */
  public int nextInt(int n)
  {
    return (int) Math.floor(rnd.nextDouble() * n);
  }

  /**
   * @return int
   */
  public int nextInt()
  {
    return rnd.nextInt();
  }

  /**
   * @return double
   */
  public double nextDouble()
  {
    return rnd.nextDouble();
  }

  /**
   * @return double
   */
  public double nextGaussian()
  {
    return rnd.nextGaussian();
  }

  /**
   * @return double
   */
  public boolean nextBoolean()
  {
    return rnd.nextBoolean();
  }
}
