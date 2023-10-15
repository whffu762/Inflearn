package hello.upload.Entity;

import hello.upload.DTO.UploadFile;
import lombok.Data;

import java.util.List;

@Data
public class Item {

    private Long id;
    private String itemName;
    private UploadFile attachFile;
    private List<UploadFile> imageFiles;
}
