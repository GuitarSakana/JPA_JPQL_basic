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
                Team team = new Team();
                team.setName("teamA");
                em.persist(team);

                Member member = new Member();
                member.setUsername("홍길동");
                member.setAge(20);
                member.setTeam(team);
                member.setType(MemberType.ADMIN);
                em.persist(member);

                Member member2 = new Member();
                member2.setUsername("이순신");
                member2.setAge(15);
                em.persist(member2);

                Member member3 = new Member();
                member3.setUsername("이몽룡");
                member3.setAge(25);
                em.persist(member3);

            /*  TypeQuery, Query 예제
            TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
            TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);
            Query query3 = em.createQuery("select m.age, m.username from Member m");
            */


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


            /*  new 오퍼레이션 사용 예제
            List<MemberDto> result = em.createQuery("select new jpql.MemberDto(m.username, m.age) from Member m ", MemberDto.class).getResultList();
            for (MemberDto findMemberDto : result) {
                System.out.println(findMemberDto.getUsername());
                System.out.println(findMemberDto.getAge());
            }
            */


            /*페이징 처리 실습 예제
            List<Member> results = em.createQuery("select m from Member m order by m.age desc", Member.class)
                                     .setFirstResult(0)
                                     .setMaxResults(10)
                                     .getResultList();
            System.out.println(results.size());

            for (Member findMember : results) {
                System.out.println("Member = "+findMember.toString());
            }
            */

            /* 조인 예제
            List<Member> resultList = em.createQuery("select m from Member m join m.team t", Member.class)
                    .getResultList();
            for (Member member1 : resultList) {
                System.out.println(member1.getTeam().getName());
            }*/

            /* 이넘 타입으로 조회 해오기 예제
            List<Object[]> resultList = em.createQuery("select m.username,'Hello',true from Member m where m.type = jpql.MemberType.ADMIN")
                    .getResultList();

            if(resultList.isEmpty()) System.out.println("good");

            for (Object[] objects : resultList) {
                System.out.println(objects[0]);
                System.out.println(objects[1]);
                System.out.println(objects[2]);
            }*/


            List<String> resultList = em.createQuery("select function('group_concat',m.username) " +
                                                        "from Member m", String.class)
                    .getResultList();
            if(resultList.isEmpty()) System.out.println("good");

            for (String s : resultList) {
                System.out.println(s);
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
