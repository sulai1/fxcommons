package application;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;

public class Test {
	public static void main(String[] args) throws ParseException {
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		nf.setMaximumFractionDigits(1);
		nf.setMinimumFractionDigits(0);
		nf.setRoundingMode(RoundingMode.HALF_UP);
		String s = "$145.03";
		ParsePosition pos = new ParsePosition(0);
		System.out.println(nf.parse(s,pos));
		System.out.println(pos.getIndex()+" "+pos.getErrorIndex());
	}
}
