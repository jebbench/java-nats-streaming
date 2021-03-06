/*
 *  Copyright (c) 2015-2016 Apcera Inc. All rights reserved. This program and the accompanying
 *  materials are made available under the terms of the MIT License (MIT) which accompanies this
 *  distribution, and is available at http://opensource.org/licenses/MIT
 */

package io.nats.streaming;

import java.io.Serializable;
import java.time.Duration;

public class Options {
    private final String natsUrl;
    private final io.nats.client.Connection natsConn;
    Duration connectTimeout;
    private final Duration ackTimeout;
    private final String discoverPrefix;
    private final int maxPubAcksInFlight;

    private Options(Builder builder) {
        this.natsUrl = builder.natsUrl;
        this.natsConn = builder.natsConn;
        this.connectTimeout = builder.connectTimeout;
        this.ackTimeout = builder.ackTimeout;
        this.discoverPrefix = builder.discoverPrefix;
        this.maxPubAcksInFlight = builder.maxPubAcksInFlight;
    }

    String getNatsUrl() {
        return natsUrl;
    }

    io.nats.client.Connection getNatsConn() {
        return natsConn;
    }

    Duration getConnectTimeout() {
        return connectTimeout;
    }

    Duration getAckTimeout() {
        return ackTimeout;
    }

    String getDiscoverPrefix() {
        return discoverPrefix;
    }

    int getMaxPubAcksInFlight() {
        return maxPubAcksInFlight;
    }

    public static final class Builder implements Serializable {
		private static final long serialVersionUID = 4774214916207501660L;
		
		private String natsUrl = NatsStreaming.DEFAULT_NATS_URL;
        private transient io.nats.client.Connection natsConn; // A Connection is not Serializable
        private Duration connectTimeout = Duration.ofSeconds(NatsStreaming.DEFAULT_CONNECT_WAIT);
        private Duration ackTimeout = Duration.ofMillis(SubscriptionImpl.DEFAULT_ACK_WAIT);
        private String discoverPrefix = NatsStreaming.DEFAULT_DISCOVER_PREFIX;
        private int maxPubAcksInFlight = NatsStreaming.DEFAULT_MAX_PUB_ACKS_IN_FLIGHT;

        public Builder() {}

        /**
         * Constructs a {@link Builder} instance based on the supplied {@link Options} instance.
         *
         * @param template the {@link Options} object to use as a template
         */
        public Builder(Options template) {
            this.natsUrl = template.natsUrl;
            this.natsConn = template.natsConn;
            this.connectTimeout = template.connectTimeout;
            this.ackTimeout = template.ackTimeout;
            this.discoverPrefix = template.discoverPrefix;
            this.maxPubAcksInFlight = template.maxPubAcksInFlight;
        }

        public Builder pubAckWait(Duration ackTimeout) {
            this.ackTimeout = ackTimeout;
            return this;
        }

        public Builder connectWait(Duration connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public Builder discoverPrefix(String discoverPrefix) {
            this.discoverPrefix = discoverPrefix;
            return this;
        }

        public Builder maxPubAcksInFlight(int maxPubAcksInFlight) {
            this.maxPubAcksInFlight = maxPubAcksInFlight;
            return this;
        }

        public Builder natsConn(io.nats.client.Connection natsConn) {
            this.natsConn = natsConn;
            return this;
        }

        public Builder natsUrl(String natsUrl) {
            this.natsUrl = natsUrl;
            return this;
        }

        public Options build() {
            return new Options(this);
        }
    }
}
