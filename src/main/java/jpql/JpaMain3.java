package jpql;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

public class JpaMain3 {

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
        //다대일 패치 조인
        /*    String sql = "select m from Member m join fetch m.team";
            List<Member> resultList = em.createQuery(sql, Member.class).getResultList();

            for (Member findMember : resultList) {
                System.out.println(findMember.getUsername()+" "+findMember.getTeam().getName());
                //회원1, 팀A (SQL)
                //회원2, 팀A (1차 캐시)
                //회원3, 팀B (SQL)

                //회원100명 조회 -> n+1
            }
         */
            
        // 일대다 패치 조인 예제
            String sql ="select distinct t from Team t join fetch t.members";
            List<Team> resultList = em.createQuery(sql, Team.class).getResultList();

            for (Team team : resultList) {
                List<Member> members = team.getMembers();
                for (Member findMember : members) {
                    System.out.println(team.getName()+" "+findMember.getUsername());
                }
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
