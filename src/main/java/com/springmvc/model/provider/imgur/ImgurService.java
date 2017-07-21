package com.springmvc.model.provider.imgur;

import com.springmvc.model.provider.ProviderResponse;
import com.springmvc.model.provider.facebook.PipelinedMessage;
import com.springmvc.model.provider.imgur.receive.UploadResponse;
import com.springmvc.model.provider.imgur.send.UploadPayload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ImgurService {
    private static final ImgurRepository imgurRepository = new ImgurRepository();

    @NotNull
    public ProviderResponse upload(PipelinedMessage pipelinedMessage) {
        List<String> urls = pipelinedMessage.getMessaging().getMessage().getImageURLs();
        String image = urls.get(0);
        UploadPayload payload = new UploadPayload(image);
        UploadResponse response = imgurRepository.upload(payload);

        String prettyResponse = UploadResponse.toPrettyMessage(Arrays.asList(response));

        return new ProviderResponse(prettyResponse);
    }

    @NotNull
    public ProviderResponse delete(PipelinedMessage pipelinedMessage) {
        return new ProviderResponse("not implemented");
    }
}
