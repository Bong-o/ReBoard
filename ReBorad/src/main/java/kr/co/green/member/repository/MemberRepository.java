package kr.co.green.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.green.member.entity.MemberEntity;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, String> {
    
    boolean existsById(String memberId);
    
    Optional<MemberEntity> findById(String id);
    
}
