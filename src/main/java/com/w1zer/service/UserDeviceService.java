package com.w1zer.service;

import com.w1zer.entity.RefreshToken;
import com.w1zer.entity.UserDevice;
import com.w1zer.exception.TokenRefreshException;
import com.w1zer.payload.DeviceInfo;
import com.w1zer.repository.UserDeviceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDeviceService {
    public static final String NO_DEVICE_FOUND = "No device found for the matching token. Please login again";
    public static final String REFRESH_BLOCKED =
            "Refresh blocked for this device. Please login through different device";
    private final UserDeviceRepository userDeviceRepository;

    public UserDeviceService(UserDeviceRepository userDeviceRepository) {
        this.userDeviceRepository = userDeviceRepository;
    }

    public Optional<UserDevice> findByProfileId(Long userId) {
        return userDeviceRepository.findByProfileId(userId);
    }

    public Optional<UserDevice> findByRefreshToken(RefreshToken refreshToken) {
        return userDeviceRepository.findByRefreshToken(refreshToken);
    }

    public UserDevice createUserDevice(DeviceInfo deviceInfo) {
        UserDevice userDevice = new UserDevice();
        userDevice.setBrowserName(deviceInfo.browserName());
        userDevice.setDeviceType(deviceInfo.deviceType());
        userDevice.setIsRefreshActive(true);
        return userDevice;
    }

    public void verifyRefreshAvailability(RefreshToken refreshToken) {
        UserDevice userDevice = findByRefreshToken(refreshToken).orElseThrow(
                () -> new TokenRefreshException(refreshToken.getToken(), NO_DEVICE_FOUND)
        );
        if (!userDevice.getIsRefreshActive()) {
            throw new TokenRefreshException(refreshToken.getToken(), REFRESH_BLOCKED);
        }
    }
}
