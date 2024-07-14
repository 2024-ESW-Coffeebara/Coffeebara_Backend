package com.goldenengineering.coffeebara.device;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.goldenengineering.coffeebara.common.response.BaseResponse;
import com.goldenengineering.coffeebara.device.dto.request.SaveCapacityRequest;
import com.goldenengineering.coffeebara.device.dto.response.GetDeviceOnMapResponse;
import com.goldenengineering.coffeebara.device.exception.DeviceException;
import com.goldenengineering.coffeebara.device.validation.DeviceValidation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.goldenengineering.coffeebara.common.response.status.BaseExceptionResponseStatus.INVALID_DEVICE_INFO;

@Slf4j
@RestController
@RequestMapping("/device")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;
    private final DeviceValidation deviceValidation;

    @PatchMapping("")
    public BaseResponse saveCapacity(@Validated @RequestBody SaveCapacityRequest saveCapacityRequest, BindingResult bindingResult) {
        log.info("DeviceController.saveCapacity");

        if(bindingResult.hasErrors()) {
            throw new DeviceException(INVALID_DEVICE_INFO, bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }

        deviceValidation.isExistDeviceWithId(saveCapacityRequest.deviceId());

        deviceService.saveCapacity(saveCapacityRequest);

        return new BaseResponse<>();
    }

    @GetMapping("/map")
    public BaseResponse<GetDeviceOnMapResponse> getDeviceOnMap(
            @RequestParam Double southLeftLatitude,
            @RequestParam Double southLeftLongitude,
            @RequestParam Double northRightLatitude,
            @RequestParam Double northRightLongitude
    ) {
        log.info("DeviceController.getDeviceOnMap");

        deviceValidation.isValidLatitude(southLeftLatitude);
        deviceValidation.isValidLatitude(northRightLatitude);
        deviceValidation.isValidLongitude(southLeftLongitude);
        deviceValidation.isValidLongitude(northRightLongitude);

        return new BaseResponse<>(deviceService.getDeviceOnMap(southLeftLatitude,southLeftLongitude,northRightLatitude,northRightLongitude));
    }
}
