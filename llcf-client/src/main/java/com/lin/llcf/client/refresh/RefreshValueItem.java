package com.lin.llcf.client.refresh;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RefreshValueItem {

    private String key;

    private Object bean;

    private Class type;

    private Field targetField;

    private Object currentValue;

}
