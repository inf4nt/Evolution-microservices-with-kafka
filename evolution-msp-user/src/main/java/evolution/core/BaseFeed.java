package evolution.core;

public interface BaseFeed<Key> extends Base<Key> {

    default String getFeed() {
        return this.getClass().getSimpleName() + "Feed";
    }
}
