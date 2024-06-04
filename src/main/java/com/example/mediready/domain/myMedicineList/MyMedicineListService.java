package com.example.mediready.domain.myMedicineList;

import com.example.mediready.domain.folder.Folder;
import com.example.mediready.domain.folder.FolderRepository;
import com.example.mediready.domain.medicine.Medicine;
import com.example.mediready.domain.medicine.MedicineRepository;
import com.example.mediready.domain.myMedicineList.dto.SaveMyMedicineReq;
import com.example.mediready.domain.user.User;
import com.example.mediready.global.config.exception.BaseException;
import com.example.mediready.global.config.exception.errorCode.FolderErrorCode;
import com.example.mediready.global.config.exception.errorCode.MedicineErrorCode;
import com.example.mediready.global.config.exception.errorCode.MyMedicineListErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyMedicineListService {

    private final MyMedicineListRepository myMedicineListRepository;
    private final MedicineRepository medicineRepository;
    private final FolderRepository folderRepository;

    public void saveMyMedicine(User user, SaveMyMedicineReq request) {
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
}
