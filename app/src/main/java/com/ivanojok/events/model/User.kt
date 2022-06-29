package com.ivanojok.events.model


class User{
    var phone:String ?= null
    var firstName:String ?= null
    var lastName:String ?= null
    var nin:String ?= null
    var residence:String ?= null
    constructor(phone:String,
                firstName:String,
                lastName:String,
                nin:String,
                residence:String,){

        this.phone = phone
        this.firstName=firstName
        this.lastName=lastName
        this.nin=nin
        this.residence=residence
    }

    constructor()
}


class Event {
    var id:String ?= null
    var name:String ?= null
    var location:String ?= null
    var fee:String ?= null
    var image:String ?= null
    var description:String ?= null
    var organizer:String ?= null
    var time:String ?= null
    var contact:String ?= null

    constructor(id:String, name:String, location:String, fee:String, image:String, description:String, organizer:String, time:String, contact:String,){
        this.id = id
        this.name = name
        this.location = location
        this.fee = fee
        this.image = image
        this.description = description
        this.organizer = organizer
        this.time = time
        this.contact = contact
    }

    constructor()
}

class Ticket {
    var id:String ?= null
    var name:String ?= null
    var fee:String ?= null
    var image:String ?= null
    var method:String ?= null

    constructor(id:String, name:String, fee:String, image:String, method:String,){
        this.id = id
        this.name = name
        this.fee = fee
        this.image = image
        this.method = method
    }

    constructor()
}