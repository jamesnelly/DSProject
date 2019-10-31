package ie.gmit.sw.ds;

import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.logging.Logger;

//Adapted from async lab: https://github.com/john-french/distributed-systems-labs/tree/master/grpc-async-inventory

public class ServerPassword {

	private io.grpc.Server grpcServer;
	private static final Logger logger = Logger.getLogger(ServerPassword.class.getName());
	private static final int PORT = 50551;

	private void start() throws IOException {
		grpcServer = ServerBuilder.forPort(PORT).addService(new PasswordServiceImpl()).build().start();
		logger.info("Server started, listening on " + PORT);
	}

	private void stop() {
		if (grpcServer != null) {
			grpcServer.shutdown();
		}
	}

	private void blockUntilShutdown() throws InterruptedException {
		if (grpcServer != null) {
			grpcServer.awaitTermination();
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		final ServerPassword Serverpassword = new ServerPassword();
		Serverpassword.start();
		Serverpassword.blockUntilShutdown();
	}
}
