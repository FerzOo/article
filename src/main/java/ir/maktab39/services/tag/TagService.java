package ir.maktab39.services.tag;

import ir.maktab39.base.service.BaseService;
import ir.maktab39.entities.Tag;

public interface TagService extends BaseService<Tag, Long> {
    Tag getTag(String title);
}
