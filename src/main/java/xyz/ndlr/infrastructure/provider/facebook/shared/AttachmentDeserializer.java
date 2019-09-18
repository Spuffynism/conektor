package xyz.ndlr.infrastructure.provider.facebook.shared;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import xyz.ndlr.infrastructure.provider.facebook.sendAPI.message.attachment.AbstractPayload;
import xyz.ndlr.infrastructure.provider.facebook.sendAPI.message.attachment.MultimediaPayload;
import xyz.ndlr.infrastructure.provider.facebook.sendAPI.message.attachment.template.TemplatePayload;

import java.io.IOException;

public class AttachmentDeserializer extends JsonDeserializer<Attachment> {

    @Override
    public Attachment deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext) throws IOException {
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode attachment = codec.readTree(jsonParser);

        AttachmentType type = tryParseAttachmentType(attachment);
        Class<? extends AbstractPayload> payloadClass = tryGetPayloadClass(type);

        AbstractPayload payload = codec.treeToValue(attachment.get("payload"), payloadClass);
        Attachment parsedAttachment = new Attachment(type, payload);

        try {
            parsedAttachment.setTitle(attachment.get("title").asText());
        } catch (Exception e) {
            parsedAttachment.setTitle("");
        }

        try {
            parsedAttachment.setURL(attachment.get("url").asText());
        } catch (Exception e) {
            parsedAttachment.setURL("");
        }

        return parsedAttachment;
    }

    private static AttachmentType tryParseAttachmentType(JsonNode tree) {
        AttachmentType type;
        try {
            type = AttachmentType.valueOf(tree.get("type").asText().toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException("unsupported attachment type");
        }

        return type;
    }

    private static Class<? extends AbstractPayload> tryGetPayloadClass(AttachmentType type) {
        Class<? extends AbstractPayload> payloadClass;
        if (type.isMultimedia()) {
            payloadClass = MultimediaPayload.class;
        } else if (type == AttachmentType.TEMPLATE) {
            payloadClass = TemplatePayload.class;
        } else {
            throw new RuntimeException("unsupported attachment type");
        }

        return payloadClass;
    }
}
