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

            /*
            List<Member> test = em.createQuery("select m from Member m where m.username =: username", Member.class)
                    .setParameter("username","홍길동")
                    .getResultList();

            for (Member member1 : test) {
                System.out.println(member1.getUsername());
            }*/

            /*List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
            for (Member findMember : result) {
                System.out.println(findMember.getUsername());
                System.out.println(findMember.getAge());
            }/*/

            /*List<Team> result = em.createQuery("select t from Member m join m.team t", Team.class).getResultList();
            if(result.isEmpty()) System.out.println("없어 이뇨석아!");*/

            /*List<Adress> result2 = em.createQuery("select o.adress from Order o", Adress.class).getResultList();*/

            List<MemberDto> result = em.createQuery("select new jpql.MemberDto(m.username, m.age) from Member m ", MemberDto.class).getResultList();
            for (MemberDto findMemberDto : result) {
                System.out.println(findMemberDto.getUsername());
                System.out.println(findMemberDto.getAge());
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
