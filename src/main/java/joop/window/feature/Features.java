/*
 * Copyright 2019 Upyter
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package joop.window.feature;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.JFrame;

/**
 * A collection of features. This can be used for a class that takes only one
 * feature. This class has the interface of a single feature, but spreads the
 * calls to all the features contained by it.
 * <p>This class is immutable and thread-safe.</p>
 * @since 0.44
 */
public class Features implements Consumer<JFrame> {
    /**
     * The feature collection.
     */
    private final Collection<Consumer<JFrame>> collection;

    /**
     * Ctor.
     * @param collection The collection of features.
     */
    public Features(final Consumer<JFrame>... collection) {
        this(List.of(collection));
    }

    /**
     * Ctor.
     * @param collection The collection of features.
     */
    public Features(final Collection<Consumer<JFrame>> collection) {
        this.collection = collection;
    }

    @Override
    public final void accept(final JFrame frame) {
        this.collection.forEach(
            feature -> feature.accept(frame)
        );
    }
}
