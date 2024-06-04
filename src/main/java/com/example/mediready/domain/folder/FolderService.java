package com.example.mediready.domain.folder;

import com.example.mediready.domain.folder.dto.GetFolderRes;
import com.example.mediready.domain.user.User;
import com.example.mediready.global.config.exception.BaseException;
import com.example.mediready.global.config.exception.errorCode.FolderErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;

    public void addFolder(User user, String name) {
        Folder lastFolder = folderRepository.findTopByUserIdOrderByPriorityDesc(user.getId())
            .orElseThrow(() -> new BaseException(FolderErrorCode.NO_FOLDER_FOUND));

        folderRepository.save(new Folder(name, lastFolder.getPriority(), user));
    }

    public List<GetFolderRes> getFoldersByUser(User user) {
        List<Folder> folders = folderRepository.findFoldersByUser(user);
        return folders.stream()
            .map(folder -> new GetFolderRes(folder.getId(), folder.getName(), folder.getPriority()))
            .collect(Collectors.toList());
    }
}
