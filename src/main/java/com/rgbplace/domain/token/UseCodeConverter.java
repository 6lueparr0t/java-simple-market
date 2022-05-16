package com.rgbplace.domain.token;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class UseCodeConverter extends AbstractBaseEnumConverter<UseCode, String> {

    @Override
    protected UseCode[] getValueList() {
        return UseCode.values();
    }
}
