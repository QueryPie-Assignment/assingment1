package assignment.application.service.member.service;

import static assignment.application.service.member.dto.request.MemberRequestDto.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import assignment.application.infrastructure.member.entity.MemberEntity;
import assignment.application.infrastructure.member.repository.MemberEntityRepository;
import assignment.application.service.member.mapper.MemberServiceMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberEntityRepository memberEntityRepository;

	private final MemberServiceMapper memberServiceMapper;

	private final BCryptPasswordEncoder passwordEncoder;

	public void saveMember(SaveMemberRequestDto requestDto) {
		/*
		 유효성 검사
		 1. Member id 중복 확인
		*/
		memberEntityRepository.isDuplicatedId(requestDto.id());

		// 비밀번호 암호화
		MemberEntity memberEntity
			= memberServiceMapper.toMemberEntity(requestDto, passwordEncoder.encode(requestDto.password()));

		memberEntityRepository.save(memberEntity);
	}
}
