package Utilities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class DateFormatter {

    // These are constant for date format for Calendar
    // Date selection and flight input detail screen
    public static String dateFormatterInFlightCalendarDateSelection = "dd MMMM yyyy";
    public static String dateFormatterInFlightInputDetail = "dd MMM, yyyy";

    /* This method will be converting date into
    * string from one date format to another format.
    */
    public static String dateConversionFromToDestinationFormat(String date,
                                                               String sourceFormat,
                                                               String destinationFormat) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(sourceFormat);
        LocalDate sourceConvertedDate = LocalDate.parse(date, formatter);
        formatter = DateTimeFormatter.ofPattern(destinationFormat);
        return sourceConvertedDate.format(formatter);
    }
}
