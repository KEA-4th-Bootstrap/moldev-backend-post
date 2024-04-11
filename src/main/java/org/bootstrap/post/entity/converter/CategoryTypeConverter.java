package org.bootstrap.post.entity.converter;

import jakarta.persistence.Converter;
import org.bootstrap.post.entity.CategoryType;
import org.bootstrap.post.utils.AbstractEnumCodeAttributeConverter;

@Converter
public class CategoryTypeConverter extends AbstractEnumCodeAttributeConverter<CategoryType> {
    public CategoryTypeConverter() { super(CategoryType.class); }
}
