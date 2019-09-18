package xyz.ndlr.infrastructure;

import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.CompletableFuture;

/**
 * Converts listenable futures to completable futures, mapping the listenable futures' callbacks
 * to completable futures' equivalents.
 */
public class ListenableFutureAdapter {

    public static <T> CompletableFuture<T> toCompletableFuture(
            ListenableFuture<T> listenableFuture) {
        CompletableFuture<T> completableFuture = new CompletableFuture<T>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                boolean cancelled = listenableFuture.cancel(mayInterruptIfRunning);
                super.cancel(cancelled);
                return cancelled;
            }
        };

        listenableFuture.addCallback(completableFuture::complete,
                completableFuture::completeExceptionally);

        return completableFuture;
    }
}
