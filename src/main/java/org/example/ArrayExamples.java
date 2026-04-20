package org.example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class ArrayExamples {
    private ArrayExamples() {}

    public static void runAll() {
        section("1. Basic 1D arrays");
        basicPrimitiveArrays();
        basicObjectArrays();
        defaultInitialization();

        section("2. Arrays are first-class objects");
        arraysAreObjects();
        returningArrays();

        section("3. Aggregate initialization");
        aggregateInitialization();

        section("4. Multidimensional arrays");
        rectangularAndRaggedArrays();
        objectMultidimensionalArrays();

        section("5. Arrays utilities");
        arraysFillAndToString();
        arraysEqualsAndDeepEquals();

        section("6. Copying arrays and shallow copy");
        arrayCopyExamples();

        section("7. Sorting and searching");
        sortingPrimitivesAndObjects();
        binarySearchExamples();

        section("8. Arrays.asList()");
        asListExamples();

        section("9. Covariance and runtime checks");
        covarianceExamples();

        section("10. Arrays and generics");
        genericsAndArrays();

        section("11. Autoboxing limits");
        autoboxingDoesNotWorkForWholeArrays();

        section("12. Generated arrays (inspired by the book)");
        generatedArraysExamples();

        section("13. Key takeaways");
        keyTakeaways();
    }

    // =========================================================================
    // Section helpers
    // =========================================================================

    private static void section(String title) {
        System.out.println();
        System.out.println("=".repeat(100));
        System.out.println(title);
        System.out.println("=".repeat(100));
    }

    private static void subsection(String title) {
        System.out.println();
        System.out.println("--- " + title + " ---");
    }

    // =========================================================================
    // 1. Basic 1D arrays
    // =========================================================================

    private static void basicPrimitiveArrays() {
        subsection("Primitive array basics");

        int[] numbers = new int[5];
        numbers[0] = 10;
        numbers[1] = 20;
        numbers[2] = 30;

        System.out.println("numbers         = " + Arrays.toString(numbers));
        System.out.println("numbers.length  = " + numbers.length);

        int[] literal = {1, 2, 3, 4, 5};
        System.out.println("literal         = " + Arrays.toString(literal));
    }

    private static void basicObjectArrays() {
        subsection("Object array basics");

        String[] words = new String[4];
        words[0] = "Java";
        words[1] = "arrays";

        System.out.println("words           = " + Arrays.toString(words));
        System.out.println("words.length    = " + words.length);

        Sphere[] spheres = new Sphere[3];
        spheres[0] = new Sphere();
        spheres[1] = new Sphere();

        System.out.println("spheres         = " + Arrays.toString(spheres));
    }

    @SuppressWarnings("MismatchedReadAndWriteOfArray")
    private static void defaultInitialization() {
        subsection("Default initialization");

        // Intentionally reading arrays before any writes to demonstrate default initialization.
        int[] ints = new int[4];
        boolean[] bools = new boolean[4];
        char[] chars = new char[4];
        double[] doubles = new double[4];
        String[] strings = new String[4];
        Sphere[] spheres = new Sphere[4];

        System.out.println("int[]           = " + Arrays.toString(ints));
        System.out.println("boolean[]       = " + Arrays.toString(bools));
        System.out.println("char[]          = " + Arrays.toString(chars)
                + "  // default char is '\\0' (null character, not visually obvious)");
        System.out.println("double[]        = " + Arrays.toString(doubles));
        System.out.println("String[]        = " + Arrays.toString(strings));
        System.out.println("Sphere[]        = " + Arrays.toString(spheres));

        // Note:
        // Array elements are default-initialized.
        // Local variables are NOT default-initialized in Java.
    }

    // =========================================================================
    // 2. Arrays are first-class objects
    // =========================================================================

    @SuppressWarnings({"UnnecessaryLocalVariable", "ConstantValue"})
    private static void arraysAreObjects() {
        subsection("Arrays are objects");

        int[] a = new int[] {1, 2, 3};
        int[] b = a;

        System.out.println("a               = " + Arrays.toString(a));
        System.out.println("b               = " + Arrays.toString(b));

        b[1] = 99;

        // Both references point to the same array object.
        System.out.println("After b[1] = 99:");
        System.out.println("a               = " + Arrays.toString(a));
        System.out.println("b               = " + Arrays.toString(b));
        System.out.println("a == b          = " + (a == b));
    }

    private static void returningArrays() {
        subsection("Returning arrays from methods");

        int[] returned6 = createSequence(6);
        int[] returned3 = createSequence(3);

        System.out.println("createSequence(6) = " + Arrays.toString(returned6));
        System.out.println("createSequence(3) = " + Arrays.toString(returned3));

        Sphere[] created4 = createSphereArray(4);
        Sphere[] created2 = createSphereArray(2);

        System.out.println("createSphereArray(4) = " + Arrays.toString(created4));
        System.out.println("createSphereArray(2) = " + Arrays.toString(created2));
    }

    private static int[] createSequence(int size) {
        int[] result = new int[size];
        for (int i = 0; i < result.length; i++) {
            result[i] = i * 10;
        }
        return result;
    }

    private static Sphere[] createSphereArray(int size) {
        Sphere[] result = new Sphere[size];
        for (int i = 0; i < result.length; i++) {
            result[i] = new Sphere();
        }
        return result;
    }

    // =========================================================================
    // 3. Aggregate initialization
    // =========================================================================

    private static void aggregateInitialization() {
        subsection("Aggregate initialization");

        // Aggregate initialization works at declaration time.
        Sphere[] a = { new Sphere(), new Sphere(), new Sphere() };
        System.out.println("a               = " + Arrays.toString(a));

        // Dynamic aggregate initialization works anywhere.
        hideSpheres(new Sphere[] { new Sphere(), new Sphere() });

        // This would NOT compile:
        // hideSpheres({ new Sphere(), new Sphere() });

        // The braces-only form works only at variable declaration time.
    }

    private static void hideSpheres(Sphere[] spheres) {
        System.out.println("Hiding " + spheres.length + " sphere(s): "
                + Arrays.toString(spheres));
    }

    // =========================================================================
    // 4. Multidimensional arrays
    // =========================================================================

    private static void rectangularAndRaggedArrays() {
        subsection("Rectangular primitive arrays");

        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6}
        };

        System.out.println("matrix          = " + Arrays.deepToString(matrix));

        int[][] rectangular = new int[3][4];
        rectangular[1][2] = 42;
        System.out.println("rectangular     = " + Arrays.deepToString(rectangular));

        subsection("Ragged / jagged arrays");

        int[][] ragged = new int[3][];
        ragged[0] = new int[2];
        ragged[1] = new int[4];
        ragged[2] = new int[1];

        ragged[0][0] = 10;
        ragged[1][3] = 99;
        ragged[2][0] = 7;

        System.out.println("ragged          = " + Arrays.deepToString(ragged));
        System.out.println("ragged[0].length = " + ragged[0].length);
        System.out.println("ragged[1].length = " + ragged[1].length);
        System.out.println("ragged[2].length = " + ragged[2].length);

        subsection("Flat representation of a 2D matrix");

        FlatIntMatrix flat = new FlatIntMatrix(3, 4);
        flat.set(1, 2, 123);
        flat.set(2, 3, 456);

        System.out.println("flat.get(1,2)   = " + flat.get(1, 2));
        System.out.println("flat.get(2,3)   = " + flat.get(2, 3));
        System.out.println(flat);
    }

    private static void objectMultidimensionalArrays() {
        subsection("Multidimensional arrays of objects");

        Sphere[][] objects = new Sphere[2][3];

        // The rows exist, but the elements are still null references.
        System.out.println("Before filling   = " + Arrays.deepToString(objects));

        for (int i = 0; i < objects.length; i++) {
            for (int j = 0; j < objects[i].length; j++) {
                objects[i][j] = new Sphere();
            }
        }

        System.out.println("After filling    = " + Arrays.deepToString(objects));

        Sphere[][][] objects3d = new Sphere[2][2][2];
        System.out.println("3D before fill   = " + Arrays.deepToString(objects3d));

        for (int i = 0; i < objects3d.length; i++) {
            for (int j = 0; j < objects3d[i].length; j++) {
                for (int k = 0; k < objects3d[i][j].length; k++) {
                    objects3d[i][j][k] = new Sphere();
                }
            }
        }

        System.out.println("3D after fill    = " + Arrays.deepToString(objects3d));
    }

    // =========================================================================
    // 5. Arrays utilities
    // =========================================================================

    private static void arraysFillAndToString() {
        subsection("Arrays.fill(), Arrays.toString(), Arrays.deepToString()");

        int[] a = new int[6];
        Arrays.fill(a, 7);
        System.out.println("fill all         = " + Arrays.toString(a));

        Arrays.fill(a, 2, 5, 99);
        System.out.println("fill range       = " + Arrays.toString(a));

        String[][] words = {
                {"The", "quick"},
                {"brown", "fox"}
        };

        System.out.println("deepToString     = " + Arrays.deepToString(words));
    }

    private static void arraysEqualsAndDeepEquals() {
        subsection("Arrays.equals() vs Arrays.deepEquals()");

        int[] p1 = {1, 2, 3};
        int[] p2 = {1, 2, 3};
        int[] p3 = {1, 2, 4};

        System.out.println("p1 == p2         = " + (p1 == p2));
        System.out.println("Arrays.equals(p1, p2) = " + Arrays.equals(p1, p2));
        System.out.println("Arrays.equals(p1, p3) = " + Arrays.equals(p1, p3));

        ValueHolder[] v1 = {
                new ValueHolder(10),
                new ValueHolder(20)
        };
        ValueHolder[] v2 = {
                new ValueHolder(10),
                new ValueHolder(20)
        };

        // ValueHolder overrides equals(), so this is semantic equality.
        System.out.println("Arrays.equals(v1, v2) = " + Arrays.equals(v1, v2));

        int[][] d1 = {{1, 2}, {3, 4}};
        int[][] d2 = {{1, 2}, {3, 4}};
        int[][] d3 = {{1, 2}, {3, 5}};

        System.out.println("Arrays.deepEquals(d1, d2) = " + Arrays.deepEquals(d1, d2));
        System.out.println("Arrays.deepEquals(d1, d3) = " + Arrays.deepEquals(d1, d3));

        Integer[][] boxed = {{1, 2}, {3, 4}};

        // Interesting nuance:
        // deepEquals() does not erase the distinction between primitive arrays and wrapper arrays.
        System.out.println("Arrays.deepEquals(d1, boxed) = " + Arrays.deepEquals(d1, boxed));
    }

    // =========================================================================
    // 6. Copying arrays and shallow copy
    // =========================================================================

    private static void arrayCopyExamples() {
        subsection("System.arraycopy() with primitives");

        int[] src = {10, 20, 30, 40, 50};
        int[] dst = new int[5];

        System.arraycopy(src, 0, dst, 0, src.length);

        System.out.println("src             = " + Arrays.toString(src));
        System.out.println("dst             = " + Arrays.toString(dst));

        subsection("System.arraycopy() with object arrays -> shallow copy");

        MutableBox[] a = {
                new MutableBox("A"),
                new MutableBox("B"),
                new MutableBox("C")
        };
        MutableBox[] b = new MutableBox[a.length];

        System.arraycopy(a, 0, b, 0, a.length);

        System.out.println("a               = " + Arrays.toString(a));
        System.out.println("b               = " + Arrays.toString(b));

        // Replacing a reference in the source array does NOT change the target slot.
        a[1] = new MutableBox("NEW");
        System.out.println("After a[1] = new MutableBox(\"NEW\"):");
        System.out.println("a               = " + Arrays.toString(a));
        System.out.println("b               = " + Arrays.toString(b));

        // Mutating a shared object DOES affect both arrays.
        b[0].value = "CHANGED THROUGH b";
        System.out.println("After b[0].value mutation:");
        System.out.println("a               = " + Arrays.toString(a));
        System.out.println("b               = " + Arrays.toString(b));
    }

    // =========================================================================
    // 7. Sorting and searching
    // =========================================================================

    @SuppressWarnings("CommentedOutCode")
    private static void sortingPrimitivesAndObjects() {
        subsection("Sorting primitives");

        int[] ints = {5, 1, 9, 2, 7, 3};
        System.out.println("before          = " + Arrays.toString(ints));
        Arrays.sort(ints);
        System.out.println("after           = " + Arrays.toString(ints));

        subsection("Sorting objects using Comparable");

        ComparableSphere[] spheres = {
                new ComparableSphere(30),
                new ComparableSphere(10),
                new ComparableSphere(20)
        };

        System.out.println("before          = " + Arrays.toString(spheres));
        Arrays.sort(spheres);
        System.out.println("after           = " + Arrays.toString(spheres));

        subsection("Sorting objects using Comparator");

        Person[] people = {
                new Person("Alice", 42),
                new Person("Bob", 25),
                new Person("Charlie", 31)
        };

        System.out.println("before          = " + Arrays.toString(people));

        Arrays.sort(people, Comparator.comparingInt(p -> p.age));
        System.out.println("by age asc      = " + Arrays.toString(people));

        Arrays.sort(people, Comparator.comparing((Person p) -> p.name).reversed());
        System.out.println("by name desc    = " + Arrays.toString(people));

        subsection("Reverse sort with wrapper types");

        Integer[] boxed = {3, 1, 7, 2, 9};
        Arrays.sort(boxed, Collections.reverseOrder());
        System.out.println("Integer[] desc  = " + Arrays.toString(boxed));

        // Primitive arrays do not support Comparator-based sort:
        // int[] primitive = {3, 1, 7, 2, 9};
        // Arrays.sort(primitive, Collections.reverseOrder()); // Does not compile
    }

    private static void binarySearchExamples() {
        subsection("binarySearch() on sorted arrays");

        int[] sorted = {10, 20, 30, 40, 50, 60};
        int foundIndex = Arrays.binarySearch(sorted, 40);
        int missingIndex = Arrays.binarySearch(sorted, 35);

        System.out.println("sorted          = " + Arrays.toString(sorted));
        System.out.println("find 40         = " + foundIndex);
        System.out.println("find 35         = " + missingIndex
                + "  // equals -(insertionPoint) - 1");

        subsection("binarySearch() on unsorted arrays is undefined");

        int[] unsorted = {50, 10, 60, 20, 40, 30};
        System.out.println("unsorted        = " + Arrays.toString(unsorted));
        System.out.println("search 40       = " + Arrays.binarySearch(unsorted, 40));

        subsection("Sort and search with the SAME Comparator");

        Person[] people = {
                new Person("Alice", 42),
                new Person("Bob", 25),
                new Person("Charlie", 31)
        };

        Comparator<Person> byAge = Comparator.comparingInt(p -> p.age);

        Arrays.sort(people, byAge);
        System.out.println("sorted by age   = " + Arrays.toString(people));

        int idx = Arrays.binarySearch(people, new Person("?", 31), byAge);
        System.out.println("search age 31   = " + idx + ", " + people[idx]);

        // Important rule:
        // If you sort with a Comparator, you must search with the same Comparator.
    }

    // =========================================================================
    // 8. Arrays.asList()
    // =========================================================================

    private static void asListExamples() {
        subsection("Arrays.asList() view");

        String[] data = {"A", "B", "C"};
        List<String> list = Arrays.asList(data);

        System.out.println("array           = " + Arrays.toString(data));
        System.out.println("list            = " + list);

        list.set(1, "X");
        System.out.println("After list.set:");
        System.out.println("array           = " + Arrays.toString(data));
        System.out.println("list            = " + list);

        System.out.println("list class      = " + list.getClass().getName());

        // The list has fixed size.
        try {
            list.add("D");
        } catch (UnsupportedOperationException e) {
            System.out.println("list.add(\"D\") throws UnsupportedOperationException");
        }

        subsection("Creating a real resizable list");

        List<String> mutable = new ArrayList<>(Arrays.asList(data));
        mutable.add("D");
        System.out.println("mutable         = " + mutable);
    }

    // =========================================================================
    // 9. Covariance and runtime checks
    // =========================================================================

    @SuppressWarnings({"UnnecessaryLocalVariable", "DataFlowIssue"})
    private static void covarianceExamples() {
        subsection("Array covariance");

        String[] strings = new String[3];
        Object[] objects = strings; // Legal because arrays are covariant.

        objects[0] = "hello";
        System.out.println("strings         = " + Arrays.toString(strings));

        try {
            // Compiles, but fails at runtime because the real array is String[].
            objects[1] = 123;
        } catch (ArrayStoreException e) {
            System.out.println("Storing Integer into String[] through Object[] causes ArrayStoreException");
        }
    }

    // =========================================================================
    // 10. Arrays and generics
    // =========================================================================

    @SuppressWarnings("unchecked")
    private static void genericsAndArrays() {
        subsection("Arrays and generics do not mix cleanly");

        // This is illegal and would not compile:
        // List<String>[] illegal = new List<String>[10];

        // Workaround with unchecked cast:
        List<String>[] arrayOfLists = (List<String>[]) new List[2];
        arrayOfLists[0] = new ArrayList<>();
        arrayOfLists[0].add("hello");

        System.out.println("arrayOfLists[0] = " + arrayOfLists[0]);

        // Why Java is careful:
        // arrays are reified and covariant,
        // generics use type erasure and are invariant.

        // Also illegal:
        // List<Object> x = new ArrayList<String>(); // Does not compile
    }

    // =========================================================================
    // 11. Autoboxing limits
    // =========================================================================

    @SuppressWarnings({"WrapperTypeMayBePrimitive", "CommentedOutCode"})
    private static void autoboxingDoesNotWorkForWholeArrays() {
        subsection("Autoboxing does not convert whole arrays");

        int primitive = 123;
        Integer boxed = primitive;   // Works
        int unboxed = boxed;         // Works

        System.out.println("primitive       = " + primitive);
        System.out.println("boxed           = " + boxed);
        System.out.println("unboxed         = " + unboxed);

        // But whole arrays are different types:
        // int[] pa = {1, 2, 3};
        // Integer[] wa = pa;   // Does not compile
        // int[] pb = wa;       // Does not compile

        int[] ints = {1, 2, 3};
        Integer[] wrappers = toWrapperArray(ints);
        int[] backToPrimitives = toPrimitiveArray(wrappers);

        System.out.println("ints            = " + Arrays.toString(ints));
        System.out.println("wrappers        = " + Arrays.toString(wrappers));
        System.out.println("back            = " + Arrays.toString(backToPrimitives));
    }

    private static Integer[] toWrapperArray(int[] input) {
        Integer[] result = new Integer[input.length];
        for (int i = 0; i < input.length; i++) {
            result[i] = input[i]; // element-wise boxing
        }
        return result;
    }

    private static int[] toPrimitiveArray(Integer[] input) {
        int[] result = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            result[i] = input[i]; // element-wise unboxing
        }
        return result;
    }

    // =========================================================================
    // 12. Generated arrays (inspired by the book)
    // =========================================================================

    private static void generatedArraysExamples() {
        subsection("A tiny Generator<T> framework");

        Integer[] counted = Generated.array(Integer.class, new CountingIntegerGenerator(), 8);
        System.out.println("counted         = " + Arrays.toString(counted));

        Integer[] skipped = Generated.array(Integer.class, new SkipIntegerGenerator(5), 8);
        System.out.println("skipped by 5    = " + Arrays.toString(skipped));

        int[] primitiveSkipped = ConvertTo.primitive(skipped);
        System.out.println("primitive skip  = " + Arrays.toString(primitiveSkipped));

        subsection("Generated arrays of custom objects");

        Sphere[] generatedSpheres = Generated.array(Sphere.class, SphereGenerator.INSTANCE, 5);
        System.out.println("generated       = " + Arrays.toString(generatedSpheres));

        subsection("Generated BigDecimal[]");

        BigDecimal[] decimals = Generated.array(
                BigDecimal.class,
                new BigDecimalGenerator(new BigDecimal("0.25")),
                10
        );
        System.out.println("BigDecimal[]    = " + Arrays.toString(decimals));

        subsection("Generated String using counting characters");

        String generatedString = new CountingStringGenerator(12).next();
        System.out.println("generated text  = " + generatedString);

        subsection("Primitive arrays still need special handling");

        double[] doubles = ConvertTo.primitive(
                Generated.array(Double.class, new CountingDoubleGenerator(), 6)
        );
        System.out.println("double[]        = " + Arrays.toString(doubles));

        // Note:
        // Generic array helpers naturally work with reference types.
        // Primitive arrays still require explicit conversion or separate APIs.
    }

    // =========================================================================
    // 13. Key takeaways
    // =========================================================================

    private static void keyTakeaways() {
        subsection("Summary");

        System.out.println("""
                1) Arrays in Java are objects.
                2) Primitive arrays store primitive values directly.
                3) Object arrays store references.
                4) int[][] is an array of int[], not a flat native 2D block.
                5) Multidimensional arrays can be ragged.
                6) Arrays utilities in java.util.Arrays are essential.
                7) arraycopy() on object arrays performs a shallow copy.
                8) Arrays.equals() for object arrays depends on element.equals().
                9) Arrays of objects need Comparable or Comparator for sorting.
                10) binarySearch() only makes sense on arrays sorted with the same ordering.
                11) Arrays are covariant; generics are not.
                12) int[] and Integer[] are different, unrelated array types.
                13) Arrays.asList() returns a fixed-size list view.
                14) In modern Java, collections are often more convenient than arrays,
                    but arrays still matter for primitives, performance, matrices, buffers,
                    and APIs that explicitly use them.
                """);
    }

    // =========================================================================
    // Helper classes used by examples
    // =========================================================================

    private static final class Sphere {
        private static int nextId = 0;
        private final int id = nextId++;

        @Override
        public String toString() {
            return "Sphere(" + id + ")";
        }
    }

    private static final class FlatIntMatrix {
        private final int rows;
        private final int cols;
        private final int[] data;

        private FlatIntMatrix(int rows, int cols) {
            this.rows = rows;
            this.cols = cols;
            this.data = new int[rows * cols];
        }

        private int get(int row, int col) {
            return data[row * cols + col];
        }

        private void set(int row, int col, int value) {
            data[row * cols + col] = value;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("FlatIntMatrix(").append(rows).append("x").append(cols).append(")\n");
            for (int r = 0; r < rows; r++) {
                sb.append("[");
                for (int c = 0; c < cols; c++) {
                    if (c > 0) {
                        sb.append(", ");
                    }
                    sb.append(get(r, c));
                }
                sb.append("]\n");
            }
            return sb.toString();
        }
    }

    private record ValueHolder(int value) {
        // Intentionally overriding the default record-generated toString() to keep
        // the output format concise and stable for the array examples.
        @SuppressWarnings("NullableProblems")
        @Override
        public String toString() {
            return "ValueHolder(" + value + ")";
        }
    }

    private static final class MutableBox {
        private String value;

        private MutableBox(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "MutableBox(" + value + ")";
        }
    }

    private record ComparableSphere(int sortKey) implements Comparable<ComparableSphere> {
        @Override
        public int compareTo(ComparableSphere other) {
            return Integer.compare(this.sortKey, other.sortKey);
        }
    }

    private record Person(String name, int age) {}

    // =========================================================================
    // Tiny generator framework inspired by the book
    // =========================================================================

    private interface Generator<T> {
        T next();
    }

    private static final class Generated {
        private Generated() {}

        static <T> T[] array(Class<T> type, Generator<T> gen, int size) {
            @SuppressWarnings("unchecked")
            T[] result = (T[]) java.lang.reflect.Array.newInstance(type, size);
            for (int i = 0; i < size; i++) {
                result[i] = gen.next();
            }
            return result;
        }
    }

    private static final class ConvertTo {
        private ConvertTo() {}

        static int[] primitive(Integer[] input) {
            int[] result = new int[input.length];
            for (int i = 0; i < input.length; i++) {
                result[i] = input[i];
            }
            return result;
        }

        static double[] primitive(Double[] input) {
            double[] result = new double[input.length];
            for (int i = 0; i < input.length; i++) {
                result[i] = input[i];
            }
            return result;
        }
    }

    private static final class CountingIntegerGenerator implements Generator<Integer> {
        private int value = 0;

        @Override
        public Integer next() {
            return value++;
        }
    }

    private static final class SkipIntegerGenerator implements Generator<Integer> {
        private int value = 0;
        private final int step;

        private SkipIntegerGenerator(int step) {
            this.step = step;
        }

        @Override
        public Integer next() {
            int old = value;
            value += step;
            return old;
        }
    }

    private static final class CountingDoubleGenerator implements Generator<Double> {
        private double value = 0.0;

        @Override
        public Double next() {
            double old = value;
            value += 1.0;
            return old;
        }
    }

    private static final class BigDecimalGenerator implements Generator<BigDecimal> {
        private BigDecimal value = BigDecimal.ZERO;
        private final BigDecimal step;

        private BigDecimalGenerator(BigDecimal step) {
            this.step = step;
        }

        @Override
        public BigDecimal next() {
            BigDecimal old = value;
            value = value.add(step);
            return old;
        }
    }

    private enum SphereGenerator implements Generator<Sphere> {
        INSTANCE;

        @Override
        public Sphere next() {
            return new Sphere();
        }
    }

    private static final class CountingCharGenerator implements Generator<Character> {
        @SuppressWarnings("SpellCheckingInspection")
        private static final char[] CHARS =
                "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

        private int index = 0;

        @Override
        public Character next() {
            char c = CHARS[index];
            index = (index + 1) % CHARS.length;
            return c;
        }
    }

    private static final class CountingStringGenerator implements Generator<String> {
        private final int length;
        private final CountingCharGenerator chars = new CountingCharGenerator();

        private CountingStringGenerator(int length) {
            this.length = length;
        }

        @Override
        public String next() {
            char[] buffer = new char[length];
            for (int i = 0; i < length; i++) {
                buffer[i] = chars.next();
            }
            return new String(buffer);
        }
    }
}