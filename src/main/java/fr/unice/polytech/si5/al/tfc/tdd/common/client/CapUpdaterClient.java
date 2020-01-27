package fr.unice.polytech.si5.al.tfc.tdd.common.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import tfc.cap.updater.CapUpdaterServiceGrpc;
import tfc.cap.updater.TfcCapUpdater;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class CapUpdaterClient {

    private static final Logger logger = Logger.getLogger(CapUpdaterClient.class.getName());
    private final ManagedChannel channel;
    private final CapUpdaterServiceGrpc.CapUpdaterServiceBlockingStub blockingStub;

    /**
     * Construct client connecting to HelloWorld server at {@code host:port}.
     */
    public CapUpdaterClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext()
                .build());
    }

    /**
     * Construct client for accessing HelloWorld server using the existing channel.
     */
    CapUpdaterClient(ManagedChannel channel) {
        this.channel = channel;
        blockingStub = CapUpdaterServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    /**
     * Say hello to server.
     */
    public TfcCapUpdater.DownscaleResponse downscaleCap(String accountID, int value) {
        TfcCapUpdater.CapDownscale downscale = TfcCapUpdater.CapDownscale.newBuilder()
                .setAccountID(accountID)
                .setValue(value)
                .build();
        try {
            return blockingStub.downscaleCap(downscale);
        } catch (StatusRuntimeException e) {
            return null;
        }
    }
}