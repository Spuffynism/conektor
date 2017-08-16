package xyz.ndlr.model.provider.imgur;

import xyz.ndlr.model.provider.imgur.receive.Image;
import xyz.ndlr.model.provider.imgur.receive.UploadResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureAdapter;

import java.util.concurrent.ExecutionException;

public class ImageAdapter extends ListenableFutureAdapter<Image,
        ResponseEntity<UploadResponse>> {
    ImageAdapter(ListenableFuture<ResponseEntity<UploadResponse>> adaptee) {
        super(adaptee);
    }

    @Override
    protected Image adapt(ResponseEntity<UploadResponse> uploadResponseResponseEntity)
            throws ExecutionException {
        Image image = null;

        if (uploadResponseResponseEntity.hasBody())
            image = uploadResponseResponseEntity.getBody().getData();

        return image;
    }
}
