package xconf.data.processor;

import org.apache.storm.Config;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class TwitterSpout extends BaseRichSpout {
    private final String consumerKey;
    private final String consumerSecret;
    private final String accessToken;
    private final String accessTokenSecret;
    private final String[] keywords;
    LinkedBlockingQueue queue = null;
    SpoutOutputCollector _collector;
    TwitterStream _twitterStream;

    public TwitterSpout(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret, String[] keywords){
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.accessToken = accessToken;
        this.accessTokenSecret =accessTokenSecret;
        this.keywords = keywords;
    }
    @Override
    public void open(Map<String, Object> map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        queue = new LinkedBlockingQueue<Status>(1000);
        _collector = spoutOutputCollector;
        StatusListener listener = new StatusListener() {
            @Override
            public void onStatus(Status status) {
                queue.offer(status);
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

            }

            @Override
            public void onTrackLimitationNotice(int i) {

            }

            @Override
            public void onScrubGeo(long l, long l1) {

            }

            @Override
            public void onStallWarning(StallWarning stallWarning) {

            }

            @Override
            public void onException(Exception e) {

            }
        };

        ConfigurationBuilder cb =new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);

        _twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        _twitterStream.addListener(listener);

        if(keywords.length == 0 ){
            _twitterStream.sample();
        }else{
            FilterQuery query = new FilterQuery().track(keywords);
            _twitterStream.filter(query);
        }
    }

    @Override
    public void nextTuple() {
        Status ret = (Status) queue.poll();

        if(ret == null){
            Utils.sleep(50);
        }else{
            _collector.emit(new Values(ret));
        }
    }

    @Override
    public void close(){
        _twitterStream.shutdown();
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("tweet"));
    }
}
