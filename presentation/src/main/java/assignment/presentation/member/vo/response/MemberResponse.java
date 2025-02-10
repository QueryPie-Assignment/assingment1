package assignment.presentation.member.vo.response;

public class MemberResponse {
	public record LoginMemberResponse(
		String accessToken,
		String refreshToken
	) {

	}
}
