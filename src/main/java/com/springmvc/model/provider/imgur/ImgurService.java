package com.springmvc.model.provider.imgur;

import com.springmvc.model.provider.ProviderResponse;

import javax.validation.constraints.NotNull;
import java.util.Map;

public class ImgurService {

    @NotNull
    public ProviderResponse upload(Map<String, String> arguments) {
        UploadPayload payload = new UploadPayload
                ("R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7");
        return null;
    }

    @NotNull
    public ProviderResponse delete(Map<String, String> arguments) {
        return new ProviderResponse("not implemented");
    }
}
