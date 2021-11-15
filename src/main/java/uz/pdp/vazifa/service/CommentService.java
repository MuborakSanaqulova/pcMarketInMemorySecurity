package uz.pdp.vazifa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.vazifa.entity.Comment;
import uz.pdp.vazifa.entity.User;
import uz.pdp.vazifa.payload.CommentDto;
import uz.pdp.vazifa.repository.CommentRepository;

import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserService userService;

    public Page<Comment> getAll(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    public Comment getById(Integer id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        return optionalComment.orElse(null);
    }

    public Comment add(CommentDto commentDto) {
        User byId = userService.getById(commentDto.getUserId());
        if (byId==null)
            return null;
        Comment comment =new Comment();
        comment.setText(commentDto.getText());
        comment.setUser(byId);
        commentRepository.save(comment);
        return comment;
    }

    public Comment edit(Integer id, CommentDto commentDto) {
        User byId = userService.getById(commentDto.getUserId());
        if (byId==null)
            return null;
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            Comment comment =new Comment();
            comment.setId(id);
            comment.setText(commentDto.getText());
            comment.setUser(byId);
            commentRepository.save(comment);
            return comment;
        }
        return null;
    }

    public boolean delete(Integer id) {
        try {
            commentRepository.deleteById(id);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

}
