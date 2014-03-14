package game.util;

/**
 * Implementerer litt enkel hashing som beskrevet i Joshua Blochs Effective Java, 2. utgave.
 * (ISBN-13: 978-0321356680)
 * @author Haakon Løtveit
 *
 * Bruk disse til å hashe klassene dine med, så slipper du en del bry.
 */
public class EffectiveJavaHasher {
	public static int hashBoolean(boolean b){
		return b? 1 : 0;
	}
	
	public static int hashInteger(int i){
		return (int) i;
	}
	
	public static int hashLong(long l){
		/* slapp av, hva dette gjør kommer nok ikke på eksamen. ;) */
		return (int) (l ^ (l >>> 32));
	}
	
	public static int hashFloat(float f){
		return Float.floatToIntBits(f);
	}
	
	public static int hashDouble(double d) { /* Insert Beavis and Butthead fnising når vi ser "double d" */
		return hashLong(Double.doubleToLongBits(d));
	}

}
