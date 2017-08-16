package xyz.ndlr;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Nothing {

    public static void main(String[] args) {
        CompletableFuture<String> one = new CompletableFuture<>();

        System.out.println("start! will wait now...");
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("waited a while...Will now complete");
                one.complete("ok");
                System.out.println("completed");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        one.thenAcceptAsync(s -> waitAndPrint(200,s + " async"))
                .thenCompose(s -> new CompletableFuture<>());
        one.thenAcceptAsync(s -> waitAndPrint(300,s + " async"));

        try {
            System.out.println("Blocking & Waiting for response");
            System.out.println("we got it!" + one.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        one.thenAcceptAsync(s -> waitAndPrint(400,s + " async"));
        one.thenAcceptAsync(s -> waitAndPrint(500,s + " async"));
    }

    private static void waitAndPrint(int millis, String message){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("waited " + millis + " : " + message);
    }
}