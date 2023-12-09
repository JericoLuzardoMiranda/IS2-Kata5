package software.ulpgc.kata5;

import java.util.stream.IntStream;

public class OperationCommand implements Command {

    @Override
    public Output execute(Input input) {
        try {
            int number = Integer.parseInt(input.get(":number"));
            String operation = input.get(":operation");

            switch (operation) {
                case "factorial":
                    return isOutOfBound(number) ? outOfBoundOutput() : outputOfFactorial(number);
                case "square":
                    return outputOfSquare(number);
                default:
                    return invalidOperationOutput();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Output invalidOperationOutput() {
        return new Output() {
            @Override
            public int response() {
                return 400;
            }

            @Override
            public String result() {
                return "Invalid operation";
            }
        };
    }

    private Output outputOfSquare(int number) {
        return new Output() {
            @Override
            public int response() {
                return 200;
            }

            @Override
            public String result() {
                return String.valueOf(number*number);
            }
        };
    }

    private Output outputOfFactorial(int number) {
        return new Output() {
            @Override
            public int response() {
                return 200;
            }

            @Override
            public String result() {
                return String.valueOf(factorial(number));
            }
        };
    }

    private int factorial(int number) {
        return IntStream.range(2, number+1).reduce(1, (a,i) -> a*i);
    }

    private Output outOfBoundOutput() {
        return new Output() {
            @Override
            public int response() {
                return 404;
            }

            @Override
            public String result() {
                return "Number out of bound";
            }
        };
    }

    private boolean isOutOfBound(int number) {
        return number < 1 || number > 100;
    }

}
