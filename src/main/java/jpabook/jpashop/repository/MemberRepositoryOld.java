package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryOld {

//    @PersistenceContext 스프링 부트 쓰면 autowired가 PersistenceContext 가능하게 지원으로 이 방식 가능
    private final EntityManager em;

//    public MemberRepository(EntityManager em) {
//        this.em = em;
//    }

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){
        // JPQL 작성 SQL이랑 문법 조금 다름
        // SQL은 테이블 대상으로 쿼리를 하는데
        // JPQL은 엔티티 객체 대상으로 쿼리함
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name){
        // setParameter로 JPQL의 name 바인딩 시켜줌
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
