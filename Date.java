public class Date {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Please provide the date, month, and year as command line arguments.");
            return;
        }

        int d = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]) - 1; // Months are 0-based in the array
        int y = Integer.parseInt(args[2]);

        String[] dayNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        int[] mon = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int[] mon_l = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        // Find the base year
        int x;
        if (y < 400) {
            x = 0;
        } else if (y < 800) {
            x = 400;
        } else if (y < 1200) {
            x = 800;
        } else if (y < 1600) {
            x = 1200;
        } else if (y < 2000) {
            x = 1600;
        } else {
            x = 2000;
        }

        int w = y - x;
        int z = w / 100;
        int l = w % 100;

        int odd_days = 0;
        for (int i = 0; i < l; i++) {
            odd_days += 1;
            if ((x + i + 1) % 4 == 0 && ((x + i + 1) % 100 != 0 || (x + i + 1) % 400 == 0)) {
                odd_days += 1;
            }
        }
        
        odd_days += (y - x) / 4 - (y - x) / 100 + (y - x) / 400;
        odd_days %= 7;

        int add = 0;
        if (y % 4 == 0 && (y % 100 != 0 || y % 400 == 0)) {
            for (int i = 0; i < m; i++) {
                add += mon_l[i];
            }
        } else {
            for (int i = 0; i < m; i++) {
                add += mon[i];
            }
        }

        add += d;
        int value = (odd_days + add) % 7;

        System.out.println(dayNames[value]);
    }
}
