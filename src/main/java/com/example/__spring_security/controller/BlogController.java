package com.example.__spring_security.controller;

import com.example.__spring_security.entity.Blog;
import com.example.__spring_security.service.BlogService;
import com.example.__spring_security.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/blog")
public class BlogController {

    private final BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createBlog(@RequestBody Blog blog) {
        blogService.createBlog(blog);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Blog> getBlog(@PathVariable int id) {
        Blog blog = blogService.getBlog(id);
        if (blog == null) {
            throw new ResourceNotFoundException("Blog not found with id: " + id);
        }
        return ResponseEntity.ok(blog);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Blog>> getAllBlog() {
        List<Blog> blogs = blogService.getAll();
        if (blogs == null) {
            throw new ResourceNotFoundException("No blogs found");
        }
        return ResponseEntity.ok(blogs);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<String> updateBlog(@PathVariable int id, @RequestBody Blog newblog) {
        Blog blog = blogService.getBlog(id);
        if (blog != null) {
            blog.setEntry(newblog.getEntry());
            blogService.updateBlog(blog);
            return ResponseEntity.ok("Success");
        } else {
            throw new ResourceNotFoundException("Blog not found with id: " + id);
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        blogService.deleteBlog(id);
        return ResponseEntity.ok("Success");
    }


}
