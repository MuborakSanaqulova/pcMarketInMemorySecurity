package uz.pdp.vazifa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.vazifa.entity.Comment;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
