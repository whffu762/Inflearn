package hello.upload.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadFile {

    //client 가 업로드한 파일명
    private String uploadFileName;

    //서버에 저장되는 파일명
    private String storeFileName;

}
