package ua.eugenesokolov.interview.calendar;

import java.io.IOException;

import ua.eugenesokolov.interview.calendar.manager.EmployeeManager;
import ua.eugenesokolov.interview.calendar.manager.EmployeeManagerImpl;
import ua.eugenesokolov.interview.calendar.model.BookingCalendar;
import ua.eugenesokolov.interview.calendar.util.CalendarPrinter;
import ua.eugenesokolov.interview.calendar.util.FileReader;
import ua.eugenesokolov.interview.calendar.util.PlainTextCalendarPrinter;
import ua.eugenesokolov.interview.calendar.util.parser.BookingCalendarParser;

public class Main {

	public static void main(String[] args) {
		String filePath = "input.txt"; 
		if (args != null && args.length > 0) {
			filePath = args[0];
		}
		String data = null; 
		FileReader reader = new FileReader();
		try {
			data = reader.readFileAsString(filePath);
			EmployeeManager employeeManager = new EmployeeManagerImpl();
			CalendarPrinter printer = new PlainTextCalendarPrinter();
			
			BookingCalendar calendar = new BookingCalendarParser(employeeManager).parse(data);
			String result = printer.print(calendar);
			System.out.println(result);
		} catch (IOException e) {
			System.err.println(String.format("ERROR!.\nFile %s can't be found.\nPlease make sure you have input.txt available in current dir or specify path to file as first argument for running the program.", filePath));
			System.exit(1);
		}
	}
	

}
