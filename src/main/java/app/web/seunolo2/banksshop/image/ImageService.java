package app.web.seunolo2.banksshop.image;

import app.web.seunolo2.banksshop.responseMessage.ResponseMessage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {

    public Image getImageById(Long imageId);

    public List<Image> getImagesByProductId(Long productId);

    public ResponseMessage deleteImageById(Long imageId);

    public ResponseMessage saveImage(List<MultipartFile> imageFiles, ImageModel imageModel);

    public ResponseMessage updateImage(Long imageId, MultipartFile imageFile);


}
