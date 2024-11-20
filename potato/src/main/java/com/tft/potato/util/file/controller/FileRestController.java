package com.tft.potato.util.file.controller;

import com.tft.potato.aop.exception.ErrorEnum;
import com.tft.potato.common.vo.ApiResponse;
import com.tft.potato.util.file.entity.FileEntity;
import com.tft.potato.util.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FileRestController {

    private final FileService fileService;

    @PostMapping("/file/list")
    public ResponseEntity<ApiResponse> showFileList(@RequestBody String path){

        Optional<List<FileEntity>> fileList = fileService.searchFilesUnderPath(path);
        ApiResponse apiResponse = null;

        if(fileList.isPresent()){
            apiResponse = ApiResponse.builder()
                                            .response(fileList.get())
                                            .errorCode(ErrorEnum.OK.getStatusCode())
                                            .errorDescription(ErrorEnum.OK.getMessage())
                                            .build();
        }else{
            apiResponse = ApiResponse.builder()
                                    .response(fileList.get())
                                    .errorCode(ErrorEnum.NO_SUCH_FILE_ERROR.getStatusCode())
                                    .errorDescription(ErrorEnum.NO_SUCH_FILE_ERROR.getMessage())
                                    .build();
        }

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/file/upload")
    public ResponseEntity<ApiResponse> uploadFile(@RequestParam MultipartFile multipartFile){

        ApiResponse apiResponse = null;

        try{
            FileEntity file = fileService.saveFile(multipartFile);
            apiResponse = ApiResponse.builder()
                                    .response(file)
                                    .errorCode(ErrorEnum.OK.getStatusCode())
                                    .errorDescription(ErrorEnum.OK.getMessage())
                                    .build();
        }catch (IOException e){
            log.error("An error occured during uploading a file.", e);
            apiResponse = ApiResponse.builder()
                    .response("Upload Failed.")
                    .errorCode(ErrorEnum.FILE_UPLOAD_ERROR.getStatusCode())
                    .errorDescription(ErrorEnum.FILE_UPLOAD_ERROR.getMessage())
                    .build();
        }catch (Exception e){
            log.error("Unchecked error occured during uploading a file.", e);
            apiResponse = ApiResponse.builder()
                    .response(e.getMessage())
                    .errorCode(ErrorEnum.FILE_UPLOAD_ERROR.getStatusCode())
                    .errorDescription(ErrorEnum.FILE_UPLOAD_ERROR.getMessage())
                    .build();
        }

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
