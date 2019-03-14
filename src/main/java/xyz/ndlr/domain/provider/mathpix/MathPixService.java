package xyz.ndlr.domain.provider.mathpix;

import xyz.ndlr.domain.dispatching.mapping.ProviderMapping;
import xyz.ndlr.domain.provider.ImageService;
import xyz.ndlr.domain.provider.ProviderResponse;
import xyz.ndlr.domain.provider.facebook.PipelinedMessage;
import xyz.ndlr.domain.provider.mathpix.request.Request;
import xyz.ndlr.domain.user.User;

/**
 * @see <a href="mathpix">https://docs.mathpix.com/</a>
 */
@ProviderMapping(value = "mathpix", humanName = "MathPix")
public class MathPixService extends ImageService {

    private final MathPix mathPix;

    public MathPixService(MathPix mathPix) {
        this.mathPix = mathPix;
    }

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
        String url = pipelinedMessage.getParsedMessage().getFirstArgumentValue();
        Request request = new Request(url);

        DetectionResult detectionResult = mathPix.process(request);

        String message = !"".equals(detectionResult.getError())
                ? detectionResult.getError()
                : detectionResult.getLatex();

        return new ProviderResponse(user, message);
    }
}
