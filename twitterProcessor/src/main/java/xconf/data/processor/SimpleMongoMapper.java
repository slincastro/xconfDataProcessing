package xconf.data.processor;

import org.apache.storm.mongodb.common.MongoUtils;
import org.apache.storm.mongodb.common.mapper.MongoMapper;
import org.apache.storm.tuple.ITuple;
import org.bson.Document;

import java.util.List;

public class SimpleMongoMapper implements MongoMapper {

    private String[] fields;

    @Override
    public Document toDocument(ITuple tuple) {
        Document document = new Document();
        for(String field : fields){
            document.append(field, tuple.getValueByField(field));
        }
        return document;
    }

    @Override
    public Document toDocumentByKeys(List<Object> keys) {
        Document document = new Document();
        document.append("_id", MongoUtils.getId(keys));
        return document;
    }

    public SimpleMongoMapper withFields(String... fields) {
        this.fields = fields;
        return this;
    }
}
