package com.namoo.social.shared.exception;

public class NamooSocialExceptionFactory {

	private NamooSocialExceptionFactory() {
		//
	}

	public static NamooSocialException createRuntime(String msg) {
		//
		return new NamooSocialException(msg);
	}
}
