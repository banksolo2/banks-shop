package app.web.seunolo2.banksshop.image;

import app.web.seunolo2.banksshop.exceptions.ImageNotFoundException;
import app.web.seunolo2.banksshop.exceptions.ProductNotFoundException;
import app.web.seunolo2.banksshop.product.ProductRepository;
import app.web.seunolo2.banksshop.product.ProductService;
import app.web.seunolo2.banksshop.responseMessage.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ProductService productService;

    @Override
    public Image getImageById(Long imageId) {
        return imageRepository.findById(imageId)
                .orElseThrow(()-> new ImageNotFoundException("Image with ID "+imageId+" not found"));
    }

    @Override
    public List<Image> getImagesByProductId(Long productId) {
        return imageRepository.getImagesByProductProductId(productId);
    }

    @Override
    public ResponseMessage deleteImageById(Long imageId) {
        try {
            var image = getImageById(imageId);
            imageRepository.delete(image);
            return ResponseMessage.builder()
                    .type("success")
                    .message("Image deleted")
                    .httpStatus(HttpStatus.NO_CONTENT)
                    .build();
        }
        catch(ImageNotFoundException imageNotFoundException){
            return ResponseMessage.builder()
                    .type("error")
                    .message(imageNotFoundException.getMessage())
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }
        catch(Exception ex){
            return ResponseMessage.builder()
                    .type("error")
                    .message(ex.getMessage())
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @Override
    public ResponseMessage saveImage(List<MultipartFile> imageFiles, ImageModel imageModel){
        try {
            //check if product with ID exists
            var product = productService.getById(imageModel.getProductId());
            List<Image> images = new ArrayList<>();
            for(MultipartFile imageFile : imageFiles ) {
                 var image =Image.builder()
                        .fileName(imageFile.getOriginalFilename())
                        .fileType(imageFile.getContentType())
                        .image(new SerialBlob(imageFile.getBytes()))
                        .product(product)
                        .build();
                 var savedImage = imageRepository.save(image);
                 savedImage.setDownloadUrl("/api/v1/images/image/download/"+savedImage.getImageId());
                 images.add(savedImage);

            }

            return ResponseMessage.builder()
                    .type("success")
                    .message("Image created")
                    .object(imageRepository.saveAll(images))
                    .httpStatus(HttpStatus.CREATED)
                    .build();
        }
        catch (ProductNotFoundException productNotFoundException){
            return ResponseMessage.builder()
                    .type("error")
                    .message(productNotFoundException.getMessage())
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }
        catch(IOException ioException){
            return ResponseMessage.builder()
                    .type("error")
                    .message(ioException.getMessage())
                    .httpStatus(HttpStatus.CONFLICT)
                    .build();
        }
        catch(Exception ex){
            return ResponseMessage.builder()
                    .type("error")
                    .message(ex.getMessage())
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @Override
    public ResponseMessage updateImage(Long imageId, MultipartFile imageFile){
        try {
            var image = getImageById(imageId);
            image.setFileName(imageFile.getOriginalFilename());
            image.setFileType(imageFile.getContentType());
            image.setImage(new SerialBlob(imageFile.getBytes()));
            return ResponseMessage.builder()
                    .type("success")
                    .message("Image updated")
                    .object(imageRepository.save(image))
                    .httpStatus(HttpStatus.OK)
                    .build();
        }
        catch (ImageNotFoundException imageNotFoundException){
            return ResponseMessage.builder()
                    .type("error")
                    .message(imageNotFoundException.getMessage())
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }
        catch(IOException ioException){
            return ResponseMessage.builder()
                    .type("error")
                    .message(ioException.getMessage())
                    .httpStatus(HttpStatus.CONFLICT)
                    .build();
        }
        catch(Exception ex){
            return ResponseMessage.builder()
                    .type("error")
                    .message(ex.getMessage())
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
