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

package joop.shape;

import java.io.File;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * A file from the resources folder. <b>The file will be loaded each time
 * {@link #get()} is called.</b>
 * <p>This class is mutable and not thread-safe.</p>
 * @since 0.73
 */
public class ResourceFile implements Supplier<File> {
    /**
     * The path to the file (starting from the resources folder).
     */
    private final String path;

    /**
     * Ctor.
     * @param path The path to the file (starting from the resources folder).
     */
    public ResourceFile(final String path) {
        this.path = Objects.requireNonNull(path);
    }

    @Override
    public final File get() {
        return new File(
            Objects.requireNonNull(
                Thread
                    .currentThread()
                    .getContextClassLoader()
                    .getResource(this.path)
            ).getFile()
        );
    }
}
