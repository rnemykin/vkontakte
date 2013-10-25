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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Mixin for {@link org.springframework.social.vkontakte.api.Post.Likes}
 * @author nekoval
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostLikesMixin {

	@JsonCreator
    PostLikesMixin(@JsonProperty("count") int count) {}

    @JsonProperty("count")
    private int count;

    @JsonProperty("user_likes")
    private boolean userLikes;

    @JsonProperty("can_like")
    private boolean canLike;

    @JsonProperty("can_publish")
    private boolean canPublish;

}
