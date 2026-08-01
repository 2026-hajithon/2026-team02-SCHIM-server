package com.hajithon.schim.guestbook;

import com.hajithon.schim.common.exception.BusinessException;
import com.hajithon.schim.common.exception.ErrorCode;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Component
public class GuestbookImageValidator {

    private static final long MAX_FILE_SIZE = 10L * 1024 * 1024;
    private static final int MAX_WIDTH = 2160;
    private static final int MAX_HEIGHT = 2880;
    private static final double TARGET_RATIO = 9.0 / 14.0;
    private static final double RATIO_TOLERANCE = 0.01;
    private static final int[] PNG_SIGNATURE = {0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A};

    public void validate(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            throw new BusinessException(ErrorCode.INVALID_IMAGE_FORMAT);
        }
        if (image.getSize() > MAX_FILE_SIZE) {
            throw new BusinessException(ErrorCode.IMAGE_TOO_LARGE);
        }

        byte[] bytes;
        try {
            bytes = image.getBytes();
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.INVALID_IMAGE_FORMAT);
        }

        if (!isPng(bytes)) {
            throw new BusinessException(ErrorCode.INVALID_IMAGE_FORMAT);
        }

        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new ByteArrayInputStream(bytes));
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.INVALID_IMAGE_FORMAT);
        }

        if (bufferedImage == null) {
            throw new BusinessException(ErrorCode.INVALID_IMAGE_FORMAT);
        }

        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        if (width > MAX_WIDTH || height > MAX_HEIGHT) {
            throw new BusinessException(ErrorCode.IMAGE_DIMENSION_EXCEEDED);
        }
    }

    private boolean isPng(byte[] bytes) {
        if (bytes.length < PNG_SIGNATURE.length) {
            return false;
        }
        for (int i = 0; i < PNG_SIGNATURE.length; i++) {
            if ((bytes[i] & 0xFF) != PNG_SIGNATURE[i]) {
                return false;
            }
        }
        return true;
    }
}