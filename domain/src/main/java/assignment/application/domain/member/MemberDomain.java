package assignment.application.domain.member;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import assignment.application.exception.message.ErrorResult;
import assignment.application.exception.status.BadRequestException;
import assignment.application.exception.status.UnAuthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberDomain {
	private final RedisTemplate<String, String> redisTemplate;
	private final RedisTemplate<String, String> blackListTemplate;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	@Value("${jwt.secret}")
	private String jwtSecret;
	@Value("${jwt.accessTokenExpiration}")
	private long accessTokenExpiration;
	@Value("${jwt.refreshTokenExpiration}")
	private long refreshTokenExpiration;

	public void checkIdPassword(String inputId, String inputPassword, String id, String encodedPassword) {
		// 아이디가 일치하는지 확인
		if (!inputId.equals(id)) {
			throw new BadRequestException(ErrorResult.ID_PASSWORD_MISMATCH_BAD_REQUEST_EXCEPTION);
		}

		// 비밀번호가 일치하는지 확인 (BCrypt 암호화 검증)
		if (!bCryptPasswordEncoder.matches(inputPassword, encodedPassword)) {
			throw new BadRequestException(ErrorResult.ID_PASSWORD_MISMATCH_BAD_REQUEST_EXCEPTION);
		}
	}

	public String generateAccessToken(String id) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + accessTokenExpiration);

		return Jwts.builder()
			.setSubject(id)
			.setIssuedAt(now)
			.setExpiration(expiryDate)
			.signWith(SignatureAlgorithm.HS512, jwtSecret)
			.compact();
	}

	public String generateRefreshToken(String id) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + refreshTokenExpiration);

		return Jwts.builder()
			.setSubject(id)
			.setIssuedAt(now)
			.setExpiration(expiryDate)
			.signWith(SignatureAlgorithm.HS512, jwtSecret)
			.compact();
	}

	public void saveRefreshToken(String refreshToken, String id) {
		redisTemplate.opsForValue()
			.set(
				id,
				refreshToken,
				refreshTokenExpiration,
				TimeUnit.MILLISECONDS
			);
	}

	public long getTokenRemainTime(String token) {
		Jws<Claims> claimsJws = Jwts.parser()
			.setSigningKey(jwtSecret)
			.parseClaimsJws(token);

		Claims claims = claimsJws.getBody();
		Date expiration = claims.getExpiration();
		long now = System.currentTimeMillis();

		return expiration.getTime() - now;
	}

	public void saveBlackListToken(String token, String id, long remainTime) {
		blackListTemplate.opsForValue()
			.set(
				token,
				id,
				remainTime,
				TimeUnit.MILLISECONDS
			);
	}

	public void deleteRefreshToken(String id) {
		redisTemplate.delete(id);
	}

	public void checkRefreshToken(String refreshToken) {
		try {
			Jws<Claims> claimsJws = Jwts.parser()
				.setSigningKey(jwtSecret)
				.parseClaimsJws(refreshToken);
			Claims claims = claimsJws.getBody();

			// RefreshToken 에서 id 추출
			String id = claims.getSubject();

			// email 로 refreshToken 값 확인
			String storedRefreshToken = redisTemplate.opsForValue().get(id);
			if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
				redisTemplate.delete(id);
				throw new UnAuthorizedException(ErrorResult.TOKEN_UNAUTHORIZED_EXCEPTION);
			}
		} catch (JwtException e) {
			throw new UnAuthorizedException(ErrorResult.TOKEN_UNAUTHORIZED_EXCEPTION);
		}
	}
}
