package assignment.application.infrastructure.member.repository;

import org.springframework.stereotype.Component;

import assignment.application.exception.message.ErrorResult;
import assignment.application.exception.status.ConflictException;
import assignment.application.infrastructure.member.entity.MemberEntity;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberEntityRepositoryImpl implements MemberEntityRepository {
	private final MemberEntityJpaRepository memberEntityJpaRepository;

	@Override
	public MemberEntity save(MemberEntity memberEntity) {
		return memberEntityJpaRepository.save(memberEntity);
	}

	@Override
	public void isDuplicatedId(String id) {
		memberEntityJpaRepository.findById(id)
			.ifPresent(entity -> {
				throw new ConflictException(ErrorResult.ID_DUPLICATION_CONFLICT_EXCEPTION);
			});
	}
}
