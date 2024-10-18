import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.util.Calendar;

public class Main {
    public static String getDurationinHrf(String timestamp1, String timestamp2) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        try {
            Date startDate = simpleDateFormat.parse(timestamp1);
            Date endDate = simpleDateFormat.parse(timestamp2);

            if (endDate.before(startDate)) {
                return "End date is before start date";
            }

            // Use Calendar to calculate the difference
            Calendar startCal = Calendar.getInstance();
            Calendar endCal = Calendar.getInstance();
            startCal.setTime(startDate);
            endCal.setTime(endDate);

            int years = endCal.get(Calendar.YEAR) - startCal.get(Calendar.YEAR);
            int months = endCal.get(Calendar.MONTH) - startCal.get(Calendar.MONTH);
            int days = endCal.get(Calendar.DAY_OF_MONTH) - startCal.get(Calendar.DAY_OF_MONTH);

            if (days < 0) {
                months--;
                endCal.add(Calendar.MONTH, -1);
                days += endCal.getActualMaximum(Calendar.DAY_OF_MONTH);
            }

            if (months < 0) {
                years--;
                months += 12;
            }

            long durationInMillis = endDate.getTime() - startDate.getTime();
            long seconds = (durationInMillis / 1000) % 60;
            long minutes = (durationInMillis / (60 * 1000)) % 60;
            long hours = (durationInMillis / (60 * 60 * 1000)) % 24;

            StringBuilder result = new StringBuilder();

            if (years > 0) {
                result.append(years).append("y ");
            }
            if (months > 0) {
                result.append(months).append("m ");
            }
            if (days > 0) {
                result.append(days).append("d ");
            }
            if (years == 0 && months == 0 && days > 0) {
                if (hours > 0) {
                    result.append(hours).append("h ");
                }
                if (minutes > 0) {
                    result.append(minutes).append("m ");
                }
            } else if (years == 0 && months == 0 && days == 0) {
                if (hours > 0) {
                    result.append(hours).append("h ");
                }
                if (minutes > 0) {
                    result.append(minutes).append("m ");
                }
                if (result.length() == 0 && seconds > 0) {
                    result.append(seconds).append("s");
                }
            }

            return result.toString().trim();
        } catch (ParseException e) {
            e.printStackTrace();
            return "Invalid date format";
        }
    }

    public static void main(String[] args) {

        System.out.println(getDurationinHrf("2023-03-17 13:51:33", "2023-03-17 13:51:50")); 

        System.out.println(getDurationinHrf("2023-03-17 13:51:33", "2023-03-18 14:51:50"));

        System.out.println(getDurationinHrf("2023-03-17 13:51:33", "2024-05-19 17:51:50")); 

        System.out.println(getDurationinHrf("2023-03-17 13:51:33", "2023-05-20 13:51:50"));

        System.out.println(getDurationinHrf("2023-03-17 13:51:33", "2023-03-17 17:53:33")); 

        System.out.println(getDurationinHrf("2023-03-18 14:51:33", "2023-03-17 13:51:33")); 

        System.out.println(getDurationinHrf("2023-03-17 23:59:59", "2023-03-18 00:00:01")); 

        System.out.println(getDurationinHrf("2021-02-27 12:00:00", "2021-02-28 12:00:00")); 

        System.out.println(getDurationinHrf("2023-04-30 00:00:01", "2023-05-01 00:00:01")); 

        System.out.println(getDurationinHrf("2000-01-01 00:00:00", "2024-01-01 01:00:00")); 

        System.out.println(getDurationinHrf("2023-02-18 12:35:30", "2023-04-18 12:35:30"));
    }
}
