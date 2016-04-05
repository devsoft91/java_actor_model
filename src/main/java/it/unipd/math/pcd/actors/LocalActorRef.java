package it.unipd.math.pcd.actors;

/**
 * Real implementation of the interface ActorRef
 *
 * @author Giacomo Beltrame
 * @version 1.0
 * @since 1.0
 */
public class LocalActorRef<M extends Message> implements ActorRef<M> {
    @Override
    public void send(M message, ActorRef to) {
        Pack pack = new Pack<M,ActorRef<M>>(message,this);
        ((ActorSystemImpl.getSystem()).getActor(to)).pushMessage(pack);
    }

    @Override
    public int compareTo(ActorRef o) {
        if(o != null){
            if(this.equals(o))
                return 0;
            return -1;
        }
        else throw new NullPointerException();
    }
}
