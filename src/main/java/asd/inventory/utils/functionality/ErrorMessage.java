package asd.inventory.utils.functionality;

public class ErrorMessage {
    private String message;
    private boolean success;
    private String timestamp;

    public ErrorMessage(String message, boolean success, String timestamp) {
        this.message = message;
        this.success = success;
        this.timestamp = timestamp;
    }

    public String getMessage() { return message; }
    public boolean isSuccess() { return success; }
    public String getTimestamp() { return timestamp; }
}
