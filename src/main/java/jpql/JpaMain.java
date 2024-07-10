package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
                Member member = new Member();
                member.setUsername("홍길동");
                member.setAge(20);
                em.persist(member);

            TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
            TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);
            Query query3 = em.createQuery("select m.age, m.username from Member m");
            /*
            List<Member> resultList = query.getResultList();
            for (Member member1 : resultList) {
                System.out.println(member1);
            }*/
            List<Member> test = em.createQuery("select m from Member m where m.username =: username", Member.class)
                    .setParameter("username","홍길동")
                    .getResultList();

            for (Member member1 : test) {
                System.out.println(member1.getUsername());
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
