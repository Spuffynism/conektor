package com.springmvc.model.provider.imgur;

import com.springmvc.model.ListenableFutureAdapter;
import com.springmvc.model.provider.ProviderResponse;
import com.springmvc.model.provider.facebook.shared.AttachmentType;
import com.springmvc.model.provider.facebook.webhook.Message;
import com.springmvc.model.provider.imgur.receive.Image;
import com.springmvc.model.provider.imgur.receive.UploadResponse;
import com.springmvc.model.provider.imgur.send.UploadPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class ImgurService {
    private final ImgurRepository imgurRepository;

    @Autowired
    public ImgurService(ImgurRepository imgurRepository) {
        this.imgurRepository = imgurRepository;
    }

    CompletableFuture<ProviderResponse> upload(Message message) {
        // get urls that'll be uploaded to Imgur
        List<String> urls = message.getAttachmentURLs(AttachmentType.IMAGE);

        // collect upload futures
        List<CompletableFuture<Image>> responses = urls.stream()
                .map(UploadPayload::new)
                .map(imgurRepository::upload)
                .map(ListenableFutureAdapter::toCompletableFuture)
                .collect(Collectors.toList());

        return convertToProviderResponseFuture(responses);
    }

    /**
     * Converts a list of completable image futures to a provider response completable future.
     *
     * @param futures - the futures in allFutures
     * @return a ProviderResponse future - for consumation by ImgurDispatcher
     */
    private CompletableFuture<ProviderResponse> convertToProviderResponseFuture(
            List<CompletableFuture<Image>> futures) {
        CompletableFuture<List<Image>> allFutures = sequence(futures);

        return allFutures.thenApply(images -> {
            String links = images.stream()
                    .map(Image::getLink)
                    .reduce("", UploadResponse::formatLinks);

            return new ProviderResponse(links);
        });
    }

    private static <T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> futures) {
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(
                new CompletableFuture[futures.size()]));
        //TODO Run tests to see if there's diff. between thenApply & thenApplyAsync
        return allFutures.thenApply(v ->
                futures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList())
        );
    }

    @NotNull
    public ProviderResponse delete() {
        return new ProviderResponse("not implemented");
    }
}
