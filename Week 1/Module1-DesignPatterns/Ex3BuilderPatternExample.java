class Computer {
    private String CPU;
    private int RAM;
    private int storage;

    public static class Builder {
        private String CPU;
        private int RAM;
        private int storage;

        public Builder setCPU(String CPU) {
            this.CPU = CPU;
            return this;
        }

        public Builder setRAM(int RAM) {
            this.RAM = RAM;
            return this;
        }

        public Builder setStorage(int storage) {
            this.storage = storage;
            return this;
        }

        public Computer build() {
            Computer computer = new Computer(this);

            return computer;
        }
    }

    private Computer(Builder builder) {
        this.CPU = builder.CPU;
        this.RAM = builder.RAM;
        this.storage = builder.storage;
    }

    public void showSpecs() {
        System.out.println();
        System.out.println("CPU: " + CPU);
        System.out.println("RAM: " + RAM + " GB");
        System.out.println("Storage: " + storage + " GB");
    }
}

public class Ex3BuilderPatternExample {
    public static void main(String[] args) {
        Computer computer = new Computer.Builder()
                .setCPU("Intel i7-12700H")
                .setRAM(16)
                .setStorage(512)
                .build();

        computer.showSpecs();
    }
}
