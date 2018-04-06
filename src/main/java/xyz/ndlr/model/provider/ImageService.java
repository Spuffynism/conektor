package xyz.ndlr.model.provider;

import xyz.ndlr.model.dispatching.mapping.ActionMapping;
import xyz.ndlr.model.entity.User;
import xyz.ndlr.model.provider.facebook.PipelinedMessage;

/**
 * Image uploading services
 */
public interface ImageService {

    /**
     * Usage:
     * `$ <provider> upload http://example.com/image.png`
     *
     * @param user
     * @param pipelinedMessage
     * @return
     */
    @ActionMapping({"u", "-u", "upload", "--upload"})
    public ProviderResponse upload(User user, PipelinedMessage pipelinedMessage);
}
