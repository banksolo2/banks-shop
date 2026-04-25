package app.web.seunolo2.banksshop.image;

import app.web.seunolo2.banksshop.exceptions.ImageNotFoundException;
import app.web.seunolo2.banksshop.responseMessage.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;
    private ResponseMessage rm;

    @GetMapping("/{id}")
    public ResponseEntity<?> getImageById(@PathVariable Long id){
        try{
            return new ResponseEntity<>(imageService.getImageById(id),HttpStatus.OK);
        }
        catch(ImageNotFoundException imageNotFoundException){
            return new ResponseEntity<>(imageNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<List<Image>> getImagesWithProductId(@PathVariable Long id){
        return new ResponseEntity<>(imageService.getImagesByProductId(id),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteImageById(@PathVariable Long id){
        rm = imageService.deleteImageById(id);
        return new ResponseEntity<>(rm, rm.getHttpStatus());
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> addImages(@RequestBody ImageModel imageModel, @RequestParam  List<MultipartFile> images){
        rm = imageService.saveImage(images,imageModel);
        return new ResponseEntity<>(rm,rm.getHttpStatus());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> updateImage(@PathVariable Long id, @RequestBody MultipartFile image){
        rm = imageService.updateImage(id,image);
        return new ResponseEntity<>(rm, rm.getHttpStatus());
    }

    @GetMapping("/image/download/{id}")
    public ResponseEntity<?> downloadImage(@PathVariable Long id){
        try{
            var image = imageService.getImageById(id);
            var resource = new ByteArrayResource(image.getImage().getBytes(1,(int) image.getImage().length()));
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+image.getFileName()+"\"")
                    .body(resource);
        }
        catch(ImageNotFoundException imageNotFoundException){
            return new ResponseEntity<>(
                    ResponseMessage.builder()
                    .type("error")
                    .message(imageNotFoundException.getMessage())
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build(),
                    HttpStatus.NOT_FOUND
            );
        }
        catch(Exception ex){
            return new ResponseEntity<>(
                    ResponseMessage.builder()
                            .type("error")
                            .message(ex.getMessage())
                            .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                            .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

}
