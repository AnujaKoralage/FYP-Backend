package com.fyp.paymentservice.paymentservice.Utils;

import java.io.Serializable;

public interface Identifiable<T extends Serializable> {
    T getId();
}

