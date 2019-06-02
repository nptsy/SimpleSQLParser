package com.ripstech.challenges.parser;

import java.util.LinkedList;

import com.ripstech.challenges.models.sql.SQLObject;
import com.ripstech.challenges.utils.Token;

public class Tokenizer {
	
	private LinkedList<TokenInfo> lstTokens;
	
	public Tokenizer() {
		lstTokens = new LinkedList<TokenInfo>();
	}
	
	public LinkedList<TokenInfo> tokenize(String line) throws ParseException{
		StringBuilder strToken = new StringBuilder();
		boolean isOpen = false;
		for(int i = 0 ; i < line.length() ; ++i) {
			char c = line.charAt(i);

			switch (c) {
				
				case '\n':
					break;
				case '\r':
					break;
			
				case 32:
					formAToken(strToken.toString());
					strToken = new StringBuilder();
					break;
			
				case Token.CDQUOTE:
					isOpen = true;
					strToken.append(c);
					
					while(i < line.length()) {
						i++;
						c = line.charAt(i);
						strToken.append(c);
						if(c == Token.CDQUOTE) {
							isOpen = false;
							break;
						}
					}
					
					if(isOpen) {
						throw new ParseException("Invalid SQL statement: " + line);
					}
					
					formAToken(strToken.toString());
					strToken = new StringBuilder();
				break;
				
				case Token.CSQUOTE:
					isOpen = true;
					strToken.append(c);
					
					while(i < line.length()-1) {
						i++;
						c = line.charAt(i);
						strToken.append(c);
						if(c == Token.CSQUOTE) {
							isOpen = false;
							break;
						}
					}
					
					if(isOpen) {
						throw new ParseException("Invalid SQL statement: " + line);
					}

					formAToken(strToken.toString());
					strToken = new StringBuilder();
				break;
				
				//parentheses
				case Token.CLPAREN:
					formAToken(strToken.toString());
					strToken = new StringBuilder();
					formAToken(Token.CLPAREN +"");
					break;
				
				case Token.CRPAREN:
					formAToken(strToken.toString());
					strToken = new StringBuilder();
					formAToken(Token.CRPAREN +"");
					break;
				
				// bracket
				case Token.CLBRACK:
					formAToken(strToken.toString());
					strToken = new StringBuilder();
					formAToken(Token.CLBRACK +"");
					break;
				
				case Token.CRBRACK:
					formAToken(strToken.toString());
					strToken = new StringBuilder();
					formAToken(Token.CRBRACK +"");
					break;
					
				case Token.CLBRACE:
					formAToken(strToken.toString());
					strToken = new StringBuilder();
					formAToken(Token.CLBRACE +"");
					break;
					
				case Token.CRBRACE:
					formAToken(strToken.toString());
					strToken = new StringBuilder();
					formAToken(Token.CRBRACE +"");
					break;
					
				//semicolon
				case Token.CSEMI:
					formAToken(strToken.toString());
					strToken = new StringBuilder();
					formAToken(Token.CSEMI +"");
					break;
				
				//comma
				case Token.CCOMMA:
					formAToken(strToken.toString());
					strToken = new StringBuilder();
					formAToken(Token.CCOMMA +"");
					break;
			default:
				strToken.append(c);
				break;
			}
		}
		return lstTokens;
	}
	
	public LinkedList<TokenInfo> tokenize(LinkedList<String> lstLines) throws ParseException{
		if(lstLines.size() <= 0)
			return null;
		
		boolean isOpen = false;
		for(String line : lstLines){
			StringBuilder strToken = new StringBuilder();
			
			for(int i = 0 ; i < line.length() ; ++i) {
				char c = line.charAt(i);
							
				
				switch (c) {
					
					case '\n':
						break;
					case '\r':
						break;
				
					case 32:
						formAToken(strToken.toString());
						strToken = new StringBuilder();
						break;
				
					case Token.CDQUOTE:
						isOpen = true;
						strToken.append(c);
						
						while(i < line.length()) {
							i++;
							c = line.charAt(i);
							strToken.append(c);
							if(c == Token.CDQUOTE) {
								isOpen = false;
								break;
							}
						}
						
						if(isOpen) {
							throw new ParseException("Invalid SQL statement: " + line);
						}
						
						formAToken(strToken.toString());
						strToken = new StringBuilder();
					break;
					
					case Token.CSQUOTE:
						isOpen = true;
						strToken.append(c);
						
						while(i < line.length()) {
							i++;
							c = line.charAt(i);
							strToken.append(c);
							if(c == Token.CSQUOTE) {
								isOpen = false;
								break;
							}
						}
						
						if(isOpen) {
							throw new ParseException("Invalid SQL statement: " + line);
						}

						formAToken(strToken.toString());
						strToken = new StringBuilder();
					break;
					
					//parentheses
					case Token.CLPAREN:
						formAToken(strToken.toString());
						strToken = new StringBuilder();
						formAToken(Token.CLPAREN +"");
						break;
					
					case Token.CRPAREN:
						formAToken(strToken.toString());
						strToken = new StringBuilder();
						formAToken(Token.CRPAREN +"");
						break;
					
					// bracket
					case Token.CLBRACK:
						formAToken(strToken.toString());
						strToken = new StringBuilder();
						formAToken(Token.CLBRACK +"");
						break;
					
					case Token.CRBRACK:
						formAToken(strToken.toString());
						strToken = new StringBuilder();
						formAToken(Token.CRBRACK +"");
						break;
						
					case Token.CLBRACE:
						formAToken(strToken.toString());
						strToken = new StringBuilder();
						formAToken(Token.CLBRACE +"");
						break;
						
					case Token.CRBRACE:
						formAToken(strToken.toString());
						strToken = new StringBuilder();
						formAToken(Token.CRBRACE +"");
						break;
						
					//semicolon
					case Token.CSEMI:
						formAToken(strToken.toString());
						strToken = new StringBuilder();
						formAToken(Token.CSEMI +"");
						break;
					
					//comma
					case Token.CCOMMA:
						formAToken(strToken.toString());
						strToken = new StringBuilder();
						formAToken(Token.CCOMMA +"");
						break;
				default:
					strToken.append(c);
					break;
				}
			}
		}
		
		/**
		 *  TODO check invalid if there is not any complete statement
		 */
		
		return lstTokens;
	}
	
	private void formAToken(String tok) {
		if(!tok.isEmpty()) {
			TokenInfo tokInfo = new TokenInfo();
			tokInfo.origValue = tok.toString();
			lstTokens.add(tokInfo);
		}
	}
	
	public class TokenInfo{
		private SQLObject sqlObj;
		private String origValue;
		public String getOrigValue() {
			return origValue;
		}
		public void setOrigValue(String origValue) {
			this.origValue = origValue;
		}
	}
	
	
//	public void tokenize(String inFilePath) {
//		InputStream in;
//		try {
//			in = new FileInputStream(inFilePath);
//			Reader r = new InputStreamReader(in, "UTF-8");
//			int intch;
//			while ((intch = r.read()) != -1) {
//			  char ch = (char) intch;
//			}	
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	
	
//	public LinkedList<TokenInfo> tokenize2(LinkedList<String> lstLines) throws ParseException{
//		if(lstLines.size() <= 0)
//			return null;
//		
//		boolean formAToken = false;
//		boolean isOpen = false;
//		for(String line : lstLines){
//			StringBuilder strToken = new StringBuilder();
//			
//			for(int i = 0 ; i < line.length() ; ++i) {
//				char c = line.charAt(i);
//							
//				
//				switch (c) {
//					
//					case 32:
//						formAToken = true;
//						break;
//				
//					case Token.CDQUOTE:
//						isOpen = true;
//						strToken.append(c);
//						
//						while(i < line.length()) {
//							i++;
//							c = line.charAt(i);
//							strToken.append(c);
//							if(c == Token.CDQUOTE) {
//								isOpen = false;
//								break;
//							}
//						}
//						
//						if(isOpen) {
//							throw new ParseException("Invalid SQL statement: " + line);
//						}
//						
//						formAToken = true;
//					break;
//					
//					case Token.CSQUOTE:
//						isOpen = true;
//						strToken.append(c);
//						
//						while(i < line.length()) {
//							i++;
//							c = line.charAt(i);
//							strToken.append(c);
//							if(c == Token.CSQUOTE) {
//								isOpen = false;
//								break;
//							}
//						}
//						
//						if(isOpen) {
//							throw new ParseException("Invalid SQL statement: " + line);
//						}
//
//						formAToken = true;
//					break;
//					//parentheses
//					case Token.CLPAREN:
//						formAToken = true;
//						break;
//					
//					case Token.CRPAREN:
//						formAToken = true;
//						break;
//					
//					// bracket
//					case Token.CLBRACK:
//						formAToken = true;
//						break;
//					
//					case Token.CRBRACK:
//						formAToken = true;
//						break;
//						
//					case Token.CLBRACE:
//						formAToken = true;
//						break;
//						
//					case Token.CRBRACE:
//						formAToken = true;
//						break;
//						
//					//semicolon
//					case Token.CSEMI:
//						formAToken = true;
//						break;
//					
//					//comma
//					case Token.CCOMMA:
//						formAToken = true;
//						break;
//				default:
//					strToken.append(c);
//					break;
//				}
//				
//				if(formAToken) {
//					formAToken =false;
//					if(!strToken.toString().isEmpty()) {
//						TokenInfo tokInfo = new TokenInfo();
//						tokInfo.origValue = strToken.toString();
//						lstTokens.add(tokInfo);
//						strToken = new StringBuilder();
//					}
//				}
//			}
//		}
//		
//		return lstTokens;
//	}
		
}
