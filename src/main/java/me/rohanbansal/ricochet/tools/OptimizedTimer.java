package me.rohanbansal.ricochet.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.DelayedRemovalArray;

import java.util.HashMap;

public class OptimizedTimer {

    private static HashMap<Integer, Float> timeLeft = new HashMap<>();
    private static HashMap<Integer, Float> timerLengths = new HashMap<>();
    private static HashMap<Integer, Runnable> runnables = new HashMap<>();

    private static DelayedRemovalArray<Integer> currentActiveTimers = new DelayedRemovalArray<>();

    private static int additiveTimerID = 0;

    /**
     * Initialize a new timer
     * @param seconds timer run length
     * @param runnable run code after timer finishes
     */
    public static void init(float seconds, Runnable runnable) {
        additiveTimerID++;

        timeLeft.put(additiveTimerID, 0f);
        timerLengths.put(additiveTimerID, seconds);
        runnables.put(additiveTimerID, runnable);

        currentActiveTimers.add(additiveTimerID);
    }

    /**
     * Run this function in the `update()` method, ticking it every frame
     */
    public static void tick() {

        if(currentActiveTimers.size > 0) {
            for(int timerID : currentActiveTimers) {
                timeLeft.put(timerID, timeLeft.get(timerID) + Gdx.graphics.getRawDeltaTime());

                if(timeLeft.get(timerID) >= timerLengths.get(timerID)) {
                    runnables.get(timerID).run();
                    timeLeft.remove(timerID);
                    timerLengths.remove(timerID);
                    currentActiveTimers.removeValue(timerID, true);
                }
            }
        }
    }
}

