package com.base.rest.handel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "API 統一響應結構")
public class ApiResponse<T> {

    @Schema(description = "業務響應碼 (0: 成功, 其他: 錯誤)", example = "0")
    private Integer code; // 業務錯誤碼，非 HTTP 狀態碼

    @Schema(description = "響應訊息", example = "操作成功")
    private String msg;

    @Schema(description = "響應數據載體")
    private T data;

    /**
     * 創建一個成功的響應 (無數據)
     */
    public static <T> ApiResponse<T> success() {
        return ApiResponse.<T>builder()
            .code(0)
            .msg("操作成功")
            .data(null)
            .build();
    }

    /**
     * 創建一個成功的響應 (含數據)
     */
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
            .code(0)
            .msg("操作成功")
            .data(data)
            .build();
    }

    /**
     * 創建一個錯誤的響應
     */
    public static <T> ApiResponse<T> error(Integer code, String msg) {
        return ApiResponse.<T>builder()
            .code(code)
            .msg(msg)
            .data(null)
            .build();
    }
}
