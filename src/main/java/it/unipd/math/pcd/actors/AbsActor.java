/**
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2015 Riccardo Cardin
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * <p/>
 * Please, insert description here.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */

/**
 * Please, insert description here.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */
package it.unipd.math.pcd.actors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Defines common properties of all actors.
 *
 * @author Riccardo Cardin
 * @author Giacomo Beltrame
 * @version 1.3
 * @since 1.0
 */
public abstract class AbsActor<M extends Message> implements Actor<M> {

    /**
     * The "mailbox" made by threads witch one runs a task orderely
     */
    private static ExecutorService pool = Executors.newSingleThreadExecutor();

    /**
     * Self-reference of the actor
     */
    protected ActorRef<M> self;

    /**
     * Sender of the current message
     */
    protected ActorRef<M> sender;

    /**
     * Sets the self-referece.
     *
     * @param self The reference to itself
     * @return The actor.
     */
    protected final Actor<M> setSelf(ActorRef<M> self) {
        this.self = self;
        return this;
    }

    /**
     * Sets the sender-referece.
     *
     * @param sender The reference to the sender
     */
    protected final void setSender(ActorRef<M> sender) {
        this.sender = sender;
    }

    /**
     * Push an incoming message in the queue of pool
     *
     * @param pack The couple "Message,ActorRef" coming from sender actor
     */
    protected final void pushMessage(final Pack<M,ActorRef<M>> pack){
        try {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    setSender(pack.getRef());
                    receive(pack.getMessage());
                }
            });
        }
        catch(RejectedExecutionException e){
            pool = Executors.newSingleThreadExecutor();
            pushMessage(pack);
        }
    }

    /**
     * Kill the actor
     *
     * @param maxWaitingTime Max time that the system is willing to wait for an actor to be killed
     */
    protected final void killActor(int maxWaitingTime){
        pool.shutdown();
        try {
            if (!pool.awaitTermination(maxWaitingTime, TimeUnit.SECONDS))
                pool.shutdownNow();
        }
        catch(InterruptedException e) {
            System.err.print("Waiting interrupted");
            pool.shutdownNow();
            if(!pool.isShutdown())
                Thread.currentThread().interrupt();
        }
    }


}
