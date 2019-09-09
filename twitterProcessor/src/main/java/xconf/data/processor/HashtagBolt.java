package xconf.data.processor;

import org.apache.storm.messaging.ConnectionWithStatus;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import twitter4j.HashtagEntity;
import twitter4j.Status;

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
        for (HashtagEntity hashtage : tweet.getHashtagEntities()) {
            System.out.println("Hashtag: " + hashtage.getText());
            this.hashTagCollector.emit(new Values(hashtage.getText()));
        }
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("hashtag"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
