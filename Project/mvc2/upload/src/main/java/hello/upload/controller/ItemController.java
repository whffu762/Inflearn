package hello.upload.controller;

import hello.upload.DTO.ItemForm;
import hello.upload.Entity.Item;
import hello.upload.Repository.ItemRepository;
import hello.upload.DTO.UploadFile;
import hello.upload.file.FileStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;
    private final FileStore fileStore;

    @GetMapping("/items/new")
    public String newItem(){
        return "item-form";
    }

    @PostMapping("/items/new")
    public String saveItem(@ModelAttribute ItemForm itemForm, RedirectAttributes redirectAttributes) throws IOException {
        MultipartFile attachFile = itemForm.getAttachFile();
        UploadFile uploadFile = fileStore.storeFile(attachFile);

        List<MultipartFile> imageFiles = itemForm.getImageFiles();
        List<UploadFile> uploadFiles = fileStore.storeFiles(imageFiles);

        //DB 저장
        Item item = new Item();
        item.setItemName(itemForm.getItemName());
        item.setAttachFile(uploadFile);
        item.setImageFiles(uploadFiles);

        itemRepository.save(item);

        redirectAttributes.addAttribute("itemId", item.getId());

        return "redirect:/items/{itemId}";
    }

    //결과창 프레임
    @GetMapping("/items/{id}")
    public String items(@PathVariable Long id, Model model){
        Item item = itemRepository.findById(id);
        model.addAttribute("item", item);

        return "item-view";
    }



    //결과창 구성 이미지 출력
    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFilePath(filename));
    }

    //결과창 구성 이미지 다운
    //@ResponseBody
    @GetMapping("/attach/{itemId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long itemId) throws MalformedURLException {
        Item item = itemRepository.findById(itemId);
        String storeFileName = item.getAttachFile().getStoreFileName();
        String uploadFileName = item.getAttachFile().getUploadFileName();

        UrlResource urlResource = new UrlResource("file:" + fileStore.getFilePath(storeFileName));

        log.info("uploadFileName = {}", uploadFileName);

        //파일명 깨지는거 방지
        String encodeUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);

        //다운로드 되도록 하기 위한 header 설정(프로토콜임)
        String contentDisposition = "attachment; filename=\"" + encodeUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(urlResource);

    }
}
