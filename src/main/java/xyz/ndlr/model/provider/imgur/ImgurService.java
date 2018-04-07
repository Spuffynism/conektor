package xyz.ndlr.model.provider.imgur;

import org.springframework.beans.factory.annotation.Autowired;
import xyz.ndlr.model.ListenableFutureAdapter;
import xyz.ndlr.model.dispatching.mapping.ActionMapping;
import xyz.ndlr.model.dispatching.mapping.ProviderMapping;
import xyz.ndlr.model.entity.User;
import xyz.ndlr.model.provider.ImageService;
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

@ProviderMapping(value = "imgur", humanName = "Imgur")
public class ImgurService extends ImageService {
    private final ImgurRepository imgurRepository;

    @Autowired
    public ImgurService(ImgurRepository imgurRepository) {
        this.imgurRepository = imgurRepository;
    }

    /**
     * Usage:
     * `$ imgur upload http://example.com/image.png`
     *
     * @param user
     * @param pipelinedMessage
     * @return
     */
    @Override
    public ProviderResponse process(User user, PipelinedMessage pipelinedMessage) {
        return ProviderResponse.notImplemented(user);
    }

    @ActionMapping({"d", "-d", "delete", "--delete"})
    public ProviderResponse delete(User user, PipelinedMessage pipelinedMessage) {
        return ProviderResponse.notImplemented(user);
    }

    /**
     * Usage: send a picture to the facebook bot
     *
     * @param user
     * @param message
     * @return
     */
    public CompletableFuture<ProviderResponse> upload(User user, Message message) {
        // get urls of images that will be uploaded to Imgur
        List<String> urls = message.getAttachmentURLs(AttachmentType.IMAGE);

        // collect process futures
        List<CompletableFuture<Image>> responses = urls.stream()
                .map(UploadPayload::new)
                .map(imgurRepository::upload)
                .map(ListenableFutureAdapter::toCompletableFuture)
                .collect(Collectors.toList());

        return convertToProviderResponseFuture(user, responses);
    }

    /**
     * Converts a list of completable image futures to a provider response completable future.
     *
     * @param futures - the futures in allFutures
     * @return a ProviderResponse future - for consumation by ImgurDispatcher
     */
    private CompletableFuture<ProviderResponse> convertToProviderResponseFuture(User user,
                                                                                List<CompletableFuture<Image>> futures) {
        CompletableFuture<List<Image>> allFutures = sequence(futures);

        // we get the uploaded images' links and join them as one string
        return allFutures.thenApply(images -> {
            String links = images.stream()
                    .map(Image::getLink)
                    .reduce("", UploadResponse::formatLinks);

            return new ProviderResponse(user, links);
        });
    }

    private static <T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> futures) {
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(
                new CompletableFuture[futures.size()]));
        return allFutures.thenApply(v ->
                futures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList())
        );
    }
}
