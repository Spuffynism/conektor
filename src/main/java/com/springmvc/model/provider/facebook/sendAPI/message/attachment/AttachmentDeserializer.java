package com.springmvc.model.provider.facebook.sendAPI.message.attachment;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.springmvc.model.provider.facebook.sendAPI.message.attachment.template.TemplatePayload;
import com.springmvc.model.provider.facebook.shared.Attachment;
import com.springmvc.model.provider.facebook.shared.AttachmentType;

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

        return new Attachment(type, payload);
    }

    private AttachmentType tryParseAttachmentType(JsonNode tree) {
        AttachmentType type;
        try {
            type = AttachmentType.valueOf(tree.get("type").asText().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new RuntimeException("unsupported attachment type");
        }

        return type;
    }

    private Class<? extends AbstractPayload> tryGetPayloadClass(AttachmentType type) {
        Class<? extends AbstractPayload> payloadClass;
        if (AttachmentType.isMultimedia(type)) {
            payloadClass = MultimediaPayload.class;
        } else if (type == AttachmentType.TEMPLATE) {
            payloadClass = TemplatePayload.class;
        } else {
            throw new RuntimeException("unsupported attachment type");
        }

        return payloadClass;
    }
}
