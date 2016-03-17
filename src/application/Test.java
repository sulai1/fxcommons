package application;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;

public class Test {
	public static void main(String[] args) throws ParseException {
		NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.GERMANY);
		String s = "145,01 €";
		s = nf.format(BigDecimal.ZERO);
		System.out.println(s);
	}
}
