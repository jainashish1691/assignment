package com.assignment.cs.db.entity;

import lombok.extern.slf4j.Slf4j;
import lombok.var;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Slf4j
public class Test {
	public static void main(String[] args) {
		log.info("test");
		Runnable runnable = () -> {
			log.info(Thread.currentThread().getName());
		};
		/*
		 * Thread thread = new Thread(runnable); thread.start();
		 */
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		executorService.submit(runnable);
		executorService.submit(runnable);
		executorService.submit(runnable);

		List<String> ls = new ArrayList<>();
		ls.add("test");
		ls.add("test2");
		Consumer<String> consumer = System.out::println;
		ls.forEach(consumer);

	}

}
