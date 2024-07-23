import java.util.Calendar;

public class Find_Day {
    public static void main(String[] args) {

        Calendar cl = Calendar.getInstance();

        cl.set(2024, cl.JULY, 23);

        String[] days_Week = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        String dayOfWeekString = days_Week[dayOfWeek - 1];

        System.out.println("The day of the week is: " + dayOfWeekString);
    }
}
