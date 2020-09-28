package ir.maktab39.services.tag;

import ir.maktab39.base.service.BaseServiceImpl;
import ir.maktab39.entities.Tag;
import ir.maktab39.repositories.tag.TagRepo;
import ir.maktab39.repositories.tag.TagRepoImpl;

public class TagServiceImpl
        extends BaseServiceImpl<Long, Tag, TagRepo> implements TagService {
    public TagServiceImpl() {
        super(TagRepoImpl.class, Tag.class);
    }

    @Override
    public Tag getTag(String title) {
        return repository.getTag(title);
    }
}
