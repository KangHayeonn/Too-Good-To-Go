//package com.toogoodtogo.domain.user;
//
//import javax.persistence.AttributeConverter;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//public class ListLongToStringConverter implements AttributeConverter<List<Long>, String> {
//    @Override
//    public String convertToDatabaseColumn(List<Long> attribute) {
//        return attribute == null ? null : String.join(",", attribute);
//    }
//
//    @Override
//    public List<Long> convertToEntityAttribute(String dbData) {
//        return dbData == null ? Collections.emptyList() : Arrays.asList(dbData.split(","));
//    }
//}
//
