package assignment.presentation.member.controller;

import static assignment.presentation.member.vo.request.MemberRequest.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import assignment.application.exception.GlobalExceptionHandler;
import assignment.application.service.member.service.MemberService;
import assignment.presentation.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Tag(name = "회원")
public class MemberController {
	private final MemberService memberService;

	@PostMapping()
	@Operation(summary = "회원가입 API", description = "회원 정보를 입력하여 가입을 진행합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "회원가입 성공",
			content = @Content(schema = @Schema(implementation = BaseResponse.class))),
		@ApiResponse(responseCode = "B001", description = "400 요청 데이터 형식 오류 (BadRequestException 발생)",
			content = @Content(schema = @Schema(implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(responseCode = "IDC001", description = "409 중복된 ID (ConflictException 발생)",
			content = @Content(schema = @Schema(implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(responseCode = "S500", description = "500 서버 오류 (ServerException 발생)",
			content = @Content(schema = @Schema(implementation = GlobalExceptionHandler.ErrorResponse.class)))
	})
	public ResponseEntity<BaseResponse<String>> saveMember(@RequestBody SaveMemberRequest request) {
		// Request 유효성 검사
		request.validateSaveMemberRequest();

		memberService.saveMember(request.toSaveMemberRequestDto());

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), "SUCCESS"));
	}
}
