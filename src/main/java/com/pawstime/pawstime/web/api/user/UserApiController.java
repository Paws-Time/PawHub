package com.pawstime.pawstime.web.api.user;

import com.pawstime.pawstime.domain.user.facade.UserFacade;
import com.pawstime.pawstime.global.common.ApiResponse;
import com.pawstime.pawstime.global.enums.Status;
import com.pawstime.pawstime.global.exception.CustomException;
import com.pawstime.pawstime.web.api.user.dto.req.LoginUserReqDto;
import com.pawstime.pawstime.web.api.user.dto.req.UserCreateReqDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "USER API", description = "유저 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserApiController {

  private final UserFacade userFacade;

  @Operation(summary = "회원 가입")
  @PostMapping("/member")
  public ResponseEntity<ApiResponse<Void>> createUser(@Valid @RequestBody UserCreateReqDto req) {
    try {
      userFacade.createUser(req);
      return ApiResponse.generateResp(Status.CREATE, "회원가입이 완료되었습니다.", null);
    } catch (CustomException e) {
      Status status = Status.valueOf(e.getClass()
          .getSimpleName()
          .replace("Exception", "")
          .toUpperCase());
      return ApiResponse.generateResp(status, e.getMessage(), null);

    } catch (Exception e) {
      return ApiResponse.generateResp(
          Status.ERROR, "회원가입 중 오류가 발생하였습니다 : " + e.getMessage(), null);
    }
  }

  @Operation(summary = "로그인")
  @PostMapping("/login")
  public ResponseEntity<ApiResponse<String>> loginUser(@Valid @RequestBody LoginUserReqDto req) {
    try {
      String token = userFacade.login(req);
      return ApiResponse.generateResp(Status.SUCCESS, null, token);
    } catch (CustomException e) {
      Status status = Status.valueOf(e.getClass()
          .getSimpleName()
          .replace("Exception", "")
          .toUpperCase());
      return ApiResponse.generateResp(status, e.getMessage(), null);

    } catch (Exception e) {
      return ApiResponse.generateResp(
          Status.ERROR, "로그인 중 오류가 발생하였습니다 : " + e.getMessage(), null);
    }
  }

  @Operation(summary = "로그아웃", security = @SecurityRequirement(name = "bearerAuth"))
  @PostMapping("/logout")
  public ResponseEntity<ApiResponse<Void>> logoutUser(Authentication authentication, HttpServletRequest request) {
    try {
      userFacade.logout(authentication, request);
      return ApiResponse.generateResp(Status.SUCCESS, "로그아웃 되었습니다.", null);
    } catch (CustomException e) {
      Status status = Status.valueOf(e.getClass()
          .getSimpleName()
          .replace("Exception", "")
          .toUpperCase());
      return ApiResponse.generateResp(status, e.getMessage(), null);

    } catch (Exception e) {
      return ApiResponse.generateResp(
          Status.ERROR, "로그아웃 중 오류가 발생하였습니다 : " + e.getMessage(), null);
    }
  }
}
