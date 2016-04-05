package it.unipd.math.pcd.actors;

import it.unipd.math.pcd.actors.exceptions.NoSuchActorException;

import java.util.Collection;

/**
 * Real implementation of AbsActorSystem.
 *
 * @author Giacomo Beltrame
 * @version 1.0
 * @since 1.0
 */
public class ActorSystemImpl extends AbsActorSystem{

    /**
     * Max time that the system is willing to wait for an actor to be killed
     */
    private int time_to_wait;

    /**
     * A fake singleton of ActorSystemImpl
     */
    private static ActorSystemImpl system = null;

    /**
     * Constructor
     * @param a Max time that the system is willing to wait for an actor to be killed
     */
    public ActorSystemImpl(int a){
        time_to_wait = a;
        system = this;
    }

    /**
     * Constructor
     */
    public ActorSystemImpl(){
        this(10);
    }

    @Override
    protected ActorRef createActorReference(ActorMode mode) throws IllegalArgumentException{
        if(mode == ActorMode.LOCAL)
            return new LocalActorRef();
        throw new IllegalArgumentException();
    }

    @Override
    public void stop(ActorRef<?> ar) {
        getActor(ar).killActor(time_to_wait);
        popActor(ar);
    }

    @Override
    public void stop() {/*
        try {
            Collection<Actor<?>> c = getActors();
            for (Actor<?> actor : c)
                ((AbsActor<?>) actor).killActor(time_to_wait);/*
        try {
            c.clear();  //optional
        }
        catch(UnsupportedOperationException e){
            e.printStackTrace();
        }
        } catch (NoSuchActorException a) {
            a.printStackTrace();
        }*/
        Collection<Actor<?>> c = getActors();
        for (Actor<?> actor : c)
            ((AbsActor<?>) actor).killActor(time_to_wait);
        c.clear();
    }

    /**
     * Return the instance of the ActorSystemImpl "singleton"
     * @return The only instance of ActorSystemImpl
     */
    public static ActorSystemImpl getSystem(){
        if(system != null)
            return system;
        return system = new ActorSystemImpl();
    }
}
