package bill_generator;

import java.time.LocalDate;
import java.time.Period;

public class DateDifference {

	public static void main(String[] args) {
		
		LocalDate d1=LocalDate.of(2020, 5, 4);
		LocalDate d2=LocalDate.of(2020, 4, 1);
		
		System.out.println(d1);
		System.out.println(d2);
		Period diff=Period.between(d2, d1);
		System.out.println("Difference between two dates is :");
		System.out.println(diff.getYears()+" years "+diff.getMonths()+" months "+diff.getDays()+" days");

	}

}
