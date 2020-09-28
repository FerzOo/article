package ir.maktab39.repositories.tag;

import ir.maktab39.base.repository.BaseRepository;
import ir.maktab39.entities.Tag;

public interface TagRepo extends BaseRepository<Long, Tag> {
    Tag getTag(String title);
}
