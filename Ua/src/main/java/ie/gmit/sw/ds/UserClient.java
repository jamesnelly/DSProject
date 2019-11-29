package ie.gmit.sw.ds;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.google.protobuf.ByteString;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class UserClient {

	private static final Logger logger = Logger.getLogger(UserClient.class.getName());
	private final ManagedChannel channel;
	private final PasswordServiceGrpc.PasswordServiceStub asyncPasswordService;
	private final PasswordServiceGrpc.PasswordServiceBlockingStub syncPasswordService;

	public void shutdown() throws InterruptedException {
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	}

	HashRequest hashRequest;
	HashResponse hashResponse;

	byte[] saltArray;
	ByteString salt;
	ByteString passwordHashed;

	// ---- Get user salt ----//
	public ByteString getUserSalt() {
		return salt;
	}

	// ---- Get user hashed password ----//
	public ByteString getUserPasswordHashed() {
		return passwordHashed;
	}

	public UserClient(String host, int port) {
		channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();

		syncPasswordService = PasswordServiceGrpc.newBlockingStub(channel);
		asyncPasswordService = PasswordServiceGrpc.newStub(channel);
	}
}
