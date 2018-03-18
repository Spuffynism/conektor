package xyz.ndlr.model.provider.imgur;

import org.springframework.beans.factory.annotation.Autowired;
import xyz.ndlr.model.ListenableFutureAdapter;
import xyz.ndlr.model.dispatching.mapping.ActionMapping;
import xyz.ndlr.model.dispatching.mapping.ProviderMapping;
import xyz.ndlr.model.entity.User;
import xyz.ndlr.model.provider.ProviderResponse;
import xyz.ndlr.model.provider.facebook.PipelinedMessage;
import xyz.ndlr.model.provider.facebook.shared.AttachmentType;
import xyz.ndlr.model.provider.facebook.webhook.Message;
import xyz.ndlr.model.provider.imgur.receive.Image;
import xyz.ndlr.model.provider.imgur.receive.UploadResponse;
import xyz.ndlr.model.provider.imgur.send.UploadPayload;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@ProviderMapping("imgur")
public class ImgurService {
    private final ImgurRepository imgurRepository;

    @Autowired
    public ImgurService(ImgurRepository imgurRepository) {
        this.imgurRepository = imgurRepository;
    }

    //@ActionMapping("upload")
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
        //TODO Run tests to see if what's the difference between thenApply & thenApplyAsync
        return allFutures.thenApply(v ->
                futures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList())
        );
    }

    @ActionMapping({"remove","delete"})
    public ProviderResponse delete(User user, PipelinedMessage pipelinedMessage) {
        return new ProviderResponse("not implemented");
    }

    /*public ProviderResponse delete(Message message) {
        return new ProviderResponse("not implemented");
    }*/
}
