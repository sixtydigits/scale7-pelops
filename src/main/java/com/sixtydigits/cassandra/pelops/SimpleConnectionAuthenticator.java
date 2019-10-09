/*
 * The MIT License
 *
 * Copyright (c) 2011 Dominic Williams, Daniel Washusen and contributors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.sixtydigits.cassandra.pelops;

import java.util.HashMap;
import java.util.Map;

import org.apache.cassandra.thrift.AuthenticationRequest;

public class SimpleConnectionAuthenticator implements IConnectionAuthenticator {
    public static final java.lang.String USERNAME_KEY = "username";
    public static final java.lang.String PASSWORD_KEY = "password";

	private String username;
	private String password;
	
	public SimpleConnectionAuthenticator() {
		
	}
	
	public SimpleConnectionAuthenticator(String username,String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public AuthenticationRequest getAuthenticationRequest() {
	  Map<String, String> params = new HashMap<String, String>();
	  params.put(USERNAME_KEY, username);
	  params.put(PASSWORD_KEY, password);
	  return new AuthenticationRequest(params);
	}

	@Override
	public String toString() {
		return "SimpleConnectionAuthenticator [username=" + username
				+ ", password=" + password + "]";
	}
}
