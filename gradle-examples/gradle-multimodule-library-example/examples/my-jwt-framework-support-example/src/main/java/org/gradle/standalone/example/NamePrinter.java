package org.gradle.standalone.example;

import org.gradle.example.JwtNameDecoder;

public class NamePrinter {

	public static final void main(String[] args) {
		if(args == null || args.length != 1) {
			System.out.println("Usage: NamePrinter <JWT TOKEN>");
			System.exit(1);
		} else {
			System.out.println("Name is '" + JwtNameDecoder.getName(args[0]) + "'");
		}
	}
}
