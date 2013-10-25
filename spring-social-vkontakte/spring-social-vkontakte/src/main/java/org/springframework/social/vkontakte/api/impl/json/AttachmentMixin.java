/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.vkontakte.api.impl.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.social.vkontakte.api.*;

import java.io.IOException;

/**
 * We use non-standard TypeDeserializer to deal with VK "duplicated" type info, eg:
 * [ type: link, link:  Link Object ]
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonSubTypes({
    @JsonSubTypes.Type(name="link", value=LinkAttachment.class),
    @JsonSubTypes.Type(name="photo", value=PhotoAttachment.class),
    @JsonSubTypes.Type(name="posted_photo", value=PhotoAttachment.class),
    @JsonSubTypes.Type(name="video", value=VideoAttachment.class),
    @JsonSubTypes.Type(name="audio", value=AudioAttachment.class),
    @JsonSubTypes.Type(name="graffiti", value=GraffitiAttachment.class),
    @JsonSubTypes.Type(name="doc", value=DocumentAttachmentMixin.class),
    @JsonSubTypes.Type(name="note", value=NoteAttachmentMixin.class),
    @JsonSubTypes.Type(name="app", value=ApplicationAttachmentMixin.class),
    @JsonSubTypes.Type(name="poll", value=PollAttachmentMixin.class),
    @JsonSubTypes.Type(name="page", value=PageAttachmentMixin.class)
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttachmentMixin {

    @JsonProperty("type")
   	@JsonDeserialize(using = AttachmentTypeDeserializer.class)
    Attachment.AttachmentType type;

    private static class AttachmentTypeDeserializer extends JsonDeserializer<Attachment.AttachmentType> {
   		@Override
   		public Attachment.AttachmentType deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
   			return Attachment.AttachmentType.valueOf(jp.getText().toUpperCase());
   		}
   	}
}
