class Logger {
    private static Logger logger;
    private static int count = 0;

    private Logger() {}

    public static Logger getInstance() {

        if(logger == null){
            logger = new Logger();
        }

        return logger;
    }

    public void log() {
        System.out.println("Logger is Logging... "+count++);
    }
}

public class Ex1SingletonPatternExample {
    public static void main(String[] args) {
        Logger logger = Logger.getInstance();
        logger.log();

        Logger logger2 = Logger.getInstance();
        logger2.log();
        logger2.log();

        Logger logger3 = Logger.getInstance();
        logger3.log();

        Logger logger4 = Logger.getInstance();
        logger4.log();
    }


}
