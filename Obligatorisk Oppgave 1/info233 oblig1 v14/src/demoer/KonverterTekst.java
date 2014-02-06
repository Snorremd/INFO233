package demoer;

import org.apache.commons.lang3.StringEscapeUtils;

public class KonverterTekst {
	public static void main(String[] args) {
		/* Slik unescaper du unicode */
		String str = "Dodd\\u2013Frank";
		System.out.println(str);
		System.out.println(StringEscapeUtils.unescapeJava(str));
	}
}
