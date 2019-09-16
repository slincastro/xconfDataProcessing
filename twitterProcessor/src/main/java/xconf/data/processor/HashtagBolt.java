package xconf.data.processor;

import org.apache.storm.messaging.ConnectionWithStatus;
import org.apache.storm.mongodb.bolt.MongoInsertBolt;
import org.apache.storm.mongodb.common.mapper.MongoMapper;
import org.apache.storm.shade.org.json.simple.JSONObject;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import twitter4j.HashtagEntity;
import twitter4j.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HashtagBolt implements IRichBolt {
    private OutputCollector hashTagCollector;

    @Override
    public void prepare(Map<String, Object> map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.hashTagCollector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {
        Status tweet = (Status) tuple.getValueByField("tweet");
        List<String> hashtags = new ArrayList();
        String user = tweet.getUser().getScreenName();
        String originalText = tweet.getText();
        for (HashtagEntity hashtage : tweet.getHashtagEntities()) {
            System.out.println("******************** Hashtag: " + hashtage.getText());
            System.out.println("******************** Hashtag: " + hashtage.getText());
            hashtags.add(hashtage.getText());
        }
        JSONObject json = new JSONObject();
        json.put("user", user);
        json.put("hashtag", hashtags);
        json.put("message", originalText);

        this.hashTagCollector.emit(new Values(json));
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("hashtags"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
