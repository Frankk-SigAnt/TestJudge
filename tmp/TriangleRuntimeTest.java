public class TriangleRuntimeTest {
    public static void main(String[] args) {
        Triangle triangle = new Triangle();
        System.out.println("Test default triangle:");
        System.out.printf("a=%d, b=%d, c=%d, perimeter=%d, area=%.2f, valid=%b\n",
                triangle.getA(), triangle.getB(), triangle.getC(), triangle.getPerimeter(), triangle.getArea(), triangle.isValid());
        System.out.println("Test modified triangle:");
        triangle.setA(4);
        triangle.setB(3);
        triangle.setC(2);
        System.out.printf("a=%d, b=%d, c=%d, perimeter=%d, area=%.2f, valid=%b\n",
                triangle.getA(), triangle.getB(), triangle.getC(), triangle.getPerimeter(), triangle.getArea(), triangle.isValid());
        triangle.setC(1);
        System.out.printf("a=%d, b=%d, c=%d, valid=%b\n",
                triangle.getA(), triangle.getB(), triangle.getC(), triangle.getPerimeter(), triangle.getArea(), triangle.isValid());
    }
}