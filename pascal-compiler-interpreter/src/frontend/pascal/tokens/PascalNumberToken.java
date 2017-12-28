package frontend.pascal.tokens;

import frontend.Source;
import frontend.pascal.PascalErrorCode;
import frontend.pascal.PascalTokenType;

public class PascalNumberToken extends PascalToken {

	public PascalNumberToken(Source source) throws Exception {
		super(source);
	}

	protected void extractNumber(StringBuilder textBuffer) throws Exception {

		String wholeDigits = null; // digits before decimal point
		String fractionDigits = null; // digits after decimal point
		String exponentDigits = null; // exponent digits
		char exponentSign = '+'; // + or -
		boolean sawDotDot = false; // true if saw .. token
		char currentChar; // current character

		type = PascalTokenType.INTEGER;

		wholeDigits = unsignedIntegerDigits(textBuffer);
		if (type == PascalTokenType.ERROR) {
			return;
		}

		// is there a . ?
		// it could be a decimal point or start of a .. token

		currentChar = currentChar();
		if (currentChar == '.') {
			if (peekChar() == '.') {
				sawDotDot = true;
			} else {
				type = PascalTokenType.REAL;
				textBuffer.append(currentChar);
				currentChar = nextChar();

				fractionDigits = unsignedIntegerDigits(textBuffer);
				if (type == PascalTokenType.ERROR) {
					return;
				}
			}
		}

		// is there an exponent part ?
		// there cannot be an exponent part if we already saw a '..' token
		currentChar = currentChar();
		if (!sawDotDot && (currentChar == 'E') || (currentChar == 'e')) {
			type = PascalTokenType.REAL;
			textBuffer.append(currentChar);
			currentChar = nextChar();

			// exponent signt ?
			if ((currentChar == '+') || (currentChar == '-')) {
				textBuffer.append(currentChar);
				exponentSign = currentChar;
				currentChar = nextChar();
			}

			exponentDigits = unsignedIntegerDigits(textBuffer);
		}

		if (type == PascalTokenType.INTEGER) {
			int integerValue = computeIntegerValue(wholeDigits);
			if (type != PascalTokenType.ERROR) {
				value = new Integer(integerValue);
			}
		}

		// compute the value of a real number token
		else if (type == PascalTokenType.REAL) {
			float floatValue = computeFloatValue(wholeDigits, fractionDigits, exponentDigits, exponentSign);

			if (type != PascalTokenType.ERROR) {
				value = new Float(floatValue);
			}
		}

	}

	/**
	 * Extract and return the digits of an unsigned integer.
	 * 
	 * @param textBuffer
	 *            the buffer to append the token&apos;s characters.
	 * @return the string of digits.
	 * @throws Exception
	 *             if an error occurred.
	 */
	private String unsignedIntegerDigits(StringBuilder textBuffer) throws Exception {
		char currentChar = currentChar();

		// must have at least one digit
		if (!Character.isDigit(currentChar)) {
			type = PascalTokenType.ERROR;
			value = PascalErrorCode.INVALID_NUMBER;
			return null;
		}

		// extract the digits
		StringBuilder digits = new StringBuilder();
		while (Character.isDigit(currentChar)) {
			textBuffer.append(currentChar);
			digits.append(currentChar);
			currentChar = nextChar();
		}

		return digits.toString();
	}

	/**
	 * Compute and return the integer value of a string of digits. Check for
	 * overflow.
	 * 
	 * @param digits
	 *            the string of digits.
	 * @return the integer value.
	 */
	private int computeIntegerValue(String digits) {
		if (digits == null) {
			return 0;
		}

		int integerValue = 0;
		int prevValue = -1;
		int index = 0;

		// loop over digits to compute integer value as long as there is no overflow
		while ((index < digits.length()) && (integerValue >= prevValue)) {
			prevValue = integerValue;
			integerValue = 10 * integerValue + Character.getNumericValue(digits.charAt(index++));
		}

		if (integerValue > prevValue) {
			return integerValue;
		}

		else {
			type = PascalTokenType.ERROR;
			value = PascalErrorCode.RANGE_INTEGER;
			return 0;
		}
	}

	private float computeFloatValue(String wholeDigits, String fractionDigits, String exponentDigits,
			char exponentSign) {

		double floatValue = 0.0;
		int exponentValue = computeIntegerValue(exponentDigits);
		String digits = wholeDigits;

		// Negate the exponent if the exponent sign is &apos;-&apos;.
		if (exponentSign == '-') {
			exponentValue = -exponentValue;
		}
		// If there are any fraction digits, adjust the exponent value
		// and append the fraction digits.
		if (fractionDigits != null) {
			exponentValue -= fractionDigits.length();
			digits += fractionDigits;
		}
		// Check for a real number out of range error.
		if (Math.abs(exponentValue + wholeDigits.length()) > Float.MAX_EXPONENT) {
			type = PascalTokenType.ERROR;
			value = PascalErrorCode.RANGE_REAL;
			return 0.0f;
		}
		// Loop over the digits to compute the float value.
		int index = 0;
		while (index < digits.length()) {
			floatValue = 10 * floatValue + Character.getNumericValue(digits.charAt(index++));
		}
		// Adjust the float value based on the exponent value.
		if (exponentValue != 0) {
			floatValue *= Math.pow(10, exponentValue);
		}
		return (float) floatValue;

	}

}
