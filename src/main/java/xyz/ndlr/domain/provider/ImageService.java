package xyz.ndlr.domain.provider;

import xyz.ndlr.domain.dispatching.mapping.ActionMapping;
import xyz.ndlr.domain.provider.facebook.PipelinedMessage;
import xyz.ndlr.domain.user.User;

/**
 * Image uploading services
 */
public abstract class ImageService {

    /**
     * Usage:
     * `$ <provider> upload http://example.com/image.png`
     *
     * @param user
     * @param pipelinedMessage
     * @return
     */
    @ActionMapping({"u", "-u", "upload", "--upload"})
    public abstract ProviderResponse upload(User user, PipelinedMessage pipelinedMessage);
}
