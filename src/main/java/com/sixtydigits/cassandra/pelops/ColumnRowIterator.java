/*
 * The MIT License
 *
 * Copyright (c) 2011 Dominic Williams, Daniel Washusen and contributors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.sixtydigits.cassandra.pelops;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.SlicePredicate;

public class ColumnRowIterator extends RowIterator<Column> {
    public ColumnRowIterator(Selector selector, String columnFamily, Bytes startBeyondKey, int batchSize, SlicePredicate colPredicate, ConsistencyLevel cLevel) {
        super(selector, columnFamily, startBeyondKey, batchSize, colPredicate, cLevel);
    }

    @Override
    protected LinkedHashMap<Bytes, List<Column>> fetchNextBatch() {
        return this.selector.getColumnsFromRows(
                this.columnFamily, Selector.newKeyRange(this.startBeyondKey, Bytes.EMPTY, this.batchSize), this.colPredicate, this.cLevel
        );
    }
}
