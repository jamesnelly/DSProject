package ie.gmit.sw.ds;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.logging.Level;

import com.google.protobuf.BoolValue;
import com.google.protobuf.ByteString;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

//  Adapted from https://github.com/john-french/distributed-systems-labs/tree/master/grpc-async-inventory  

public class Client {
	
	private static final Logger logger =
    Logger.getLogger(Client.class.getName());
    private final ManagedChannel channel;
    private final PasswordServiceGrpc.PasswordServiceStub asyncPasswordService;
    private final PasswordServiceGrpc.PasswordServiceBlockingStub syncPasswordService;
    private String UserPassword;
    private ByteString HashedPassword;
    private ByteString Salt;
    private int UserID;
    Scanner userInput = new Scanner(System.in);

    public Client(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        syncPasswordService = PasswordServiceGrpc.newBlockingStub(channel);
        asyncPasswordService = PasswordServiceGrpc.newStub(channel);
    }
    	public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void sendHashReq(){
    	//INPUTS FOR USER
    	 System.out.println("Enter ID:");
         UserID = userInput.nextInt();
         System.out.println("Enter Password:");
         UserPassword = userInput.next();

        System.out.println("Sending Hash Request");
        HashRequest hashRequest = HashRequest.newBuilder().setUserID(UserID).setPassword(UserPassword).build();
        HashResponse hashResponse;

        try {
            hashResponse = syncPasswordService.hash(hashRequest);
            HashedPassword = hashResponse.getHashedPassword();
            Salt = hashResponse.getSalt();
        }catch (StatusRuntimeException ex){
            logger.log(Level.WARNING, "RPC failed: {0}", ex.getStatus());
            return;
        }
    }

    public void sendValidationRequest(){
        StreamObserver<BoolValue> responseObserver = new StreamObserver<BoolValue>(){
            @Override
            public void onNext(BoolValue value) {
                if(value.getValue()){
                    System.out.println("Success");
                }
                else{
                    System.out.println("Username or Password is incorrect");
                }
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(" Error has occurred.. "+t.getLocalizedMessage());
            }

            @Override
            public void onCompleted() {
                System.exit(0);
            }
        };
        try {
            asyncPasswordService.validate(ValidateRequest.newBuilder().setPassword(UserPassword)
                    .setHashedPassword(HashedPassword)
                    .setSalt(Salt).build(), responseObserver);
        } catch (StatusRuntimeException ex) {
            logger.log(Level.WARNING, "RPC failed: {0}", ex.getStatus());
            return;
        }
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client("localhost", 50551);
        try {
            client.sendHashReq();
            client.sendValidationRequest();
            
        } finally {
            Thread.currentThread().join();
        }
    }
}