package io.cdap.wrangler.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeDuration {
    private final long durationMs;

    // Constructor to parse the time duration string
    public TimeDuration(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Time duration string cannot be null or empty");
        }

        long totalMillis = 0;
        Pattern pattern = Pattern.compile("(\\d+)([dhms])", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(value);

        while (matcher.find()) {
            long amount = Long.parseLong(matcher.group(1));
            String unit = matcher.group(2).toLowerCase();

            switch (unit) {
                case "d": // Days
                    totalMillis += amount * 24 * 60 * 60 * 1000;
                    break;
                case "h": // Hours
                    totalMillis += amount * 60 * 60 * 1000;
                    break;
                case "m": // Minutes
                    totalMillis += amount * 60 * 1000;
                    break;
                case "s": // Seconds
                    totalMillis += amount * 1000;
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported time unit: " + unit);
            }
        }

        this.durationMs = totalMillis;
    }

    // Method to get the duration in milliseconds
    public long getMilliseconds() {
        return durationMs;
    }

    // Method to convert milliseconds to seconds
    public double toSeconds() {
        return durationMs / 1000.0;
    }

    // Method to convert milliseconds to minutes
    public double toMinutes() {
        return durationMs / (1000.0 * 60);
    }

    // Method to convert milliseconds to hours
    public double toHours() {
        return durationMs / (1000.0 * 60 * 60);
    }

    // Method to convert milliseconds to days
    public double toDays() {
        return durationMs / (1000.0 * 60 * 60 * 24);
    }

    // Method to get a human-readable string representation of the duration
    public String toString() {
        long days = durationMs / (1000 * 60 * 60 * 24);
        long hours = (durationMs % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (durationMs % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (durationMs % (1000 * 60)) / 1000;
        long milliseconds = durationMs % 1000;

        StringBuilder sb = new StringBuilder();
        if (days > 0)
            sb.append(days).append("d ");
        if (hours > 0)
            sb.append(hours).append("h ");
        if (minutes > 0)
            sb.append(minutes).append("m ");
        if (seconds > 0)
            sb.append(seconds).append("s ");
        if (milliseconds > 0)
            sb.append(milliseconds).append("ms");

        return sb.toString().trim();
    }
}
