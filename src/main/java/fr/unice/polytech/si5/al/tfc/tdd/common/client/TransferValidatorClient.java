package fr.unice.polytech.si5.al.tfc.tdd.common.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import tfc.transfer.validator.TfcTransferValidator;
import tfc.transfer.validator.TransferValidatorServiceGrpc;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class TransferValidatorClient {


    private static final Logger logger = Logger.getLogger(CapUpdaterClient.class.getName());
    private final ManagedChannel channel;
    private final TransferValidatorServiceGrpc.TransferValidatorServiceBlockingStub blockingStub;

    /**
     * Construct client connecting to HelloWorld server at {@code host:port}.
     */
    public TransferValidatorClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext()
                .build());
    }

    /**
     * Construct client for accessing HelloWorld server using the existing channel.
     */
    TransferValidatorClient(ManagedChannel channel) {
        this.channel = channel;
        blockingStub = TransferValidatorServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    /**
     * Say hello to server.
     */
    public TfcTransferValidator.TransferValidation pay(String accountIDOrigin, String accountIDDestination, int value) {
        TfcTransferValidator.Transfer transfer = TfcTransferValidator.Transfer.newBuilder()
                .setOrigin(accountIDOrigin)
                .setDestination(accountIDDestination)
                .setAmount(value)
                .build();
        try {
            return blockingStub.pay(transfer);
        } catch (StatusRuntimeException e) {
            return null;
        }
    }

}
