package sosteam.throwapi.domain.qr.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sosteam.throwapi.domain.qr.controller.request.QrMakeRequest;
import sosteam.throwapi.domain.qr.service.QrModifyService;
import sosteam.throwapi.domain.store.entity.Store;
import sosteam.throwapi.domain.store.repository.repo.StoreRepository;
import sosteam.throwapi.domain.user.controller.request.user.UserSaveRequest;
import sosteam.throwapi.domain.user.entity.SNSCategory;
import sosteam.throwapi.domain.user.entity.dto.user.UserSaveDto;
import sosteam.throwapi.domain.user.service.MileageModifyService;
import sosteam.throwapi.global.entity.Role;
import sosteam.throwapi.global.entity.UserStatus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Controller
@Slf4j
@RequestMapping("/qr")
@RequiredArgsConstructor
public class QRController {

    private final QrModifyService qrModifyService;



    @PostMapping("/makeqr")
    public ResponseEntity<byte[]> qrToStore(
            @RequestBody @Valid QrMakeRequest params
    ) throws WriterException, IOException {
        // QR 정보
        int width = 200;
        int height = 200;
        UUID extStoreId = params.getExtStoreId();

        // QR Code - BitMatrix: qr code 정보 생성
        BitMatrix encode = new MultiFormatWriter()
                .encode(extStoreId.toString(), BarcodeFormat.QR_CODE, width, height);

        // QR Code - Image 생성. : 1회성으로 생성해야 하기 때문에
        // stream으로 Generate(1회성이 아니면 File로 작성 가능.)
        try {
            //output Stream
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            //Bitmatrix, file.format, outputStream
            MatrixToImageWriter.writeToStream(encode, "PNG", out);

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(out.toByteArray());

        }catch (Exception e){log.warn("QR Code OutputStream 도중 Excpetion 발생, {}", e.getMessage());}

        return null;
    }

    @PostMapping("/mileageup")
    public ResponseEntity<String> mileageUp(
            @RequestBody @Valid QrMakeRequest params
    ){
        UUID extStoreId = params.getExtStoreId();

        qrModifyService.qrModify(extStoreId);

        return ResponseEntity.ok("마일리지 변경 완료");
    }

}
