package com.example.mytodolist.entity;

import android.location.Address;

/**
 * @program: MyToDoList
 * @description:
 */
public class AddressEvent implements Comparable{
    Event event;
    Address address;

    public AddressEvent() {
    }

    public AddressEvent(Event event, Address address) {
        this.event = event;
        this.address = address;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public int compareTo(Object o) {
        int id = ((AddressEvent) o).getEvent().get_id();
        /* For Ascending order*/
        return this.getEvent().get_id() - id;
    }
}
