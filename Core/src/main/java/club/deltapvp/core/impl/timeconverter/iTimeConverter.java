package club.deltapvp.core.impl.timeconverter;

import club.deltapvp.deltacore.api.DeltaAPI;
import club.deltapvp.deltacore.api.utilities.message.Message;
import club.deltapvp.deltacore.api.utilities.time.TimeConversion;

public class iTimeConverter implements TimeConversion {
    @Override
    public String format(long l, long l1) {
        return format(l, l1, false);
    }

    @Override
    public String format(long l, long l1, boolean shortened) {
        Message msg = DeltaAPI.getInstance().createMessage("%day%%hour%%min%%sec%");
        String format = "%day%%hour%%min%%sec%";
        long newTime = l - l1;

        // Under 60s
        if (newTime <= 60000L) {
            int toSec = (int) (newTime / 1000) % 60;

            String second;
            if (shortened)
                second = "s";
            else
                second = (toSec == 1 ? "second" : "seconds");

            String secDisplay = (toSec + (shortened ? "" : " ") + second);
            return msg.getMessage("%day%", "", "%hour%", "", "%min%", "", "%sec%", secDisplay);
        }

        // Under 1h
        if (newTime <= 3599000L) {
            int toSec = (int) (newTime / 1000) % 60;
            int toMin = (int) (newTime / 1000) / 60;

            boolean secondNotZero = toSec != 0;

            String minute;
            if (shortened)
                minute = "m";
            else
                minute = (toMin == 1 ? "minute" : "minutes");
            String second;
            if (shortened)
                second = "s";
            else
                second = (toSec == 1 ? "second" : "seconds");

            String secDisplay = (secondNotZero ? toMin + (shortened ? "" : " ") + second + "" : "");
            String minDisplay = (toMin + (shortened ? "" : " ") + minute + (toMin == 1 && !secondNotZero ? "" : " "));
            return msg.getMessage("%day%", "", "%hour%", "", "%min%", minDisplay, "%sec%", secDisplay);
        }

        // Under 1 day
        if (newTime <= 86040000) {

            int toSec = (int) (newTime / 1000) % 60;
            int toMin = (int) ((newTime / (1000 * 60)) % 60);
            int toHour = (int) ((newTime / (1000 * 60 * 60)) % 24);

            boolean hourNotZero = toHour != 0;
            boolean minuteNotZero = toMin != 0;
            boolean secondNotZero = toSec != 0;

            String hour;
            if (shortened)
                hour = "h";
            else
                hour = (toHour == 1 ? "hour" : "hours");

            String minute;
            if (shortened)
                minute = "m";
            else
                minute = (toMin == 1 ? "minute" : "minutes");
            String second;
            if (shortened)
                second = "s";
            else
                second = (toSec == 1 ? "second" : "seconds");

            String secDisplay = (secondNotZero ? toMin + (shortened ? "" : " ") + second + "" : "");
            String minDisplay = (minuteNotZero ? toMin + (shortened ? "" : " ") + minute + (toSec == 0 ? "" : " ") : "");
            String hourDisplay = (hourNotZero ? toHour + (shortened ? "" : " ") + hour + (toMin == 0 ? "" : " ") : "");
            return msg.getMessage("%day%", "", "%hour%", hourDisplay, "%min%", minDisplay, "%sec%", secDisplay);
        }

        // Anything over a day

        int toSec = (int) (newTime / 1000) % 60;
        int toMin = (int) ((newTime / (1000 * 60)) % 60);
        int toHour = (int) ((newTime / (1000 * 60 * 60)) % 24);
        int toDays = (int) (newTime / (1000 * 60 * 60 * 24));

        boolean dayNotZero = toDays != 0;
        boolean hourNotZero = toHour != 0;
        boolean minuteNotZero = toMin != 0;
        boolean secondNotZero = toSec != 0;


        String day;
        if (shortened)
            day = "d";
        else
            day = (toDays == 1 ? "day" : "days");

        String hour;
        if (shortened)
            hour = "h";
        else
            hour = (toHour == 1 ? "hour" : "hours");

        String minute;
        if (shortened)
            minute = "m";
        else
            minute = (toMin == 1 ? "minute" : "minutes");

        String second;
        if (shortened)
            second = "s";
        else
            second = (toSec == 1 ? "second" : "seconds");

        String secDisplay = (secondNotZero ? toMin + (shortened ? "" : " ") + second + "" : "");
        String minDisplay = (minuteNotZero ? toMin + (shortened ? "" : " ") + minute + (toSec == 0 ? "" : " ") : "");
        String hourDisplay = (hourNotZero ? toHour + (shortened ? "" : " ") + hour + (toMin == 0 ? "" : " ") : "");
        String dayDisplay = (dayNotZero ? toDays + (shortened ? "" : " ") + day + (toHour == 0 ? "" : " ") : "");
        return msg.getMessage("%day%", dayDisplay, "%hour%", hourDisplay, "%min%", minDisplay, "%sec%", secDisplay);
    }

    @Override
    public Long fromString(String s) {
        StringBuilder builder = new StringBuilder();
        int seconds = 0;
        int minutes = 0;
        int hours = 0;
        int days = 0;
        int weeks = 0;
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                builder.append(c);
            } else {
                switch (c) {
                    case 's':
                        if (builder.length() != 0) {
                            seconds += Integer.parseInt(builder.toString());
                            builder = new StringBuilder();
                        }
                        break;
                    case 'm':
                        if (builder.length() != 0) {
                            minutes += Integer.parseInt(builder.toString());
                            builder = new StringBuilder();
                        }
                        break;
                    case 'h':
                        if (builder.length() != 0) {
                            hours += Integer.parseInt(builder.toString());
                            builder = new StringBuilder();
                        }
                        break;
                    case 'd':
                        if (builder.length() != 0) {
                            days += Integer.parseInt(builder.toString());
                            builder = new StringBuilder();
                        }
                        break;
                    case 'w':
                        if (builder.length() != 0) {
                            weeks += Integer.parseInt(builder.toString());
                            builder = new StringBuilder();
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("Not a valid duration format.");
                }
            }
        }
        return 1000L * (seconds + minutes * 60L + hours * 60 * 60L + days * 24 * 60 * 60L + weeks * 7 * 24 * 60 * 60L);
    }
}
