package assignment.application.infrastructure.member.repository;

import org.springframework.stereotype.Component;

import assignment.application.infrastructure.member.entity.MemberEntity;

@Component
public interface MemberEntityRepository {
	MemberEntity save(MemberEntity memberEntity);

	void isDuplicatedId(String id);

	MemberEntity findById(String id);
}
