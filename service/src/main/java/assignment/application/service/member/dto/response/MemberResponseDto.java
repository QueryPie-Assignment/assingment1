package assignment.application.service.member.dto.response;

public class MemberResponseDto {
	public record LoginMemberResponseDto(
		String accessToken,
		String refreshToken
	) {

	}
}
