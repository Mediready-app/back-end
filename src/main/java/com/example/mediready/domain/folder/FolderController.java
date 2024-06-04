package com.example.mediready.domain.folder;

import com.example.mediready.domain.folder.dto.GetFolderRes;
import com.example.mediready.domain.folder.dto.EditFolderReq;
import com.example.mediready.domain.user.User;
import com.example.mediready.global.config.BaseResponse;
import com.example.mediready.global.config.auth.AuthUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/folders")
public class FolderController {

    private final FolderService folderService;

    @PostMapping
    public BaseResponse<String> editFolderInfo(@AuthUser User user,
        @RequestBody List<EditFolderReq> editFolderReqList) {
        folderService.editFolderInfo(user, editFolderReqList);
        return new BaseResponse<>("성공적으로 폴더 정보를 수정했습니다.");
    }

    @GetMapping
    public BaseResponse<List<GetFolderRes>> getFoldersByUser(@AuthUser User user) {
        return new BaseResponse<>("유저의 폴더 리스트입니다.", folderService.getFoldersByUser(user));
    }
}
