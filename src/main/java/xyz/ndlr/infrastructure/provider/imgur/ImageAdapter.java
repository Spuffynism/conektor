package xyz.ndlr.infrastructure.provider.imgur;

import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureAdapter;
import xyz.ndlr.infrastructure.provider.imgur.receive.Image;
import xyz.ndlr.infrastructure.provider.imgur.receive.UploadResponse;

public class ImageAdapter extends ListenableFutureAdapter<Image,
        ResponseEntity<UploadResponse>> {
    ImageAdapter(ListenableFuture<ResponseEntity<UploadResponse>> adaptee) {
        super(adaptee);
    }

    @Override
    protected Image adapt(ResponseEntity<UploadResponse> uploadResponseResponseEntity) {
        Image image = null;

        if (uploadResponseResponseEntity.hasBody())
            image = uploadResponseResponseEntity.getBody().getData();

        return image;
    }
}
