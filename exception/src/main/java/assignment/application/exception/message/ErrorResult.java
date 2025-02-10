package assignment.application.exception.message;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorResult {

	// 400 BAD REQUEST
	DTO_BAD_REQUEST_EXCEPTION(
		HttpStatus.BAD_REQUEST.value(),
		"요청 데이터 형식 오류",
		"B001"
	),

	ID_PASSWORD_MISMATCH_BAD_REQUEST_EXCEPTION(
		HttpStatus.BAD_REQUEST.value(),
		"아이디 비밀번호가 틀렸습니다.",
		"IPMB002"
	),

	// 404 NOT FOUND
	ID_NOT_FOUND_EXCEPTION(
		HttpStatus.NOT_FOUND.value(),
		"존재하지 않는 아이디입니다.",
		"INF001"
	),

	// 409 CONFLICT ERROR
	ID_DUPLICATION_CONFLICT_EXCEPTION(
		HttpStatus.CONFLICT.value(),
		"중복된 ID 값 입니다.",
		"IDC001"
	),

	// 500 SERVER ERROR
	UNKNOWN_EXCEPTION(
		HttpStatus.INTERNAL_SERVER_ERROR.value(),
		"서버 오류",
		"S500"
	),

	;

	private final int httpStatus;
	private final String message;
	private final String code;
}
