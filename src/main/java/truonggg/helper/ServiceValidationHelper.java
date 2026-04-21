package truonggg.helper;

import truonggg.handler.BusinessException;
import truonggg.response.ErrorCode;

import java.util.Optional;
import java.util.function.Supplier;

public final class ServiceValidationHelper {
    private ServiceValidationHelper() {
    }

    public static void validatePageAndSize(int page, int size, String domain) {
        if (page < 0 || size <= 0) {
            throw new BusinessException("Page must be >= 0 and size must be > 0", ErrorCode.BAD_REQUEST, domain);
        }
    }

    public static <T> T requireExists(Optional<T> optional, Supplier<BusinessException> exSupplier) {
        return optional.orElseThrow(exSupplier);
    }
}
