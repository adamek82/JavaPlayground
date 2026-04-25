package org.example;

import java.util.*;

public final class CollectionExamples {
    private CollectionExamples() {}

    public static void runAll() {
        section("1. Why containers?");
        arraysVsCollections();

        section("2. Generics and type-safe containers");
        rawCollectionsProblem();
        genericCollections();

        section("3. Collection, List, Set, Queue, Map");
        basicContainerTaxonomy();

        section("4. List basics");
        listBasics();
        removeOverloadTrap();
        subListView();

        section("5. ArrayList vs LinkedList");
        arrayListVsLinkedList();

        section("6. Set basics");
        setBasics();
        hashSetTreeSetLinkedHashSet();
        equalsAndHashCodeInHashSet();

        section("7. Map basics");
        mapBasics();
        mapViews();
        hashMapTreeMapLinkedHashMap();
        mapOfLists();

        section("8. Queue and PriorityQueue");
        queueBasics();
        priorityQueueBasics();

        section("9. Iterator, Iterable, foreach");
        iteratorBasics();
        iterableBasics();
        multipleIterationStrategies();

        section("10. Arrays.asList() and collections");
        arraysAsListAgain();

        section("11. Key takeaways");
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
    // 1. Why containers?
    // =========================================================================

    private static void arraysVsCollections() {
        subsection("Arrays have fixed size");

        Apple[] applesArray = new Apple[3];
        applesArray[0] = new Apple("A");
        applesArray[1] = new Apple("B");
        applesArray[2] = new Apple("C");

        System.out.println("array           = " + Arrays.toString(applesArray));
        System.out.println("array.length    = " + applesArray.length);

        subsection("Collections resize automatically");

        List<Apple> apples = new ArrayList<>();
        apples.add(new Apple("A"));
        apples.add(new Apple("B"));
        apples.add(new Apple("C"));
        apples.add(new Apple("D"));

        System.out.println("list            = " + apples);
        System.out.println("list.size()     = " + apples.size());

        // Note:
        // Arrays are still useful for fixed-size data, primitives, low-level APIs,
        // matrices, buffers, and performance-sensitive code.
        // Collections are usually more convenient for dynamically sized groups.
    }

    // =========================================================================
    // 2. Generics and type-safe containers
    // =========================================================================

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void rawCollectionsProblem() {
        subsection("Raw collections lose type safety");

        List raw = new ArrayList();
        raw.add(new Apple("apple-1"));
        raw.add(new Orange("orange-1")); // Compiles, but this is logically wrong.

        System.out.println("raw             = " + raw);

        try {
            Apple apple = (Apple) raw.get(1); // Runtime ClassCastException.
            System.out.println(apple);
        } catch (ClassCastException e) {
            System.out.println("Casting Orange to Apple causes ClassCastException");
        }
    }

    @SuppressWarnings("SequencedCollectionMethodCanBeUsed")
    private static void genericCollections() {
        subsection("Generic collections catch type errors at compile time");

        List<Apple> apples = new ArrayList<>();
        apples.add(new Apple("apple-1"));
        apples.add(new GrannySmith("granny-1"));

        // This would not compile:
        // apples.add(new Orange("orange-1"));

        for (Apple apple : apples) {
            System.out.println("apple           = " + apple);
        }

        // No cast is needed when fetching elements.
        Apple first = apples.get(0);
        System.out.println("first.id        = " + first.id());
    }

    // =========================================================================
    // 3. Collection, List, Set, Queue, Map
    // =========================================================================

    private static void basicContainerTaxonomy() {
        subsection("Main container families");

        List<String> list = new ArrayList<>();
        Set<String> set = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        Map<String, Integer> map = new HashMap<>();

        Collections.addAll(list, "dog", "cat", "dog");
        Collections.addAll(set, "dog", "cat", "dog");
        queue.offer("first");
        queue.offer("second");
        map.put("dog", 1);
        map.put("cat", 2);

        System.out.println("List            = " + list + "  // ordered, duplicates allowed");
        System.out.println("Set             = " + set + "  // unique elements");
        System.out.println("Queue           = " + queue + "  // retrieval discipline");
        System.out.println("Map             = " + map + "  // key -> value");

        subsection("Program to interfaces");

        List<String> names = new ArrayList<>();
        names.add("Alice");
        names.add("Bob");

        System.out.println("names           = " + names);

        // You can change the implementation at the creation point:
        names = new LinkedList<>(names);
        names.add("Charlie");

        System.out.println("names           = " + names);
    }

    // =========================================================================
    // 4. List basics
    // =========================================================================

    @SuppressWarnings({"SequencedCollectionMethodCanBeUsed", "SlowListContainsAll"})
    private static void listBasics() {
        subsection("Basic List operations");

        List<String> words = new ArrayList<>(Arrays.asList("A", "B", "C"));

        System.out.println("initial         = " + words);

        words.add("D");
        words.add(1, "X");

        System.out.println("after add       = " + words);
        System.out.println("get(2)          = " + words.get(2));
        System.out.println("contains(\"C\")  = " + words.contains("C"));
        System.out.println("indexOf(\"C\")   = " + words.indexOf("C"));

        words.set(2, "Y");
        System.out.println("after set       = " + words);

        words.remove("X");
        System.out.println("remove object   = " + words);

        words.remove(0);
        System.out.println("remove index    = " + words);

        subsection("Bulk operations");

        List<String> copy = new ArrayList<>(words);
        copy.addAll(Arrays.asList("A", "B", "C", "D"));

        System.out.println("copy            = " + copy);
        System.out.println("containsAll     = " + copy.containsAll(Arrays.asList("A", "B")));

        copy.removeAll(Arrays.asList("A", "D"));
        System.out.println("removeAll       = " + copy);

        copy.retainAll(Arrays.asList("B", "C"));
        System.out.println("retainAll       = " + copy);
    }

    private static void removeOverloadTrap() {
        subsection("remove(int index) vs remove(Object value)");

        List<Integer> numbers = new ArrayList<>(Arrays.asList(10, 20, 30, 20, 40));

        System.out.println("initial         = " + numbers);

        numbers.remove(2);
        System.out.println("remove(2)       = " + numbers + "  // removed element at index 2");

        numbers.remove(Integer.valueOf(20));
        System.out.println("remove(Integer.valueOf(20)) = " + numbers
                + "  // removed value 20");

        // With List<String>, this trap is less likely:
        List<String> letters = new ArrayList<>(Arrays.asList("A", "B", "C"));
        letters.remove("B");
        System.out.println("letters         = " + letters);
    }

    private static void subListView() {
        subsection("subList() returns a view, not an independent copy");

        List<String> letters = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E"));
        List<String> sub = letters.subList(1, 4);

        System.out.println("letters         = " + letters);
        System.out.println("sub             = " + sub);

        sub.set(1, "X");
        System.out.println("After sub.set(1, \"X\"):");
        System.out.println("letters         = " + letters);
        System.out.println("sub             = " + sub);

        sub.clear();
        System.out.println("After sub.clear():");
        System.out.println("letters         = " + letters);
        System.out.println("sub             = " + sub);

        subsection("Make a copy when you need independence");

        List<String> original = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));
        List<String> independent = new ArrayList<>(original.subList(1, 3));

        independent.add("X");

        System.out.println("original        = " + original);
        System.out.println("independent     = " + independent);
    }

    // =========================================================================
    // 5. ArrayList vs LinkedList
    // =========================================================================

    @SuppressWarnings("ConstantValue")
    private static void arrayListVsLinkedList() {
        subsection("ArrayList: good default List implementation");

        List<String> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, "A", "B", "C", "D");

        System.out.println("arrayList       = " + arrayList);
        System.out.println("arrayList.get(2)= " + arrayList.get(2));
        System.out.println("RandomAccess?   = " + (arrayList instanceof RandomAccess));

        subsection("LinkedList: List + Deque/Queue-style operations");

        LinkedList<String> linkedList = new LinkedList<>();
        Collections.addAll(linkedList, "B", "C");

        linkedList.addFirst("A");
        linkedList.addLast("D");

        System.out.println("linkedList      = " + linkedList);
        System.out.println("getFirst()      = " + linkedList.getFirst());
        System.out.println("getLast()       = " + linkedList.getLast());
        System.out.println("removeFirst()   = " + linkedList.removeFirst());
        System.out.println("after remove    = " + linkedList);
        System.out.println("RandomAccess?   = " + (linkedList instanceof RandomAccess));

        // Rule of thumb:
        // ArrayList is usually the default choice.
        // LinkedList is useful mainly when you need Deque/Queue-style operations
        // or specific insertion/removal behavior through iterators.
    }

    // =========================================================================
    // 6. Set basics
    // =========================================================================

    @SuppressWarnings("OverwrittenKey")
    private static void setBasics() {
        subsection("Set stores unique values");

        Set<String> words = new HashSet<>();

        words.add("dog");
        words.add("cat");
        words.add("dog");   // Intentionally duplicated: Set keeps only one "dog".
        words.add("rat");

        System.out.println("set             = " + words);
        System.out.println("size            = " + words.size());
        System.out.println("contains dog    = " + words.contains("dog"));
    }

    private static void hashSetTreeSetLinkedHashSet() {
        subsection("HashSet vs TreeSet vs LinkedHashSet");

        List<String> input = Arrays.asList("dog", "cat", "rat", "cat", "ant");

        Set<String> hashSet = new HashSet<>(input);
        Set<String> treeSet = new TreeSet<>(input);
        Set<String> linkedHashSet = new LinkedHashSet<>(input);

        System.out.println("input           = " + input);
        System.out.println("HashSet         = " + hashSet + "  // no guaranteed order");
        System.out.println("TreeSet         = " + treeSet + "  // sorted order");
        System.out.println("LinkedHashSet   = " + linkedHashSet + "  // insertion order");
    }

    private static void equalsAndHashCodeInHashSet() {
        subsection("HashSet depends on equals() and hashCode()");

        Set<PersonBadHash> bad = new HashSet<>();
        bad.add(new PersonBadHash("123"));
        bad.add(new PersonBadHash("123"));

        System.out.println("bad hash set    = " + bad);
        System.out.println("bad size        = " + bad.size()
                + "  // logical duplicates may remain");

        Set<PersonGoodHash> good = new HashSet<>();
        good.add(new PersonGoodHash("123"));
        good.add(new PersonGoodHash("123"));

        System.out.println("good hash set   = " + good);
        System.out.println("good size       = " + good.size());

        // Contract:
        // if a.equals(b), then a.hashCode() must equal b.hashCode().
    }

    // =========================================================================
    // 7. Map basics
    // =========================================================================

    @SuppressWarnings("OverwrittenKey")
    private static void mapBasics() {
        subsection("Map stores key-value pairs");

        Map<String, Integer> ages = new HashMap<>();

        ages.put("Alice", 30);
        ages.put("Bob", 40);
        ages.put("Alice", 31); // Replaces previous value for this key.

        System.out.println("ages            = " + ages);
        System.out.println("Alice           = " + ages.get("Alice"));
        System.out.println("missing         = " + ages.get("Charlie"));

        subsection("Counting with Map");

        List<String> words = Arrays.asList("java", "map", "java", "set", "map", "java");
        Map<String, Integer> counts = new HashMap<>();

        for (String word : words) {
            counts.put(word, counts.getOrDefault(word, 0) + 1);
        }

        System.out.println("words           = " + words);
        System.out.println("counts          = " + counts);
    }

    private static void mapViews() {
        subsection("keySet(), values(), entrySet()");

        Map<String, Pet> pets = new LinkedHashMap<>();
        pets.put("dog", new Pet("Rex"));
        pets.put("cat", new Pet("Molly"));
        pets.put("rat", new Pet("Fuzzy"));

        System.out.println("map             = " + pets);
        System.out.println("keySet          = " + pets.keySet());
        System.out.println("values          = " + pets.values());
        System.out.println("entrySet        = " + pets.entrySet());

        subsection("Iterating with Map.Entry");

        for (Map.Entry<String, Pet> entry : pets.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    private static void hashMapTreeMapLinkedHashMap() {
        subsection("HashMap vs TreeMap vs LinkedHashMap");

        Map<String, String> input = new HashMap<>();
        input.put("PL", "Poland");
        input.put("DE", "Germany");
        input.put("FR", "France");
        input.put("ES", "Spain");

        Map<String, String> hashMap = new HashMap<>(input);
        Map<String, String> treeMap = new TreeMap<>(input);
        Map<String, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("PL", "Poland");
        linkedHashMap.put("DE", "Germany");
        linkedHashMap.put("FR", "France");
        linkedHashMap.put("ES", "Spain");

        System.out.println("HashMap         = " + hashMap + "  // no guaranteed order");
        System.out.println("TreeMap         = " + treeMap + "  // sorted by key");
        System.out.println("LinkedHashMap   = " + linkedHashMap + "  // insertion order");
    }

    private static void mapOfLists() {
        subsection("Map<K, List<V>>");

        Map<String, List<Integer>> positions = new HashMap<>();
        List<String> words = Arrays.asList("java", "map", "java", "list", "map", "java");

        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i);
            positions.computeIfAbsent(word, _ -> new ArrayList<>()).add(i);
        }

        System.out.println("words           = " + words);
        System.out.println("positions       = " + positions);

        subsection("Recreate original order using inverted TreeMap");

        Map<Integer, String> byPosition = new TreeMap<>();

        for (Map.Entry<String, List<Integer>> entry : positions.entrySet()) {
            for (Integer position : entry.getValue()) {
                byPosition.put(position, entry.getKey());
            }
        }

        System.out.println("byPosition      = " + byPosition);
        System.out.println("recreated       = " + byPosition.values());
    }

    // =========================================================================
    // 8. Queue and PriorityQueue
    // =========================================================================

    @SuppressWarnings("ConstantValue")
    private static void queueBasics() {
        subsection("Queue FIFO behavior");

        Queue<String> queue = new LinkedList<>();

        queue.offer("first");
        queue.offer("second");
        queue.offer("third");

        System.out.println("queue           = " + queue);
        System.out.println("peek            = " + queue.peek());
        System.out.println("poll            = " + queue.poll());
        System.out.println("after poll      = " + queue);

        subsection("poll()/peek() vs remove()/element() on empty queue");

        Queue<String> empty = new LinkedList<>();

        System.out.println("empty.peek()    = " + empty.peek());
        System.out.println("empty.poll()    = " + empty.poll());

        try {
            empty.element();
        } catch (NoSuchElementException e) {
            System.out.println("empty.element() throws NoSuchElementException");
        }

        try {
            empty.remove();
        } catch (NoSuchElementException e) {
            System.out.println("empty.remove()  throws NoSuchElementException");
        }
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static void priorityQueueBasics() {
        subsection("PriorityQueue natural order");

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        Collections.addAll(pq, 10, 3, 7, 1, 5);

        System.out.println("internal view   = " + pq + "  // not sorted display order");

        while (!pq.isEmpty()) {
            System.out.print(pq.poll() + " ");
        }
        System.out.println();

        subsection("PriorityQueue with Comparator");

        PriorityQueue<Task> tasks = new PriorityQueue<>(
                Comparator.comparingInt(Task::priority).reversed()
        );

        tasks.offer(new Task("low", 1));
        tasks.offer(new Task("high", 10));
        tasks.offer(new Task("medium", 5));

        while (!tasks.isEmpty()) {
            System.out.println(tasks.poll());
        }

        subsection("PriorityQueue needs Comparable or Comparator");

        PriorityQueue<Dummy> dummyQueue = new PriorityQueue<>();

        // PriorityQueue needs either naturally comparable elements
        // or an explicit Comparator. Otherwise insertion may fail.
        try {
            dummyQueue.offer(new Dummy());
        } catch (ClassCastException e) {
            System.out.println("Adding non-comparable Dummy causes ClassCastException");
        }
    }

    // =========================================================================
    // 9. Iterator, Iterable, foreach
    // =========================================================================

    @SuppressWarnings({"WhileLoopReplaceableByForEach", "Java8CollectionRemoveIf"})
    private static void iteratorBasics() {
        subsection("Iterator basics");

        List<String> words = new ArrayList<>(Arrays.asList("A", "B", "C"));

        Iterator<String> it = words.iterator();
        while (it.hasNext()) {
            System.out.println("next            = " + it.next());
        }

        subsection("Iterator.remove() removes last returned element");

        words = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));
        Iterator<String> removeIt = words.iterator();

        while (removeIt.hasNext()) {
            String value = removeIt.next();
            if (value.equals("B") || value.equals("C")) {
                removeIt.remove();
            }
        }

        System.out.println("after remove    = " + words);

        subsection("ListIterator can move both ways and modify");

        List<String> letters = new ArrayList<>(Arrays.asList("A", "B", "C"));
        ListIterator<String> listIterator = letters.listIterator();

        while (listIterator.hasNext()) {
            String value = listIterator.next();
            if (value.equals("B")) {
                listIterator.set("X");
                listIterator.add("Y");
            }
        }

        System.out.println("letters         = " + letters);
    }

    private static void iterableBasics() {
        subsection("Anything Iterable works with foreach");

        WordSequence sequence = new WordSequence("And that is how we know the Earth is banana-shaped");

        for (String word : sequence) {
            System.out.print(word + " ");
        }
        System.out.println();

        subsection("Arrays work in foreach, but arrays are not Iterable");

        String[] array = {"A", "B", "C"};

        for (String s : array) {
            System.out.print(s + " ");
        }
        System.out.println();

        // This would not compile:
        // acceptIterable(array);

        acceptIterable(Arrays.asList(array));
    }

    private static void acceptIterable(Iterable<String> iterable) {
        System.out.print("Iterable input  = ");
        for (String s : iterable) {
            System.out.print(s + " ");
        }
        System.out.println();
    }

    private static void multipleIterationStrategies() {
        subsection("One class, multiple Iterable views");

        PetSequence pets = new PetSequence();

        System.out.print("normal          = ");
        for (Pet pet : pets) {
            System.out.print(pet + " ");
        }
        System.out.println();

        System.out.print("reversed        = ");
        for (Pet pet : pets.reversed()) {
            System.out.print(pet + " ");
        }
        System.out.println();

        System.out.print("shuffled        = ");
        for (Pet pet : pets.shuffled()) {
            System.out.print(pet + " ");
        }
        System.out.println();

        // This is the Adapter Method idiom:
        // additional methods return alternative Iterable views.
    }

    // =========================================================================
    // 10. Arrays.asList() and collections
    // =========================================================================

    private static void arraysAsListAgain() {
        subsection("Arrays.asList() returns a fixed-size view backed by the array");

        String[] array = {"A", "B", "C", "D"};
        List<String> view = Arrays.asList(array);

        System.out.println("array           = " + Arrays.toString(array));
        System.out.println("view            = " + view);

        view.set(1, "X");

        System.out.println("After view.set(1, \"X\"):");
        System.out.println("array           = " + Arrays.toString(array));
        System.out.println("view            = " + view);

        try {
            view.add("E");
        } catch (UnsupportedOperationException e) {
            System.out.println("view.add(\"E\") throws UnsupportedOperationException");
        }

        subsection("Make a copy when you need a resizable list");

        List<String> copy = new ArrayList<>(Arrays.asList(array));
        copy.add("E");

        System.out.println("copy            = " + copy);
        System.out.println("array           = " + Arrays.toString(array));
    }

    // =========================================================================
    // 11. Key takeaways
    // =========================================================================

    private static void keyTakeaways() {
        subsection("Summary");

        System.out.println("""
                1) Collections resize automatically; arrays have fixed size.
                2) Generics make containers type-safe at compile time.
                3) List preserves order and allows duplicates.
                4) Set stores unique values.
                5) Queue defines retrieval discipline: FIFO or priority-based.
                6) Map stores key-value pairs and is not a Collection.
                7) Program to interfaces: List, Set, Queue, Map, Collection, Iterable.
                8) ArrayList is usually the default List implementation.
                9) LinkedList is useful mostly as Deque/Queue or for iterator-based insertion/removal.
                10) HashSet/HashMap are fast on average but do not guarantee order.
                11) TreeSet/TreeMap keep elements/keys sorted.
                12) LinkedHashSet/LinkedHashMap preserve insertion order.
                13) Hash-based containers require a correct equals()/hashCode() contract.
                14) subList() is a view, not an independent copy.
                15) Arrays.asList() is a fixed-size view backed by the array.
                16) Iterator separates traversal from the underlying container.
                17) Iterable is enough for foreach; Collection is a much larger contract.
                18) PriorityQueue requires Comparable elements or an explicit Comparator.
                """);
    }

    // =========================================================================
    // Helper classes
    // =========================================================================

    private static class Apple {
        private final String id;

        private Apple(String id) {
            this.id = id;
        }

        private String id() {
            return id;
        }

        @Override
        public String toString() {
            return "Apple(" + id + ")";
        }
    }

    private static final class GrannySmith extends Apple {
        private GrannySmith(String id) {
            super(id);
        }
    }

    private record Orange(String id) {}

    private record Pet(String name) {
        @Override
        public String toString() {
            return "Pet(" + name + ")";
        }
    }

    private record Task(String name, int priority) {
        @Override
        public String toString() {
            return "Task(" + name + ", priority=" + priority + ")";
        }
    }

    private static final class Dummy {}

    @SuppressWarnings("ClassCanBeRecord")
    private static final class PersonBadHash {
        private final String pesel;

        private PersonBadHash(String pesel) {
            this.pesel = pesel;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof PersonBadHash other
                    && Objects.equals(this.pesel, other.pesel);
        }

        // Intentionally wrong:
        // equals() is based on pesel, but hashCode() remains identity-based.

        @Override
        public String toString() {
            return "PersonBadHash(" + pesel + ")";
        }
    }

    @SuppressWarnings("ClassCanBeRecord")
    private static final class PersonGoodHash {
        private final String pesel;

        private PersonGoodHash(String pesel) {
            this.pesel = pesel;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof PersonGoodHash other
                    && Objects.equals(this.pesel, other.pesel);
        }

        @Override
        public int hashCode() {
            return Objects.hash(pesel);
        }

        @Override
        public String toString() {
            return "PersonGoodHash(" + pesel + ")";
        }
    }

    private static final class WordSequence implements Iterable<String> {
        private final String[] words;

        private WordSequence(String text) {
            this.words = text.split(" ");
        }

        @SuppressWarnings("NullableProblems")
        @Override
        public Iterator<String> iterator() {
            return Arrays.asList(words).iterator();
        }
    }

    private static final class PetSequence implements Iterable<Pet> {
        private final Pet[] pets = {
                new Pet("Rat"),
                new Pet("Manx"),
                new Pet("Cymric"),
                new Pet("Mutt"),
                new Pet("Pug")
        };

        @SuppressWarnings("NullableProblems")
        @Override
        public Iterator<Pet> iterator() {
            return Arrays.asList(pets).iterator();
        }

        private Iterable<Pet> reversed() {
            return () -> new Iterator<>() {
                private int index = pets.length - 1;

                @Override
                public boolean hasNext() {
                    return index >= 0;
                }

                @Override
                public Pet next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    return pets[index--];
                }
            };
        }

        private Iterable<Pet> shuffled() {
            return () -> {
                List<Pet> shuffled = new ArrayList<>(Arrays.asList(pets));
                Collections.shuffle(shuffled, new Random(47));
                return shuffled.iterator();
            };
        }
    }
}
