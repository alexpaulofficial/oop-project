package it.univpm.GiAle.twitterProj.tests;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.univpm.GiAle.twitterProj.exception.WrongFilterException;
import it.univpm.GiAle.twitterProj.exception.GetTweetException;
import it.univpm.GiAle.twitterProj.service.TweetServiceImpl;

/**
 * Questo test verifica che ritorni una lista vuota nel caso in cui non
 * siano stati scaricati dei Tweet
 * @author Verdolini Gian Paolo, Paolucci Alessio
 */
class TestProj {
	TweetServiceImpl tsi;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

/**
 * Si va a fare il test per vedere che la lista sia vuota
 * @throws GetTweetException
 * @throws WrongFilterException
 */

	@Test
	public void test1() throws GetTweetException, WrongFilterException {
		tsi = new TweetServiceImpl();
		assertThrows(GetTweetException.class, ()-> tsi.getTweet());
		
		
	

}
}