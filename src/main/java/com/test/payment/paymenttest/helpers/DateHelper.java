package com.test.payment.paymenttest.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import com.test.payment.paymenttest.constants.CommonConstant;
import com.test.payment.paymenttest.constants.Message;
import com.test.payment.paymenttest.enums.ErrorHttpEnum;

public class DateHelper {
    /**
     * getDateHelper is method to generate Date from given string format,
     * formt using {@link com.test.payment.paymenttest.constants.CommonConstant#COMMON_STRING_DATE_FORMAT}
     * @param dateStr
     * @return
     * @throws CommonException
     */
    public static Date getDateFromStr(Optional<String> dateStr) throws CommonException {
        if (!dateStr.isEmpty()) {
            try {
                return new SimpleDateFormat(CommonConstant.COMMON_STRING_DATE_FORMAT)
                                .parse(dateStr.get());
            }
            catch (ParseException e) {
                throw new CommonException(Message.INVALID_REQUEST, ErrorHttpEnum.BAD_REQUEST);
            }
        }

        return null;
    }
    
}
