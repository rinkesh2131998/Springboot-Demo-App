package com.conduit.medium.controller;

import com.conduit.medium.model.entity.Tag;
import com.conduit.medium.service.tag.TagService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * endpoints related to tags.
 */
@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/tags")
public class TagController {

  private final TagService tagService;

  /**
   * get all tags.
   */
  @GetMapping()
  public ResponseEntity<List<Tag>> getAllTags() {
    final List<Tag> tags = tagService.getTags();
    return ResponseEntity.ok(tags);
  }
}
