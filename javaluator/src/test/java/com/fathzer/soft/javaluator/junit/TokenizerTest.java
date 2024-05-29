package com.fathzer.soft.javaluator.junit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.fathzer.soft.javaluator.Tokenizer;

public class TokenizerTest {

	@Test
	public void test() {
		final Tokenizer tokenizer = new Tokenizer(Arrays.asList("&&", "&", "||", "|"));
		Iterator<String> iterator = tokenizer.tokenize(" & ab && b | c || d ");
		List<String> tokens = new ArrayList<String>();
		while (iterator.hasNext()) {
			tokens.add(iterator.next());
		}
		assertEquals(Arrays.asList("&", "ab", "&&", "b", "|", "c", "||", "d"), tokens);
		
		tokenizer.setTrimTokens(false);
		iterator = tokenizer.tokenize(" & ab && b | c || d ");
		tokens.clear();
		while (iterator.hasNext()) {
			tokens.add(iterator.next());
		}
		assertEquals(Arrays.asList(" ", "&", " ab ", "&&", " b ", "|", " c ", "||", " d "), tokens);
	}

}
