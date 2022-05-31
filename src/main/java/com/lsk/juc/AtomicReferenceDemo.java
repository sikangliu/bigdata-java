package com.lsk.juc;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceDemo {
    public static void main(String[] args) {
        User z3 = new User("zhangsan", 18);
        User l4 = new User("lisi", 20);
        AtomicReference<User> atomicReference = new AtomicReference<>();
        atomicReference.set(z3);

        System.out.println(atomicReference.compareAndSet(z3, l4) + "\t" + atomicReference.get().toString()); //true	User(userName=lisi, age=20)
        System.out.println(atomicReference.compareAndSet(z3, l4) + "\t" + atomicReference.get().toString()); //false User(userName=lisi, age=20)
    }
}

@Data
@AllArgsConstructor
class User {
    String name;
    Integer age;
}