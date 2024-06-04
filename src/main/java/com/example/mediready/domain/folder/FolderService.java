package com.example.mediready.domain.folder;

import com.example.mediready.domain.folder.dto.GetFolderRes;
import com.example.mediready.domain.folder.dto.ModifyFolderPriorityReq;
import com.example.mediready.domain.myMedicineList.MyMedicineList;
import com.example.mediready.domain.myMedicineList.MyMedicineListRepository;
import com.example.mediready.domain.user.User;
import com.example.mediready.global.config.exception.BaseException;
import com.example.mediready.global.config.exception.errorCode.FolderErrorCode;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;
    private final MyMedicineListRepository myMedicineListRepository;

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

    public void deleteFolder(User user, Long id) {
        Folder folder = folderRepository.findByIdAndUser(id, user)
            .orElseThrow(() -> new BaseException(FolderErrorCode.FOLDER_NOT_OWNED_BY_USER));

        Folder defaultFolder = folderRepository.findByUserAndPriority(user, 0);
        List<MyMedicineList> myMedicineLists = myMedicineListRepository.findByUserIdAndFolderId(
            user.getId(), id);
        for (MyMedicineList myMedicineList : myMedicineLists) {
            myMedicineList.setFolder(defaultFolder);
            myMedicineListRepository.save(myMedicineList);
        }

        folderRepository.delete(folder);
    }

    public void modifyFolderName(User user, Long id, String name) {
        Folder folder = folderRepository.findByIdAndUser(id, user)
            .orElseThrow(() -> new BaseException(FolderErrorCode.FOLDER_NOT_OWNED_BY_USER));
        folder.setName(name);
        folderRepository.save(folder);
    }

    public void modifyFolderPriority(User user,
        List<ModifyFolderPriorityReq> modifyFolderPriorityReqs) {
        List<Folder> folderList = folderRepository.findFoldersByUser(user);

        Map<Long, Integer> priorityMap = modifyFolderPriorityReqs.stream()
            .collect(Collectors.toMap(ModifyFolderPriorityReq::getId,
                ModifyFolderPriorityReq::getPriority));

        folderList.forEach(folder -> {
            if (priorityMap.containsKey(folder.getId())) {
                folder.setPriority(priorityMap.get(folder.getId()));
            }
        });

        folderRepository.saveAll(folderList);
    }
}
