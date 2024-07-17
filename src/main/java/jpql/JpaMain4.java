package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain4 {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        /* 패치조인 연습 및 예제 */
        try {
            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Member member = new Member();
            member.setUsername("회원1");
            member.setTeam(teamA);
            em.persist(member);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3  = new Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            //엔티티 직접 사용 - 기본키 값
            /*
            String sql = "select m from Member m where m=:member";
            List<Member> findMembers = em.createQuery(sql, Member.class)
                    .setParameter("member", member)
                    .getResultList();

            for (Member findMember : findMembers) {
                System.out.println(findMember);
            }*/


            //엔티티 직접 사용 - 외래키 값
            String sql = "select m from Member m where m.team =: team";
            List<Member> teamAMembers = em.createQuery(sql, Member.class)
                    .setParameter("team", teamA)
                    .getResultList();
            for (Member teamAMember : teamAMembers) {
                System.out.println(teamAMember);
            }


            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();


    }
}
