package evolution.coreold;

public interface Event<Key> extends Base<Key> {

    default String getTopic() {
        return EventService.getTopic(this.getClass());
    }
}
