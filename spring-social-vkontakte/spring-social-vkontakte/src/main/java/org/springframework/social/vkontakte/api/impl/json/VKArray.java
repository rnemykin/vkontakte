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

import java.util.List;

/**
 * VK array is a JSON array where:
 * - first element denotes total count
 * - all elements that follow contain JSON objects.
 */
public class VKArray<T> {

    /** Total count */
    private final int count;
    /** Items retrieved */
    private List<T> items;

    /**
     * Create VK array.
     * @param count total count
     * @param items items retrieved (considering offset/limit)
     */
    public VKArray(int count, List<T> items) {
        this.count = count;
        this.items = items;
    }

    public int getCount() {
        return count;
    }

    public List<T> getItems() {
        return items;
    }
}
