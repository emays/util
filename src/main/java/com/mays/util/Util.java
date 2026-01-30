package com.mays.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Util {

	private Util() {
	}

	public static PrintWriter newPrintWriter(Path path) throws IOException {
		return new PrintWriter(Files.newBufferedWriter(path));
	}

	public static void trFile(String fr, String to, String in_file, String out_file) throws IOException {
		Stream<String> lines = Files.lines(Paths.get(in_file)).map(line -> line.replace(fr, to));
		Files.write(Paths.get(out_file), iterable(lines));
	}

	@SafeVarargs
	public static <T> ArrayList<T> asArrayList(T... a) {
		return new ArrayList<>(Arrays.asList(a));
	}

	public static <T> Iterable<T> iterable(Stream<T> stream) {
		return (Iterable<T>) stream::iterator;
	}

	public static <T> Iterable<T> iterable(Iterator<T> iterator) {
		return (Iterable<T>) () -> iterator;
	}

	public static Iterable<String> lines(Path path) throws IOException {
		return iterable(Files.lines(path));
	}

	public static Stream<String> getResourceLines(String resource) throws FileNotFoundException {
		InputStream stream = Util.class.getResourceAsStream(resource);
		if (stream == null)
			throw new FileNotFoundException(resource);
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		return reader.lines();
	}

	public static String tabDelimitedString(List<String> fields) {
		return delimitedString("\t", fields);
	}

	public static String delimitedString(String delim, List<String> fields) {
		return String.join(delim, fields);
	}

	public static String tabDelimitedString(Object... fields) {
		return delimitedString("\t", fields);
	}

	public static String delimitedString(String delim, Object... fields) {
		ArrayList<String> list = new ArrayList<>();
		Arrays.stream(fields).forEach(field -> {
			if (field == null)
				throw new IllegalArgumentException(list.toString());
			list.add(field.toString());
		});
		return delimitedString(delim, list);
	}

	public static <T extends Comparable<T>> T get(Set<T> set, T elem) {
		return set.stream().filter(edit -> edit.compareTo(elem) == 0).findFirst().orElse(null);
	}

	public static void repeat(int count, IntConsumer consumer) {
		IntStream.range(0, count).forEach(consumer);
	}

}
