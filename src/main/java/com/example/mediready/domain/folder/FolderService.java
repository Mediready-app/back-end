package com.example.mediready.domain.folder;

import com.example.mediready.domain.folder.dto.GetFolderRes;
import com.example.mediready.domain.folder.dto.PostFolderReq;
import com.example.mediready.domain.user.User;
import com.example.mediready.global.config.exception.BaseException;
import com.example.mediready.global.config.exception.errorCode.FolderErrorCode;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;

    @Transactional
    public void editFolderInfo(User user, List<PostFolderReq> postFolderReqList) {
        Set<String> folderNames = postFolderReqList.stream()
            .map(PostFolderReq::getName)
            .collect(Collectors.toSet());

        Set<Integer> folderPriorities = postFolderReqList.stream()
            .map(PostFolderReq::getPriority)
            .collect(Collectors.toSet());

        if (folderNames.size() != postFolderReqList.size()) {
            throw new BaseException(FolderErrorCode.FOLDER_NAME_DUPLICATE);
        }

        if (folderPriorities.size() != postFolderReqList.size()) {
            throw new BaseException(FolderErrorCode.FOLDER_PRIORITY_DUPLICATE);
        }

        List<Folder> existingFolders = folderRepository.findByUser(user);

        // 기존 폴더 목록에서 이름을 기준으로 매칭되는 폴더를 맵으로 저장
        Map<String, Folder> existingFolderMap = existingFolders.stream()
            .collect(Collectors.toMap(Folder::getName, folder -> folder));

        // 저장할 폴더 리스트
        List<Folder> foldersToSave = postFolderReqList.stream()
            .map(req -> {
                Folder existingFolder = existingFolderMap.get(req.getName());
                if (existingFolder != null) {
                    // 기존 폴더 업데이트
                    if (!existingFolder.getPriority().equals(req.getPriority())) {
                        existingFolder.setPriority(req.getPriority());
                        return existingFolder;
                    }
                    return null;  // 변경 사항이 없는 경우
                } else {
                    // 새로운 폴더 추가
                    return new Folder(null, req.getName(), req.getPriority(), user);
                }
            })
            .filter(Objects::nonNull)  // null 값 제거
            .collect(Collectors.toList());

        // 맵에 남아 있는 폴더는 요청 목록에 없는 폴더들이므로 삭제
        List<Folder> foldersToDelete = existingFolderMap.values().stream()
            .filter(existingFolder -> !folderNames.contains(existingFolder.getName()))
            .collect(Collectors.toList());

        // 트랜잭션 내에서 저장 및 삭제를 한 번에 처리
        try {
            if (!foldersToSave.isEmpty()) {
                folderRepository.saveAll(foldersToSave);
            }

            if (!foldersToDelete.isEmpty()) {
                folderRepository.deleteAll(foldersToDelete);
            }
        } catch (Exception e) {
            throw new BaseException(FolderErrorCode.DATABASE_ERROR);
        }
    }

    public List<GetFolderRes> getFoldersByUser(User user) {
        List<Folder> folders = folderRepository.findFoldersByUser(user);
        return folders.stream()
            .map(folder -> new GetFolderRes(folder.getId(), folder.getName(), folder.getPriority()))
            .collect(Collectors.toList());
    }
}
