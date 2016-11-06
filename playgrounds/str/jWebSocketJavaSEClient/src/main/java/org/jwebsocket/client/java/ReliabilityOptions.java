//    ---------------------------------------------------------------------------
//    jWebSocket - Copyright (c) 2011 Innotrade GmbH
//    ---------------------------------------------------------------------------
//    This program is free software; you can redistribute it and/or modify it
//    under the terms of the GNU Lesser General Public License as published by the
//    Free Software Foundation; either version 3 of the License, or (at your
//    option) any later version.
//    This program is distributed in the hope that it will be useful, but WITHOUT
//    ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
//    FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for
//    more details.
//    You should have received a copy of the GNU Lesser General Public License along
//    with this program; if not, see <http://www.gnu.org/licenses/lgpl.html>.
//    ---------------------------------------------------------------------------
package org.jwebsocket.client.java;

/**
 * 
 * @author aschulze
 * @author jforth
 */
public class ReliabilityOptions {

    private boolean mAutoReconnect = false;
    private long mReconnectDelay = -1;
    private long mReconnectTimeout = -1;
    private int mQueueItemLimit = -1;
    private int mQueueSizeLimit = -1;

    // The following are only relevant to token or custom servers.

    /**
     * Automatically set the client connection socket timeout the same as the
     * server's socket timeout when receiving the WELCOME token.
     */
    private boolean mSetSocketTimeoutFromServer = false;

    /**
     * Automatically start a heartbeat when receiving the WELCOME token. This
     * could be integrated into the baseWebSocketClient implementation, but then
     * it wouldn't be possible to have the option of automatically setting the
     * heartbeat interval relative to the server socket timeout.
     */
    private boolean mHeartbeat = false;
    private int mHeartbeatInterval = -1; // Defaults to 0.75*SoTimeout or 1
                                         // minute.

    public ReliabilityOptions(boolean aAutoReconnect, long aReconnectDelay, long aReconnectTimeout,
            int aQueueItemLimit, int aQueueSizeLimit, boolean aSetSocketTimeoutFromServer,
            boolean aHeartbeat, int aHeartbeatInterval) {
        mAutoReconnect = aAutoReconnect;
        mReconnectDelay = aReconnectDelay;
        mReconnectTimeout = aReconnectTimeout;
        mQueueItemLimit = aQueueItemLimit;
        mQueueSizeLimit = aQueueSizeLimit;
        mSetSocketTimeoutFromServer = aSetSocketTimeoutFromServer;
        mHeartbeat = aHeartbeat;
        mHeartbeatInterval = aHeartbeatInterval;
    }

    /**
     * @return the AutoReconnect
     */
    public boolean isAutoReconnect() {
        return mAutoReconnect;
    }

    /**
     * @param aAutoReconnect
     *            the AutoReconnect to set
     */
    public void setAutoReconnect(boolean aAutoReconnect) {
        this.mAutoReconnect = aAutoReconnect;
    }

    /**
     * @return the ReconnectDelay
     */
    public long getReconnectDelay() {
        return mReconnectDelay;
    }

    /**
     * @param aReconnectDelay
     *            the ReconnectDelay to set
     */
    public void setReconnectDelay(long aReconnectDelay) {
        this.mReconnectDelay = aReconnectDelay;
    }

    /**
     * @return the ReconnectTimeout
     */
    public long getReconnectTimeout() {
        return mReconnectTimeout;
    }

    /**
     * @param aReconnectTimeout
     *            the ReconnectTimeout to set
     */
    public void setReconnectTimeout(int aReconnectTimeout) {
        this.mReconnectTimeout = aReconnectTimeout;
    }

    /**
     * @return the QueueItemLimit
     */
    public int getQueueItemLimit() {
        return mQueueItemLimit;
    }

    /**
     * @param aQueueItemLimit
     *            the QueueItemLimit to set
     */
    public void setQueueItemLimit(int aQueueItemLimit) {
        this.mQueueItemLimit = aQueueItemLimit;
    }

    /**
     * @return the QueueSizeLimit
     */
    public int getQueueSizeLimit() {
        return mQueueSizeLimit;
    }

    /**
     * @param aQueueSizeLimit
     *            the QueueSizeLimit to set
     */
    public void setQueueSizeLimit(int aQueueSizeLimit) {
        this.mQueueSizeLimit = aQueueSizeLimit;
    }

    /**
     * @return the set socket timeout from server
     */
    public boolean isSetSocketTimeoutFromServer() {
        return this.mSetSocketTimeoutFromServer;
    }

    /**
     * @param aSetSocketTimeoutFromServer
     */
    public void setSocketTimeoutFromServer(boolean aSetSocketTimeoutFromServer) {
        this.mSetSocketTimeoutFromServer = aSetSocketTimeoutFromServer;
    }

    /**
     * @return the heartbeat
     */
    public boolean isHeartbeat() {
        return this.mHeartbeat;
    }

    /**
     * @param aHeartbeat
     *            set the heartbeat
     */
    public void setHeartbeat(boolean aHeartbeat) {
        this.mHeartbeat = aHeartbeat;
    }

    /**
     * @return the heartbeat interval
     */
    public int getHeartbeatInterval() {
        return this.mHeartbeatInterval;
    }

    /**
     * @param aHeartbeatInterval
     *            set the heartbeat interval
     */
    public void setHeartbeatInterval(int aHeartbeatInterval) {
        this.mHeartbeatInterval = aHeartbeatInterval;
    }

}
