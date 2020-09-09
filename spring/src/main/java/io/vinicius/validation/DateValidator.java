package io.vinicius.validation;

import java.util.Calendar;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator<ValidDate, Date> {

	public void initialize(ValidDate constraint) {
	}

	public boolean isValid(Date value, ConstraintValidatorContext context) {
		return isDateValid(value);

	}

	public boolean isDateValid(Date date) {
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setLenient(false);
			cal.setTime(date);
			try {
				cal.getTime();
				return true;
			} catch (Exception e) {
			}
		}
		return false;

	}
}