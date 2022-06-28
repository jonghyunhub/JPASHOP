package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    /**
     * Item은 JPA에 저장하기 전까지 아이디가 없기 때문에 먼저 아이디가 존재하는지 여부부터 확인
     */
    public void save(Item item) {
        if (item.getId() == null) {
            //완전히 새로 생성한 객체일때
            em.persist(item);
        } else {
            //디비에 등록된걸 가져온 경우 => 덮어쓰기(update 느낌)
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
