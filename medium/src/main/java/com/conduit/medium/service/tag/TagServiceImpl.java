package com.conduit.medium.service.tag;

import com.conduit.medium.model.entity.Tag;
import com.conduit.medium.model.entity.TagToArticle;
import com.conduit.medium.repository.TagRepository;
import com.conduit.medium.repository.TagToArticleRepository;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * impl of tagService.
 */
@Slf4j
@Data
@Service
public class TagServiceImpl implements TagService {

  private final TagRepository tagRepository;
  private final TagToArticleRepository toArticleRepository;

  @Override
  public Tag addTag(final String tag) {
    log.info("Adding tag: [{}] to db", tag);
    final Optional<Tag> byName = tagRepository.findByName(tag);
    if (byName.isPresent()) {
      log.info("Tag: [{}], already present, not adding again", tag);
      return byName.get();
    }
    final Tag tagToSave = new Tag();
    tagToSave.setName(tag);
    tagRepository.save(tagToSave);
    log.info("Saved tag: [{}], to db", tag);
    return tagToSave;
  }

  @Override
  public List<Tag> addTag(final List<String> tags) {
    return tags.parallelStream()
        .map(this::addTag)
        .toList();
  }

  @Override
  public List<String> getTags(final UUID articleId) {
    final List<TagToArticle> allByArticleId = toArticleRepository.findAllByArticleId(articleId);
    if (allByArticleId.isEmpty()) {
      return Collections.emptyList();
    }
    return allByArticleId.parallelStream()
        .map(tagToArticle -> tagRepository.findById(tagToArticle.getTagId()).orElse(null))
        .filter(Objects::nonNull)
        .map(Tag::getName)
        .toList();
  }
}
