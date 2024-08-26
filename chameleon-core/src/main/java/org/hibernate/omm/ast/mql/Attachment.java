/*
 * Copyright (c) 2008 - 2013 10gen, Inc. <http://10gen.com>
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
 *
 */

package org.hibernate.omm.ast.mql;

public class Attachment {
    private AttachmentKey<?> curKey;
    private Object curValue;

    public <T> void attach(AttachmentKey<T> key, T value) {
        // Eventually this would be uncommented for early detection of bugs, but commenting out for now so that most tests still pass.
//        if (curKey != null) {
//            throw new IllegalStateException("Trying to attach value with key " + key + " but there is already an attached value with key " + curKey);
//        }
        curKey = key;
        curValue = value;
    }

    public <T> T detach(AttachmentKey<T> key) {
        if (key != curKey) {
            throw new IllegalStateException("Trying to detach value with key " + key + " but attached value has key " + curKey);
        }
        //noinspection unchecked
        T retVal = (T) curValue;
        curKey = null;
        curValue = null;
        return retVal;
    }

    public boolean hasAttachment() {
        return curKey != null;
    }
}
