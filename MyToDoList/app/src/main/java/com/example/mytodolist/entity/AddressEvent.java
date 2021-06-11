package com.example.mytodolist.entity;

import android.location.Address;

/**
 * @program: MyToDoList
 * @description: Event with address
 */

// TODO Event with address
// Add MapFragment
public class AddressEvent{
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

}
