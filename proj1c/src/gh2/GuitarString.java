package gh2;

import deque.Deque61B;
import deque.LinkedListDeque61B;

public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private Deque61B<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        // Initialize the buffer with capacity = SR / frequency.
        int capacity = (int) Math.round(SR / frequency);
        buffer = new LinkedListDeque61B<>();

        // Fill buffer with zeros
        for (int i = 0; i < capacity; i++) {
            buffer.addLast(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        // Dequeue everything in buffer, and replace with random numbers
        // between -0.5 and 0.5
        int size = buffer.size();

        // Remove all current values
        for (int i = 0; i < size; i++) {
            buffer.removeFirst();
        }

        // Fill with random values
        for (int i = 0; i < size; i++) {
            double r = Math.random() - 0.5;
            buffer.addLast(r);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        // Dequeue the front sample
        double firstSample = buffer.removeFirst();

        // Get the new front sample (which was second before removal)
        double secondSample = buffer.get(0);

        // Calculate average and apply decay
        double newSample = DECAY * (firstSample + secondSample) / 2.0;

        // Enqueue the new sample
        buffer.addLast(newSample);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.get(0);
    }
}