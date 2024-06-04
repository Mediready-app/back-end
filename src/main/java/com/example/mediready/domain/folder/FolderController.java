package com.example.mediready.domain.folder;

import com.example.mediready.domain.folder.dto.GetFolderRes;
import com.example.mediready.domain.user.User;
import com.example.mediready.global.config.BaseResponse;
import com.example.mediready.global.config.auth.AuthUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/folders")
public class FolderController {

    private final FolderService folderService;

    @PostMapping
    public BaseResponse<String> addFolder(@AuthUser User user,
        @RequestParam String name) {
        folderService.addFolder(user, name);
        return new BaseResponse<>("폴더가 추가되었습니다.");
    }

    @GetMapping
    public BaseResponse<List<GetFolderRes>> getFoldersByUser(@AuthUser User user) {
        return new BaseResponse<>("유저의 폴더 리스트입니다.", folderService.getFoldersByUser(user));
    }

    @DeleteMapping("/{id}")
    public BaseResponse<String> deleteFolder(@AuthUser User user,
        @PathVariable Long id) {
        folderService.deleteFolder(user, id);
        return new BaseResponse<>("폴더가 삭제되었습니다.");
    }
}
