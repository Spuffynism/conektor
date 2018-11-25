package xyz.ndlr.domain.provider.imgur.receive;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.concurrent.atomic.AtomicInteger;

public class UploadResponse {
    @JsonProperty("data")
    private Image data;
    @JsonProperty("success")
    private boolean success;
    @JsonProperty("status")
    private int status;
    private static AtomicInteger count = new AtomicInteger(0);

    public static String formatLinks(String linkA, String linkB) {
        linkB = linkB != null ? linkB : "";
        return String.format("%s\n%s", linkA, linkB);
    }

    public Image getData() {
        return data;
    }

    public void setData(Image data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
