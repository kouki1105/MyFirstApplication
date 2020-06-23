package com.example.myfirstapplication.app.comment;

import java.util.List;

import com.example.myfirstapplication.entity.Comment;
import com.example.myfirstapplication.entity.Idea;
import com.example.myfirstapplication.repository.CommentRepository;
import com.example.myfirstapplication.repository.IdeaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ideas/{idea_id}/comments")
public class CommentController {
	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private IdeaRepository ideaRepository;

	@GetMapping
	public String index(@PathVariable Long idea_id, Model model) {
		List<Comment> comments = commentRepository.findByIdeaId(idea_id);
		Idea idea = ideaRepository.findById(idea_id).orElse(null);
		model.addAttribute("comments", comments);
		model.addAttribute("idea", idea);
		model.addAttribute("title", "Comment Index");
		return "comment/index";
	}

    @GetMapping("new")
    public String newComment(@PathVariable Long idea_id, Model model) {
		Idea idea = ideaRepository.findById(idea_id).orElse(null);
		model.addAttribute("idea", idea);
        model.addAttribute("title", "New Comment");
        return "comment/new";
	}

    @PostMapping
    public String create(@PathVariable Long idea_id, @ModelAttribute Comment comment) {
		Idea idea = ideaRepository.findById(idea_id).orElse(null);
		comment.setIdea(idea);
        commentRepository.save(comment);
        return "redirect:/ideas/{idea_id}/comments";
	}

    @GetMapping("{id}/edit")
    public String edit(@PathVariable Long idea_id,@PathVariable Long id, Model model) {
        Idea idea = ideaRepository.findById(idea_id).orElse(null);
		Comment comment = commentRepository.findById(id).orElse(null);
		model.addAttribute("comment", comment);
        model.addAttribute("idea", idea);
        model.addAttribute("title", "Edit Comment");
        return "comment/edit";
    }

    @PutMapping("{id}")
    public String update(@PathVariable Long idea_id,@PathVariable Long id, @ModelAttribute Comment comment) {
		Idea idea = ideaRepository.findById(idea_id).orElse(null);
		comment.setIdea(idea);
        comment.setId(id);
        ideaRepository.save(idea);
        return "redirect:/ideas/{idea_id}/comments";
	}

    @GetMapping("{id}")
    public String show(@PathVariable Long idea_id,@PathVariable Long id, Model model) {
		Idea idea = ideaRepository.findById(idea_id).orElse(null);
		Comment comment = commentRepository.findById(id).orElse(null);
		model.addAttribute("comment", comment);
        model.addAttribute("idea", idea);
        model.addAttribute("title", "Show Comment");
        return "comment/show";
    }

    @DeleteMapping("{id}")
    public String destroy(@PathVariable Long id) {
        commentRepository.deleteById(id);
        return "redirect:/ideas/{idea_id}/comments";
    }
}