package xyz.ndlr.model.provider.mathpix;

import xyz.ndlr.model.dispatching.mapping.ProviderMapping;
import xyz.ndlr.model.entity.User;
import xyz.ndlr.model.provider.ImageService;
import xyz.ndlr.model.provider.ProviderResponse;
import xyz.ndlr.model.provider.facebook.PipelinedMessage;
import xyz.ndlr.model.provider.mathpix.request.Request;

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
