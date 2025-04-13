package io.cdap.wrangler.utils;

public class ByteSize {
    private final long byteSize;

    public ByteSize(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Byte size string cannot be null or empty");
        }

        // Extract the numeric value and the unit
        String numericPart = value.replaceAll("[^0-9.]", "");
        String unitPart = value.replaceAll("[0-9.]", "").toUpperCase();

        if (numericPart.isEmpty() || unitPart.isEmpty()) {
            throw new IllegalArgumentException("Invalid byte size format: " + value);
        }

        double size = Double.parseDouble(numericPart);
        switch (unitPart) {
            case "B":
                byteSize = (long) size;
                break;
            case "KB":
                byteSize = (long) (size * 1024);
                break;
            case "MB":
                byteSize = (long) (size * 1024 * 1024);
                break;
            case "GB":
                byteSize = (long) (size * 1024 * 1024 * 1024);
                break;
            case "TB":
                byteSize = (long) (size * 1024L * 1024 * 1024 * 1024);
                break;
            default:
                throw new IllegalArgumentException("Unsupported unit: " + unitPart);
        }
    }

    public long getBytes() {
        return byteSize;
    }

    public double toMB() {
        return byteSize / (1024.0 * 1024.0); // Convert bytes to MB
    }

    public double toKB() {
        return byteSize / 1024.0; // Convert bytes to KB
    }
}
