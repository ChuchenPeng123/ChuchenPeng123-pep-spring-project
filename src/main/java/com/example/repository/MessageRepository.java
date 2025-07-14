//package com.example.repository;

//public interface MessageRepository {
//}
package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Message entities. Extends JpaRepository to provide standard CRUD operations.
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findAllMessagesByPostedBy(int postedBy);

}
