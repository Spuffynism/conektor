package com.springmvc.model.provider.imgur.receive;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class UploadResponse {
    @JsonProperty("data")
    private Image data;
    @JsonProperty("success")
    private boolean success;
    @JsonProperty("status")
    private int status;

    public static String toPrettyMessage(List<UploadResponse> uploadResponses) {
        StringBuilder prettyMessage = new StringBuilder();
        for (UploadResponse uploadResponse : uploadResponses)
            prettyMessage.append(uploadResponse.data.getLink());
        return prettyMessage.toString();
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
