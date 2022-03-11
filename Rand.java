/**
 * A wrapper class to easily provide two types of random numbers
 */
public class Rand {
    private static int lehmer;

    /**
     * An implementation of the Mersenne Twister algorithm which can generate
     * extremely high quality pseudo-random numbers, however this algorithm is
     * quite resource intensive and can run very slowly for large applications
     * 
     * @see https://en.wikipedia.org/wiki/Mersenne_Twister#Advantages
     * 
     * @param seed a long (64 bit) to seed the twister
     * @return a new MersenneTwister seeded with the given seed
     */
    public static MersenneTwister MersenneTwister_32(long seed) {
        return new MersenneTwister(seed);
    }

    /**
     * An implementation of the Lehmer algorithm, a much faster alternative
     * to the Mersenne Twister which still produces fairly high quality
     * random numbers at a fraction of the computing cost
     * 
     * @see https://en.wikipedia.org/wiki/Lehmer_random_number_generator
     * 
     * @param seed an int (32 bit) to seed the lehmer generator
     * @return an pseudo-random int generated from the lehmer algorithm
     */
    public static int Lehmer_32(int seed) {
        lehmer = seed;
        long tmp;
        tmp = (long)lehmer * 0x4a39b70d;
        int m1 = (int)((tmp >> 32) ^ tmp);
        tmp = (long)m1 * 0x12fad5c9;
        int m2 = (int)((tmp >> 32) ^ tmp);  
        return Math.abs(m2);
    }
}
