package truonggg.response;

public enum ErrorCode {

    // ===== SUCCESS =====
    OK,


    // ===== 4xx – CLIENT / BUSINESS =====
    BAD_REQUEST, // dữ liệu không hợp lệ
    NOT_FOUND, // không tìm thấy resource
    CONFLICT, // trùng dữ liệu
    ALREADY_EXIST, // thay thế ALREADY_EXIT
    FORBIDDEN, // bị cấm (business hoặc security)

    // ===== ACCOUNT =====
    ACCOUNT_INACTIVE, ACCOUNT_LOCKED, ACCOUNT_DISABLED,

    // ===== 5xx – SERVER =====
    INTERNAL_SERVER_ERROR,
}