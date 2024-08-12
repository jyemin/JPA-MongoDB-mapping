/*
 * Copyright 2008-present MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hibernate.omm.exception;

/**
 * Denotes that the feature in question is decided not to be supported by OMM
 *
 * @author Nathan Xu
 * @see NotYetImplementedException
 * @since 1.0.0
 */
public class NotSupportedRuntimeException extends RuntimeException {

    public NotSupportedRuntimeException() {
    }

    public NotSupportedRuntimeException(final String message) {
        super(message);
    }

    public NotSupportedRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NotSupportedRuntimeException(final Throwable cause) {
        super(cause);
    }
}
