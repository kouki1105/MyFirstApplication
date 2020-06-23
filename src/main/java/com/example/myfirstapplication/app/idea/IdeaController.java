package com.example.myfirstapplication.app.idea;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.cloudinary.utils.ObjectUtils;
import com.example.myfirstapplication.config.Singleton;
import com.example.myfirstapplication.entity.Idea;
import com.example.myfirstapplication.entity.Photo;
import com.example.myfirstapplication.form.IdeaForm;
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
@RequestMapping("/ideas")
public class IdeaController {
	
	@Autowired
	private IdeaRepository ideaRepository;
	
	@GetMapping
	public String index(Model model) {
		List<Idea> ideas = ideaRepository.findAll();
	
		model.addAttribute("ideas", ideas);
		model.addAttribute("title", "Idea Index");
		return "idea/index";
	}

	@GetMapping("new")
    public String newIdea(Model model) {
		model.addAttribute("title", "New Idea");
        return "idea/new";
    }
	
	@PostMapping
    public String create(@ModelAttribute Idea idea) {
		ideaRepository.save(idea);
        return "redirect:/ideas";
    }

    @PostMapping
    public String create(@ModelAttribute IdeaForm ideaForm) throws IOException {
        Idea idea = new Idea();
        idea.setName(ideaForm.getName());
        idea.setDescription(ideaForm.getDescription());
        PhotoUpload photoUpload = new PhotoUpload();
        Map uploadResult = null;
        if (ideaForm.getFile() != null && !ideaForm.getFile().isEmpty()) {
            uploadResult = Singleton.getCloudinary().uploader().upload(ideaForm.getFile().getBytes(),
                    ObjectUtils.asMap("resource_type", "auto"));
            photoUpload.setPublicId((String) uploadResult.get("public_id"));
            Object version = uploadResult.get("version");
            if (version instanceof Integer) {
                photoUpload.setVersion(new Long((Integer) version));    
            } else {
                photoUpload.setVersion((Long) version);
            }

            photoUpload.setSignature((String) uploadResult.get("signature"));
            photoUpload.setFormat((String) uploadResult.get("format"));
            photoUpload.setResourceType((String) uploadResult.get("resource_type"));
        }

        Photo photo = new Photo();
        photo.setIdea(idea);
        photo.setUpload(photoUpload);

        ideaRepository.save(idea);
        photoRepository.save(photo);
        return "redirect:/ideas";
    }
	
	@GetMapping("{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Idea idea = ideaRepository.findById(id).orElse(null);
        model.addAttribute("idea", idea);
        model.addAttribute("title", "Edit Idea");
        return "idea/edit";
    }
	
	@PutMapping("{id}")
    public String update(@PathVariable Long id, @ModelAttribute Idea idea) {
		idea.setId(id);
		ideaRepository.save(idea);
        return "redirect:/ideas";
    }
	
	@GetMapping("{id}")
    public String show(@PathVariable Long id, Model model) {
        Idea idea = ideaRepository.findById(id).orElse(null);
        model.addAttribute("idea", idea);
        model.addAttribute("title", "Show Idea");
        return "idea/show";
    }
	
	@DeleteMapping("{id}")
    public String destroy(@PathVariable Long id) {
		ideaRepository.deleteById(id);
        return "redirect:/ideas";
    }

}