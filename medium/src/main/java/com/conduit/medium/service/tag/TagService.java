package com.conduit.medium.service.tag;

import com.conduit.medium.model.entity.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

/**
 * service to operate on tagList for an article.
 */
@Service
public interface TagService {

  /**
   * add a single tag to db.
   *
   * @param tag to add
   * @return return tag
   */
  Tag addTag(String tag);

  /**
   * add the given list of tagList to db.
   *
   * @param tags to add
   * @return tagList
   */
  List<Tag> addTag(List<String> tags);

  /**
   * fetch list of all tagList for an article.
   *
   * @param articleId to fetch tagList for
   * @return list of all tagList found
   */
  List<String> getTags(UUID articleId);
}
