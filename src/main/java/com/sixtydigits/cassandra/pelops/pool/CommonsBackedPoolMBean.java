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

package com.sixtydigits.cassandra.pelops.pool;

public interface CommonsBackedPoolMBean {
    String JMX_MBEAN_OBJ_NAME = "com.scale7.cassandra.pelops.pool:type=Pool";

    /*
        RUNNING STATS
     */

    int getConnectionsCreated();

    int getConnectionsDestroyed();

    int getConnectionsCorrupted();

    int getConnectionsActive();

    int getNodesActive();

    int getNodesSuspended();

    int getConnectionsBorrowedTotal();

    int getConnectionsReleasedTotal();

    /*
        CONFIGURATION
     */

    public int getMaxActivePerNode();

    public void setMaxActivePerNode(int maxActivePerNode);

    public int getMaxIdlePerNode();

    public void setMaxIdlePerNode(int maxIdlePerNode);

    public int getMaxTotal();

    public void setMaxTotal(int maxTotal);

    public int getMinIdlePerNode();

    public void setMinIdlePerNode(int minIdlePerNode);

    public int getMaxWaitForConnection();

    public void setMaxWaitForConnection(int maxWaitForConnection);

    public boolean isTestConnectionsWhileIdle();

    public void setTestConnectionsWhileIdle(boolean testConnectionsWhileIdle);

    public int getNodeDownSuspensionMillis();

    public void setNodeDownSuspensionMillis(int nodeDownSuspensionMillis);

    /*
        OPERATIONS
     */

    void runMaintenanceTasks();
}
