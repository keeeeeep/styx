/*
  Copyright (C) 2013-2018 Expedia Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */
package com.hotels.styx.support.api.matchers;

import com.hotels.styx.api.HttpResponse;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static com.hotels.styx.support.api.HttpMessageBodies.bodyAsString;
import static org.hamcrest.CoreMatchers.equalTo;

public class HttpResponseBodyMatcher<T extends HttpResponse> extends TypeSafeMatcher<T> {

    private final Matcher<String> matcher;

    @Factory
    public static <T extends HttpResponse> Matcher<T> hasBody(Matcher<String> matcher) {
        return new HttpResponseBodyMatcher<>(matcher);
    }

    @Factory
    public static <T extends HttpResponse> Matcher<T> hasBody(String content) {
        return new HttpResponseBodyMatcher<>(equalTo(content));
    }

    public HttpResponseBodyMatcher(Matcher<String> matcher) {
        this.matcher = matcher;
    }

    @Override
    public boolean matchesSafely(T actual) {
        return matcher.matches(bodyAsString(actual));
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("content was '" + bodyAsString(item) + "'");
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("content with ");
        matcher.describeTo(description);
    }
}
