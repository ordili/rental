package com.baocheng.rental.repertory.operatioin;

public interface OperationI<T1,T2> {

    public void add(T1 obj, T2 nums);

    public void delete(T1 obj);

    public Long selectQuantity(T1 obj);

    public void update(T1 obj, T2 nums);

    public boolean contains(T1 obj);
}
