package sbu.cs;

/*
    In this exercise, you must analyse the following code and use interrupts
    in the main function to terminate threads that run for longer than 3 seconds.

    A thread may run for longer than 3 seconds due the many different reasons,
    including lengthy process times or getting stuck in an infinite loop.

    Take note that you are NOT ALLOWED to change or delete any existing line of code.
 */

public class UseInterrupts
{

    public static class SleepThread extends Thread {
        int sleepCounter;

        public SleepThread(int sleepCounter) {
            super();
            this.sleepCounter = sleepCounter;
        }

        @Override
        public void run() {
            System.out.println(this.getName() + " is Active.");

            while (this.sleepCounter > 0)
            {
                if (Thread.interrupted()) {
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }
                finally {
                    this.sleepCounter--;
                    System.out.println("Number of sleeps remaining: " + this.sleepCounter);
                }
            }

        }
    }

/*
    TODO
     Analyse the following class and add new code where necessary.
     If an object from this type of thread is Interrupted, it must print this:
        "{ThreadName} has been interrupted"
     And then terminate itself.
     (Hint: Use the isInterrupted() method)
 */
    public static class LoopThread extends Thread {
        int value;
        public LoopThread(int value) {
            super();
            this.value = value;
        }

        @Override
        public void run() {
            System.out.println(this.getName() + " is Active.");

            for (int i = 0; i < 10; i += 3)
            {
                i -= this.value;

            }
        }
    }

    public static class TimerThread extends Thread {
        long sleepTime; // in milli seconds
        public TimerThread(long sleepTime) {
            super();
            this.sleepTime = sleepTime;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(this.sleepTime);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }

    /*
        You can add new code to the main function. This is where you must utilize interrupts.
        No existing line of code should be changed or deleted.
     */
    public static void main(String[] args) {
        SleepThread sleepThread = new SleepThread(5);
        sleepThread.start();

        TimerThread timerThread = new TimerThread(3000);
        timerThread.start();
        try {
            timerThread.join();
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        } finally {
            if (sleepThread.isAlive()) {
                sleepThread.interrupt();
                System.out.println(sleepThread.getName() + " has been interrupted");
            }
        }


        LoopThread loopThread = new LoopThread(3);
        loopThread.start();

        // TODO  Check if this thread runs for longer than 3 seconds (if it does, interrupt it)

    }
}
