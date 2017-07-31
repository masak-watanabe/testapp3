package test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class TestMain {

	public static void main(String[] args) {
	
		List<String> lists = Arrays.asList("tokyo", "ibaraki", "saitama");
		Stream<String> stream = lists.stream();   // 直列ストリーム
		Stream<String> pstream = lists.parallelStream();   // 並列ストリーム
		stream.forEach(System.out::println);
		System.out.println("============");
		pstream.forEach(System.out::println);
		
		final StringBuilder builder = new StringBuilder("Hello ");
        Consumer<String> consumer = builder::append;
        
        consumer.accept("aaaaaaaaaaaa");
        consumer.andThen(System.out::println);
       
        Maze maze = new Maze(1, 1);
        
        maze.print();;
        
	}
}
