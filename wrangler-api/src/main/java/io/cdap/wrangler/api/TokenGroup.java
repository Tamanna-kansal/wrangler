/*
 *  Copyright © 2017-2019 Cask Data, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 */

package io.cdap.wrangler.api;

import io.cdap.wrangler.api.parser.Token;
import io.cdap.wrangler.api.parser.ByteSizeToken;
import io.cdap.wrangler.api.parser.TimeDurationToken;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class description here.
 */
public final class TokenGroup {
  private final SourceInfo info;
  private final List<Token> tokens;
  private long byteSize; // To accumulate byte size values
  private long timeDuration; // To accumulate time duration values
  private final List<Token> otherTokens; // To store other token types

  public TokenGroup() {
    this.info = null;
    this.tokens = new ArrayList<>();
    this.otherTokens = new ArrayList<>();
  }

  public TokenGroup(SourceInfo info) {
    this.info = info;
    this.tokens = new ArrayList<>();
    this.otherTokens = new ArrayList<>();
  }

  public TokenGroup(List<Token> tokens) {
    this();
    for (Token token : tokens) {
      addToken(token);
    }
  }

  public void add(Token token) {
    tokens.add(token);
  }

  public int size() {
    return tokens.size();
  }

  public Token get(int i) {
    return tokens.get(i);
  }

  public Iterator<Token> iterator() {
    return tokens.iterator();
  }

  public SourceInfo getSourceInfo() {
    return info;
  }

  public void addToken(Token token) {
    if (token instanceof ByteSizeToken) {
      // Handle ByteSize token
      // For instance, convert to canonical unit (bytes)
      this.byteSize = ((ByteSizeToken) token).getBytes();
    } else if (token instanceof TimeDurationToken) {
      // Handle TimeDuration token
      // For instance, convert to canonical unit (milliseconds)
      this.timeDuration = ((TimeDurationToken) token).getMilliseconds();
    } else {
      // Handle other token types
      this.otherTokens.add(token);
    }
  }

}
