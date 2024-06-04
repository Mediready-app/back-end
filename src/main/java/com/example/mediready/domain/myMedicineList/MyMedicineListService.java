package com.example.mediready.domain.myMedicineList;

import com.example.mediready.domain.folder.Folder;
import com.example.mediready.domain.folder.FolderRepository;
import com.example.mediready.domain.medicine.Medicine;
import com.example.mediready.domain.medicine.MedicineRepository;
import com.example.mediready.domain.myMedicineList.dto.GetMyMedicineRes;
import com.example.mediready.domain.myMedicineList.dto.ModifyMyMedicineReq;
import com.example.mediready.domain.myMedicineList.dto.AddMyMedicineReq;
import com.example.mediready.domain.user.User;
import com.example.mediready.global.config.exception.BaseException;
import com.example.mediready.global.config.exception.errorCode.FolderErrorCode;
import com.example.mediready.global.config.exception.errorCode.MedicineErrorCode;
import com.example.mediready.global.config.exception.errorCode.MyMedicineListErrorCode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MyMedicineListService {

    private final MyMedicineListRepository myMedicineListRepository;
    private final MedicineRepository medicineRepository;
    private final FolderRepository folderRepository;

    @Transactional
    public void addMyMedicine(User user, AddMyMedicineReq request) {
        if (myMedicineListRepository.existsByUserIdAndMedicineId(user.getId(),
            request.getMedicineId())) {
            throw new BaseException(MyMedicineListErrorCode.MEDICINE_ALREADY_EXISTS);
        }

        Medicine medicine = medicineRepository.findById(request.getMedicineId())
            .orElseThrow(() -> new BaseException(MedicineErrorCode.INVALID_MEDICINE_ID));

        Folder folder = folderRepository.findById(request.getFolderId())
            .orElseThrow(() -> new BaseException(FolderErrorCode.INVALID_FOLDER_ID));

        MyMedicineList myMedicineList = new MyMedicineList(
            null,
            request.getExpirationDate(),
            user,
            medicine,
            folder
        );
        myMedicineListRepository.save(myMedicineList);
    }

    @Transactional
    public void modifyMyMedicine(User user, Long id, ModifyMyMedicineReq request) {
        MyMedicineList myMedicineList = myMedicineListRepository.findById(id)
            .orElseThrow(
                () -> new BaseException(MyMedicineListErrorCode.MY_MEDICINE_LIST_NOT_FOUND));

        if (!myMedicineList.getUser().getId().equals(user.getId())) {
            throw new BaseException(MyMedicineListErrorCode.MY_MEDICINE_LIST_NOT_OWNED_BY_USER);
        }
        Folder newFolder = folderRepository.findById(request.getFolderId())
            .orElseThrow(
                () -> new BaseException(FolderErrorCode.INVALID_FOLDER_ID));

        myMedicineList.setExpirationDate(request.getExpirationTime());
        if (!myMedicineList.getFolder().getId().equals(request.getFolderId())) {
            myMedicineList.setFolder(newFolder);
        }
        myMedicineListRepository.save(myMedicineList);
    }

    @Transactional
    public void deleteMyMedicine(User user, Long id) {
        MyMedicineList myMedicineList = myMedicineListRepository.findById(id)
            .orElseThrow(
                () -> new BaseException(MyMedicineListErrorCode.MY_MEDICINE_LIST_NOT_FOUND));

        if (!myMedicineList.getUser().getId().equals(user.getId())) {
            throw new BaseException(MyMedicineListErrorCode.MY_MEDICINE_LIST_NOT_OWNED_BY_USER);
        }

        myMedicineListRepository.delete(myMedicineList);
    }

    public List<GetMyMedicineRes> getMyMedicineList(User user, Long folderId) {
        List<MyMedicineList> myMedicineLists = myMedicineListRepository.findByUserIdAndFolderId(
            user.getId(), folderId);

        return myMedicineLists.stream()
            .map(medicine -> new GetMyMedicineRes(medicine.getMedicine().getId(),
                medicine.getMedicine().getName(), medicine.getMedicine().getImgUrl(),
                medicine.getExpirationDate()))
            .collect(Collectors.toList());
    }
}
