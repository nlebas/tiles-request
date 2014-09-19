package org.apache.tiles.request.reflections;

import java.util.regex.Pattern;

public class AntPath {
	private char[] antPath;
	private Pattern pattern;
	public AntPath(String antPath) {
		this.antPath = antPath.toCharArray();
	}
	
	public boolean matches(String input) {
		return getPattern().matcher(input).matches();
	}

	public Pattern getPattern() {
		if(pattern == null) {
			pattern = Pattern.compile(toRegex());
		}
		return pattern;
	}
	
	public String toRegex() {
		StringBuilder output = new StringBuilder();
		boolean escape = false;
		for(int i=0; i<antPath.length; i++) {
			if(escape) {
				escape = false;
				switch(antPath[i]) {
				case '/':
				case '*':
				case '?':
				case '^':
				case '$':
				case '.':
				case '|':
				case '+':
				case '(':
				case ')':
				case '[':
				case ']':
				case '{':
				case '}':
				case '\\':
					output.append('\\').append(antPath[i]);
					break;
				default:
					output.append(antPath[i]);
				}
			}
			else {
				switch(antPath[i]) {
				case '/':
					output.append("[/\\\\]");
					if(i==0) {
						output.append('*');
					}
					else {
						output.append('+');
					}
					break;
				case '*':
					if(i+1 < antPath.length && antPath[i+1] == '*') {
						i++;
						output.append(".*");
					}
					else {
						output.append("[^/\\\\]*");
					}
					break;
				case '?':
					output.append("[^/\\\\]");
					break;
				case '^':
				case '$':
				case '.':
				case '|':
				case '+':
				case '(':
				case ')':
				case '[':
				case ']':
				case '{':
				case '}':
					output.append('\\').append(antPath[i]);
					break;
				case '\\':
					escape = true;
					break;
				default:
					output.append(antPath[i]);
				}
			}
		}
		return output.toString();
	}
}
