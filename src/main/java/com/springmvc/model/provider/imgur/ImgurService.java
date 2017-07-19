package com.springmvc.model.provider.imgur;

import com.springmvc.model.provider.ProviderResponse;
import com.springmvc.model.provider.facebook.PipelinedMessage;

import javax.validation.constraints.NotNull;
import java.util.Map;

public class ImgurService {

    @NotNull
    public ProviderResponse upload(PipelinedMessage pipelinedMessage) {
        UploadPayload payload = new UploadPayload
                ("R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7");
        return null;
    }

    @NotNull
    public ProviderResponse delete(PipelinedMessage pipelinedMessage) {
        return new ProviderResponse("not implemented");
    }
}
