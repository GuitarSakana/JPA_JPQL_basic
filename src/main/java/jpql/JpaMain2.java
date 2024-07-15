package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain2 {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("홍길동");
            em.persist(member);

            Member member2 = new Member();
            member2.setUsername("이순신");
            em.persist(member2);

            Team team = new Team();
            team.setName("활빈당");
            em.persist(team);

            member.setTeam(team);

            em.flush();
            em.clear();

            String sql = "select m from Member m";
            List<Member> resultList = em.createQuery(sql, Member.class).getResultList();

            for (Member res : resultList) {
                if(res.getTeam() == null)System.out.println("소속 없음");
                else System.out.println(res.getTeam().getName());
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
