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

import static com.sixtydigits.cassandra.pelops.Bytes.fromUTF8;
import static com.sixtydigits.cassandra.pelops.Bytes.nullSafeGet;

import com.sixtydigits.cassandra.pelops.exceptions.PelopsException;
import com.sixtydigits.cassandra.pelops.pool.IThriftPool;
import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.ConsistencyLevel;

/**
 * Facilitates the removal of data at a key-level.
 *
 * @author dominicwilliams
 *
 */
public class RowDeletor extends Operand {

	private final long timestamp;

	/**
	 * Delete a row with a specified key from a specified column family. The function succeeds even if
	 * the row does not exist.
	 * @param columnFamily				The column family from which to delete the row
	 * @param rowKey					The key of the row
	 * @param cLevel					The Cassandra consistency level to be used
	 * @throws PelopsException
	 */
	public void deleteRow(final String columnFamily, final String rowKey, final ConsistencyLevel cLevel) throws PelopsException {
		deleteRow(columnFamily, fromUTF8(rowKey), cLevel);
	}

	/**
	 * Delete a row with a specified key from a specified column family. The function succeeds even if
	 * the row does not exist.
	 * @param columnFamily				The column family from which to delete the row
	 * @param rowKey					The key of the row
	 * @param cLevel					The Cassandra consistency level to be used
	 * @throws PelopsException
	 */
	public void deleteRow(String columnFamily, final Bytes rowKey, final ConsistencyLevel cLevel) throws PelopsException {
        final ColumnPath path = new ColumnPath(columnFamily);
		IOperation<Void> operation = new IOperation<Void>() {
			@Override
			public Void execute(IThriftPool.IPooledConnection conn) throws Exception {
				conn.getAPI().remove(nullSafeGet(rowKey), path, timestamp, cLevel);
				return null;
			}
		};
		tryOperation(operation);
	}

	public RowDeletor(IThriftPool thrift) {
		this(thrift, System.currentTimeMillis() * 1000);
	}

	public RowDeletor(IThriftPool thrift, long timestamp) {
		super(thrift);
        this.timestamp = timestamp;
	}
}
