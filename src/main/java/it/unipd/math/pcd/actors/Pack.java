package it.unipd.math.pcd.actors;

/**
 * Defines class composed by a message & reference of the sender {@link Actor}
 *
 * @author Giacomo Beltrame
 * @version 1.0
 * @since 1.0
 */
public class Pack<M extends Message,A extends ActorRef<M>> {
    /**
     * The message
     */
    private M message;

    /**
     * The sender {@link Actor} reference
     */
    private A ref_to_sender;

    /**
     * Constructor
     *
     * @param mess The message
     * @param ref The sender {@link Actor} reference
     */
    public Pack(M mess,A ref){
        message = mess;
        ref_to_sender = ref;
    }

    /**
     * Return the message
     * @return The message
     */
    public M getMessage(){
        return message;
    }

    /**
     * Return the sender {@link Actor} reference
     * @return The sender {@link Actor} reference
     */
    public ActorRef<M> getRef(){
        return ref_to_sender;
    }
}
