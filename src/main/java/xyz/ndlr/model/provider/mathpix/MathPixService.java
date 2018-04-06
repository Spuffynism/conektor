package xyz.ndlr.model.provider.mathpix;

import xyz.ndlr.model.dispatching.mapping.ProviderMapping;
import xyz.ndlr.model.entity.User;
import xyz.ndlr.model.provider.ImageService;
import xyz.ndlr.model.provider.ProviderResponse;
import xyz.ndlr.model.provider.facebook.PipelinedMessage;

/**
 * @see <a href="mathpix">https://docs.mathpix.com/</a>
 */
@ProviderMapping("mathpix")
public class MathPixService implements ImageService {

    /**
     * Usage:
     * `$ mathpix upload http://example.com/image.png`
     *
     * @param user
     * @param pipelinedMessage
     * @return
     */
    @Override
    public ProviderResponse upload(User user, PipelinedMessage pipelinedMessage) {
        return ProviderResponse.notImplemented(user);
    }
}
